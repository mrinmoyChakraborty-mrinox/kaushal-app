package com.kaushal.worker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaushal.worker.navigation.AppNavigation
import com.kaushal.worker.ui.theme.KaushalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaushalTheme {
                val appState: TemporaryAppViewModel = viewModel()
                AppNavigation(appState)
            }
        }
    }
}
