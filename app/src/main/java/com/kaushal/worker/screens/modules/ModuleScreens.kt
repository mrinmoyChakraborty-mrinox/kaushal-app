package com.kaushal.worker.screens.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kaushal.worker.data.model.staticModules
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun LearningModulesScreen(onBack: () -> Unit, onModule: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(KaushalCream)) {
        ScreenTopBar("Learning Modules", onBack)
        Column(Modifier.padding(horizontal = 18.dp).verticalScroll(rememberScrollState())) {
            Text("Your safety training modules", style = MaterialTheme.typography.bodyLarge)
            Text("Available product content is shown below. Progress appears only when real state exists.", color = KaushalMuted, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(16.dp))
            staticModules.forEach { module ->
                ModuleCard(module.title, module.description, module.icon, module.hasAr) { onModule(module.id) }
                Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun ModuleDetailScreen(moduleId: String, onBack: () -> Unit, onAr: (String) -> Unit, onAssessment: (String) -> Unit) {
    val module = staticModules.firstOrNull { it.id == moduleId } ?: staticModules.first()
    Column(Modifier.fillMaxSize().background(KaushalCream).verticalScroll(rememberScrollState())) {
        ScreenTopBar(module.title, onBack)
        Column(Modifier.padding(20.dp)) {
            Text(module.icon, style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.height(8.dp))
            Text(module.title, style = MaterialTheme.typography.headlineMedium)
            Text(module.description, color = KaushalMuted, modifier = Modifier.padding(top = 8.dp))
            Spacer(Modifier.height(24.dp))
            KaushalButton("START LEARNING", onClick = {})
            Spacer(Modifier.height(12.dp))
            OutlinedKaushalButton("AR TRAINING", { onAr(module.id) })
            Spacer(Modifier.height(12.dp))
            OutlinedKaushalButton("TAKE ASSESSMENT", { onAssessment(module.id) })
            Spacer(Modifier.height(12.dp))
            OutlinedKaushalButton("🔒  VIEW CERTIFICATE", {})
            Spacer(Modifier.height(18.dp))
            Text("Certificate remains locked until an actual qualifying completion exists.", color = KaushalMuted, style = MaterialTheme.typography.bodySmall)
        }
    }
}
