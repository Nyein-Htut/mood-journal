package com.example.moodjournal.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * A squashed, slightly lumpy "blob" character with a simple hand-drawn face -
 * the same friendly vocabulary as the reference mood-diary art, reused across
 * entry rows, the new-entry mascot, and empty states.
 */
@Composable
fun BlobCharacter(
    bucket: MoodBucket?,
    modifier: Modifier = Modifier,
    seed: Int = 0,
    outlined: Boolean = true
) {
    val bodyColor = bucket?.color ?: Color(0xFFCBD2D9)
    Canvas(modifier = modifier) {
        val blobPath = buildBlobPath(size.width, size.height, seed)
        drawPath(blobPath, color = bodyColor)
        if (outlined) {
            drawPath(blobPath, color = Color(0xFF2C2C2C).copy(alpha = 0.18f), style = Stroke(width = size.minDimension * 0.035f))
        }
        // Soft top-left highlight for a squishy, glossy feel.
        drawCircle(
            color = Color.White.copy(alpha = 0.35f),
            radius = size.minDimension * 0.16f,
            center = Offset(size.width * 0.32f, size.height * 0.3f)
        )
        drawBlobFace(this, bucket)
    }
}

/** Builds a gently irregular blob outline so characters don't look like plain circles. */
private fun buildBlobPath(w: Float, h: Float, seed: Int): Path {
    val cx = w / 2f
    val cy = h / 2f
    val baseR = kotlin.math.min(w, h) / 2f * 0.92f
    val points = 8
    val path = Path()

    fun bump(index: Int): Float {
        // Deterministic, gentle wobble per point so the shape feels hand-drawn but stable.
        val v = sin((seed + index) * 12.9898f) * 43758.5453f
        val frac = v - kotlin.math.floor(v)
        return 0.90f + frac * 0.16f
    }

    val outline = (0 until points).map { i ->
        val angle = (2 * Math.PI * i / points).toFloat()
        val r = baseR * bump(i)
        Offset(cx + r * cos(angle), cy + r * sin(angle))
    }

    path.moveTo((outline[0].x + outline.last().x) / 2f, (outline[0].y + outline.last().y) / 2f)
    for (i in outline.indices) {
        val current = outline[i]
        val next = outline[(i + 1) % outline.size]
        val mid = Offset((current.x + next.x) / 2f, (current.y + next.y) / 2f)
        path.quadraticBezierTo(current.x, current.y, mid.x, mid.y)
    }
    path.close()
    return path
}

