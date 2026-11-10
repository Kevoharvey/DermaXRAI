package com.codenytra.amme.dermaxrai.features.scan.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.rounded.CenterFocusStrong
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.features.scan.Peach
import com.codenytra.amme.dermaxrai.ui.Dot
import com.codenytra.amme.dermaxrai.ui.IconCircle
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

@Composable
fun GoodOverlay(onFlipCamera: () -> Unit) {

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Pill(modifier = Modifier.weight(1f)) {
                    Dot(color = Color(0xFF7CC24D), size = 12.dp)
                    Text(
                        text = stringResource(R.string.camera_face_detected),
                        style = dermaType(12, 16),
                        color = Color(0xFFFFF8F6)
                    )
                }
            }
        }

        FaceGuide(
            modifier = Modifier
                .align(Alignment.Center)
                .size(width = 224.dp, height = 280.dp),
            showFaceLines = true
        )

        // شريحة المميزات تحت
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FeatureTag(Icons.Rounded.LightMode, R.string.camera_tag_light)
            FeatureTag(Icons.Rounded.Face, R.string.camera_tag_makeup)
            FeatureTag(Icons.Rounded.CenterFocusStrong, R.string.camera_tag_front)
            FeatureTag(Icons.Rounded.Visibility, R.string.camera_tag_eyes)
        }
    }
}

@Composable
fun FeatureTag(icon: ImageVector, @StringRes text: Int) {
    Pill {
        Icon(icon, null, tint = Peach, modifier = Modifier.size(16.dp))
        Text(stringResource(text), style = dermaType(11, 16), color = Color(0xFFFFE2D7))
    }
}

@Composable
fun Pill(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(Color(0xFF241209).copy(alpha = 0.78f))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) { content() }
}

@Composable
fun GoodControls(onCapture: () -> Unit, onGallery: () -> Unit, onGuide: () -> Unit) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.background)
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SideAction(Icons.AutoMirrored.Rounded.HelpOutline, R.string.camera_guide, onGuide)
            // زرار الالتقاط
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .shadow(
                        16.dp,
                        CircleShape,
                        ambientColor = derma.buttonGlow,
                        spotColor = derma.buttonGlow
                    )
                    .clip(CircleShape)
                    .background(derma.heroGlow.copy(alpha = 0.4f))
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(derma.ctaStart, derma.ctaEnd)))
                    .clickable(role = Role.Button, onClick = onCapture)
            )
            SideAction(Icons.Rounded.PhotoLibrary, R.string.camera_gallery, onGallery)
        }
        Spacer(Modifier.height(16.dp))
        PrivacyNote()
    }
}

@Composable
fun SideAction(icon: ImageVector, @StringRes label: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(role = Role.Button, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconCircle(
            icon = icon,
            size = 52.dp,
            container = MaterialTheme.derma.chipStrong,
            tint = MaterialTheme.derma.heading,
            iconSize = 24.dp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            stringResource(label),
            style = dermaType(12, 16),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PrivacyNote() {
    val derma = MaterialTheme.derma
    Panel(
        shape = RoundedCornerShape(24.dp),
        color = derma.zoneBox,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.camera_privacy),
                modifier = Modifier.weight(1f),
                style = dermaType(12, 18),
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(Icons.Rounded.Lock, null, tint = derma.iconMuted, modifier = Modifier.size(18.dp))
        }
    }
}