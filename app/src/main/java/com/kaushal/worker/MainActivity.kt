package com.kaushal.worker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaushal.worker.localization.AppLanguageProvider
import com.kaushal.worker.navigation.AppNavigation
import com.kaushal.worker.ui.theme.KaushalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState: TemporaryAppViewModel = viewModel()
            val session by appState.session.collectAsState()

            AppLanguageProvider(languageCode = session.profile.language) {
                KaushalTheme {
                    AppNavigation(appState)
                }
            }
        }
    }
}
