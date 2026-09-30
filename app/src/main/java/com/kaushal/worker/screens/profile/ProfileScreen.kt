package com.kaushal.worker.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaushal.worker.R
import com.kaushal.worker.TemporaryUserProfile
import com.kaushal.worker.localization.AppLanguage
import com.kaushal.worker.ui.components.*
import com.kaushal.worker.ui.theme.*

@Composable
fun ProfileScreen(
    profile: TemporaryUserProfile,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    onUpdatePhoto: (String?) -> Unit,
    onLogout: () -> Unit
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            onUpdatePhoto(uri.toString())
        }
    }

    Scaffold(
        containerColor = KaushalCream,
        bottomBar = {
            BottomNavigationBar(
                current = "Profile",
                onNavigate = onNavigate
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            ScreenTopBar(stringResource(R.string.profile_title), onBack)
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(8.dp))

                ProfileAvatar(
                    photoUri = profile.profilePhotoUri,
                    size = 104.dp,
                    showAddBadge = profile.profilePhotoUri == null,
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )

                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text(
                            text = if (profile.profilePhotoUri == null) stringResource(R.string.profile_add_photo) else stringResource(R.string.profile_change_photo),
                            color = KaushalOrange,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (profile.profilePhotoUri != null) {
                        TextButton(onClick = { onUpdatePhoto(null) }) {
                            Text(
                                text = stringResource(R.string.profile_remove_photo),
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    profile.name.ifBlank { stringResource(R.string.profile_your_name) },
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    profile.userType.name.lowercase().replaceFirstChar { it.uppercase() },
                    color = KaushalMuted
                )
                Spacer(Modifier.height(18.dp))
            }

            Column(Modifier.padding(horizontal = 20.dp)) {
                ProfileLine(
                    stringResource(R.string.profile_mobile_number),
                    profile.mobileNumber.ifBlank { stringResource(R.string.status_not_entered) }
                )
                ProfileLine(
                    stringResource(R.string.profile_worker_id),
                    profile.workerId.ifBlank { stringResource(R.string.status_not_added) }
                )
                if (profile.workerId.isNotBlank()) {
                    ProfileLine(
                        stringResource(R.string.profile_status),
                        stringResource(R.string.status_verification_pending)
                    )
                }
                ProfileLine(
                    stringResource(R.string.profile_sector),
                    profile.industrialSector.ifBlank { stringResource(R.string.status_not_selected) }
                )
                ProfileLine(
                    stringResource(R.string.profile_subsector),
                    profile.subSector.ifBlank { stringResource(R.string.status_not_selected) }
                )
                ProfileLine(
                    stringResource(R.string.profile_department),
                    profile.department.ifBlank { stringResource(R.string.status_not_selected) }
                )
                ProfileLine(
                    stringResource(R.string.profile_role),
                    profile.role.ifBlank { stringResource(R.string.status_not_selected) }
                )

                val displayLang = when (AppLanguage.fromCode(profile.language)) {
                    AppLanguage.HINDI -> "Hindi (हिन्दी)"
                    AppLanguage.SANTALI -> "Santali (ᱥᱟᱱᱛᱟᱲᱤ)"
                    AppLanguage.ENGLISH -> "English"
                }
                ProfileLine(
                    stringResource(R.string.profile_language),
                    if (profile.language.isBlank()) stringResource(R.string.status_not_selected) else displayLang
                )

                Spacer(Modifier.height(16.dp))
                TextButton(onClick = { onNavigate("Language") }) {
                    Text(stringResource(R.string.profile_language), color = KaushalNavy)
                }
                TextButton(onClick = {}) {
                    Text(stringResource(R.string.profile_notification_settings), color = KaushalNavy)
                }
                TextButton(onClick = {}) {
                    Text(stringResource(R.string.profile_help_support), color = KaushalNavy)
                }
                TextButton(onClick = onLogout) {
                    Text(stringResource(R.string.profile_logout), color = MaterialTheme.colorScheme.error)
                }
                Spacer(Modifier.height(30.dp))
            }
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
