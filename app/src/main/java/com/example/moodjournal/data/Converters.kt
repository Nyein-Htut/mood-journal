package com.example.moodjournal.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStatus(status: AnalysisStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): AnalysisStatus = AnalysisStatus.valueOf(value)
}
