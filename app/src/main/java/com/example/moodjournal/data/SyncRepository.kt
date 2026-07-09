package com.example.moodjournal.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SyncRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun uploadEntry(userId: String, entry: JournalEntry) {
        try {
            db.collection("users").document(userId)
                .collection("entries").document(entry.id.toString())
                .set(entry).await()
        } catch (e: Exception) {
            // Log error
        }
    }

    suspend fun downloadEntries(userId: String): List<JournalEntry> {
        return try {
            val snapshot = db.collection("users").document(userId)
                .collection("entries").get().await()
            snapshot.toObjects(JournalEntry::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
