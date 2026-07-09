package com.example.moodjournal.ui.components;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a0\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a\"\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a\u001a\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002\u001aB\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\tH\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001f\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006 "}, d2 = {"BlobCharacter", "", "bucket", "Lcom/example/moodjournal/ui/components/MoodBucket;", "modifier", "Landroidx/compose/ui/Modifier;", "seed", "", "outlined", "", "blush", "scope", "Landroidx/compose/ui/graphics/drawscope/DrawScope;", "ink", "Landroidx/compose/ui/graphics/Color;", "blush-4WTKRHQ", "(Landroidx/compose/ui/graphics/drawscope/DrawScope;J)V", "buildBlobPath", "Landroidx/compose/ui/graphics/Path;", "w", "", "h", "drawBlobFace", "drawCurvedEye", "center", "Landroidx/compose/ui/geometry/Offset;", "radius", "color", "strokeW", "up", "drawCurvedEye-IfIb9nM", "(Landroidx/compose/ui/graphics/drawscope/DrawScope;JFJFZ)V", "app_debug"})
public final class BlobCharacterKt {
    
    /**
     * A squashed, slightly lumpy "blob" character with a simple hand-drawn face -
     * the same friendly vocabulary as the reference mood-diary art, reused across
     * entry rows, the new-entry mascot, and empty states.
     */
    @androidx.compose.runtime.Composable()
    public static final void BlobCharacter(@org.jetbrains.annotations.Nullable()
    com.example.moodjournal.ui.components.MoodBucket bucket, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, int seed, boolean outlined) {
    }
    
    /**
     * Builds a gently irregular blob outline so characters don't look like plain circles.
     */
    private static final androidx.compose.ui.graphics.Path buildBlobPath(float w, float h, int seed) {
        return null;
    }
    
    private static final void drawBlobFace(androidx.compose.ui.graphics.drawscope.DrawScope scope, com.example.moodjournal.ui.components.MoodBucket bucket) {
    }
}