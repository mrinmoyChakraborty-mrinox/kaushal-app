package com.kaushal.worker.screens.ar

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.kaushal.ar.ArPhase
import com.kaushal.ar.ArSessionController
import com.kaushal.ar.UnityArHost
import com.kaushal.ar.bridge.ArScenarioMapping
import com.kaushal.worker.R
import com.kaushal.worker.ui.components.KaushalButton
import com.kaushal.worker.ui.components.ScreenTopBar
import com.kaushal.worker.ui.theme.*

private val ArGreen = Color(0xFF2E9B63)

/**
 * Real Unity AR host screen.
 *
 * The Kotlin question/answer engine stays on the story decision screen; this
 * screen only shows the Unity viewport and a small Kotlin-owned status area.
 * Returning (system Back or "BACK TO DECISION") sends EXIT_AR and pops back to
 * the exact same story decision screen with its state intact.
 */
@Composable
fun ArTrainingScreen(
    moduleId: String,
    scenarioId: String,
    chapterId: String,
    screenId: String,
    languageCode: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    // Do NOT use LocalContext.current as Activity: AppLanguageProvider swaps in
    // a localized ContextWrapper. LocalActivity gives the real host Activity.
    val activity = LocalActivity.current
    val step = remember(scenarioId) { ArScenarioMapping.resolve(scenarioId) }

    var cameraGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var cameraRequested by remember { mutableStateOf(false) }
    // Preview mode needs no camera: if the user denies the permission they may
    // still continue into the Unity 3D preview fallback (no new commands).
    var proceedWithoutCamera by remember { mutableStateOf(false) }
    val cameraEffective = cameraGranted || proceedWithoutCamera

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        cameraGranted = granted
        cameraRequested = true
    }

    LaunchedEffect(step) {
        if (step != null && !cameraGranted && !cameraRequested) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
            cameraRequested = true
        }
    }

    val session = remember(step, cameraEffective) {
        if (step != null && cameraEffective && activity != null) {
            ArSessionController(languageCode = languageCode, step = step)
        } else {
            null
        }
    }

    // Flips true after the single Unity runtime instance has been created, so
    // the AndroidView only attaches an existing Unity view (never creates the
    // runtime from a view factory / recomposition).
    var hostReady by remember(session) { mutableStateOf(false) }

    LaunchedEffect(session, activity) {
        if (session != null && activity != null) {
            UnityArHost.ensurePlayer(activity)
            hostReady = UnityArHost.isCreated
            session.start(activity)
        }
    }

    DisposableEffect(session) {
        onDispose {
            session?.exit()
            session?.release()
        }
    }

    val exitAndBack = {
        session?.exit()
        onBack()
    }

    BackHandler(enabled = true, onBack = exitAndBack)

    Column(Modifier.fillMaxSize().background(KaushalCream)) {
        ScreenTopBar(stringResource(R.string.ar_title), exitAndBack)

        when {
            step == null -> {
                // No defensible Unity mapping for this scenario: Kotlin-owned
                // state, no incorrect step is sent to Unity.
                ArMessageState(
                    title = stringResource(R.string.ar_unavailable_title),
                    message = stringResource(R.string.ar_unavailable_desc),
                    buttonText = stringResource(R.string.ar_back_to_decision),
                    onButton = onBack
                )
            }

            !cameraEffective -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.ar_camera_title),
                        style = MaterialTheme.typography.headlineMedium,
                        color = KaushalNavy
                    )
                    Text(
                        if (cameraRequested) stringResource(R.string.ar_camera_denied)
                        else stringResource(R.string.ar_camera_needed),
                        color = KaushalMuted,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Spacer(Modifier.height(22.dp))
                    KaushalButton(
                        stringResource(R.string.ar_camera_grant),
                        { permissionLauncher.launch(Manifest.permission.CAMERA) }
                    )
                    Spacer(Modifier.height(12.dp))
                    // 3D preview needs no camera: proceed into the Unity
                    // fallback instead of dead-ending here.
                    KaushalButton(
                        stringResource(R.string.ar_continue_preview),
                        { proceedWithoutCamera = true }
                    )
                }
            }

            else -> {
                val phase = session?.phase ?: ArPhase.Idle
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .border(1.dp, KaushalBorder, RoundedCornerShape(18.dp))
                        .background(Color.Black)
                ) {
                    if (hostReady) {
                        AndroidView(
                            factory = { ctx ->
                                android.widget.FrameLayout(ctx).also { container ->
                                    UnityArHost.attach(container)
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    if (phase != ArPhase.StepReady) {
                        Text(
                            text = session?.statusText.orEmpty(),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(20.dp)
                        )
                    }
                }

                ArStatusBar(
                    phase = phase,
                    statusText = session?.statusText.orEmpty(),
                    lastInteraction = session?.lastInteraction,
                    renderMode = session?.renderMode.orEmpty(),
                    onReset = { session?.resetStep() },
                    onBack = exitAndBack
                )
            }
        }
    }
}

@Composable
private fun ArStatusBar(
    phase: ArPhase,
    statusText: String,
    lastInteraction: String?,
    renderMode: String,
    onReset: () -> Unit,
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = when (phase) {
                    ArPhase.StepReady -> stringResource(R.string.ar_status_ready)
                    ArPhase.WaitingReady -> stringResource(R.string.ar_status_starting)
                    ArPhase.LoadingStep -> stringResource(R.string.ar_status_loading)
                    ArPhase.Error -> statusText.ifBlank { stringResource(R.string.ar_status_error) }
                    ArPhase.Exited -> stringResource(R.string.ar_status_exited)
                    ArPhase.Idle -> stringResource(R.string.ar_status_starting)
                },
                color = if (phase == ArPhase.Error) KaushalOrangeDark else KaushalNavy,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            // Mode chip comes straight from Unity's AR_READY payload.
            if (renderMode.isNotBlank()) {
                Text(
                    text = if (renderMode == "preview") stringResource(R.string.ar_mode_preview)
                    else stringResource(R.string.ar_mode_ar),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (renderMode == "preview") KaushalNavy else ArGreen)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
        if (!lastInteraction.isNullOrBlank()) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.ar_interaction, lastInteraction),
                color = ArGreen,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            if (phase == ArPhase.StepReady) {
                KaushalButton(
                    text = stringResource(R.string.ar_reset_step),
                    onClick = onReset,
                    modifier = Modifier.weight(1f)
                )
            }
            KaushalButton(
                text = stringResource(R.string.ar_back_to_decision),
                onClick = onBack,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ArMessageState(
    title: String,
    message: String,
    buttonText: String,
    onButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium, color = KaushalNavy)
        Text(
            message,
            color = KaushalMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp)
        )
        Spacer(Modifier.height(22.dp))
        KaushalButton(buttonText, onButton)
    }
}