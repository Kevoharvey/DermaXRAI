package com.codenytra.amme.dermaxrai.features.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.DermaApp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.core.utils.ThemeMode
import com.codenytra.amme.dermaxrai.core.utils.isCameraPermissionGranted
import com.codenytra.amme.dermaxrai.features.settings.components.AppearanceCard
import com.codenytra.amme.dermaxrai.features.settings.components.Chevron
import com.codenytra.amme.dermaxrai.features.settings.components.SettingRow
import com.codenytra.amme.dermaxrai.features.settings.components.TrustedProcessorCard
import com.codenytra.amme.dermaxrai.ui.Dot
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.DermaXRAITheme
import com.codenytra.amme.dermaxrai.ui.theme.derma

class SettingsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val vm = DermaApp.instance.themeViewModel
            val context = LocalContext.current
            val isCameraPermissionGranted = remember(context) {
                isCameraPermissionGranted(context)
            }
            DermaXRAITheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Column (modifier = Modifier.padding(4.dp)) {
                                    Image(
                                        painter = painterResource(R.drawable.banner),
                                        contentDescription = "DermaXRAI",
                                        modifier = Modifier.height(40.dp)
                                    )
                                    Text(
                                        text = stringResource(R.string.settings_title),
                                        style = dermaType(12, 12, FontWeight.Medium),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    finish()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                                        contentDescription = "Settings"
                                    )
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    SettingsScreen(
                        modifier = Modifier.padding(innerPadding),
                        themeMode = vm.themeMode,
                        onThemeModeChange = { vm.updateThemeMode(it) },
                        language = AppLanguage.EN,
                        onLanguageChange = {},
                        cameraPermissionEnabled = isCameraPermissionGranted,
                        onCameraPermissionChange = {
                            context.startActivity(
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}




enum class AppLanguage { AR, EN }

// ───────────────────────── Screen ─────────────────────────

/**
 * كل الحالات (state) جاية من برّه (hoisted): الـ theme واللغة والكاميرا،
 * فالـ ViewModel أو الـ DataStore بتاعك هو اللي يحفظ الاختيار.
 */
@Composable
fun SettingsScreen(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    cameraPermissionEnabled: Boolean,
    onCameraPermissionChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onPrivacyPolicyClick: () -> Unit = {},
) {

    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 8.dp, bottom = 24.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        TrustedProcessorCard()

        Spacer(Modifier.height(26.dp))
        SectionHeader(R.string.settings_section_appearance, Icons.Rounded.Palette)
        Spacer(Modifier.height(14.dp))
        AppearanceCard(
            themeMode = themeMode,
            onThemeModeChange = onThemeModeChange,
            language = language,
            onLanguageChange = onLanguageChange
        )

        Spacer(Modifier.height(26.dp))
        SectionHeader(R.string.settings_section_privacy, Icons.Rounded.Security)
        Spacer(Modifier.height(14.dp))
        PrivacyGuaranteeCard()
        Spacer(Modifier.height(8.dp))
        PermissionsCard(
            cameraEnabled = cameraPermissionEnabled,
            onCameraChange = onCameraPermissionChange,
            onPrivacyPolicyClick = onPrivacyPolicyClick,
            onAboutClick = {
                context.startActivity(Intent(context, AboutActivity::class.java))
            }
        )

        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun SectionHeader(@StringRes title: Int, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.derma.accentText,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = stringResource(title),
            style = dermaType(18, 24, FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// ───────────────────────── Privacy ─────────────────────────

@Composable
private fun PrivacyGuaranteeCard() {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Panel(
        shape = RoundedCornerShape(28.dp),
        color = derma.privacyCard,
        withShadow = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // مربع القفل
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(derma.tile),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = null,
                        tint = derma.iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.settings_guarantee_title),
                            modifier = Modifier.weight(1f),
                            style = dermaType(20, 28, FontWeight.Medium),
                            color = derma.heading
                        )
                        Spacer(Modifier.width(8.dp))
                        Panel(
                            shape = RoundedCornerShape(18.dp),
                            color = derma.privacyBadge,
                            modifier = Modifier.width(59.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.settings_guarantee_badge),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = dermaType(11, 15, FontWeight.SemiBold),
                                color = derma.accentText,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.settings_guarantee_body),
                        style = dermaType(13, 20),
                        color = colors.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Panel(
                shape = RoundedCornerShape(2.dp),
                color = derma.deepInset,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Dot(color = derma.accentText)
                        Text(
                            text = stringResource(R.string.settings_e2ee_label),
                            style = dermaType(12, 16),
                            color = colors.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PermissionsCard(
    cameraEnabled: Boolean,
    onCameraChange: (Boolean) -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onAboutClick: () -> Unit
) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Panel(
        shape = RoundedCornerShape(28.dp),
        color = derma.card,
        withShadow = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            SettingRow(
                icon = Icons.Rounded.PhotoCamera,
                title = stringResource(R.string.settings_camera_title),
                subtitle = stringResource(R.string.settings_camera_subtitle),
                onClick = { onCameraChange(!cameraEnabled) }
            ) {
                Switch(
                    checked = cameraEnabled,
                    onCheckedChange = onCameraChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.onPrimary,
                        checkedTrackColor = colors.primary,
                        checkedBorderColor = Color.Transparent,
                        uncheckedThumbColor = colors.onSurfaceVariant,
                        uncheckedTrackColor = derma.zoneBox,
                        uncheckedBorderColor = colors.outlineVariant
                    )
                )
            }
            SettingRow(
                icon = Icons.Rounded.PrivacyTip,
                title = stringResource(R.string.settings_policy_title),
                subtitle = stringResource(R.string.settings_policy_subtitle),
                onClick = onPrivacyPolicyClick
            ) { Chevron() }
            SettingRow(
                icon = Icons.Rounded.Info,
                title = stringResource(R.string.about),
                subtitle = stringResource(R.string.about_subtitle),
                onClick = onAboutClick
            ) { Chevron() }
        }
    }
}
