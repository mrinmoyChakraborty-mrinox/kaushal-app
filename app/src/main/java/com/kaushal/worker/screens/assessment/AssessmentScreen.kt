package com.kaushal.worker.screens.assessment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun AssessmentScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        ScreenTopBar("Assessment", onBack)
        Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("📝", style = MaterialTheme.typography.displayMedium)
            Text("Assessment content coming soon", style = MaterialTheme.typography.headlineMedium, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Text("No real questions or scores are supplied in the current UI-testing phase.", color = KaushalMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(top = 10.dp))
        }
    }
}
