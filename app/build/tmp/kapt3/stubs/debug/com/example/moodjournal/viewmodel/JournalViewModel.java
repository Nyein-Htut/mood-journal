package com.example.moodjournal.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020\u0007J\u001e\u0010<\u001a\u00020:2\u0006\u0010=\u001a\u00020>2\u0006\u0010;\u001a\u00020\u0007H\u0082@\u00a2\u0006\u0002\u0010?J\u0016\u0010@\u001a\u00020:2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u000e\u0010B\u001a\u00020:2\u0006\u0010C\u001a\u00020\nJ\u0006\u0010D\u001a\u00020:J\b\u0010E\u001a\u00020:H\u0002J0\u0010F\u001a\u00020:2\u0006\u0010G\u001a\u00020\u00072\u0006\u0010H\u001a\u00020\u00072\u0018\u0010I\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020:0K\u0012\u0004\u0012\u00020:0JJ\u0006\u0010L\u001a\u00020:J\u001e\u0010M\u001a\u00020:2\u0006\u0010=\u001a\u00020>2\u0006\u0010N\u001a\u00020OH\u0082@\u00a2\u0006\u0002\u0010PJ\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0006\u0010R\u001a\u00020:J\u000e\u0010S\u001a\u00020:2\u0006\u0010C\u001a\u00020\nJ\u000e\u0010T\u001a\u00020:2\u0006\u0010U\u001a\u00020\fJ\u000e\u0010V\u001a\u00020:2\u0006\u0010U\u001a\u00020\fJ0\u0010W\u001a\u00020:2\u0006\u0010G\u001a\u00020\u00072\u0006\u0010H\u001a\u00020\u00072\u0018\u0010I\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020:0K\u0012\u0004\u0012\u00020:0JJ\b\u0010X\u001a\u00020:H\u0002J\u0016\u0010Y\u001a\u00020:2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R(\u0010\u0016\u001a\u0004\u0018\u00010\u00072\b\u0010\u0015\u001a\u0004\u0018\u00010\u00078F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010 R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010 R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010 R\u0017\u0010\'\u001a\b\u0012\u0004\u0012\u00020\f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010 R\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010 R\u000e\u0010)\u001a\u00020*X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010-\u001a\b\u0012\u0004\u0012\u00020\f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010 R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00120\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010 R\u000e\u00101\u001a\u000202X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u00103\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010 R\u0017\u00105\u001a\b\u0012\u0004\u0012\u00020\u00070\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010 R\u0017\u00107\u001a\b\u0012\u0004\u0012\u00020\u00120\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010 \u00a8\u0006Z"}, d2 = {"Lcom/example/moodjournal/viewmodel/JournalViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_dailyAffirmation", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_entries", "", "Lcom/example/moodjournal/data/JournalEntry;", "_isDarkMode", "", "_isGuestMode", "_isLoadingInsight", "_isLoggedIn", "_showCrisisSupport", "_streak", "", "_trendInsight", "_weeklyVibe", "value", "apiKey", "getApiKey", "()Ljava/lang/String;", "setApiKey", "(Ljava/lang/String;)V", "authRepository", "Lcom/example/moodjournal/auth/AuthRepository;", "dailyAffirmation", "Lkotlinx/coroutines/flow/StateFlow;", "getDailyAffirmation", "()Lkotlinx/coroutines/flow/StateFlow;", "dao", "Lcom/example/moodjournal/data/JournalDao;", "entries", "getEntries", "isDarkMode", "isGuestMode", "isLoadingInsight", "isLoggedIn", "prefs", "Lcom/example/moodjournal/util/PreferencesManager;", "repository", "Lcom/example/moodjournal/network/GroqRepository;", "showCrisisSupport", "getShowCrisisSupport", "streak", "getStreak", "syncRepository", "Lcom/example/moodjournal/data/SyncRepository;", "trendInsight", "getTrendInsight", "weeklyVibe", "getWeeklyVibe", "wellbeingScore", "getWellbeingScore", "addEntry", "", "text", "analyzeEntry", "id", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "calculateStreak", "list", "deleteEntry", "entry", "dismissCrisisSupport", "fetchDailyAffirmation", "login", "username", "password", "onResult", "Lkotlin/Function1;", "Lkotlin/Result;", "logout", "markStatus", "status", "Lcom/example/moodjournal/data/AnalysisStatus;", "(JLcom/example/moodjournal/data/AnalysisStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recentEntriesForTrend", "requestTrendInsight", "retryAnalysis", "setDarkMode", "enabled", "setGuestMode", "signup", "syncFromCloud", "updateWeeklyVibe", "app_debug"})
public final class JournalViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.moodjournal.data.JournalDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.moodjournal.network.GroqRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.moodjournal.auth.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.moodjournal.data.SyncRepository syncRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.moodjournal.util.PreferencesManager prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.moodjournal.data.JournalEntry>> _entries = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.moodjournal.data.JournalEntry>> entries = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoggedIn = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoggedIn = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isGuestMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGuestMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _showCrisisSupport = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> showCrisisSupport = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _trendInsight = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> trendInsight = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoadingInsight = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoadingInsight = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isDarkMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isDarkMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _dailyAffirmation = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> dailyAffirmation = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _streak = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> streak = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _weeklyVibe = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> weeklyVibe = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> wellbeingScore = null;
    
    public JournalViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.moodjournal.data.JournalEntry>> getEntries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoggedIn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isGuestMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getShowCrisisSupport() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getTrendInsight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoadingInsight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isDarkMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getDailyAffirmation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getStreak() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getWeeklyVibe() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getWellbeingScore() {
        return null;
    }
    
    private final void updateWeeklyVibe(java.util.List<com.example.moodjournal.data.JournalEntry> list) {
    }
    
    private final void fetchDailyAffirmation() {
    }
    
    private final void calculateStreak(java.util.List<com.example.moodjournal.data.JournalEntry> list) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getApiKey() {
        return null;
    }
    
    public final void setApiKey(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    public final void setDarkMode(boolean enabled) {
    }
    
    public final void setGuestMode(boolean enabled) {
    }
    
    public final void login(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.Result<kotlin.Unit>, kotlin.Unit> onResult) {
    }
    
    public final void signup(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.Result<kotlin.Unit>, kotlin.Unit> onResult) {
    }
    
    public final void logout() {
    }
    
    private final void syncFromCloud() {
    }
    
    public final void dismissCrisisSupport() {
    }
    
    /**
     * Saves the entry immediately, then kicks off analysis in the background.
     */
    public final void addEntry(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void retryAnalysis(@org.jetbrains.annotations.NotNull()
    com.example.moodjournal.data.JournalEntry entry) {
    }
    
    private final java.lang.Object analyzeEntry(long id, java.lang.String text, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object markStatus(long id, com.example.moodjournal.data.AnalysisStatus status, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void deleteEntry(@org.jetbrains.annotations.NotNull()
    com.example.moodjournal.data.JournalEntry entry) {
    }
    
    /**
     * Last 30 days of entries, oldest first - used for the trend chart.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.moodjournal.data.JournalEntry> recentEntriesForTrend() {
        return null;
    }
    
    public final void requestTrendInsight() {
    }
}