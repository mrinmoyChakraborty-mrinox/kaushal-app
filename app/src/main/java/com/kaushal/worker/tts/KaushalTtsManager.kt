package com.kaushal.worker.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.kaushal.worker.R
import com.kaushal.worker.localization.AppLanguage

class KaushalTtsManager(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    var isInitialized by mutableStateOf(false)
        private set
    var isSpeaking by mutableStateOf(false)
        private set

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    isSpeaking = true
                }
                override fun onDone(utteranceId: String?) {
                    isSpeaking = false
                }
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    isSpeaking = false
                }
            })
        }
    }

    fun speak(text: String, languageCode: String) {
        if (isSpeaking) {
            stop()
            return
        }

        val ttsEngine = tts
        if (ttsEngine == null || !isInitialized) {
            Toast.makeText(context, context.getString(R.string.tts_not_available), Toast.LENGTH_SHORT).show()
            return
        }

        val locale = AppLanguage.getLocale(languageCode)
        val result = ttsEngine.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(context, context.getString(R.string.tts_not_available), Toast.LENGTH_SHORT).show()
            return
        }

        ttsEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "KAUSHAL_TTS_${System.currentTimeMillis()}")
        isSpeaking = true
    }

    fun stop() {
        tts?.stop()
        isSpeaking = false
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isSpeaking = false
    }
}

@Composable
fun rememberTtsManager(): KaushalTtsManager {
    val context = LocalContext.current
    val manager = remember { KaushalTtsManager(context) }
    DisposableEffect(manager) {
        onDispose {
            manager.shutdown()
        }
    }
    return manager
}
