package com.kaushal.ar.bridge

import android.os.Handler
import android.os.Looper
import android.util.Log
import org.json.JSONObject
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Unity -> Kotlin bridge.
 *
 * Unity calls the static method `onUnityEvent(String)` on this class
 * (configured in KAUSHALHostReceiver as
 * `com.kaushal.ar.bridge.UnityBridge` / `onUnityEvent`).
 *
 * The Unity envelope is `{"type": "...", "payload": {...}}`.
 * Malformed JSON is logged and dropped; it never crashes the app.
 * Listeners are invoked on the Android main thread.
 */
object UnityBridge {

    private const val TAG = "KAUSHAL_UNITY"

    private val mainHandler = Handler(Looper.getMainLooper())
    private val listeners = CopyOnWriteArrayList<(UnityEvent) -> Unit>()

    data class UnityEvent(
        val type: String,
        val payload: JSONObject?,
        val raw: String
    )

    @JvmStatic
    fun onUnityEvent(json: String) {
        val event = parse(json) ?: return
        Log.d(TAG, "<- ${event.type} $json")
        mainHandler.post {
            listeners.forEach { listener ->
                runCatching { listener(event) }
                    .onFailure { Log.e(TAG, "Listener failed for ${event.type}", it) }
            }
        }
    }

    fun addListener(listener: (UnityEvent) -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: (UnityEvent) -> Unit) {
        listeners.remove(listener)
    }

    private fun parse(json: String): UnityEvent? {
        return try {
            val obj = JSONObject(json)
            val type = obj.optString("type", "")
            if (type.isEmpty()) {
                Log.w(TAG, "Unity event without type dropped: $json")
                null
            } else {
                UnityEvent(type, obj.optJSONObject("payload"), json)
            }
        } catch (t: Throwable) {
            Log.w(TAG, "Malformed Unity event dropped: $json", t)
            null
        }
    }
}