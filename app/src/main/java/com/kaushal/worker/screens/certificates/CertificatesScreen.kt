package com.kaushal.worker.screens.certificates
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun CertificatesScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        ScreenTopBar("My Certificates", onBack)
        Column(Modifier.fillMaxSize().padding(28.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("📜", style = MaterialTheme.typography.displayLarge)
            Text("No Certificates Yet", style = MaterialTheme.typography.headlineMedium)
            Text("Complete module assessments to earn your certificates.", color = KaushalMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(top = 10.dp))
        }
    }
}
