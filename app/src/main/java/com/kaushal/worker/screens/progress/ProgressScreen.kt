package com.kaushal.worker.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaushal.worker.R
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun ProgressScreen(onBack: () -> Unit, onNavigate: (String) -> Unit) {
    Scaffold(
        containerColor = KaushalCream,
        bottomBar = { BottomNavigationBar("Progress", onNavigate) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            ScreenTopBar(stringResource(R.string.progress_title), onBack)
            Text("0%", style = MaterialTheme.typography.displaySmall, color = KaushalOrange)
            Text(stringResource(R.string.progress_empty_title), style = MaterialTheme.typography.titleLarge)
            Text(
                stringResource(R.string.progress_empty_desc),
                color = KaushalMuted,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
