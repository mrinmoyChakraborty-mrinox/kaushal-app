package com.kaushal.worker.localization

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String, val symbol: String) {
    ENGLISH("en", "English", "I speak English", "A"),
    HINDI("hi", "Hindi", "मैं हिन्दी बोलता/बोलती हूँ", "अ"),
    SANTALI("sat", "Santali", "ᱤᱧ ᱥᱟᱱᱛᱟᱲᱤ ᱵᱚᱞᱤᱧ", "ᱟ");

    companion object {
        fun fromCode(code: String): AppLanguage {
            return entries.find { it.code.equals(code, ignoreCase = true) } ?: ENGLISH
        }

        fun getLocale(code: String): Locale {
            return when (code.lowercase()) {
                "hi" -> Locale("hi")
                "sat", "sat-olck" -> Locale.forLanguageTag("sat-Olck")
                else -> Locale.ENGLISH
            }
        }
    }
}

@Composable
fun AppLanguageProvider(
    languageCode: String,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val activityResultRegistryOwner = LocalActivityResultRegistryOwner.current
        ?: (context as? ActivityResultRegistryOwner)
        ?: (context as? ComponentActivity)
    val onBackPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current

    val locale = remember(languageCode) {
        AppLanguage.getLocale(languageCode)
    }

    val configuration = remember(locale, context) {
        Configuration(context.resources.configuration).apply {
            setLocale(locale)
        }
    }

    val localizedContext = remember(locale, context) {
        context.createConfigurationContext(configuration)
    }

    val providers = mutableListOf<ProvidedValue<*>>(
        LocalConfiguration provides configuration,
        LocalContext provides localizedContext
    )

    if (activityResultRegistryOwner != null) {
        providers.add(LocalActivityResultRegistryOwner provides activityResultRegistryOwner)
    }

    if (onBackPressedDispatcherOwner != null) {
        providers.add(LocalOnBackPressedDispatcherOwner provides onBackPressedDispatcherOwner)
    }

    CompositionLocalProvider(
        values = providers.toTypedArray()
    ) {
        content()
    }
}
