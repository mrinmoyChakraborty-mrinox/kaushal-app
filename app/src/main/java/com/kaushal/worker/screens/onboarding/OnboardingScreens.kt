package com.kaushal.worker.screens.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaushal.worker.R

private val KaushalOrange = Color(0xFFFF6800)
private val KaushalNavy = Color(0xFF12385F)
private val KaushalCream = Color(0xFFFFFBF7)
private val KaushalGray = Color(0xFF6E6E6E)
private val KaushalBorder = Color(0xFFE2E2E2)

@Composable
private fun OnboardingBackButton(onBack: () -> Unit) {
    IconButton(
        onClick = onBack,
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(1.dp, KaushalBorder, CircleShape)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = KaushalNavy
        )
    }
}

@Composable
private fun OrangeButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = KaushalOrange,
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFFFC5A0),
            disabledContentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun AccountButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = KaushalNavy
        ),
        border = BorderStroke(1.5.dp, Color(0xFFD7DDE3))
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onLogin: () -> Unit,
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KaushalCream)
    ) {
        Image(
            painter = painterResource(R.drawable.welcome_background),
            contentDescription = "KAUSHAL welcome artwork",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 28.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to",
                color = Color(0xFF252525),
                fontWeight = FontWeight.Bold,
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "KAUSHAL",
                color = KaushalNavy,
                fontWeight = FontWeight.ExtraBold,
                style = androidx.compose.material3.MaterialTheme.typography.displaySmall
            )
            Text(
                text = "Learn • Practice • Stay Safe",
                color = KaushalGray,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 2.dp, bottom = 22.dp)
            )
            OrangeButton("GET STARTED", onGetStarted)
            Spacer(Modifier.height(12.dp))
            AccountButton("I ALREADY HAVE AN ACCOUNT", onLogin)
        }
    }
}

private data class LanguageOption(
    val code: String,
    val text: String,
    val symbol: String
)

@Composable
private fun SpeechBubble(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(1.5.dp, Color(0xFFD6DCE3), RoundedCornerShape(24.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = text,
            color = KaushalNavy,
            fontWeight = FontWeight.Bold,
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LanguageOptionCard(
    option: LanguageOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) Color(0xFFFFF7EF) else Color.White)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) KaushalOrange else KaushalBorder,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(if (selected) Color(0xFFFFE2CC) else Color(0xFFFFDFA4)),
            contentAlignment = Alignment.Center
        ) {
            Text(option.symbol, color = KaushalNavy, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(14.dp))
        Text(
            text = option.text,
            color = if (selected) KaushalOrange else KaushalNavy,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}

@Composable
fun LanguageScreen(
    selected: String,
    onSelect: (String) -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit = {}
) {
    val languages = listOf(
        LanguageOption("en", "I speak English", "A"),
        LanguageOption("hi", "मैं हिन्दी बोलता/बोलती हूँ", "अ"),
        LanguageOption("sat", "ᱤᱧ ᱥᱟᱱᱛᱟᱲᱤ ᱵᱚᱞᱤᱧ", "ᱟ")
    )

    Box(Modifier.fillMaxSize().background(Color.White)) {
        Image(
            painter = painterResource(R.drawable.language_background),
            contentDescription = "Language selection artwork",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Box(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp)
        ) {
            OnboardingBackButton(onBack)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 28.dp)
                .padding(top = 86.dp)
        ) {
            Spacer(Modifier.height(126.dp))
            SpeechBubble("What language do you speak?")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 18.dp)
                .padding(top = 335.dp, bottom = 18.dp)
        ) {
            languages.forEachIndexed { index, language ->
                LanguageOptionCard(
                    option = language,
                    selected = selected == language.code,
                    onClick = { onSelect(language.code) }
                )
                if (index < languages.lastIndex) Spacer(Modifier.height(10.dp))
            }
            Spacer(Modifier.weight(1f))
            OrangeButton(
                text = "CONTINUE",
                onClick = onContinue,
                enabled = selected.isNotBlank()
            )
        }
    }
}

private data class TrainingBenefit(
    val title: String,
    val description: String,
    val symbol: String,
    val tint: Color
)

@Composable
private fun TrainingBenefitRow(benefit: TrainingBenefit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(benefit.tint),
            contentAlignment = Alignment.Center
        ) {
            Text(
                benefit.symbol,
                color = KaushalOrange,
                fontWeight = FontWeight.ExtraBold,
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
            )
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(
                benefit.title,
                color = KaushalNavy,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(3.dp))
            Text(benefit.description, color = KaushalGray)
        }
    }
}

@Composable
fun TrainingBenefitsScreen(
    onContinue: () -> Unit,
    onBack: () -> Unit = {}
) {
    val benefits = listOf(
        TrainingBenefit(
            "Spot hazards in your workplace",
            "Identify potential risks before they become dangerous.",
            "!",
            Color(0xFFFFF0E8)
        ),
        TrainingBenefit(
            "Respond correctly to emergencies",
            "Know what to do during fire, gas leaks, equipment failures, and more.",
            "🚨",
            Color(0xFFEAF4FF)
        ),
        TrainingBenefit(
            "Choose the right PPE and equipment",
            "Select and use the correct protective equipment for your work environment.",
            "🦺",
            Color(0xFFEAFBEF)
        ),
        TrainingBenefit(
            "Practice safely with AR simulations",
            "Experience real workplace situations using your phone.",
            "📱",
            Color(0xFFF2EDFF)
        ),
        TrainingBenefit(
            "Pass your safety assessment & get certified",
            "Complete assessments, earn a verified digital certificate, and build your Safety Passport.",
            "★",
            Color(0xFFFFF7DB)
        )
    )

    Box(Modifier.fillMaxSize().background(Color.White)) {
        Image(
            painter = painterResource(R.drawable.training_background),
            contentDescription = "Training benefits artwork",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Box(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp)
        ) {
            OnboardingBackButton(onBack)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .padding(top = 76.dp)
        ) {
            Spacer(Modifier.height(128.dp))
            SpeechBubble("Here's what you'll be able to do after your training!")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .padding(top = 315.dp, bottom = 18.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(benefits) { benefit ->
                    TrainingBenefitRow(benefit)
                }
            }
            Spacer(Modifier.height(10.dp))
            OrangeButton("CONTINUE", onContinue)
        }
    }
}
