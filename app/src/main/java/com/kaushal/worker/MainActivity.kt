package com.kaushal.worker

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaushal.ar.UnityArHost
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

    // The embedded Unity runtime (when created) receives the host Activity
    // lifecycle. Calls are no-ops until the AR screen has created it.

    override fun onStart() {
        super.onStart()
        UnityArHost.onStart()
    }

    override fun onStop() {
        UnityArHost.onStop()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        UnityArHost.onResume()
    }

    override fun onPause() {
        UnityArHost.onPause()
        super.onPause()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        UnityArHost.onWindowFocusChanged(hasFocus)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        UnityArHost.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        UnityArHost.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        UnityArHost.onTrimMemory(level)
    }

    override fun onDestroy() {
        UnityArHost.destroy()
        super.onDestroy()
    }
}