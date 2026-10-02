package com.kaushal.worker.screens.ar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaushal.worker.R
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun ArTrainingScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        ScreenTopBar(stringResource(R.string.ar_title), onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("📱", style = MaterialTheme.typography.displayLarge)
            Text(stringResource(R.string.ar_title), style = MaterialTheme.typography.headlineMedium)
            Text(
                stringResource(R.string.ar_soon_title),
                color = KaushalMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                stringResource(R.string.ar_soon_desc),
                color = KaushalMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(Modifier.height(22.dp))
            KaushalButton(stringResource(R.string.btn_coming_soon), {})
        }
    }
}
