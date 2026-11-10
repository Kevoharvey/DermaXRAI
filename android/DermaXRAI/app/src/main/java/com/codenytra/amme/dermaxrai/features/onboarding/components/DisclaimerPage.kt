package com.codenytra.amme.dermaxrai.features.onboarding.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.MedicalServices
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.features.onboarding.PageColumn
import com.codenytra.amme.dermaxrai.ui.Dot
import com.codenytra.amme.dermaxrai.ui.IconCircle
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

@Composable
fun DisclaimerPage(
    acknowledged: Boolean,
    onAcknowledgedChange: (Boolean) -> Unit
) {

    PageColumn(horizontal = 20.dp) {

        Spacer(Modifier.height(26.dp))
        CertifiedHero()
        Spacer(Modifier.height(21.dp))
        PageTitle(R.string.onboarding_p3_title, size = 20, lineHeight = 28)
        Spacer(Modifier.height(8.dp))
        PageBody(R.string.onboarding_p3_body)
        Spacer(Modifier.height(12.dp))

        NoticeCard(checked = acknowledged, onCheckedChange = onAcknowledgedChange)
//        Spacer(Modifier.height(14.dp))
//        ConsentCard(checked = acknowledged, onCheckedChange = onAcknowledgedChange)
    }
}

@Composable
private fun NoticeCard(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Panel(
        shape = RoundedCornerShape(20.dp),
        color = derma.noticeCard,
        borderColor = derma.noticeBorder,
        modifier = Modifier.fillMaxWidth().clickable(role = Role.Checkbox) { onCheckedChange(!checked) }
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconCircle(
                    icon = Icons.Rounded.MedicalServices,
                    size = 32.dp,
                    container = derma.noticeCircle,
                    tint = derma.noticeTitle,
                    iconSize = 18.dp,
                    borderColor = derma.noticeBorder
                )
                Text(
                    text = stringResource(R.string.onboarding_p3_notice_title),
                    style = dermaType(14, 20, FontWeight.Bold),
                    color = derma.noticeTitle
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.onboarding_p3_notice_body),
                modifier = Modifier.padding(start = 40.dp),
                style = dermaType(12, 20),
                color = derma.noticeBody
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (checked) derma.emphasis else derma.pagerInactive.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center
                ) {
                    if (checked) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            tint = colors.onPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                    Text(
                        text = stringResource(R.string.onboarding_p3_consent_title),
                        style = dermaType(16, 22, FontWeight.Medium),
                        color = colors.onSurface
                    )
            }
        }
    }
}

@Composable
private fun ConsentCard(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Panel(
        shape = RoundedCornerShape(20.dp),
        color = derma.consentCard,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Checkbox) { onCheckedChange(!checked) }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (checked) derma.emphasis else derma.pagerInactive.copy(alpha = 0.35f)),
                contentAlignment = Alignment.Center
            ) {
                if (checked) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = colors.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.onboarding_p3_consent_title),
                    style = dermaType(16, 22, FontWeight.Medium),
                    color = colors.onSurface
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = stringResource(R.string.onboarding_p3_consent_desc),
                    style = dermaType(12, 18),
                    color = colors.onSurfaceVariant
                )
            }
        }
    }
}

/** هيرو الصفحة التالتة: مدارات + بطاقة "معتمد إرشادياً". */
@Composable
private fun CertifiedHero() {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme
    val orbit = colors.tertiary
    val blob = colors.outline.copy(alpha = 0.22f)

    Panel(
        shape = RoundedCornerShape(32.dp),
        color = derma.heroBase,
        modifier = Modifier.fillMaxWidth()
    ) {
        ScaledIllustration(designWidth = 350.dp, designHeight = 270.dp) {
            Canvas(Modifier.matchParentSize()) {
                val w = size.width
                val h = size.height
                val d = 1.dp.toPx()

                drawCircle(
                    brush = Brush.radialGradient(listOf(derma.heroGlow, Color.Transparent), Offset(w - 30 * d, 40 * d), 150 * d),
                    radius = 150 * d, center = Offset(w - 30 * d, 40 * d)
                )
                drawCircle(
                    brush = Brush.radialGradient(listOf(blob, Color.Transparent), Offset(50 * d, h - 40 * d), 120 * d),
                    radius = 120 * d, center = Offset(50 * d, h - 40 * d)
                )

                val c = Offset(w / 2f, 111 * d)
                drawCircle(
                    orbit.copy(alpha = 0.5f), 81 * d, c,
                    style = Stroke(1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f * d, 6f * d)))
                )
                drawCircle(orbit.copy(alpha = 0.35f), 68 * d, c, style = Stroke(2.dp.toPx()))
                listOf(
                    Offset(c.x, c.y - 68 * d), Offset(c.x + 68 * d, c.y),
                    Offset(c.x, c.y + 68 * d), Offset(c.x - 68 * d, c.y)
                ).forEach { drawCircle(orbit, 4 * d, it) }
            }

            // البطاقة المركزية
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .absoluteOffset(y = 48.dp)
                    .size(width = 110.dp, height = 127.dp)
                    .shadow(14.dp, RoundedCornerShape(28.dp), ambientColor = derma.shadow, spotColor = derma.shadow)
                    .clip(RoundedCornerShape(28.dp))
                    .background(derma.heroTile)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(derma.heroTileCircle),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Eco,
                        contentDescription = null,
                        tint = derma.peach,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .absoluteOffset(x = 20.dp, y = 62.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(derma.emphasis),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = colors.onPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.onboarding_p3_hero_badge),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    style = dermaType(12, 16, FontWeight.Bold),
                    color = derma.heading
                )
            }

            // زخارف المدار
            Icon(
                Icons.Rounded.AutoAwesome, null, tint = derma.peach,
                modifier = Modifier
                    .absoluteOffset(x = 230.dp, y = 36.dp)
                    .size(20.dp)
            )
            Icon(
                Icons.Rounded.Spa, null, tint = derma.peach,
                modifier = Modifier
                    .absoluteOffset(x = 100.dp, y = 58.dp)
                    .size(16.dp)
            )
            Icon(
                Icons.Rounded.Verified, null, tint = derma.green,
                modifier = Modifier
                    .absoluteOffset(x = 222.dp, y = 162.dp)
                    .size(20.dp)
            )

            // الشريحة السفلية
            Panel(
                shape = CircleShape,
                color = derma.heroTile,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Dot(color = derma.peach)
                    Text(
                        text = stringResource(R.string.onboarding_p3_hero_chip),
                        style = dermaType(11, 16),
                        color = colors.onSurfaceVariant
                    )
                }
            }
        }
    }
}