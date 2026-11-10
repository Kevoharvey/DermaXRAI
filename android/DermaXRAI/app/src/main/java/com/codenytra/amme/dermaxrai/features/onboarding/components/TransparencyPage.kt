package com.codenytra.amme.dermaxrai.features.onboarding.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Insights
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.features.onboarding.PageColumn
import com.codenytra.amme.dermaxrai.ui.Dot
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

@Composable
fun TransparencyPage() {
    val derma = MaterialTheme.derma

    PageColumn(horizontal = 20.dp) {
        Spacer(Modifier.height(20.dp))
        AttributionHero()
        Spacer(Modifier.height(22.dp))
        PageTitle(R.string.onboarding_p2_title, size = 28, lineHeight = 34)
        Spacer(Modifier.height(8.dp))
        PageBody(R.string.onboarding_p2_body)
        Spacer(Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            FeatureChip(Icons.Rounded.LocalFireDepartment, R.string.onboarding_p2_feature1, derma.heading)
            FeatureChip(Icons.Rounded.Psychology, R.string.onboarding_p2_feature2, derma.peach)
        }
    }
}

@Composable
private fun FeatureChip(icon: ImageVector, @StringRes text: Int, color: Color) {
    Panel(shape = RoundedCornerShape(22.dp), color = MaterialTheme.derma.zoneBox) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Text(
                text = stringResource(text),
                style = dermaType(13, 20, FontWeight.Medium),
                color = color
            )
        }
    }
}

