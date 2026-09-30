package com.kaushal.worker.screens.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kaushal.worker.R
import com.kaushal.worker.ui.components.KaushalButton
import com.kaushal.worker.ui.components.KaushalTextField
import com.kaushal.worker.ui.components.ProfileAvatar
import com.kaushal.worker.ui.components.ScreenTopBar
import com.kaushal.worker.ui.components.WorkerHero
import com.kaushal.worker.ui.theme.KaushalCream
import com.kaushal.worker.ui.theme.KaushalMuted
import com.kaushal.worker.ui.theme.KaushalNavy
import com.kaushal.worker.ui.theme.KaushalOrange

@Composable
fun LoginScreen(
    onSendOtp: (String) -> Unit,
    onRegister: () -> Unit,
    onBack: () -> Unit
) {
    var mobile by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val invalidMobileErr = stringResource(R.string.err_invalid_mobile)

    Column(
        Modifier
            .fillMaxSize()
            .background(KaushalCream)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        ScreenTopBar(stringResource(R.string.auth_app_title), onBack = onBack)
        WorkerHero()

        Text(stringResource(R.string.login_welcome), style = MaterialTheme.typography.headlineMedium, color = KaushalNavy)
        Text(
            stringResource(R.string.login_subtitle),
            color = KaushalMuted,
            modifier = Modifier.padding(top = 5.dp, bottom = 18.dp)
        )

        KaushalTextField(
            value = mobile,
            onValueChange = {
                mobile = it.filter(Char::isDigit).take(10)
                error = ""
            },
            label = stringResource(R.string.label_mobile)
        )

        if (error.isNotBlank()) {
            Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 6.dp))
        }

        Spacer(Modifier.height(14.dp))
        KaushalButton(stringResource(R.string.btn_send_otp), {
            if (mobile.length == 10) onSendOtp(mobile)
            else error = invalidMobileErr
        })

        Spacer(Modifier.height(18.dp))
        Text(
            stringResource(R.string.login_prompt_register),
            color = KaushalMuted,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextButton(
            onClick = onRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_register), color = KaushalOrange, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LoginOtpScreen(
    mobile: String,
    onVerify: (String) -> Unit,
    onBack: () -> Unit
) {
    OtpScreen(
        title = stringResource(R.string.otp_title),
        subtitle = stringResource(R.string.otp_subtitle),
        mobile = "+91 $mobile",
        button = stringResource(R.string.btn_verify_login),
        onVerify = onVerify,
        onBack = onBack
    )
}

@Composable
fun RegisterScreen(
    onCreateAccount: (String, String, String?) -> Unit,
    onLogin: () -> Unit,
    onBack: () -> Unit
) {
    var mobile by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf("") }

    val invalidMobileErr = stringResource(R.string.err_invalid_mobile)
    val enterNameErr = stringResource(R.string.err_enter_name)

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            photoUri = uri.toString()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(KaushalCream)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenTopBar(stringResource(R.string.auth_app_title), onBack = onBack)

        Text(
            stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineMedium,
            color = KaushalNavy,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            stringResource(R.string.register_subtitle),
            color = KaushalMuted,
            modifier = Modifier.align(Alignment.Start).padding(top = 5.dp, bottom = 16.dp)
        )

        // Photo Upload Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            ProfileAvatar(
                photoUri = photoUri,
                size = 96.dp,
                showAddBadge = photoUri == null,
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = if (photoUri == null) stringResource(R.string.profile_add_photo) else stringResource(R.string.profile_change_photo),
                color = KaushalOrange,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.profile_photo_optional),
                color = KaushalMuted,
                style = MaterialTheme.typography.bodySmall
            )
            if (photoUri != null) {
                TextButton(onClick = { photoUri = null }) {
                    Text(stringResource(R.string.profile_remove_photo), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        KaushalTextField(
            mobile,
            { mobile = it.filter(Char::isDigit).take(10); error = "" },
            stringResource(R.string.label_mobile)
        )
        Spacer(Modifier.height(12.dp))
        KaushalTextField(name, { name = it; error = "" }, stringResource(R.string.label_full_name))

        if (error.isNotBlank()) {
            Text(
                error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Start).padding(top = 6.dp)
            )
        }

        Spacer(Modifier.height(14.dp))
        KaushalButton(stringResource(R.string.btn_create_account), onClick = {
            when {
                mobile.length != 10 -> error = invalidMobileErr
                name.trim().length < 2 -> error = enterNameErr
                else -> onCreateAccount(name.trim(), mobile, photoUri)
            }
        })

        TextButton(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.register_prompt_login), color = KaushalNavy)
        }
    }
}

@Composable
fun RegisterOtpScreen(
    mobile: String,
    onVerify: () -> Unit,
    onBack: () -> Unit
) {
    OtpScreen(
        title = stringResource(R.string.otp_title),
        subtitle = stringResource(R.string.otp_subtitle),
        mobile = if (mobile.isBlank()) stringResource(R.string.label_mobile) else "+91 $mobile",
        button = stringResource(R.string.btn_verify_continue),
        onVerify = { onVerify() },
        onBack = onBack
    )
}

@Composable
private fun OtpScreen(
    title: String,
    subtitle: String,
    mobile: String,
    button: String,
    onVerify: (String) -> Unit,
    onBack: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val invalidOtpErr = stringResource(R.string.err_invalid_otp)

    Column(
        Modifier
            .fillMaxSize()
            .background(KaushalCream)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        ScreenTopBar(title, onBack = onBack)
        WorkerHero()

        Text(title, style = MaterialTheme.typography.headlineMedium, color = KaushalNavy)
        Text(subtitle, color = KaushalMuted, modifier = Modifier.padding(top = 6.dp))
        Text(
            mobile,
            style = MaterialTheme.typography.titleMedium,
            color = KaushalNavy,
            modifier = Modifier.padding(top = 3.dp, bottom = 18.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            repeat(6) { index ->
                OutlinedTextField(
                    value = otp.getOrNull(index)?.toString() ?: "",
                    onValueChange = { value ->
                        val digit = value.filter(Char::isDigit).takeLast(1)
                        val chars = otp.padEnd(6, ' ').toCharArray()
                        if (digit.isNotEmpty()) chars[index] = digit[0] else chars[index] = ' '
                        otp = String(chars).trimEnd()
                        error = ""
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(textAlign = androidx.compose.ui.text.style.TextAlign.Center),
                    shape = MaterialTheme.shapes.medium
                )
            }
        }

        TextButton(
            onClick = { },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.otp_resend), color = KaushalOrange)
        }

        if (error.isNotBlank()) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(10.dp))
        KaushalButton(button, onClick = {
            if (otp.length == 6) onVerify(otp)
            else error = invalidOtpErr
        })

        Spacer(Modifier.height(24.dp))
        Text(
            stringResource(R.string.otp_testing_note),
            color = KaushalMuted,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
