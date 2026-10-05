package com.kaushal.worker.screens.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kaushal.worker.R
import com.kaushal.worker.TemporaryUserProfile
import com.kaushal.worker.ui.components.BottomNavigationBar
import com.kaushal.worker.ui.components.ProfileAvatar
import com.kaushal.worker.ui.theme.*

@Composable
fun DashboardScreen(
    profile: TemporaryUserProfile,
    completedScreens: Int,
    checkpointChapterId: String?,
    checkpointScreenId: String?,
    onNavigate: (String) -> Unit,
    onContinueLearning: () -> Unit
) {
    var showNotifications by rememberSaveable { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { showNotifications = false }
    val progress = (completedScreens / 70f).coerceIn(0f, 1f)

    Scaffold(
        containerColor = Color(0xFFF9FAFC),
        bottomBar = { BottomNavigationBar(current = "Home", onNavigate = onNavigate) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. TOP / UPPER SECTION WITH BACKGROUND IMAGE
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(325.dp)
            ) {
                // Background image area (quarry worker background fading into white)
                Image(
                    painter = painterResource(id = R.drawable.dashboard_bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    // Profile Header Row: Avatar + Greeting + Notification Icon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileAvatar(
                            photoUri = profile.profilePhotoUri,
                            size = 46.dp,
                            onClick = { onNavigate("Profile") }
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = "Good morning,",
                                color = KaushalNavy.copy(alpha = 0.85f),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = profile.name.ifBlank { "Banashree Kundu" },
                                    color = KaushalNavy,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.width(4.dp))
                                Text("👋", style = MaterialTheme.typography.titleMedium)
                            }
                            Text(
                                text = profile.industrialSector.ifBlank { "Mining" },
                                color = KaushalNavy.copy(alpha = 0.75f),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        // Notification icon with red indicator dot
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { showNotifications = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Box {
                                Icon(
                                    imageVector = Icons.Default.NotificationsNone,
                                    contentDescription = "Notifications",
                                    tint = KaushalNavy,
                                    modifier = Modifier.size(24.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(9.dp)
                                        .align(Alignment.TopEnd)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE53935))
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(6.dp))

                    // Large “Let’s Learn, Work Safer Today!” hero section
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.72f)
                            .padding(start = 18.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(Modifier.padding(18.dp)) {
                            Text(
                                text = "Let's Learn",
                                color = KaushalNavy,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Work Safer ",
                                    color = KaushalOrange,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Text(
                                    text = "Today!",
                                    color = KaushalNavy,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }

                            Spacer(Modifier.height(6.dp))

                            Text(
                                text = "Build your skills, stay safe, and grow with Kaushal.",
                                color = KaushalMuted,
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(Modifier.height(14.dp))

                            Button(
                                onClick = { onNavigate("Learn") },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = KaushalOrange),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "EXPLORE TRAINING",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 2. QUICK ACTIONS SECTION (2 columns x 3 rows layout)
            Text(
                text = "Quick Actions",
                color = KaushalNavy,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Row 1: Learning Modules + My Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        icon = "📖",
                        title = "Learning\nModules",
                        backgroundColor = Color(0xFFEDF5FF),
                        accentColor = Color(0xFF2B6CB0),
                        onClick = { onNavigate("Learn") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        icon = "📊",
                        title = "My\nProgress",
                        backgroundColor = Color(0xFFEAF8F0),
                        accentColor = Color(0xFF2F855A),
                        onClick = { onNavigate("Progress") },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 2: Certificates + Field Book
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        icon = "🏆",
                        title = "Certificates",
                        backgroundColor = Color(0xFFFFF9E6),
                        accentColor = Color(0xFFD69E2E),
                        onClick = { onNavigate("Certificates") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        icon = "📕",
                        title = "Field\nBook",
                        backgroundColor = Color(0xFFF5EEFF),
                        accentColor = Color(0xFF805AD5),
                        onClick = { onNavigate("Field Book") },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Row 3: AR Training + Safety Passport
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        icon = "🥽",
                        title = "AR\nTraining",
                        backgroundColor = Color(0xFFFFF2EC),
                        accentColor = Color(0xFFDD6B20),
                        onClick = { onNavigate("AR Training") },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        icon = "🛡️",
                        title = "Safety\nPassport",
                        backgroundColor = Color(0xFFFFEAEA),
                        accentColor = Color(0xFFE53E3E),
                        onClick = { onNavigate("Safety Passport") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // 3. BELOW QUICK ACTIONS: CONTINUE LEARNING (Fire & Explosion Response)
            Text(
                text = "Continue Learning",
                color = KaushalNavy,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Thumbnail graphic with "Module 1" badge overlay
                    Box(
                        modifier = Modifier
                            .size(width = 100.dp, height = 110.dp)
                            .clip(RoundedCornerShape(14.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fx_fire),
                            contentDescription = "Fire & Explosion Response",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Surface(
                            modifier = Modifier
                                .padding(6.dp)
                                .align(Alignment.TopStart),
                            shape = RoundedCornerShape(6.dp),
                            color = Color.White
                        ) {
                            Text(
                                text = "Module 1",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = KaushalNavy
                            )
                        }
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "Fire & Explosion Response",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = KaushalNavy,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options",
                                tint = KaushalMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        // Dynamic local progress calculation (no mock data)
                        Text(
                            text = if (completedScreens > 0) "$completedScreens/70 Lessons Completed" else "0/70 Lessons Completed",
                            style = MaterialTheme.typography.bodySmall,
                            color = KaushalMuted
                        )

                        Spacer(Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = KaushalOrange,
                            trackColor = Color(0xFFFFE8DC)
                        )

                        Spacer(Modifier.height(10.dp))

                        Button(
                            onClick = onContinueLearning,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = KaushalOrange),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (checkpointChapterId != null && checkpointScreenId != null) "CONTINUE" else "START",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White
                                )
                                Spacer(Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))
        }
    }

    if (showNotifications) {
        NotificationPromptDialog(
            onDismiss = { showNotifications = false },
            onTurnOn = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                else showNotifications = false
            }
        )
    }
}

@Composable
private fun QuickActionCard(
    icon: String,
    title: String,
    backgroundColor: Color,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(78.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 26.sp,
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = KaushalNavy,
                    lineHeight = 14.sp
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
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

                Text(
                    text = stringResource(R.string.notif_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = KaushalNavy,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.notif_dialog_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    color = KaushalMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(22.dp))

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