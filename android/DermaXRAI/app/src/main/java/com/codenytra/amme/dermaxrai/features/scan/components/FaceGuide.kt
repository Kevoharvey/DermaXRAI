package com.codenytra.amme.dermaxrai.features.scan.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.features.scan.Peach

@Composable
fun FaceGuide(modifier: Modifier, showFaceLines: Boolean) {
    val bracket = Color(0xFFFFD9CC)
    val dash = Peach

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val d = 1.dp.toPx()
        val ovalPad = 24 * d

        // بيضاوي منقط
        drawOval(
            color = dash,
            topLeft = Offset(ovalPad, 0f),
            size = Size(w - ovalPad * 2, h),
            style = Stroke(
                2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f * d, 6f * d))
            )
        )

        // أقواس الزوايا
        val arm = 26 * d
        val st = Stroke(3.dp.toPx(), cap = StrokeCap.Round)
        val corners = listOf(
            Triple(Offset(0f, 0f), 1f, 1f), Triple(Offset(w, 0f), -1f, 1f),
            Triple(Offset(0f, h), 1f, -1f), Triple(Offset(w, h), -1f, -1f)
        )
        corners.forEach { (o, sx, sy) ->
            val p = Path().apply {
                moveTo(o.x, o.y + sy * arm); lineTo(o.x, o.y); lineTo(o.x + sx * arm, o.y)
            }
            drawPath(p, bracket, style = st)
        }

        if (showFaceLines) {
            val cx = w / 2
            val thin = Stroke(
                1.2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f * d, 3f * d))
            )
            drawOval(
                color = Color.White.copy(alpha = 0.35f),
                topLeft = Offset(cx - 60 * d, h * 0.22f),
                size = Size(120 * d, h * 0.62f),
                style = thin
            )
            val eyeY = h * 0.5f
            drawLine(
                Color.White.copy(alpha = 0.3f),
                Offset(cx - 70 * d, eyeY),
                Offset(cx + 70 * d, eyeY),
                1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f * d, 3f * d))
            )
            drawCircle(dash, 3.dp.toPx(), Offset(cx - 44 * d, eyeY))
            drawCircle(dash, 3.dp.toPx(), Offset(cx + 44 * d, eyeY))
            drawLine(
                Color.White.copy(alpha = 0.4f),
                Offset(cx, eyeY - 18 * d),
                Offset(cx, eyeY + 40 * d),
                1.dp.toPx()
            )
            val smile = Path().apply {
                moveTo(cx - 20 * d, h * 0.7f)
                quadraticTo(
                    cx,
                    h * 0.73f,
                    cx + 20 * d,
                    h * 0.7f
                )
            }
            drawPath(
                smile,
                Color.White.copy(alpha = 0.45f),
                style = Stroke(1.2.dp.toPx(), cap = StrokeCap.Round)
            )
            // خط المسح
            drawLine(
                Brush.horizontalGradient(listOf(Color.Transparent, dash, Color.Transparent)),
                Offset(0f, 18 * d), Offset(w, 18 * d), 2.dp.toPx()
            )
        }
    }
}