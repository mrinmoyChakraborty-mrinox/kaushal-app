package com.kaushal.worker.screens.fieldbook
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun FieldBookScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)) {
        ScreenTopBar("Field Book", onBack)
        Text("Your interactive safety manual", style = MaterialTheme.typography.headlineMedium)
        Text("Two layers: read the safety knowledge, then practice through interactive exercises.", color = KaushalMuted, modifier = Modifier.padding(top = 8.dp))
        Spacer(Modifier.height(20.dp))
        Card { Column(Modifier.padding(18.dp)) {
            Text("LAYER 1 — BOOK", style = MaterialTheme.typography.titleMedium, color = KaushalOrange)
            Text("Illustration • story • safety knowledge", modifier = Modifier.padding(top = 6.dp))
        }}
        Spacer(Modifier.height(12.dp))
        Card { Column(Modifier.padding(18.dp)) {
            Text("LAYER 2 — INTERACTIVE", style = MaterialTheme.typography.titleMedium, color = KaushalOrange)
            Text("Tap • drag • scan • identify • choose • arrange • simulate", modifier = Modifier.padding(top = 6.dp))
        }}
        Spacer(Modifier.height(20.dp))
        Text("Training assets are not connected in this UI-only milestone.", color = KaushalMuted)
    }
}
