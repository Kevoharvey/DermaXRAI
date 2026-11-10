package com.codenytra.amme.dermaxrai.features.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.features.settings.components.MedicalDisclaimerCard
import com.codenytra.amme.dermaxrai.ui.Dot
import com.codenytra.amme.dermaxrai.ui.Panel
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.DermaXRAITheme
import com.codenytra.amme.dermaxrai.ui.theme.derma

/**
 * Team member. All three links are optional: pass null (or leave blank)
 * and the matching button is simply not shown.
 */
data class TeamMember(
    val name: String,
    val role: String,
    @DrawableRes val photo: Int? = null,
    val linkedIn: String? = null,
    val github: String? = null,
    val portfolio: String? = null,
)

// TODO: replace with the real names, roles and links.
private val teamMembers = listOf(
    TeamMember(
        name = "Member 1",
        role = "Role",
        linkedIn = "https://www.linkedin.com/in/username",
        github = "https://github.com/username",
        portfolio = "https://example.com",
    ),
    TeamMember(
        name = "Member 2",
        role = "Role",
        linkedIn = "https://www.linkedin.com/in/username",
        github = "https://github.com/username",
    ),
    TeamMember(
        name = "Member 3",
        role = "Role",
        github = "https://github.com/username",
        portfolio = "https://example.com",
    ),
    TeamMember(
        name = "Member 4",
        role = "Role",
        linkedIn = "https://www.linkedin.com/in/username",
    ),
    TeamMember(
        name = "Member 5",
        role = "Role",
        portfolio = "https://example.com",
    ),
    TeamMember(
        name = "Member 6",
        role = "Role",
    ),
)

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DermaXRAITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AboutScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ---- Header ----
        Panel(
            shape = RoundedCornerShape(20.dp),
            color = derma.card,
            withShadow = true
        ) {
            Box(
                modifier = Modifier.size(88.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(76.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = dermaType(16, 22, FontWeight.Bold),
            color = derma.heading
        )
        Text(
            text = stringResource(R.string.settings_about_version, "1.0.0", 123),
            style = dermaType(11, 16),
            color = colors.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Dot(color = derma.accentText, size = 7.dp)
            Text(
                text = stringResource(R.string.settings_about_engine),
                style = dermaType(11, 16),
                color = derma.accentText
            )
        }

        // ---- Disclaimer ----
        Spacer(Modifier.height(20.dp))
        MedicalDisclaimerCard()

        // ---- Team ----
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Meet the Team",
            style = dermaType(14, 20, FontWeight.Bold),
            color = derma.heading,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        // Grid of 2 columns. LazyVerticalGrid can't be nested inside verticalScroll,
        // so the grid is built from chunked Rows with equal-weight cells.
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            teamMembers.chunked(2).forEach { rowMembers ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowMembers.forEach { member ->
                        TeamMemberCard(
                            member = member,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                    // Keep the last card half-width when the count is odd.
                    if (rowMembers.size == 1) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

        // ---- Footer ----
        Spacer(Modifier.height(20.dp))
        Text(
            text = stringResource(
                R.string.settings_copyright,
                stringResource(R.string.app_name),
                "2026"
            ),
            style = dermaType(11, 16),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun TeamMemberCard(member: TeamMember, modifier: Modifier = Modifier) {
    val derma = MaterialTheme.derma
    val colors = MaterialTheme.colorScheme

    // Surface (instead of Panel) so the card reliably fills its grid cell.
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = derma.card,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MemberAvatar(member)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = member.name,
                    style = dermaType(13, 18, FontWeight.Bold),
                    color = derma.heading,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = member.role,
                    style = dermaType(11, 16),
                    color = colors.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            val links = listOfNotNull(
                member.linkedIn?.takeIf { it.isNotBlank() }?.let { "LinkedIn" to it },
                member.github?.takeIf { it.isNotBlank() }?.let { "GitHub" to it },
                member.portfolio?.takeIf { it.isNotBlank() }?.let { "Portfolio" to it },
            )

            if (links.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    links.forEach { (label, url) ->
                        LinkChip(label = label, url = url)
                    }
                }
            }
        }
    }
}

@Composable
private fun MemberAvatar(member: TeamMember) {
    val derma = MaterialTheme.derma

    if (member.photo != null) {
        Image(
            painter = painterResource(member.photo),
            contentDescription = member.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
    } else {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(derma.accentText.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = member.name.trim().firstOrNull()?.uppercase() ?: "?",
                style = dermaType(16, 22, FontWeight.Bold),
                color = derma.accentText
            )
        }
    }
}

@Composable
private fun LinkChip(label: String, url: String) {
    val derma = MaterialTheme.derma
    val uriHandler = LocalUriHandler.current
    val shape = RoundedCornerShape(50)

    Text(
        text = label,
        style = dermaType(11, 16, FontWeight.Medium),
        color = derma.accentText,
        modifier = Modifier
            .clip(shape)
            .border(1.dp, derma.accentText.copy(alpha = 0.5f), shape)
            .clickable {
                // Guard against malformed links so a bad URL never crashes the app.
                runCatching { uriHandler.openUri(url) }
            }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}