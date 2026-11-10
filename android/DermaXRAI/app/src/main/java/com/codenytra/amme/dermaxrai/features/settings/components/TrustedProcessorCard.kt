package com.codenytra.amme.dermaxrai.features.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.ui.IconCircle
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

@Composable
fun TrustedProcessorCard() {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Panel(
        shape = RoundedCornerShape(28.dp),
        color = Color.Transparent,
        withShadow = true,
        modifier = Modifier.fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .background(Brush.horizontalGradient(derma.trustGradient))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconCircle(
                icon = Icons.Rounded.VerifiedUser,
                size = 48.dp,
                container = derma.emphasis,
                tint = colors.onPrimary,
                borderColor = Color.Transparent
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.settings_trusted_title),
                    style = dermaType(16, 22, FontWeight.Medium),
                    color = derma.heading
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = stringResource(R.string.settings_trusted_desc),
                    style = dermaType(12, 19),
                    color = colors.onSurfaceVariant
                )
            }
        }
    }
}