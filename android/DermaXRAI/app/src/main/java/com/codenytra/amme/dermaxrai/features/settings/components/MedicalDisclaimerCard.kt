package com.codenytra.amme.dermaxrai.features.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HealthAndSafety
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

@Composable
fun MedicalDisclaimerCard() {
    val derma = MaterialTheme.derma

    Panel(
        shape = RoundedCornerShape(28.dp),
        color = derma.tipCard,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.HealthAndSafety,
                    contentDescription = null,
                    tint = derma.accentText,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.settings_disclaimer_title),
                    style = dermaType(14, 20, FontWeight.Bold),
                    color = derma.heading
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.settings_disclaimer_body),
                modifier = Modifier.padding(start = 28.dp),
                style = dermaType(11, 20),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}