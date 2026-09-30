package com.kaushal.worker.screens.profile
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushal.worker.TemporaryUserProfile
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun ProfileScreen(profile: TemporaryUserProfile, onBack: () -> Unit, onLogout: () -> Unit) {
    Column(Modifier.fillMaxSize().background(KaushalCream).verticalScroll(rememberScrollState())) {
        ScreenTopBar("My Profile", onBack)
        Column(Modifier.padding(horizontal = 20.dp)) {
            Text(profile.name.ifBlank { "Your Name" }, style = MaterialTheme.typography.headlineMedium)
            Text(profile.userType?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Learner", color = KaushalMuted)
            Spacer(Modifier.height(18.dp))
            ProfileLine("Mobile Number", profile.mobileNumber.ifBlank { "Not entered" })
            ProfileLine("Worker ID", profile.workerId.ifBlank { "Not added" })
            if (profile.workerId.isNotBlank()) ProfileLine("Status", "Verification pending")
            ProfileLine("Industrial Sector", profile.industrialSector.ifBlank { "Not selected" })
            ProfileLine("Sub-Sector", profile.subSector.ifBlank { "Not selected" })
            ProfileLine("Department / Operation", profile.department.ifBlank { "Not selected" })
            ProfileLine("Role", profile.role.ifBlank { "Not selected" })
            ProfileLine("Language", profile.language.ifBlank { "Not selected" })
            Spacer(Modifier.height(16.dp))
            TextButton(onClick = {}) { Text("Language", color = KaushalNavy) }
            TextButton(onClick = {}) { Text("Notification Settings", color = KaushalNavy) }
            TextButton(onClick = {}) { Text("Help & Support", color = KaushalNavy) }
            TextButton(onClick = onLogout) { Text("Logout", color = MaterialTheme.colorScheme.error) }
            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ProfileLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 9.dp)) {
        Text(label, color = KaushalMuted, modifier = Modifier.weight(1f))
        Text(value, color = KaushalNavy, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
    }
}
