package com.codenytra.amme.dermaxrai.features.routine.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun ReminderCard(
    enabled: Boolean,
    onEnabledChange: (Boolean) -> Unit,
    onTimeClick: () -> Unit
) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Panel(
        shape = RoundedCornerShape(20.dp),
        color = derma.zoneBox,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconCircle(
                    icon = Icons.Rounded.NotificationsActive,
                    size = 40.dp,
                    container = colors.primary,
                    tint = colors.onPrimary,
                    iconSize = 20.dp,
                    borderColor = Color.Transparent
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.routine_reminder_title),
                        style = dermaType(18, 24, FontWeight.Medium),
                        color = colors.onSurface
                    )
                    Text(
                        text = stringResource(R.string.routine_reminder_subtitle),
                        style = dermaType(12, 16),
                        color = colors.onSurfaceVariant
                    )
                }
                Switch(
                    checked = enabled,
                    onCheckedChange = onEnabledChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.onPrimary,
                        checkedTrackColor = colors.primary,
                        checkedBorderColor = Color.Transparent,
                        uncheckedThumbColor = colors.onSurfaceVariant,
                        uncheckedTrackColor = derma.card,
                        uncheckedBorderColor = colors.outlineVariant
                    )
                )
            }

            Spacer(Modifier.height(18.dp))

            Panel(
                shape = CircleShape,
                color = derma.card,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Alarm,
                            contentDescription = null,
                            tint = derma.peach,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = stringResource(R.string.routine_reminder_time_label),
                            style = dermaType(14, 20),
                            color = colors.onSurfaceVariant
                        )
                    }

                    Panel(
                        shape = CircleShape,
                        color = derma.zoneBox,
                        onClick = onTimeClick
                    ) {
                        Row(
                            modifier = Modifier
                                .height(42.dp)
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.routine_reminder_time),
                                style = dermaType(16, 22, FontWeight.Medium),
                                color = colors.onSurface
                            )
                            Text(
                                text = stringResource(R.string.routine_reminder_am),
                                style = dermaType(12, 16),
                                color = colors.onSurfaceVariant
                            )
                            Icon(
                                imageVector = Icons.Rounded.KeyboardArrowDown,
                                contentDescription = null,
                                tint = colors.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}