package com.kaushal.worker.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushal.worker.TemporaryAppViewModel
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun ProgressScreen(
    appState: TemporaryAppViewModel,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val session = appState.session.collectAsState().value
    val progress = session.module1Progress
    val percent = (progress.completedScreens.size / 70f * 100f).toInt().coerceIn(0, 100)

    Scaffold(
        containerColor = KaushalCream,
        bottomBar = { BottomNavigationBar("Progress", onNavigate) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp)
        ) {
            ScreenTopBar("My Progress", onBack)
            Text("Module 1 — Fire & Explosion Response", style = MaterialTheme.typography.titleLarge, color = KaushalNavy)
            Spacer(Modifier.height(12.dp))
            Text("$percent%", style = MaterialTheme.typography.displaySmall, color = KaushalOrange)
            LinearProgressIndicator(
                progress = { percent / 100f },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = KaushalOrange,
                trackColor = KaushalBorder
            )
            Spacer(Modifier.height(16.dp))
            Text("${progress.completedScreens.size} / 70 story screens completed", color = KaushalText)
            Text("${progress.completedChapters.size} / 7 chapters completed", color = KaushalText)
            Text("${progress.decisionAttempts} story decisions attempted", color = KaushalText)
            if (progress.assessmentCompleted) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Assessment: ${progress.assessmentScore ?: 0} / ${progress.assessmentTotal ?: 14}",
                    color = KaushalGreen,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (percent == 0 && !progress.assessmentCompleted) {
                Spacer(Modifier.height(18.dp))
                Text("No activity yet", style = MaterialTheme.typography.titleLarge)
                Text("Progress is calculated only from real Module 1 activity.", color = KaushalMuted, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}
