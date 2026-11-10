package com.codenytra.amme.dermaxrai.features.onboarding.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FaceRetouchingNatural
import androidx.compose.material.icons.rounded.GppGood
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.features.onboarding.PageColumn
import com.codenytra.amme.dermaxrai.ui.IconCircle
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

@Composable
fun ScanPage(scanImage: Painter?) {
    val derma = MaterialTheme.derma

    PageColumn(horizontal = 20.dp) {

        // Hero
        Panel(
            shape = RoundedCornerShape(32.dp),
            color = derma.heroBase,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.radialGradient(listOf(derma.heroGlow, derma.heroBase), radius = 520f))
            ) {
                if (scanImage != null) {
                    Image(
                        painter = scanImage,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width(129.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(88.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(derma.heroTile),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FaceRetouchingNatural,
                            contentDescription = null,
                            tint = derma.iconTint,
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }

                Panel(
                    shape = CircleShape,
                    color = derma.heroTile,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.QrCodeScanner,
                            contentDescription = null,
                            tint = derma.peach,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = stringResource(R.string.onboarding_p1_chip),
                            style = dermaType(11, 16, FontWeight.SemiBold),
                            color = derma.peach
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        PageTitle(R.string.onboarding_p1_title, size = 32, lineHeight = 42)
        Spacer(Modifier.height(8.dp))
        PageBody(R.string.onboarding_p1_body)
        Spacer(Modifier.height(15.dp))

        Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
            FeatureRow(Icons.Rounded.Timer, R.string.onboarding_p1_feature1)
            FeatureRow(Icons.Rounded.GppGood, R.string.onboarding_p1_feature2)
        }
    }
}

@Composable
private fun FeatureRow(icon: ImageVector, @StringRes text: Int) {
    val derma = MaterialTheme.derma

    Panel(
        shape = RoundedCornerShape(2.dp),
        color = derma.zoneBox,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconCircle(
                icon = icon,
                size = 36.dp,
                container = derma.chipStrong,
                tint = derma.peach,
                iconSize = 20.dp
            )
            Text(
                text = stringResource(text),
                style = dermaType(12, 18),
                color = derma.heading
            )
        }
    }
}