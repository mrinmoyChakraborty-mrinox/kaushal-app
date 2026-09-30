package com.kaushal.worker.screens.ar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun ArTrainingScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        ScreenTopBar("AR Training", onBack)
        Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("📱", style = MaterialTheme.typography.displayLarge)
            Text("AR Training", style = MaterialTheme.typography.headlineMedium)
            Text("AR training for this module will be available soon.", color = KaushalMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(top = 12.dp))
            Text("We are preparing real workplace simulations for hands-on learning with your phone.", color = KaushalMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
            Spacer(Modifier.height(22.dp))
            KaushalButton("COMING SOON", {})
        }
    }
}
