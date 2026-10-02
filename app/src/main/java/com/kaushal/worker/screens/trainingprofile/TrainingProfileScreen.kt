package com.kaushal.worker.screens.trainingprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaushal.worker.R
import com.kaushal.worker.TemporaryUserProfile
import com.kaushal.worker.ui.components.KaushalButton
import com.kaushal.worker.ui.components.ScreenTopBar
import com.kaushal.worker.ui.theme.KaushalBorder
import com.kaushal.worker.ui.theme.KaushalCream
import com.kaushal.worker.ui.theme.KaushalMuted
import com.kaushal.worker.ui.theme.KaushalNavy
import com.kaushal.worker.ui.theme.KaushalOrange

@Composable
fun TrainingProfileScreen(
    initial: TemporaryUserProfile,
    onBack: () -> Unit,
    onContinue: (String, String, String, String) -> Unit
) {
    var sector by remember { mutableStateOf(initial.industrialSector) }
    var subSector by remember { mutableStateOf(initial.subSector) }
    var workerId by remember { mutableStateOf(initial.workerId) }
    var showSectorDialog by remember { mutableStateOf(false) }
    var showSubSectorDialog by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val errSector = stringResource(R.string.tp_err_sector)
    val errSubSector = stringResource(R.string.tp_err_subsector)

    val subOptions = when (sector) {
        "Mining" -> listOf("Open Cast", "Underground Mining")
        "Steel Manufacturing" -> listOf("Hot Metal Production", "Rolling Mills and Processing")
        "Mica Processing" -> listOf("Scrap Mining", "Splitting")
        else -> emptyList()
    }

    if (showSectorDialog) {
        ChoiceDialog(
            title = stringResource(R.string.tp_dialog_sector_title),
            options = listOf("Mining", "Steel Manufacturing", "Mica Processing"),
            selected = sector,
            onDismiss = { showSectorDialog = false },
            onSelect = {
                sector = it
                subSector = ""
                showSectorDialog = false
                error = ""
            }
        )
    }

    if (showSubSectorDialog) {
        ChoiceDialog(
            title = stringResource(R.string.tp_dialog_subsector_title),
            options = subOptions,
            selected = subSector,
            onDismiss = { showSubSectorDialog = false },
            onSelect = {
                subSector = it
                showSubSectorDialog = false
                error = ""
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KaushalCream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(6.dp))
            ScreenTopBar(stringResource(R.string.tp_title), onBack = onBack)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
                Text(
                    stringResource(R.string.tp_subtitle),
                    color = KaushalOrange,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(16.dp))

                ReadOnlyField(stringResource(R.string.label_full_name), initial.name)
                Spacer(Modifier.height(10.dp))
                ReadOnlyField(
                    stringResource(R.string.profile_mobile_number),
                    if (initial.mobileNumber.isBlank()) stringResource(R.string.status_not_entered) else "+91 ${initial.mobileNumber}"
                )
                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = workerId,
                    onValueChange = { workerId = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.tp_worker_id_label)) },
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KaushalOrange,
                        focusedLabelColor = KaushalOrange,
                        unfocusedBorderColor = KaushalBorder
                    )
                )

                Spacer(Modifier.height(10.dp))
                SelectField(
                    label = stringResource(R.string.profile_sector),
                    value = sector,
                    placeholder = stringResource(R.string.tp_select_sector),
                    onClick = { showSectorDialog = true }
                )

                Spacer(Modifier.height(10.dp))
                SelectField(
                    label = stringResource(R.string.profile_subsector),
                    value = subSector,
                    placeholder = if (sector.isBlank()) stringResource(R.string.tp_select_sector_first) else stringResource(R.string.tp_select_subsector),
                    enabled = sector.isNotBlank(),
                    onClick = { showSubSectorDialog = true },
                    onDisabledClick = { error = errSector }
                )

                if (error.isNotBlank()) {
                    Text(
                        error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(Modifier.height(20.dp))
                KaushalButton(stringResource(R.string.action_continue), onClick = {
                    when {
                        sector.isBlank() -> error = errSector
                        subSector.isBlank() -> error = errSubSector
                        else -> onContinue(
                            initial.language.ifBlank { "en" },
                            sector,
                            subSector,
                            workerId.trim()
                        )
                    }
                })
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ReadOnlyField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true,
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = KaushalBorder,
            unfocusedBorderColor = KaushalBorder,
            disabledTextColor = KaushalNavy,
            unfocusedTextColor = KaushalNavy
        )
    )
}

@Composable
private fun SelectField(
    label: String,
    value: String,
    placeholder: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    onDisabledClick: (() -> Unit)? = null
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            trailingIcon = {
                Text(
                    "⌄",
                    color = if (enabled) KaushalNavy else KaushalMuted,
                    fontWeight = FontWeight.Bold
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (enabled) KaushalOrange else KaushalBorder,
                unfocusedBorderColor = KaushalBorder,
                disabledBorderColor = KaushalBorder,
                disabledTextColor = KaushalNavy,
                disabledLabelColor = KaushalMuted,
                disabledPlaceholderColor = KaushalMuted,
                disabledTrailingIconColor = KaushalMuted
            )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    if (enabled) {
                        onClick()
                    } else {
                        onDisabledClick?.invoke()
                    }
                }
        )
    }
}

@Composable
private fun ChoiceDialog(
    title: String,
    options: List<String>,
    selected: String,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, color = KaushalNavy, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEach { option ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(option) },
                        shape = RoundedCornerShape(14.dp),
                        color = if (option == selected) Color(0xFFFFF3E9) else Color.White,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (option == selected) KaushalOrange else KaushalBorder
                        )
                    ) {
                        Text(
                            option,
                            modifier = Modifier.padding(15.dp),
                            color = if (option == selected) KaushalOrange else KaushalNavy,
                            fontWeight = if (option == selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.action_close), color = KaushalOrange)
            }
        }
    )
}
