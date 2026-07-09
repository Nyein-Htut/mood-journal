package com.example.moodjournal.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002\u001a\u0014\u0010\u0007\u001a\u00020\b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u001a\u0014\u0010\t\u001a\u00020\b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"WINDOW_DAYS", "", "WINDOW_MS", "analyzedWindow", "", "Lcom/example/moodjournal/data/JournalEntry;", "entries", "calculateMoodStability", "Lcom/example/moodjournal/util/MoodStat;", "calculateStressLevel", "app_debug"})
public final class MoodAnalyticsKt {
    private static final long WINDOW_DAYS = 14L;
    private static final long WINDOW_MS = 1209600000L;
    
    private static final java.util.List<com.example.moodjournal.data.JournalEntry> analyzedWindow(java.util.List<com.example.moodjournal.data.JournalEntry> entries) {
        return null;
    }
    
    /**
     * Mood stability: how consistent the mood score has been over the last two weeks.
     * Computed from the standard deviation of daily mood scores (range -5..5) - a calm,
     * even week scores high; a week of big swings scores low.
     */
    @org.jetbrains.annotations.NotNull()
    public static final com.example.moodjournal.util.MoodStat calculateMoodStability(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.moodjournal.data.JournalEntry> entries) {
        return null;
    }
    
    /**
     * Stress level: a blend of how often recent entries land in the low moods, how intense
     * those low moods are, and how often the on-device concern check has fired.
     */
    @org.jetbrains.annotations.NotNull()
    public static final com.example.moodjournal.util.MoodStat calculateStressLevel(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.moodjournal.data.JournalEntry> entries) {
        return null;
    }
}