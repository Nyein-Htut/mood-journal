package com.example.moodjournal.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0003\u001a4\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u000b2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002\u001a\u0018\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a*\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000fH\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001c"}, d2 = {"MoodBubble", "", "bucket", "Lcom/example/moodjournal/ui/components/MoodBucket;", "modifier", "Landroidx/compose/ui/Modifier;", "MoodJar", "entries", "", "Lcom/example/moodjournal/data/JournalEntry;", "onBubbleClick", "Lkotlin/Function1;", "computeBubbleLayout", "Lcom/example/moodjournal/ui/components/BubbleSpot;", "count", "", "drawFace", "scope", "Landroidx/compose/ui/graphics/drawscope/DrawScope;", "drawGlassJar", "fillColor", "Landroidx/compose/ui/graphics/Color;", "borderColor", "drawGlassJar-WkMS-hQ", "(Landroidx/compose/ui/graphics/drawscope/DrawScope;JJ)V", "pseudoRandom", "", "seed", "app_debug"})
public final class MoodJarKt {
    
    /**
     * Simple deterministic pseudo-random in [-1, 1], seeded by an int - keeps layout stable across recompositions.
     */
    private static final float pseudoRandom(int seed) {
        return 0.0F;
    }
    
    private static final java.util.List<com.example.moodjournal.ui.components.BubbleSpot> computeBubbleLayout(int count) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MoodJar(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.moodjournal.data.JournalEntry> entries, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.moodjournal.data.JournalEntry, kotlin.Unit> onBubbleClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MoodBubble(com.example.moodjournal.ui.components.MoodBucket bucket, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final void drawFace(androidx.compose.ui.graphics.drawscope.DrawScope scope, com.example.moodjournal.ui.components.MoodBucket bucket) {
    }
}