package com.kaushal.ar

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kaushal.ar.bridge.ArScenarioMapping
import com.kaushal.ar.bridge.UnityBridge
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

enum class ArPhase {
    Idle,
    WaitingReady,
    LoadingStep,
    StepReady,
    Error,
    Exited
}

/**
 * Kotlin-owned AR session state machine for a single mapped Unity step.
 *
 * Flow:
 *   OPEN_AR -> (Unity) AR_READY -> LOAD_STEP -> (Unity) STEP_READY
 * and, on exit:
 *   EXIT_AR -> (Unity) AR_EXITED
 *
 * No events are fabricated: the phases only change when the matching Unity
 * event actually arrives.
 */
class ArSessionController(
    private val languageCode: String,
    private val step: ArScenarioMapping.UnityStep
) {
    companion object {
        private const val TAG = "KAUSHAL_AR"
        private const val OPEN_RETRY_INTERVAL_MS = 1500L
        private const val OPEN_RETRY_ATTEMPTS = 40
    }

    var phase by mutableStateOf(ArPhase.Idle)
        private set
    var statusText by mutableStateOf("")
        private set
    var lastInteraction by mutableStateOf<String?>(null)
        private set
    var errorReason by mutableStateOf<String?>(null)
        private set
    // Render mode reported by Unity in AR_READY ("ar" or "preview").
    // Kotlin ignores it for logic; it is only shown in the status area.
    // Empty = not reported yet.
    var renderMode by mutableStateOf("")
        private set

    private val scope = CoroutineScope(Dispatchers.Main)
    private var openRetryJob: Job? = null
    private var loadStepSent = false
    private var released = false

    private val listener: (UnityBridge.UnityEvent) -> Unit = ::onUnityEvent

    fun start(activity: Activity) {
        if (released) return
        UnityBridge.addListener(listener)
        UnityArHost.ensurePlayer(activity)
        phase = ArPhase.WaitingReady
        statusText = "Starting Unity AR runtime..."
        sendOpenAr()
        openRetryJob = scope.launch {
            repeat(OPEN_RETRY_ATTEMPTS) {
                delay(OPEN_RETRY_INTERVAL_MS)
                if (!released && phase == ArPhase.WaitingReady) {
                    Log.i(TAG, "No AR_READY yet, re-sending OPEN_AR")
                    sendOpenAr()
                }
            }
        }
    }

    fun exit() {
        if (released) return
        UnityArHost.sendCommand(json("EXIT_AR"))
    }

    fun resetStep() {
        if (phase == ArPhase.StepReady) {
            UnityArHost.sendCommand(json("RESET_STEP").put("stepId", step.stepId))
        }
    }

    fun showConsequence(consequenceId: String) {
        UnityArHost.sendCommand(json("SHOW_CONSEQUENCE").put("consequenceId", consequenceId))
    }

    fun answerSelected(assetId: String) {
        UnityArHost.sendCommand(
            json("ANSWER_SELECTED").put("stepId", step.stepId).put("assetId", assetId)
        )
    }

    fun hideAr() {
        UnityArHost.sendCommand(json("HIDE_AR"))
    }

    /** Removes the listener and detaches the Unity view. Keeps the runtime alive. */
    fun release() {
        if (released) return
        released = true
        openRetryJob?.cancel()
        openRetryJob = null
        UnityBridge.removeListener(listener)
        UnityArHost.detach()
    }

    private fun sendOpenAr() {
        UnityArHost.sendCommand(
            json("OPEN_AR")
                .put("moduleId", step.moduleId)
                .put("scenarioId", step.scenarioId)
                .put("stepId", step.stepId)
                .put("sector", "mining")
                .put("variant", "underground")
                .put("language", languageCode.ifBlank { "en" })
        )
    }

    private fun onUnityEvent(event: UnityBridge.UnityEvent) {
        if (released) return
        when (event.type) {
            "AR_READY" -> {
                val mode = event.payload?.optString("mode").orEmpty()
                if (mode.isNotBlank()) renderMode = mode
                Log.i(TAG, "AR_READY received (mode=$renderMode)")
                phase = ArPhase.LoadingStep
                statusText = "AR ready. Loading visual step..."
                if (!loadStepSent) {
                    loadStepSent = true
                    UnityArHost.sendCommand(json("LOAD_STEP").put("stepId", step.stepId))
                }
            }

            "STEP_READY" -> {
                Log.i(TAG, "STEP_READY received (${event.payload?.optString("stepId")})")
                phase = ArPhase.StepReady
                statusText = "Step ready: ${event.payload?.optString("stepId").orEmpty()}. " +
                    "Aim the camera at the floor and tap to place the scene."
            }

            "STEP_VISUAL_COMPLETE" -> {
                Log.i(TAG, "STEP_VISUAL_COMPLETE (${event.raw})")
            }

            "OBJECT_INTERACTED" -> {
                val assetId = event.payload?.optString("assetId").orEmpty()
                lastInteraction = assetId
                Log.i(TAG, "OBJECT_INTERACTED assetId=$assetId (Kotlin owns correctness)")
            }

            "AR_ERROR" -> {
                val reason = event.payload?.optString("reason").orEmpty()
                errorReason = reason
                phase = ArPhase.Error
                statusText = "AR error: $reason"
                Log.e(TAG, "AR_ERROR received: $reason")
                openRetryJob?.cancel()
            }

            "AR_EXITED" -> {
                Log.i(TAG, "AR_EXITED received (${event.payload?.optString("reason")})")
                phase = ArPhase.Exited
                statusText = "AR exited."
            }

            else -> Log.d(TAG, "Unhandled Unity event: ${event.type}")
        }
    }

    private fun json(command: String): JSONObject = JSONObject().put("command", command)
}