private fun drawBlobFace(scope: DrawScope, bucket: MoodBucket?) {
    val w = scope.size.width
    val h = scope.size.height
    val ink = Color(0xFF2C2C2C)
    val eyeY = h * 0.46f
    val eyeOffsetX = w * 0.19f
    val eyeR = w * 0.05f
    val strokeW = (w * 0.045f).coerceAtLeast(1.5f)

    when (bucket) {
        MoodBucket.GREAT -> {
            drawCurvedEye(scope, Offset(w / 2 - eyeOffsetX, eyeY), eyeR * 1.3f, ink, strokeW, up = true)
            drawCurvedEye(scope, Offset(w / 2 + eyeOffsetX, eyeY), eyeR * 1.3f, ink, strokeW, up = true)
            val mouth = Path().apply {
                val c = Offset(w / 2f, h * 0.66f)
                moveTo(c.x - w * 0.16f, c.y)
                quadraticBezierTo(c.x, c.y + h * 0.18f, c.x + w * 0.16f, c.y)
            }
            scope.drawPath(mouth, ink, style = Stroke(strokeW, cap = StrokeCap.Round))
            blush(scope, ink)
        }
        MoodBucket.GOOD -> {
            drawCurvedEye(scope, Offset(w / 2 - eyeOffsetX, eyeY), eyeR * 1.1f, ink, strokeW, up = true)
            drawCurvedEye(scope, Offset(w / 2 + eyeOffsetX, eyeY), eyeR * 1.1f, ink, strokeW, up = true)
            val mouth = Path().apply {
                val c = Offset(w / 2f, h * 0.64f)
                moveTo(c.x - w * 0.12f, c.y)
                quadraticBezierTo(c.x, c.y + h * 0.08f, c.x + w * 0.12f, c.y)
            }
            scope.drawPath(mouth, ink, style = Stroke(strokeW, cap = StrokeCap.Round))
        }
        MoodBucket.NEUTRAL -> {
            scope.drawCircle(ink, radius = eyeR * 0.8f, center = Offset(w / 2 - eyeOffsetX, eyeY))
            scope.drawCircle(ink, radius = eyeR * 0.8f, center = Offset(w / 2 + eyeOffsetX, eyeY))
            scope.drawLine(
                ink,
                Offset(w / 2 - w * 0.09f, h * 0.63f),
                Offset(w / 2 + w * 0.09f, h * 0.63f),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )
        }
        MoodBucket.BAD -> {
            scope.drawCircle(ink, radius = eyeR * 0.8f, center = Offset(w / 2 - eyeOffsetX, eyeY))
            scope.drawCircle(ink, radius = eyeR * 0.8f, center = Offset(w / 2 + eyeOffsetX, eyeY))
            val mouth = Path().apply {
                val c = Offset(w / 2f, h * 0.68f)
                moveTo(c.x - w * 0.12f, c.y + h * 0.03f)
                quadraticBezierTo(c.x, c.y - h * 0.05f, c.x + w * 0.12f, c.y + h * 0.03f)
            }
            scope.drawPath(mouth, ink, style = Stroke(strokeW, cap = StrokeCap.Round))
        }
        MoodBucket.AWFUL -> {
            scope.drawCircle(ink, radius = eyeR * 0.8f, center = Offset(w / 2 - eyeOffsetX, eyeY))
            scope.drawCircle(ink, radius = eyeR * 0.8f, center = Offset(w / 2 + eyeOffsetX, eyeY))
            // Little tear drop under one eye.
            val tear = Path().apply {
                val tx = w / 2 + eyeOffsetX
                val ty = eyeY + h * 0.08f
                moveTo(tx, ty)
                quadraticBezierTo(tx + w * 0.045f, ty + h * 0.12f, tx, ty + h * 0.18f)
                quadraticBezierTo(tx - w * 0.045f, ty + h * 0.12f, tx, ty)
                close()
            }
            scope.drawPath(tear, Color(0xFF8FC7F2))
            val mouth = Path().apply {
                val c = Offset(w / 2f, h * 0.70f)
                moveTo(c.x - w * 0.13f, c.y + h * 0.05f)
                quadraticBezierTo(c.x, c.y - h * 0.08f, c.x + w * 0.13f, c.y + h * 0.05f)
            }
            scope.drawPath(mouth, ink, style = Stroke(strokeW, cap = StrokeCap.Round))
        }
        null -> {
            // Unanalyzed / pending entry - a simple sleepy "..." face.
            scope.drawCircle(ink.copy(alpha = 0.6f), radius = eyeR * 0.6f, center = Offset(w / 2 - eyeOffsetX, eyeY))
            scope.drawCircle(ink.copy(alpha = 0.6f), radius = eyeR * 0.6f, center = Offset(w / 2, eyeY))
            scope.drawCircle(ink.copy(alpha = 0.6f), radius = eyeR * 0.6f, center = Offset(w / 2 + eyeOffsetX, eyeY))
        }
    }
}

private fun drawCurvedEye(scope: DrawScope, center: Offset, radius: Float, color: Color, strokeW: Float, up: Boolean) {
    val path = Path().apply {
        if (up) {
            moveTo(center.x - radius, center.y)
            quadraticBezierTo(center.x, center.y - radius * 1.4f, center.x + radius, center.y)
        } else {
            moveTo(center.x - radius, center.y - radius)
            quadraticBezierTo(center.x, center.y + radius * 0.4f, center.x + radius, center.y - radius)
        }
    }
    scope.drawPath(path, color, style = Stroke(strokeW, cap = StrokeCap.Round))
}

private fun blush(scope: DrawScope, ink: Color) {
    val w = scope.size.width
    val h = scope.size.height
    val cheekY = h * 0.58f
    scope.drawCircle(Color(0xFFFF8FA3).copy(alpha = 0.45f), radius = w * 0.09f, center = Offset(w * 0.24f, cheekY))
    scope.drawCircle(Color(0xFFFF8FA3).copy(alpha = 0.45f), radius = w * 0.09f, center = Offset(w * 0.76f, cheekY))
}

/** Convenience default sizes so callers don't have to remember dp values. */
object BlobSizes {
    val Tiny = 28.dp
    val Small = 40.dp
    val Medium = 64.dp
    val Mascot = 108.dp
}
