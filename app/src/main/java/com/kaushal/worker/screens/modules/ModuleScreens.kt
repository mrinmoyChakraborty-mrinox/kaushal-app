package com.kaushal.worker.screens.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kaushal.worker.R
import com.kaushal.worker.data.model.staticModules
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun LearningModulesScreen(
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onModule: (String) -> Unit
) {
    Scaffold(
        containerColor = KaushalCream,
        bottomBar = {
            BottomNavigationBar(
                current = "Learn",
                onNavigate = onNavigate
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ScreenTopBar(stringResource(R.string.modules_title), onBack)
            Column(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(stringResource(R.string.modules_subtitle), style = MaterialTheme.typography.bodyLarge)
                Text(
                    stringResource(R.string.modules_note),
                    color = KaushalMuted,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(16.dp))
                staticModules.forEach { module ->
                    ModuleCard(
                        title = stringResource(module.titleResId),
                        description = stringResource(module.descriptionResId),
                        icon = module.icon,
                        hasAr = module.hasAr
                    ) { onModule(module.id) }
                    Spacer(Modifier.height(10.dp))
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ModuleDetailScreen(
    moduleId: String,
    learningCompleted: Boolean = false,
    arTrainingCompleted: Boolean = false,
    quickSummaryCompleted: Boolean = false,
    onBack: () -> Unit,
    onAr: (String) -> Unit,
    onQuickSummary: (String) -> Unit = {},
    onAssessment: (String) -> Unit,
    onStartModule: (String) -> Unit
) {
    val module = staticModules.firstOrNull { it.id == moduleId } ?: staticModules.first()
    val title = stringResource(module.titleResId)
    val description = stringResource(module.descriptionResId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KaushalCream)
            .verticalScroll(rememberScrollState())
    ) {
        ScreenTopBar(title, onBack)
        Column(Modifier.padding(20.dp)) {
            Text(module.icon, style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.headlineMedium)
            Text(description, color = KaushalMuted, modifier = Modifier.padding(top = 8.dp))
            Spacer(Modifier.height(24.dp))
            KaushalButton(stringResource(R.string.btn_start_learning), onClick = { onStartModule(module.id) })
            Spacer(Modifier.height(12.dp))
            OutlinedKaushalButton(stringResource(R.string.btn_ar_training), { onAr(module.id) })
            Spacer(Modifier.height(12.dp))
            OutlinedKaushalButton("QUICK SUMMARY", { onQuickSummary(module.id) })
            Spacer(Modifier.height(12.dp))
            OutlinedKaushalButton(stringResource(R.string.btn_take_assessment), { onAssessment(module.id) })
            Spacer(Modifier.height(12.dp))
            OutlinedKaushalButton(stringResource(R.string.btn_view_certificate), {})
            Spacer(Modifier.height(18.dp))
            Text(
                stringResource(R.string.module_certificate_locked),
                color = KaushalMuted,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
