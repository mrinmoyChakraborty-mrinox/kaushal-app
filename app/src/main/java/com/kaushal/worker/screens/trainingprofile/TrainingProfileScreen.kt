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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

    val subOptions = when (sector) {
        "Mining" -> listOf("Open Cast", "Underground Mining")
        "Steel Manufacturing" -> listOf("Hot Metal Production", "Rolling Mills and Processing")
        "Mica Processing" -> listOf("Scrap Mining", "Splitting")
        else -> emptyList()
    }

    if (showSectorDialog) {
        ChoiceDialog(
            title = "Select Industrial Sector",
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
            title = "Select Sub-Sector",
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KaushalCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
    ) {
        ScreenTopBar("Set Up Your Training Profile", onBack = onBack)

        Text(
            "Help us personalise your learning experience.",
            color = KaushalMuted,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(Modifier.height(14.dp))
        StepIndicator()
        Spacer(Modifier.height(16.dp))

        ReadOnlyField("Full Name", initial.name)
        Spacer(Modifier.height(10.dp))
        ReadOnlyField("Mobile Number", if (initial.mobileNumber.isBlank()) "Not provided" else "+91 ${initial.mobileNumber}")
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = workerId,
            onValueChange = { workerId = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Worker ID (Optional)") },
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
            label = "Industrial Sector",
            value = sector,
            placeholder = "Select industrial sector",
            onClick = { showSectorDialog = true }
        )

        Spacer(Modifier.height(10.dp))
        SelectField(
            label = "Sub-Sector",
            value = subSector,
            placeholder = if (sector.isBlank()) "Select industrial sector first" else "Select sub-sector",
            enabled = sector.isNotBlank(),
            onClick = { showSubSectorDialog = true }
        )

        if (error.isNotBlank()) {
            Text(
                error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(Modifier.height(18.dp))
        KaushalButton("CONTINUE", onClick = {
            when {
                sector.isBlank() -> error = "Please select an industrial sector."
                subSector.isBlank() -> error = "Please select a sub-sector."
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

@Composable
private fun StepIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        (1..4).forEach { step ->
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                Surface(
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = if (step == 1) KaushalOrange else Color(0xFFE7EAF0),
                    modifier = Modifier.size(30.dp)
                ) {
                    Box(contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text(
                            step.toString(),
                            color = if (step == 1) Color.White else KaushalMuted,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if (step < 4) {
                    // spacing is handled by the parent; the target only needs a compact step indicator.
                }
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
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,

            // Important:
            // The outer Box handles the click.
            // Keeping this field disabled prevents it from consuming the tap.
            enabled = false,

            modifier = Modifier.fillMaxWidth(),

            label = {
                Text(label)
            },

            placeholder = {
                Text(placeholder)
            },

            trailingIcon = {
                Text(
                    "⌄",
                    color = if (enabled) KaushalNavy else KaushalMuted
                )
            },

            singleLine = true,
            shape = RoundedCornerShape(15.dp),

            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = KaushalBorder,
                disabledTextColor = KaushalNavy,
                disabledLabelColor = KaushalMuted,
                disabledPlaceholderColor = KaushalMuted,
                disabledTrailingIconColor = KaushalNavy
            )
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
                Text("CLOSE", color = KaushalOrange)
            }
        }
    )
}
