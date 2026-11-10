package com.codenytra.amme.dermaxrai.features.onboarding.components

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.features.onboarding.PAGE_COUNT
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

/** بيرسم الـ illustration على مقاس التصميم (350dp) وبيصغّره/يكبّره حسب عرض الشاشة. */
@Composable
fun ScaledIllustration(
    designWidth: Dp,
    designHeight: Dp,
    content: @Composable BoxScope.() -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val scale = maxWidth / designWidth
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(designHeight * scale),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .requiredSize(designWidth, designHeight)
                    .graphicsLayer(scaleX = scale, scaleY = scale),
                content = content
            )
        }
    }
}

@Composable
fun SkipPill(onClick: () -> Unit) {
    Panel(
        shape = CircleShape,
        color = MaterialTheme.derma.activeBadge,
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.onboarding_skip),
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp),
            style = dermaType(14, 20),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun LogoTile(logo: Painter?, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF7A3E2B)),
        contentAlignment = Alignment.Center
    ) {
        if (logo != null) {
            Image(painter = logo, contentDescription = null, modifier = Modifier.fillMaxSize())
        } else {
            Text(
                text = "D",
                style = dermaType(20, 24, FontWeight.Bold),
                color = Color(0xFFFFF8F6)
            )
        }
    }
}


@Composable
fun PageTitle(@StringRes text: Int, size: Int, lineHeight: Int) {
    Text(
        text = stringResource(text),
        style = dermaType(size, lineHeight, FontWeight.Bold),
        color = MaterialTheme.derma.heading
    )
}

@Composable
fun PageBody(@StringRes text: Int) {
    Text(
        text = stringResource(text),
        style = dermaType(14, 23),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

// ═══════════════════════════ Bottom controls ═══════════════════════════

@Composable
fun BottomControls(
    page: Int,
    horizontal: Dp,
    canFinish: Boolean,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onFinish: () -> Unit
) {
    val derma = MaterialTheme.derma

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontal)
            .padding(top = 4.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PagerDots(current = page)
        Spacer(Modifier.height(16.dp))

        when (page) {
            0 -> CtaButton(
                modifier = Modifier.fillMaxWidth().height(52.dp),
                onClick = onNext
            ) {
                Text(
                    text = stringResource(R.string.onboarding_next),
                    style = dermaType(16, 22, FontWeight.Medium),
                    color = derma.buttonText
                )
                Spacer(Modifier.width(10.dp))
                Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, tint = derma.buttonText, modifier = Modifier.size(20.dp))
            }

            1 -> Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Panel(
                    shape = CircleShape,
                    color = derma.card,
                    withShadow = true,
                    onClick = onPrevious,
                    modifier = Modifier
                        .width(117.dp)
                        .height(52.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, null, tint = derma.heading, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.onboarding_previous),
                            style = dermaType(15, 22, FontWeight.Medium),
                            color = derma.heading
                        )
                    }
                }
                CtaButton(
                    modifier = Modifier.weight(1f).height(52.dp),
                    onClick = onNext
                ) {
                    Text(
                        text = stringResource(R.string.onboarding_next),
                        style = dermaType(16, 22, FontWeight.Medium),
                        color = derma.buttonText
                    )
                    Spacer(Modifier.width(10.dp))
                    Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, tint = derma.buttonText, modifier = Modifier.size(20.dp))
                }
            }

            else -> {
                CtaButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .alpha(if (canFinish) 1f else 0.5f),
                    enabled = canFinish,
                    onClick = onFinish,
                    spaced = true
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Rounded.AutoAwesome, null, tint = derma.buttonText, modifier = Modifier.size(22.dp))
                        Text(
                            text = stringResource(R.string.onboarding_start),
                            style = dermaType(16, 22, FontWeight.Bold),
                            color = derma.buttonText
                        )
                    }
                    Icon(Icons.AutoMirrored.Rounded.ArrowForward, null, tint = derma.buttonText, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.height(16.dp))
                Panel(
                    shape = CircleShape,
                    color = derma.outlinePill,
                    onClick = onPrevious,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack, null,
                            tint = derma.brandText,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.onboarding_back),
                            style = dermaType(14, 20, FontWeight.Medium),
                            color = derma.brandText
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CtaButton(
    modifier: Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    spaced: Boolean = false,
    content: @Composable () -> Unit
) {
    val derma = MaterialTheme.derma

    Row(
        modifier = modifier
            .shadow(14.dp, CircleShape, ambientColor = derma.buttonGlow, spotColor = derma.buttonGlow)
            .clip(CircleShape)
            .background(Brush.horizontalGradient(listOf(derma.ctaStart, derma.ctaEnd)))
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = if (spaced) 40.dp else 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (spaced) Arrangement.SpaceBetween else Arrangement.Center
    ) {
        content()
    }
}

@Composable
fun PagerDots(current: Int) {
    val derma = MaterialTheme.derma

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(PAGE_COUNT) { index ->
            val active = index == current
            val width by animateDpAsState(if (active) 36.dp else 10.dp, label = "dot")
            Box(
                modifier = Modifier
                    .width(width)
                    .height(10.dp)
                    .clip(CircleShape)
                    .background(if (active) derma.emphasis else derma.pagerInactive)
            )
        }
    }
}