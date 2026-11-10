package com.codenytra.amme.dermaxrai.features.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.DermaXRAIApp
import com.codenytra.amme.dermaxrai.MainActivity
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.core.utils.isOnboardingDone
import com.codenytra.amme.dermaxrai.core.utils.markOnboardingDone
import com.codenytra.amme.dermaxrai.features.onboarding.components.BottomControls
import com.codenytra.amme.dermaxrai.features.onboarding.components.DisclaimerPage
import com.codenytra.amme.dermaxrai.features.onboarding.components.ScanPage
import com.codenytra.amme.dermaxrai.features.onboarding.components.SkipPill
import com.codenytra.amme.dermaxrai.features.onboarding.components.TransparencyPage
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.DermaXRAITheme
import com.codenytra.amme.dermaxrai.ui.theme.derma
import kotlinx.coroutines.launch

const val PAGE_COUNT = 3

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (isOnboardingDone(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            DermaXRAITheme {
                OnboardingScreen(
                    onFinish = {
                        markOnboardingDone(this)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                )
            }
        }
    }
}
// ═══════════════════════════ Entry ═══════════════════════════

/**
 * شاشة الـ Onboarding (3 صفحات).
 *
 * @param onFinish بيتنادى لما المستخدم يضغط "ابدأ الآن" (بعد الإقرار في الصفحة الأخيرة).
 * @param onSkip بيتنادى عند "تخطي".
 * @param logo أيقونة التطبيق (اختياري، وإلا بيظهر حرف D مؤقتاً).
 * @param wordmark صورة اللوجو النصي DERMA-X-R{AI} في الصفحة الأولى (اختياري).
 * @param scanImage الصورة اللي في منتصف هيرو الصفحة الأولى (اختياري).
 */
@Composable
fun OnboardingScreen(
    onFinish:  () -> Unit,
    modifier: Modifier = Modifier,
    onSkip: () -> Unit = onFinish,
    scanImage: Painter? = null
) {
    val pagerState = rememberPagerState(pageCount = { PAGE_COUNT })
    val scope = rememberCoroutineScope()
    var acknowledged by rememberSaveable { mutableStateOf(false) }

    val page = pagerState.currentPage
    val horizontal = if (page == 0) 16.dp else 20.dp
    val derma = MaterialTheme.derma


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier.size(36.dp)
                )
                Spacer(Modifier.width(4.dp))
                Image(
                    painter = painterResource(R.drawable.banner),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier.height(28.dp)
                )
            }
            if (page == 3) {
                Panel(shape = CircleShape, color = derma.chipStrong) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.VerifiedUser,
                            contentDescription = null,
                            tint = derma.peach,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = stringResource(R.string.onboarding_ready),
                            style = dermaType(12, 16),
                            color = derma.peach
                        )
                    }
                }
            } else
                SkipPill(onSkip)
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            when (index) {
                0 -> ScanPage(scanImage = scanImage)
                1 -> TransparencyPage()
                else -> DisclaimerPage(
                    acknowledged = acknowledged,
                    onAcknowledgedChange = { acknowledged = it }
                )
            }
        }

        BottomControls(
            page = page,
            horizontal = horizontal,
            canFinish = acknowledged,
            onNext = { scope.launch { pagerState.animateScrollToPage(page + 1) } },
            onPrevious = { scope.launch { pagerState.animateScrollToPage(page - 1) } },
            onFinish = onFinish
        )
    }
}

// ═══════════════════════════ Pages ═══════════════════════════

@Composable
fun PageColumn(
    horizontal: Dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = horizontal)
            .padding(top = 0.dp, bottom = 12.dp),
        content = content
    )
}



// ═══════════════════════════ Preview ═══════════════════════════

@Preview(showBackground = true, widthDp = 390, heightDp = 800, locale = "ar")
@Composable
private fun OnboardingLightPreview() {
    DermaXRAITheme(darkTheme = false) { OnboardingScreen(onFinish = {}) }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 800, locale = "ar")
@Composable
private fun OnboardingDarkPreview() {
    DermaXRAITheme(darkTheme = true) { OnboardingScreen(onFinish = {}) }
}