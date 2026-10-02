package com.kaushal.worker.screens.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kaushal.worker.R
import com.kaushal.worker.TemporaryUserProfile
import com.kaushal.worker.ui.components.BottomNavigationBar
import com.kaushal.worker.ui.components.ProfileAvatar
import com.kaushal.worker.ui.theme.KaushalCream
import com.kaushal.worker.ui.theme.KaushalMuted
import com.kaushal.worker.ui.theme.KaushalNavy
import com.kaushal.worker.ui.theme.KaushalOrange

@Composable
fun DashboardScreen(
    profile: TemporaryUserProfile,
    onNavigate: (String) -> Unit
) {
    var showNotifications by rememberSaveable { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ ->
        showNotifications = false
    }

    if (showNotifications) {
        NotificationPromptDialog(
            onDismiss = { showNotifications = false },
            onTurnOn = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    showNotifications = false
                }
            }
        )
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                current = "Home",
                onNavigate = onNavigate
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header & Explore Training Section on White Background
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    // Header: Profile (Left) - Greeting - Notifications (Right)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileAvatar(
                            photoUri = profile.profilePhotoUri,
                            size = 46.dp,
                            onClick = { onNavigate("Profile") }
                        )

                        Spacer(Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.dashboard_good_morning),
                                color = KaushalMuted,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "${profile.name.ifBlank { stringResource(R.string.dashboard_default_name) }} 👋",
                                color = KaushalNavy,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (profile.industrialSector.isBlank()) stringResource(R.string.learner) else profile.industrialSector,
                                color = KaushalMuted,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        IconButton(
                            onClick = { showNotifications = true },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0xFFFFF4EA))
                                .border(1.dp, Color(0xFFFFD4B3), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsNone,
                                contentDescription = stringResource(R.string.dashboard_notifications),
                                tint = KaushalNavy
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Mild/Light Orange Explore Training Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF4EA)),
                        border = BorderStroke(1.dp, Color(0xFFFFD4B3))
                    ) {
                        Column(Modifier.padding(18.dp)) {
                            Text(
                                text = stringResource(R.string.dashboard_start_journey_title),
                                color = KaushalNavy,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = stringResource(R.string.dashboard_no_modules_assigned),
                                color = KaushalMuted,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(Modifier.height(14.dp))
                            Button(
                                onClick = { onNavigate("Learn") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = KaushalOrange)
                            ) {
                                Text(
                                    stringResource(R.string.dashboard_explore_training),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Quick Actions Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = stringResource(R.string.dashboard_quick_actions),
                        color = KaushalNavy,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))

                    // Quick Actions Grid (2 columns x 3 rows)
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            QuickActionCard(
                                icon = "📖",
                                label = stringResource(R.string.dashboard_learning_modules),
                                onClick = { onNavigate("Learn") },
                                modifier = Modifier.weight(1f)
                            )
                            QuickActionCard(
                                icon = "✓",
                                label = stringResource(R.string.dashboard_my_progress),
                                onClick = { onNavigate("Progress") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            QuickActionCard(
                                icon = "📜",
                                label = stringResource(R.string.dashboard_certificates),
                                onClick = { onNavigate("Certificates") },
                                modifier = Modifier.weight(1f)
                            )
                            QuickActionCard(
                                icon = "📘",
                                label = stringResource(R.string.dashboard_field_book),
                                onClick = { onNavigate("Field Book") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            QuickActionCard(
                                icon = "📱",
                                label = stringResource(R.string.dashboard_ar_training),
                                onClick = { onNavigate("AR Training") },
                                modifier = Modifier.weight(1f)
                            )
                            QuickActionCard(
                                icon = "🛡️",
                                label = stringResource(R.string.dashboard_safety_passport),
                                onClick = { },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(86.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFEFEFEF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(icon, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = KaushalNavy,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun NotificationPromptDialog(
    onDismiss: () -> Unit,
    onTurnOn: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .widthIn(max = 340.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Bell Icon inside Circle
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF0E5)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = KaushalOrange,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Title
                Text(
                    text = stringResource(R.string.notif_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = KaushalNavy,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(10.dp))

                // Description
                Text(
                    text = stringResource(R.string.notif_dialog_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    color = KaushalMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(22.dp))

                // Primary Action Button
                Button(
                    onClick = onTurnOn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KaushalOrange)
                ) {
                    Text(
                        text = stringResource(R.string.notif_dialog_turn_on),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Secondary Action Button
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.notif_dialog_not_now),
                        color = KaushalMuted,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
