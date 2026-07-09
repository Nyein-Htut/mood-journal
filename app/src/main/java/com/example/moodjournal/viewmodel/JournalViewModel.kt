package com.example.moodjournal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodjournal.data.AnalysisStatus
import com.example.moodjournal.auth.AuthRepository
import com.example.moodjournal.data.AppDatabase
import com.example.moodjournal.data.JournalEntry
import com.example.moodjournal.data.SyncRepository
import com.example.moodjournal.network.GroqRepository
import com.example.moodjournal.network.GroqResult
import com.example.moodjournal.util.CrisisSupport
import com.example.moodjournal.util.PreferencesManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).journalDao()
    private val repository = GroqRepository()
    private val authRepository = AuthRepository()
    private val syncRepository = SyncRepository()
    private val prefs = PreferencesManager(application)

    private val _entries = MutableStateFlow<List<JournalEntry>>(emptyList())
    val entries: StateFlow<List<JournalEntry>> = _entries.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(authRepository.isLoggedIn)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _isGuestMode = MutableStateFlow(prefs.isGuestMode)
    val isGuestMode: StateFlow<Boolean> = _isGuestMode.asStateFlow()

    private val _showCrisisSupport = MutableStateFlow(false)
    val showCrisisSupport: StateFlow<Boolean> = _showCrisisSupport.asStateFlow()

    private val _trendInsight = MutableStateFlow<String?>(null)
    val trendInsight: StateFlow<String?> = _trendInsight.asStateFlow()

    private val _isLoadingInsight = MutableStateFlow(false)
    val isLoadingInsight: StateFlow<Boolean> = _isLoadingInsight.asStateFlow()

    private val _isDarkMode = MutableStateFlow(prefs.isDarkMode)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _dailyAffirmation = MutableStateFlow<String?>(null)
    val dailyAffirmation: StateFlow<String?> = _dailyAffirmation.asStateFlow()

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak.asStateFlow()

    private val _weeklyVibe = MutableStateFlow("Mostly sunny with patches of calm ☀️")
    val weeklyVibe: StateFlow<String> = _weeklyVibe.asStateFlow()

    val wellbeingScore: StateFlow<Int> = _entries
        .map { list ->
            if (list.isEmpty()) 0
            else {
                val lastWeek = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
                val analyzed = list.filter { it.moodScore != null && it.timestamp >= lastWeek }
                if (analyzed.isEmpty()) 0
                else {
                    val avg = analyzed.mapNotNull { it.moodScore }.average().toFloat()
                    // Map -5..5 to 0..100
                    ((avg + 5) * 10).toInt().coerceIn(0, 100)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        viewModelScope.launch {
            dao.getAll().collect { list -> 
                _entries.value = list
                calculateStreak(list)
                updateWeeklyVibe(list)
            }
        }
        fetchDailyAffirmation()
    }

    private fun updateWeeklyVibe(list: List<JournalEntry>) {
        val key = apiKey
        if (key.isNullOrBlank()) return
        val lastWeek = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        val recent = list.filter { it.timestamp >= lastWeek }
        if (recent.isEmpty()) return
        
        viewModelScope.launch {
            when (val result = repository.getWeeklyVibe(recent, key)) {
                is GroqResult.Success -> _weeklyVibe.value = result.data
                else -> {}
            }
        }
    }

    private fun fetchDailyAffirmation() {
        val key = apiKey
        if (key.isNullOrBlank()) return
        viewModelScope.launch {
            when (val result = repository.getDailyAffirmation(key)) {
                is GroqResult.Success -> _dailyAffirmation.value = result.data
                else -> _dailyAffirmation.value = "Every emotion you feel is a part of your story. 🌸"
            }
        }
    }

    private fun calculateStreak(list: List<JournalEntry>) {
        if (list.isEmpty()) {
            _streak.value = 0
            return
        }
        val sorted = list.map { 
            val cal = Calendar.getInstance()
            cal.timeInMillis = it.timestamp
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            cal.timeInMillis
        }.distinct().sortedDescending()

        var currentStreak = 0
        val calToday = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        var expected = calToday.timeInMillis

        for (day in sorted) {
            if (day == expected) {
                currentStreak++
                expected -= TimeUnit.DAYS.toMillis(1)
            } else if (day < expected) {
                // Check if we missed today but had a streak until yesterday
                if (day == calToday.timeInMillis - TimeUnit.DAYS.toMillis(1) && currentStreak == 0) {
                   currentStreak = 1
                   expected = day - TimeUnit.DAYS.toMillis(1)
                } else {
                    break
                }
            }
        }
        _streak.value = currentStreak
    }

    var apiKey: String?
        get() = prefs.groqApiKey
        set(value) { prefs.groqApiKey = value }

    fun setDarkMode(enabled: Boolean) {
        prefs.isDarkMode = enabled
        _isDarkMode.value = enabled
    }

    fun setGuestMode(enabled: Boolean) {
        prefs.isGuestMode = enabled
        _isGuestMode.value = enabled
    }

    fun login(username: String, password: String, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.logIn(username, password)
            if (result.isSuccess) {
                _isLoggedIn.value = true
                syncFromCloud()
            }
            onResult(result)
        }
    }

    fun signup(username: String, password: String, onResult: (Result<Unit>) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.signUp(username, password)
            if (result.isSuccess) {
                _isLoggedIn.value = true
            }
            onResult(result)
        }
    }

    fun logout() {
        authRepository.logOut()
        _isLoggedIn.value = false
        _isGuestMode.value = false
        prefs.isGuestMode = false
    }

    private fun syncFromCloud() {
        val uid = authRepository.currentUserUId ?: return
        viewModelScope.launch {
            val cloudEntries = syncRepository.downloadEntries(uid)
            cloudEntries.forEach { dao.insert(it) } // Room will handle duplicates via ID
        }
    }

    fun dismissCrisisSupport() {
        _showCrisisSupport.value = false
    }

    /** Saves the entry immediately, then kicks off analysis in the background. */
   fun addEntry(text: String, backgroundId: String? = null, stickers: List<String> = emptyList()) {
        if (text.isBlank()) return
    
        if (CrisisSupport.containsConcerningLanguage(text)) {
            _showCrisisSupport.value = true
        }

            viewModelScope.launch {
                val entry = JournalEntry(
                    text = text,
                    timestamp = System.currentTimeMillis(),
                    backgroundId = backgroundId,
                    stickers = if (stickers.isEmpty()) null else stickers.joinToString(",")
                )
                val id = dao.insert(entry)
                analyzeEntry(id, text)
            }
    }

    fun retryAnalysis(entry: JournalEntry) {
        viewModelScope.launch { analyzeEntry(entry.id, entry.text) }
    }
    
    fun updateEntryText(entry: JournalEntry, newText: String) {
        if (newText.isBlank() || newText == entry.text) return
    
        if (CrisisSupport.containsConcerningLanguage(newText)) {
            _showCrisisSupport.value = true
        }
    
        viewModelScope.launch {
            val updated = entry.copy(
                text = newText,
                status = AnalysisStatus.PENDING,
                moodScore = null,
                primaryEmotion = null,
                themes = null,
                reflection = null,
                concernFlag = false
            )
            dao.update(updated)
            analyzeEntry(updated.id, newText)
        }
    }
    private suspend fun analyzeEntry(id: Long, text: String) {
        val key = apiKey
        if (key.isNullOrBlank()) {
            markStatus(id, AnalysisStatus.ERROR)
            return
        }

        when (val result = repository.analyzeEntry(text, key)) {
            is GroqResult.Success -> {
                val analysis = result.data
                if (analysis.concernFlag) _showCrisisSupport.value = true

                dao.getById(id)?.let { existing ->
                    val updated = existing.copy(
                        moodScore = analysis.moodScore,
                        primaryEmotion = analysis.primaryEmotion,
                        themes = analysis.themes.joinToString(","),
                        reflection = analysis.reflection,
                        concernFlag = analysis.concernFlag,
                        status = AnalysisStatus.DONE
                    )
                    dao.update(updated)
                    
                    // Sync to cloud if logged in
                    authRepository.currentUserUId?.let { uid ->
                        syncRepository.uploadEntry(uid, updated)
                    }
                }
            }
            is GroqResult.Failure -> markStatus(id, AnalysisStatus.ERROR)
        }
    }

    private suspend fun markStatus(id: Long, status: AnalysisStatus) {
        dao.getById(id)?.let { dao.update(it.copy(status = status)) }
    }

    fun deleteEntry(entry: JournalEntry) {
        viewModelScope.launch { dao.delete(entry) }
    }

    /** Last 30 days of entries, oldest first - used for the trend chart. */
    fun recentEntriesForTrend(): List<JournalEntry> {
        val cutoff = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30)
        return _entries.value.filter { it.timestamp >= cutoff }.sortedBy { it.timestamp }
    }

    fun requestTrendInsight() {
        val key = apiKey
        if (key.isNullOrBlank()) {
            _trendInsight.value = "Add your Groq API key in Settings to unlock insights."
            return
        }
        val analyzed = recentEntriesForTrend().filter { it.status == AnalysisStatus.DONE }
        viewModelScope.launch {
            _isLoadingInsight.value = true
            when (val result = repository.getTrendInsight(analyzed, key)) {
                is GroqResult.Success -> _trendInsight.value = result.data
                is GroqResult.Failure -> _trendInsight.value = "Couldn't fetch an insight: ${result.message}"
            }
            _isLoadingInsight.value = false
        }
    }
}
