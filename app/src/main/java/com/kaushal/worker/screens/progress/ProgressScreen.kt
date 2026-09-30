package com.kaushal.worker.screens.progress
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun ProgressScreen(onBack: () -> Unit, onNavigate: (String) -> Unit) {
    Scaffold(bottomBar = { BottomNavigationBar("Progress", onNavigate) }) { padding ->
        Column(Modifier.fillMaxSize().background(KaushalCream).padding(padding).padding(20.dp)) {
            ScreenTopBar("My Progress", onBack)
            Text("0%", style = MaterialTheme.typography.displaySmall, color = KaushalOrange)
            Text("No modules completed yet", style = MaterialTheme.typography.titleLarge)
            Text("Progress is calculated only from real local/backend state.", color = KaushalMuted, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
