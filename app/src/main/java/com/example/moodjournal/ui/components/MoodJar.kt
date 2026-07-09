package com.example.moodjournal.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.moodjournal.data.JournalEntry
import com.example.moodjournal.ui.theme.*
import kotlin.math.sin

/** A fractional (0..1) position within the jar's visual bounds. */
private data class BubbleSpot(val xFrac: Float, val yFrac: Float)

/** Simple deterministic pseudo-random in [-1, 1], seeded by an int - keeps layout stable across recompositions. */
private fun pseudoRandom(seed: Int): Float {
    val v = sin(seed * 12.9898f) * 43758.5453f
    return (v - kotlin.math.floor(v)) * 2f - 1f
}

private fun computeBubbleLayout(count: Int): List<BubbleSpot> {
    val cols = 5
    val xMin = 0.28f
    val xMax = 0.72f
    val xStep = (xMax - xMin) / (cols - 1).coerceAtLeast(1)
    val yBottom = 0.80f
    val rowHeight = 0.12f

    return (0 until count).map { i ->
        val row = i / cols
        val col = i % cols
        val stagger = if (row % 2 == 1) xStep / 2f else 0f
        val jitterX = pseudoRandom(i * 7 + 3) * xStep * 0.2f
        val jitterY = pseudoRandom(i * 13 + 11) * rowHeight * 0.15f
        val x = (xMin + col * xStep + stagger + jitterX).coerceIn(0.22f, 0.78f)
        val y = (yBottom - row * rowHeight + jitterY).coerceIn(0.35f, 0.82f)
        BubbleSpot(x, y)
    }
}

@Composable
fun MoodJar(
    entries: List<JournalEntry>,
    onBubbleClick: (JournalEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val widthDp = maxWidth
        val heightDp = maxHeight

        val glassBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
        val glassFillColor = if (MaterialTheme.colorScheme.background == DarkPageBg1) {
            Color.White.copy(alpha = 0.12f)
        } else {
            Color.White.copy(alpha = 0.6f)
        }

        Canvas(modifier = Modifier.size(widthDp, heightDp)) {
            drawGlassJar(this, glassFillColor, glassBorderColor)
        }

        val recent = remember(entries) {
            entries.filter { it.moodScore != null }.sortedBy { it.timestamp }.takeLast(25)
        }
        val layout = remember(recent.size) { computeBubbleLayout(recent.size) }
        val bubbleSize = (widthDp / 7f)

        recent.forEachIndexed { index, entry ->
            val bucket = moodBucketFor(entry.moodScore) ?: return@forEachIndexed
            val spot = layout.getOrNull(index) ?: return@forEachIndexed

            val infiniteTransition = rememberInfiniteTransition(label = "bob_$index")
            val bob by infiniteTransition.animateFloat(
                initialValue = -1f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2200 + (index % 5) * 400, easing = LinearOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "bobVal_$index"
            )

            val xDp = widthDp * spot.xFrac - bubbleSize / 2
            val yDp = heightDp * spot.yFrac - bubbleSize / 2 + (bob * 3).dp

            MoodBubble(
                bucket = bucket,
                modifier = Modifier
                    .size(bubbleSize)
                    .offset(x = xDp, y = yDp)
                    .shadow(elevation = 1.dp, shape = CircleShape)
                    .clickable { onBubbleClick(entry) }
            )
        }
    }
}

@Composable
private fun MoodBubble(bucket: MoodBucket, modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        bucket.color.copy(alpha = 0.9f),
                        bucket.color.copy(alpha = 0.7f)
                    )
                )
            )
            .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)), CircleShape)
    ) {
        drawFace(this, bucket)
        
        // Soft gloss
        drawCircle(
            brush = Brush.verticalGradient(
                0.0f to Color.White.copy(alpha = 0.4f),
                0.4f to Color.Transparent
            ),
            radius = size.width / 2.2f,
            center = Offset(size.width * 0.35f, size.height * 0.35f)
        )
    }
}

