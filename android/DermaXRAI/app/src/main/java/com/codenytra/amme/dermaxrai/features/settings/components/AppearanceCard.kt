package com.codenytra.amme.dermaxrai.features.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness4
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.core.utils.ThemeMode
import com.codenytra.amme.dermaxrai.features.settings.AppLanguage
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.derma


@Composable
fun AppearanceCard(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit
) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    val activeLabel = when (themeMode) {
        ThemeMode.LIGHT -> R.string.settings_theme_active_light
        ThemeMode.DARK -> R.string.settings_theme_active_dark
        ThemeMode.SYSTEM -> R.string.settings_theme_active_auto
    }
    val headerIcon = when (themeMode) {
        ThemeMode.LIGHT -> Icons.Rounded.LightMode
        ThemeMode.DARK -> Icons.Rounded.DarkMode
        ThemeMode.SYSTEM -> Icons.Rounded.Brightness4
    }

    Panel(
        shape = RoundedCornerShape(28.dp),
        color = derma.card,
        withShadow = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = headerIcon,
                        contentDescription = null,
                        tint = derma.iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.settings_theme_title),
                            style = dermaType(16, 22, FontWeight.Medium),
                            color = colors.onSurface
                        )
                        Text(
                            text = stringResource(R.string.settings_theme_subtitle),
                            style = dermaType(12, 16),
                            color = colors.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // اختيار الـ theme
            Panel(
                shape = CircleShape,
                color = derma.zoneBox,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(6.dp)) {
                    ThemeOption(
                        label = R.string.settings_theme_light,
                        icon = Icons.Rounded.LightMode,
                        selected = themeMode == ThemeMode.LIGHT,
                        onClick = { onThemeModeChange(ThemeMode.LIGHT) },
                        modifier = Modifier.weight(1f)
                    )
                    ThemeOption(
                        label = R.string.settings_theme_dark,
                        icon = Icons.Rounded.DarkMode,
                        selected = themeMode == ThemeMode.DARK,
                        onClick = { onThemeModeChange(ThemeMode.DARK) },
                        modifier = Modifier.weight(1f)
                    )
                    ThemeOption(
                        label = R.string.settings_theme_auto,
                        icon = Icons.Rounded.Brightness4,
                        selected = themeMode == ThemeMode.SYSTEM,
                        onClick = { onThemeModeChange(ThemeMode.SYSTEM) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            /*// لغة التطبيق
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Translate,
                        contentDescription = null,
                        tint = derma.iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.settings_language_title),
                            style = dermaType(16, 22, FontWeight.Medium),
                            color = colors.onSurface
                        )
                        Text(
                            text = stringResource(R.string.settings_language_subtitle),
                            style = dermaType(12, 18),
                            color = colors.onSurfaceVariant
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                LanguageSwitch(selected = language, onSelect = onLanguageChange)
            }*/
        }
    }
}

@Composable
private fun ThemeOption(
    @StringRes label: Int,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val content = if (selected) colors.onPrimary else colors.onSurfaceVariant

    Row(
        modifier = modifier
            .height(26.dp)
            .clip(CircleShape)
            .background(if (selected) colors.primary else Color.Transparent)
            .clickable(role = Role.RadioButton, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = content,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = stringResource(label),
            style = dermaType(13, 18, FontWeight.Medium),
            color = content
        )
    }
}

@Composable
private fun LanguageSwitch(selected: AppLanguage, onSelect: (AppLanguage) -> Unit) {
    Panel(shape = CircleShape, color = MaterialTheme.derma.zoneBox) {
        Row(modifier = Modifier.padding(4.dp)) {
            LanguageOption(
                label = R.string.settings_language_arabic,
                selected = selected == AppLanguage.AR,
                onClick = { onSelect(AppLanguage.AR) }
            )
            LanguageOption(
                label = R.string.settings_language_english,
                selected = selected == AppLanguage.EN,
                onClick = { onSelect(AppLanguage.EN) }
            )
        }
    }
}

@Composable
private fun LanguageOption(@StringRes label: Int, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .height(24.dp)
            .clip(CircleShape)
            .background(if (selected) colors.primary else Color.Transparent)
            .clickable(role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(label),
            style = dermaType(13, 18, FontWeight.Medium),
            color = if (selected) colors.onPrimary else colors.onSurfaceVariant
        )
    }
}
