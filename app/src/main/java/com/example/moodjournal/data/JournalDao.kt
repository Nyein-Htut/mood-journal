package com.example.moodjournal.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert
    suspend fun insert(entry: JournalEntry): Long

    @Update
    suspend fun update(entry: JournalEntry)

    @Delete
    suspend fun delete(entry: JournalEntry)

    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAll(): Flow<List<JournalEntry>>

    @Query("SELECT * FROM journal_entries WHERE id = :id")
    suspend fun getById(id: Long): JournalEntry?

    @Query("SELECT * FROM journal_entries WHERE timestamp >= :since ORDER BY timestamp ASC")
    suspend fun getSince(since: Long): List<JournalEntry>
}
