package com.codenytra.amme.dermaxrai.features.scan.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CenterFocusStrong
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.NoPhotography
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.icons.rounded.WbTwilight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.features.scan.BottomAreaHeight
import com.codenytra.amme.dermaxrai.features.scan.ErrorChip
import com.codenytra.amme.dermaxrai.features.scan.Peach
import com.codenytra.amme.dermaxrai.ui.Dot
import com.codenytra.amme.dermaxrai.ui.IconCircle
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

@Composable
fun BadSheet(
    modifier: Modifier = Modifier,
    showFlash: Boolean,
    onFlashRetry: () -> Unit,
    onGallery: () -> Unit
) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(derma.privacyCard.copy(alpha = 0.6f))
            .navigationBarsPadding()
            .height(BottomAreaHeight)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // العنوان + الوصف
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconCircle(
                Icons.Rounded.WbTwilight,
                40.dp,
                derma.chipStrong,
                derma.peach,
                iconSize = 20.dp
            )
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.camera_bad_title),
                        style = dermaType(16, 22, FontWeight.Medium),
                        color = colors.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Panel(shape = CircleShape, color = derma.chipSoft.copy(alpha = 0.6f)) {
                        Text(
                            text = stringResource(R.string.camera_bad_chip),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                            style = dermaType(10, 14),
                            color = derma.peach
                        )
                    }
                }
                Text(
                    text = stringResource(R.string.camera_bad_desc),
                    style = dermaType(12, 16),
                    color = colors.onSurfaceVariant
                )
            }
        }

        // النصايح (ثابتة)
        Panel(
            shape = RoundedCornerShape(20.dp),
            color = derma.zoneBox.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Rounded.Lightbulb,
                        null,
                        tint = derma.peach,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(R.string.camera_tips_title),
                        style = dermaType(13, 18, FontWeight.Medium),
                        color = derma.heading
                    )
                }
                NumberedTip(1, R.string.camera_tip_1)
                NumberedTip(2, R.string.camera_tip_2)
                NumberedTip(3, R.string.camera_tip_3)
            }
        }

        // الأزرار: فلاش (للخلفية بس) + جاليري
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (showFlash) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .clip(CircleShape)
                        .background(Brush.horizontalGradient(listOf(derma.ctaStart, derma.ctaEnd)))
                        .clickable(role = Role.Button, onClick = onFlashRetry),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Rounded.FlashOn,
                        null,
                        tint = derma.buttonText,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.camera_flash_retry),
                        style = dermaType(13, 18, FontWeight.Bold),
                        color = derma.buttonText
                    )
                }
            }
            SecondaryButton(
                Icons.Rounded.PhotoLibrary,
                R.string.camera_pick_gallery,
                onGallery,
                Modifier.weight(1f)
            )
        }

        if (!showFlash) {
            Text(
                text = stringResource(R.string.camera_flip_for_flash),
                style = dermaType(11, 16),
                color = colors.onSurfaceVariant
            )
        }
    }
}


@Composable
fun BadOverlay(lightPercent: Int, distanceCm: Int) {

    Box(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Pill {
                Dot(color = Peach, size = 10.dp)
                Text(
                    text = stringResource(R.string.camera_light_sensor, lightPercent),
                    style = dermaType(12, 16),
                    color = Color(0xFFFFF8F6)
                )
            }
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ErrorChip)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    Icons.Rounded.Warning,
                    null,
                    tint = Color(0xFFFFB4AB),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    stringResource(R.string.camera_accuracy_low),
                    style = dermaType(11, 16),
                    color = Color(0xFFFFDAD6)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 110.dp)
                .size(width = 224.dp, height = 280.dp),
            contentAlignment = Alignment.Center
        ) {
            FaceGuide(Modifier.fillMaxSize(), showFaceLines = false)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Rounded.LocationOn,
                    null,
                    tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Rounded.NoPhotography,
                        null,
                        tint = Peach,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        stringResource(R.string.camera_landmarks_unclear),
                        style = dermaType(11, 16),
                        color = Peach
                    )
                }
            }
        }

        // شريط القياسات
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.3f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Metric(Icons.Rounded.WbTwilight, stringResource(R.string.camera_metric_light))
            Metric(Icons.Rounded.CenterFocusStrong, stringResource(R.string.camera_metric_focus))
            Metric(
                Icons.Rounded.LocationOn,
                stringResource(R.string.camera_metric_distance, distanceCm)
            )
        }
    }
}

@Composable
fun Metric(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(icon, null, tint = Peach, modifier = Modifier.size(16.dp))
        Text(text, style = dermaType(11, 16), color = Color(0xFFFFE2D7))
    }
}

@Composable
fun NumberedTip(number: Int, @StringRes text: Int) {
    val derma = MaterialTheme.derma
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(derma.chipStrong),
            contentAlignment = Alignment.Center
        ) {
            Text("$number", style = dermaType(11, 14, FontWeight.Bold), color = derma.peach)
        }
        Text(
            text = stringResource(text),
            modifier = Modifier.weight(1f),
            style = dermaType(12, 18),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun SecondaryButton(
    icon: ImageVector,
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier
) {
    val derma = MaterialTheme.derma
    Panel(
        shape = CircleShape,
        color = derma.chipSoft,
        onClick = onClick,
        modifier = modifier.height(44.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, null, tint = derma.peach, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                stringResource(text),
                style = dermaType(13, 18, FontWeight.Medium),
                color = derma.heading
            )
        }
    }
}