package com.kaushal.worker.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaushal.worker.TemporaryUserProfile
import com.kaushal.worker.ui.components.BottomNavigationBar
import com.kaushal.worker.ui.theme.KaushalCream
import com.kaushal.worker.ui.theme.KaushalMuted
import com.kaushal.worker.ui.theme.KaushalNavy
import com.kaushal.worker.ui.theme.KaushalOrange

@Composable
fun DashboardScreen(
    profile: TemporaryUserProfile,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        containerColor = KaushalCream,
        bottomBar = {
            BottomNavigationBar(
                current = "Home",
                onNavigate = onNavigate
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Good morning,", color = KaushalMuted)
                    Text(
                        text = "${profile.name.ifBlank { "KAUSHAL Learner" }} 👋",
                        color = KaushalNavy,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (profile.industrialSector.isBlank()) "Learner" else profile.industrialSector,
                        color = KaushalMuted
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = KaushalNavy
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        "Start Your Safety Learning Journey",
                        color = KaushalNavy,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "No training modules assigned yet.",
                        color = KaushalMuted
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { onNavigate("Learn") },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KaushalOrange)
                    ) {
                        Text("EXPLORE TRAINING", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(22.dp))
            Text(
                "Quick Actions",
                color = KaushalNavy,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                QuickAction("📖", "Learning\nModules", { onNavigate("Learn") }, Modifier.weight(1f))
                QuickAction("✓", "My\nProgress", { onNavigate("Progress") }, Modifier.weight(1f))
                QuickAction("📜", "Certificates", { onNavigate("Certificates") }, Modifier.weight(1f))
            }
            Spacer(Modifier.height(9.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                QuickAction("📘", "Field\nBook", { onNavigate("Field Book") }, Modifier.weight(1f))
                QuickAction("📱", "AR\nTraining", { onNavigate("AR Training") }, Modifier.weight(1f))
                QuickAction("🛡️", "Safety\nPassport", { }, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun QuickAction(
    icon: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(86.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(icon)
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = KaushalNavy,
                textAlign = TextAlign.Center
            )
        }
    }
}
