package com.kaushal.worker.screens.ar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaushal.worker.ui.components.KaushalButton
import com.kaushal.worker.ui.components.ScreenTopBar
import com.kaushal.worker.ui.theme.*

@Composable
fun ArTrainingScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().background(KaushalCream)) {
        ScreenTopBar("Practice AR", onBack)
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("AR", style = MaterialTheme.typography.displayLarge, color = KaushalGreen)
            Text(
                "AR Practice Placeholder",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = KaushalNavy
            )
            Text(
                "AR practice will be available in the next AR integration phase.",
                color = KaushalMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
            Spacer(Modifier.height(22.dp))
            KaushalButton("BACK TO DECISION", onBack)
        }
    }
}
