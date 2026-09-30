package com.kaushal.worker.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kaushal.worker.ui.components.KaushalButton
import com.kaushal.worker.ui.components.KaushalTextField
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

    Column(
        Modifier
            .fillMaxSize()
            .background(KaushalCream)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        ScreenTopBar("KAUSHAL", onBack = onBack)
        WorkerHero()

        Text("Welcome back!", style = MaterialTheme.typography.headlineMedium, color = KaushalNavy)
        Text(
            "Sign in with your mobile number and a secure OTP.",
            color = KaushalMuted,
            modifier = Modifier.padding(top = 5.dp, bottom = 18.dp)
        )

        KaushalTextField(
            value = mobile,
            onValueChange = {
                mobile = it.filter(Char::isDigit).take(10)
                error = ""
            },
            label = "+91   Mobile Number"
        )

        if (error.isNotBlank()) {
            Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 6.dp))
        }

        Spacer(Modifier.height(14.dp))
        KaushalButton("SEND OTP", {
            if (mobile.length == 10) onSendOtp(mobile)
            else error = "Please enter a valid 10-digit mobile number."
        })

        Spacer(Modifier.height(18.dp))
        Text(
            "Don't have an account?",
            color = KaushalMuted,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextButton(
            onClick = onRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register", color = KaushalOrange, fontWeight = FontWeight.Bold)
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
        title = "Enter OTP",
        subtitle = "We have sent a 6-digit OTP to",
        mobile = "+91 $mobile",
        button = "VERIFY & LOGIN",
        onVerify = onVerify,
        onBack = onBack
    )
}

@Composable
fun RegisterScreen(
    onCreateAccount: (String, String) -> Unit,
    onLogin: () -> Unit,
    onBack: () -> Unit
) {
    var mobile by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .background(KaushalCream)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        ScreenTopBar("KAUSHAL", onBack = onBack)
        WorkerHero()

        Text("Create Your KAUSHAL Account", style = MaterialTheme.typography.headlineMedium, color = KaushalNavy)
        Text(
            "Enter your details to get started.",
            color = KaushalMuted,
            modifier = Modifier.padding(top = 5.dp, bottom = 18.dp)
        )

        KaushalTextField(
            mobile,
            { mobile = it.filter(Char::isDigit).take(10); error = "" },
            "+91   Mobile Number"
        )
        Spacer(Modifier.height(12.dp))
        KaushalTextField(name, { name = it; error = "" }, "Full Name")

        if (error.isNotBlank()) {
            Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 6.dp))
        }

        Spacer(Modifier.height(14.dp))
        KaushalButton("CREATE ACCOUNT", onClick = {
            when {
                mobile.length != 10 -> error = "Please enter a valid 10-digit mobile number."
                name.trim().length < 2 -> error = "Please enter your full name."
                else -> onCreateAccount(name.trim(), mobile)
            }
        })

        TextButton(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Already have an account?  Login", color = KaushalNavy)
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
        title = "Enter OTP",
        subtitle = "We have sent a 6-digit OTP to",
        mobile = if (mobile.isBlank()) "your mobile number" else "+91 $mobile",
        button = "VERIFY & CONTINUE",
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
            Text("Didn't receive code?  Resend OTP", color = KaushalOrange)
        }

        if (error.isNotBlank()) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(10.dp))
        KaushalButton(button, onClick = {
            if (otp.length == 6) onVerify(otp)
            else error = "Please enter all 6 OTP digits."
        })

        Spacer(Modifier.height(24.dp))
        Text(
            "UI testing only • no real SMS is sent.",
            color = KaushalMuted,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