private fun drawFace(scope: DrawScope, bucket: MoodBucket) {
    val w = scope.size.width
    val h = scope.size.height
    val eyeY = h * 0.46f
    val eyeOffsetX = w * 0.2f
    val eyeRadius = w * 0.05f
    val eyeColor = Color(0xFF2C2C2C).copy(alpha = 0.8f)

    // Eyes
    scope.drawCircle(eyeColor, radius = eyeRadius, center = Offset(w / 2 - eyeOffsetX, eyeY))
    scope.drawCircle(eyeColor, radius = eyeRadius, center = Offset(w / 2 + eyeOffsetX, eyeY))

    val mouthCenter = Offset(w / 2, h * 0.65f)
    val mouthWidth = w * 0.35f
    
    val mouthPath = Path().apply {
        when (bucket) {
            MoodBucket.GREAT -> {
                moveTo(mouthCenter.x - mouthWidth / 2, mouthCenter.y)
                quadraticBezierTo(mouthCenter.x, mouthCenter.y + h * 0.2f, mouthCenter.x + mouthWidth / 2, mouthCenter.y)
            }
            MoodBucket.GOOD -> {
                moveTo(mouthCenter.x - mouthWidth / 2, mouthCenter.y)
                quadraticBezierTo(mouthCenter.x, mouthCenter.y + h * 0.1f, mouthCenter.x + mouthWidth / 2, mouthCenter.y)
            }
            MoodBucket.NEUTRAL -> {
                moveTo(mouthCenter.x - mouthWidth / 2, mouthCenter.y + h * 0.05f)
                lineTo(mouthCenter.x + mouthWidth / 2, mouthCenter.y + h * 0.05f)
            }
            MoodBucket.BAD -> {
                moveTo(mouthCenter.x - mouthWidth / 2, mouthCenter.y + h * 0.1f)
                quadraticBezierTo(mouthCenter.x, mouthCenter.y, mouthCenter.x + mouthWidth / 2, mouthCenter.y + h * 0.1f)
            }
            MoodBucket.AWFUL -> {
                moveTo(mouthCenter.x - mouthWidth / 2, mouthCenter.y + h * 0.15f)
                quadraticBezierTo(mouthCenter.x, mouthCenter.y - h * 0.05f, mouthCenter.x + mouthWidth / 2, mouthCenter.y + h * 0.15f)
            }
        }
    }
    scope.drawPath(mouthPath, eyeColor, style = Stroke(w * 0.05f, cap = StrokeCap.Round))
}

private fun drawGlassJar(scope: DrawScope, fillColor: Color, borderColor: Color) {
    scope.run {
        val w = size.width
        val h = size.height

        // Jar shape parameters
        val jarTop = h * 0.15f
        val jarBottom = h * 0.90f
        val jarLeft = w * 0.12f
        val jarRight = w * 0.88f
        val cornerRadius = w * 0.22f

        val jarPath = Path().apply {
            moveTo(jarLeft + cornerRadius, jarTop)
            lineTo(jarRight - cornerRadius, jarTop)
            quadraticBezierTo(jarRight, jarTop, jarRight, jarTop + cornerRadius)
            lineTo(jarRight, jarBottom - cornerRadius)
            quadraticBezierTo(jarRight, jarBottom, jarRight - cornerRadius, jarBottom)
            lineTo(jarLeft + cornerRadius, jarBottom)
            quadraticBezierTo(jarLeft, jarBottom, jarLeft, jarBottom - cornerRadius)
            lineTo(jarLeft, jarTop + cornerRadius)
            quadraticBezierTo(jarLeft, jarTop, jarLeft + cornerRadius, jarTop)
            close()
        }

        // Shadow at the bottom
        drawOval(
            color = Color.Black.copy(alpha = 0.08f),
            topLeft = Offset(w * 0.20f, jarBottom - 2.dp.toPx()),
            size = Size(w * 0.6f, 10.dp.toPx())
        )

        // Glass body fill
        drawPath(
            jarPath,
            color = fillColor
        )
        
        // Glass body border
        drawPath(
            jarPath,
            color = borderColor,
            style = Stroke(width = 2.dp.toPx())
        )

        // Jar "neck" and "rim"
        val rimWidth = w * 0.5f
        val rimLeft = (w - rimWidth) / 2f
        drawRect(
            color = Color.White.copy(alpha = 0.4f),
            topLeft = Offset(rimLeft, jarTop - 10.dp.toPx()),
            size = Size(rimWidth, 12.dp.toPx()),
            style = Stroke(width = 1.dp.toPx())
        )

        // Cork Lid
        val lidWidth = w * 0.15f
        val lidHeight = h * 0.08f
        val lidX = (w - lidWidth) / 2f
        val lidY = jarTop - lidHeight + 5.dp.toPx()
        
        val lidPath = Path().apply {
            moveTo(lidX, lidY)
            lineTo(lidX + lidWidth, lidY)
            lineTo(lidX + lidWidth + 4.dp.toPx(), lidY - 10.dp.toPx())
            lineTo(lidX - 4.dp.toPx(), lidY - 10.dp.toPx())
            close()
        }
        
        drawPath(
            lidPath,
            color = Color(0xFF8B76E8) // Using Lav Deep as in HTML for lid color
        )
        
        // Side reflection on glass
        drawRect(
            color = Color.White.copy(alpha = 0.3f),
            topLeft = Offset(jarLeft + 10.dp.toPx(), jarTop + 20.dp.toPx()),
            size = Size(6.dp.toPx(), jarBottom - jarTop - 60.dp.toPx()),
            style = Fill
        )
    }
}
