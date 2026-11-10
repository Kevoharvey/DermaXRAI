package com.codenytra.amme.dermaxrai.features.routine.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.core.data.model.RoutineStep
import com.codenytra.amme.dermaxrai.core.data.model.StepMeta
import com.codenytra.amme.dermaxrai.ui.IconCircle
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma

@Composable
fun StepCard(step: RoutineStep, onActionClick: () -> Unit) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Panel(
        shape = RoundedCornerShape(20.dp),
        color = derma.card,
        withShadow = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconCircle(
                    icon = step.icon,
                    size = 48.dp,
                    container = derma.zoneBox,
                    tint = derma.peach,
                    iconSize = 24.dp,
                    borderColor = colors.outlineVariant
                )
                Spacer(Modifier.size(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    // الخطوة + الـ meta
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Panel(
                            shape = RoundedCornerShape(6.dp),
                            color = derma.zoneBox,
                            borderColor = Color.Transparent
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.routine_step_label,
                                    step.number,
                                    stringResource(step.category)
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                                style = dermaType(11, 15),
                                color = derma.peach
                            )
                        }
                        StepMetaView(step.meta)
                    }

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = stringResource(step.title),
                        style = dermaType(20, 28, FontWeight.Medium),
                        color = colors.onSurface
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = stringResource(step.description),
                        style = dermaType(12, 18),
                        color = colors.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = colors.outlineVariant)
            Spacer(Modifier.height(11.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = step.footerIcon,
                        contentDescription = null,
                        tint = if (step.footerPositive) derma.green else colors.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = stringResource(step.footerText),
                        style = dermaType(12, 16),
                        color = colors.onSurfaceVariant
                    )
                }
                Text(
                    text = stringResource(step.action),
                    modifier = Modifier.clickable(role = Role.Button, onClick = onActionClick),
                    style = dermaType(12, 16, FontWeight.Medium),
                    color = derma.peach
                )
            }
        }
    }
}



@Composable
fun StepsHeader(count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.routine_steps_title),
            style = dermaType(22, 28, FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(R.string.routine_steps_count, count),
            style = dermaType(11, 16),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun StepMetaView(meta: StepMeta) {
    val derma = MaterialTheme.derma

    when (meta) {
        is StepMeta.Label -> {
            val tint = if (meta.positive) derma.green else MaterialTheme.colorScheme.onSurfaceVariant
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = meta.icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = stringResource(meta.text),
                    style = dermaType(11, 16),
                    color = tint
                )
            }
        }

        is StepMeta.Tag -> Panel(
            shape = RoundedCornerShape(6.dp),
            color = Color.Transparent,
            borderColor = derma.tagBorder
        ) {
            Text(
                text = stringResource(meta.text),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                style = dermaType(10, 14, FontWeight.Bold),
                color = derma.heading
            )
        }
    }
}

@Composable
fun EveningPlaceholder() {
    Panel(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.derma.zoneBox,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.routine_evening_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            style = dermaType(14, 20),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}