/** هيرو الصفحة التانية: وجه + بؤر حرارية + بطاقات توضيحية (رسم يدوي). */
@Composable
private fun AttributionHero() {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme
    val heat = colors.error
    val faceFill = derma.chipSoft
    val line = derma.peach.copy(alpha = 0.55f)
    val glow = derma.heroGlow
    val blob = colors.outline.copy(alpha = 0.22f)
    val bracket = derma.pagerInactive

    Panel(
        shape = RoundedCornerShape(28.dp),
        color = derma.card,
        withShadow = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        ScaledIllustration(designWidth = 350.dp, designHeight = 262.dp) {
            Canvas(Modifier.matchParentSize()) {
                val w = size.width
                val h = size.height

                // خلفيات ناعمة
                drawCircle(
                    brush = Brush.radialGradient(listOf(glow.copy(alpha = 0.9f), Color.Transparent), Offset(w - 20.dp.toPx(), 30.dp.toPx()), 120.dp.toPx()),
                    radius = 120.dp.toPx(), center = Offset(w - 20.dp.toPx(), 30.dp.toPx())
                )
                drawCircle(
                    brush = Brush.radialGradient(listOf(blob, Color.Transparent), Offset(45.dp.toPx(), h - 45.dp.toPx()), 105.dp.toPx()),
                    radius = 105.dp.toPx(), center = Offset(45.dp.toPx(), h - 45.dp.toPx())
                )

                // أقواس الزوايا
                val arc = 24.dp.toPx()
                val inset = 13.dp.toPx()
                val st = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                drawArc(bracket, 180f, 90f, false, Offset(inset, inset), Size(arc, arc), style = st)
                drawArc(bracket, 270f, 90f, false, Offset(w - inset - arc, inset), Size(arc, arc), style = st)
                drawArc(bracket, 0f, 90f, false, Offset(w - inset - arc, h - inset - arc), Size(arc, arc), style = st)
                drawArc(bracket, 90f, 90f, false, Offset(inset, h - inset - arc), Size(arc, arc), style = st)

                // الوجه (بروفايل)
                val d = 1.dp.toPx()
                val face = Path().apply {
                    moveTo(168 * d, 70 * d)
                    cubicTo(185 * d, 52 * d, 232 * d, 54 * d, 250 * d, 96 * d)
                    cubicTo(262 * d, 130 * d, 252 * d, 170 * d, 236 * d, 196 * d)
                    lineTo(244 * d, h)
                    lineTo(176 * d, h)
                    lineTo(180 * d, 208 * d)
                    cubicTo(166 * d, 204 * d, 152 * d, 196 * d, 150 * d, 186 * d)
                    cubicTo(142 * d, 184 * d, 140 * d, 176 * d, 144 * d, 170 * d)
                    lineTo(130 * d, 162 * d)
                    cubicTo(126 * d, 158 * d, 128 * d, 152 * d, 134 * d, 150 * d)
                    cubicTo(138 * d, 140 * d, 142 * d, 128 * d, 146 * d, 118 * d)
                    cubicTo(150 * d, 100 * d, 156 * d, 84 * d, 168 * d, 70 * d)
                    close()
                }
                drawPath(face, faceFill)

                // خطوط العين والحاجب
                val thin = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
                val brow = Path().apply {
                    moveTo(150 * d, 112 * d); quadraticTo(164 * d, 104 * d, 178 * d, 110 * d)
                }
                val eye = Path().apply {
                    moveTo(152 * d, 134 * d); quadraticTo(165 * d, 128 * d, 176 * d, 136 * d)
                }
                drawPath(brow, line, style = thin)
                drawPath(eye, line, style = thin)

                // بؤر الحرارة
                fun spot(cx: Float, cy: Float, r: Float, a: Float) = drawCircle(
                    brush = Brush.radialGradient(listOf(heat.copy(alpha = a), Color.Transparent), Offset(cx * d, cy * d), r * d),
                    radius = r * d, center = Offset(cx * d, cy * d)
                )
                spot(186f, 80f, 17f, 0.45f)
                spot(184f, 122f, 28f, 0.55f)
                spot(172f, 166f, 15f, 0.35f)
                drawCircle(heat, 4.dp.toPx(), Offset(184 * d, 122 * d))
                drawCircle(heat.copy(alpha = 0.6f), 8.dp.toPx(), Offset(184 * d, 122 * d), style = Stroke(1.dp.toPx()))

                // خط التوصيل المنقط
                val leader = Path().apply {
                    moveTo(192 * d, 122 * d)
                    quadraticTo(250 * d, 112 * d, 298 * d, 168 * d)
                }
                drawPath(
                    leader, line,
                    style = Stroke(width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f * d, 4f * d)))
                )
                drawCircle(derma.emphasis, 3.dp.toPx(), Offset(298 * d, 168 * d))
            }

            AttributionChip(
                modifier = Modifier.absoluteOffset(x = 13.dp, y = 14.dp),
                text = R.string.onboarding_p2_chip_neural,
                dot = derma.emphasis,
                bold = false
            )

            // البطاقات
            AttributionCard(
                modifier = Modifier.absoluteOffset(x = 40.dp, y = 73.dp),
                dot = colors.tertiary,
                title = stringResource(R.string.onboarding_p2_label_spot),
                subtitle = stringResource(R.string.onboarding_p2_label_spot_sub, 94),
                titleColor = derma.heading
            )
            AttributionCard(
                modifier = Modifier.absoluteOffset(x = 160.dp, y = 170.dp),
                dot = heat,
                title = stringResource(R.string.onboarding_p2_label_oily),
                subtitle = stringResource(R.string.onboarding_p2_label_oily_sub, 89),
                titleColor = heat
            )
        }
    }
}

@Composable
private fun AttributionChip(
    modifier: Modifier,
    @StringRes text: Int,
    icon: ImageVector? = null,
    dot: Color? = null,
    bold: Boolean
) {
    val derma = MaterialTheme.derma

    Panel(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        withShadow = true,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = null, tint = derma.iconTint, modifier = Modifier.size(16.dp))
            }
            if (dot != null) Dot(color = dot, size = 7.dp)
            Text(
                text = stringResource(text),
                style = dermaType(if (bold) 12 else 11, 16, if (bold) FontWeight.SemiBold else FontWeight.Normal),
                color = if (bold) derma.heading else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AttributionCard(
    modifier: Modifier,
    dot: Color,
    title: String,
    subtitle: String,
    titleColor: Color
) {
    Panel(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        withShadow = true,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Dot(color = dot, size = 9.dp)
            Column {
                Text(
                    text = title,
                    style = dermaType(12, 16, FontWeight.Bold),
                    color = titleColor
                )
                Text(
                    text = subtitle,
                    style = dermaType(11, 15),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

