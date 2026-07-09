package com.example.moodjournal.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000X\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a0\u0010\u0007\u001a\u00020\u00012\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a\u001e\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a\u001e\u0010\u0010\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a2\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a&\u0010\u0018\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a.\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\t2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a0\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010 \u001a\u00020!H\u0003\u001a,\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\t2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010%2\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001aD\u0010&\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00062\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u001a(\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u00030*2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006+"}, d2 = {"DailyRhythmSection", "", "entries", "", "Lcom/example/moodjournal/data/JournalEntry;", "isDarkMode", "", "GentleInsightSection", "insight", "", "isLoadingInsight", "onRequestInsight", "Lkotlin/Function0;", "MoodLineChart", "data", "Lcom/example/moodjournal/ui/screens/DailyMood;", "MoodMixChart", "MoodMixLegendItem", "label", "percent", "color", "Landroidx/compose/ui/graphics/Color;", "MoodMixLegendItem-9LQNqLg", "(Ljava/lang/String;Ljava/lang/String;JZ)V", "MoodSwingsCard", "range", "RhythmRow", "emoji", "scores", "", "SmallTrendStat", "value", "modifier", "Landroidx/compose/ui/Modifier;", "TimeRangeSelector", "selected", "onRangeSelected", "Lkotlin/Function1;", "TrendsScreen", "onBack", "buildDailyAverages", "calculateRhythm", "", "app_debug"})
public final class TrendsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TrendsScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.moodjournal.data.JournalEntry> entries, @org.jetbrains.annotations.Nullable()
    java.lang.String insight, boolean isLoadingInsight, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRequestInsight) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TimeRangeSelector(java.lang.String selected, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRangeSelected, boolean isDarkMode) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MoodSwingsCard(java.util.List<com.example.moodjournal.data.JournalEntry> entries, java.lang.String range, boolean isDarkMode) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MoodLineChart(java.util.List<com.example.moodjournal.ui.screens.DailyMood> data, boolean isDarkMode) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SmallTrendStat(java.lang.String emoji, java.lang.String value, java.lang.String label, boolean isDarkMode, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MoodMixChart(java.util.List<com.example.moodjournal.data.JournalEntry> entries, boolean isDarkMode) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void GentleInsightSection(java.lang.String insight, boolean isLoadingInsight, kotlin.jvm.functions.Function0<kotlin.Unit> onRequestInsight, boolean isDarkMode) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DailyRhythmSection(java.util.List<com.example.moodjournal.data.JournalEntry> entries, boolean isDarkMode) {
    }
    
    private static final java.util.Map<java.lang.String, java.util.List<java.lang.Integer>> calculateRhythm(java.util.List<com.example.moodjournal.data.JournalEntry> entries) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RhythmRow(java.lang.String emoji, java.lang.String label, java.util.List<java.lang.Integer> scores, boolean isDarkMode) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<com.example.moodjournal.ui.screens.DailyMood> buildDailyAverages(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.moodjournal.data.JournalEntry> entries) {
        return null;
    }
}