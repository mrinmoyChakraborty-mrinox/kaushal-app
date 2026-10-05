package com.kaushal.ar

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.unity3d.player.IUnityPlayerLifecycleEvents
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerForActivityOrService

/**
 * Owns the single embedded Unity runtime for the whole app process.
 *
 * UnityPlayer is created once, on first entry into the AR screen, and is kept
 * alive for the lifetime of the host Activity. Leaving the AR screen only
 * detaches the view and sends EXIT_AR; it never destroys/recreates the runtime.
 * The runtime is destroyed only when the Activity is destroyed.
 */
object UnityArHost {

    private const val TAG = "KAUSHAL_AR"

    private var player: UnityPlayerForActivityOrService? = null
    private var hostActivity: Activity? = null
    private var attachedView: View? = null

    val isCreated: Boolean get() = player != null

    @Synchronized
    fun ensurePlayer(activity: Activity): UnityPlayerForActivityOrService {
        player?.let { return it }
        Log.i(TAG, "UnityPlayer created (activity=${activity.javaClass.simpleName})")
        val created = UnityPlayerForActivityOrService(activity, object : IUnityPlayerLifecycleEvents {
            override fun onUnityPlayerUnloaded() {
                Log.i(TAG, "onUnityPlayerUnloaded")
            }

            override fun onUnityPlayerQuitted() {
                Log.i(TAG, "onUnityPlayerQuitted")
            }
        })
        player = created
        hostActivity = activity
        return created
    }

    fun unityView(): View? = player?.getFrameLayout()

    /** Attaches the Unity view into the supplied container, once. */
    @Synchronized
    fun attach(container: ViewGroup) {
        val view = player?.getFrameLayout() ?: return
        val currentParent = view.parent as? ViewGroup
        if (currentParent === container && view.isAttachedToWindow) return
        currentParent?.removeView(view)
        container.addView(
            view,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.requestFocus()
        attachedView = view
        Log.i(TAG, "Unity view attached")
    }

    /** Detaches the Unity view but keeps the runtime alive. */
    @Synchronized
    fun detach() {
        val view = attachedView ?: return
        (view.parent as? ViewGroup)?.removeView(view)
        attachedView = null
        Log.i(TAG, "Unity view detached")
    }

    /** Accepts a raw JSON string command. */
    fun sendCommand(json: String) {
        if (player == null) {
            Log.w(TAG, "sendCommand ignored (no Unity runtime): $json")
            return
        }
        Log.i(TAG, "-> $json")
        UnityPlayer.UnitySendMessage("KAUSHALRuntime", "OnHostCommand", json)
    }

    /** Accepts a built JSONObject command. */
    fun sendCommand(command: org.json.JSONObject) {
        sendCommand(command.toString())
    }

    // ---- Activity lifecycle forwarding (no-ops until the runtime exists) ----

    fun onStart() {
        player?.onStart()
    }

    fun onStop() {
        player?.onStop()
    }

    fun onResume() {
        player?.onResume()
    }

    fun onPause() {
        player?.onPause()
    }

    fun onWindowFocusChanged(hasFocus: Boolean) {
        player?.windowFocusChanged(hasFocus)
    }

    fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        player?.configurationChanged(newConfig)
    }

    fun onLowMemory() {
        player?.onTrimMemory(UnityPlayerForActivityOrService.MemoryUsage.Critical)
    }

    fun onTrimMemory(level: Int) {
        when (level) {
            android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE ->
                player?.onTrimMemory(UnityPlayerForActivityOrService.MemoryUsage.Medium)
            android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW ->
                player?.onTrimMemory(UnityPlayerForActivityOrService.MemoryUsage.High)
            android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL ->
                player?.onTrimMemory(UnityPlayerForActivityOrService.MemoryUsage.Critical)
        }
    }

    @Synchronized
    fun destroy() {
        detach()
        player?.let {
            Log.i(TAG, "UnityPlayer destroyed")
            it.destroy()
        }
        player = null
        hostActivity = null
        attachedView = null
    }
}