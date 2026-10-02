package com.kaushal.worker.screens.assessment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.kaushal.worker.R
import com.kaushal.worker.data.model.getLocalizedModule1Assessment
import com.kaushal.worker.ui.components.KaushalButton
import com.kaushal.worker.ui.components.ScreenTopBar
import com.kaushal.worker.ui.theme.*

private val CorrectGreen = Color(0xFF2E9B63)

@Composable
fun AssessmentScreen(
    moduleId: String,
    languageCode: String = "en",
    onBack: () -> Unit,
    onCompleted: (score: Int, total: Int) -> Unit
) {
    if (moduleId != "fire") {
        Scaffold(
            topBar = { ScreenTopBar(stringResource(R.string.assessment_title), onBack) },
            containerColor = KaushalCream
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.assessment_soon_title), color = KaushalMuted)
            }
        }
        return
    }

    val assessmentQuestions = remember(languageCode) { getLocalizedModule1Assessment(languageCode) }

    var index by rememberSaveable { mutableIntStateOf(0) }
    var selected by rememberSaveable { mutableIntStateOf(-1) }
    var checked by rememberSaveable { mutableStateOf(false) }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var finished by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(index) {
        scrollState.scrollTo(0)
    }

    if (finished) {
        Scaffold(
            topBar = { ScreenTopBar(stringResource(R.string.m1_ch8_title), onBack) },
            containerColor = KaushalCream
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.assessment_completed), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = KaushalNavy)
                Spacer(Modifier.height(8.dp))
                Text(stringResource(R.string.assessment_your_score), color = KaushalMuted)
                Text("$score / ${assessmentQuestions.size}", style = MaterialTheme.typography.displaySmall, color = KaushalOrange, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                Text(stringResource(R.string.assessment_note), color = KaushalMuted)
                Spacer(Modifier.height(22.dp))
                KaushalButton(stringResource(R.string.action_ok), onClick = { onCompleted(score, assessmentQuestions.size) })
            }
        }
        return
    }

    val question = assessmentQuestions[index]

    Scaffold(
        topBar = { ScreenTopBar(stringResource(R.string.m1_ch8_title), onBack) },
        containerColor = KaushalCream
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 18.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(stringResource(R.string.assessment_question_label, index + 1, assessmentQuestions.size), color = KaushalOrange, fontWeight = FontWeight.Bold)
            LinearProgressIndicator(
                progress = { (index + 1) / assessmentQuestions.size.toFloat() },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = KaushalOrange,
                trackColor = KaushalBorder
            )
            Text(question.question, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = KaushalNavy)

            question.options.forEachIndexed { optionIndex, option ->
                val isCorrect = optionIndex == question.correctOption
                val isSelected = selected == optionIndex
                val borderColor = when {
                    checked && isCorrect -> CorrectGreen
                    checked && isSelected -> KaushalOrange
                    isSelected -> KaushalOrange
                    else -> KaushalBorder
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) Color(0xFFFFF4EA) else Color.White)
                        .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
                        .clickable(enabled = !checked) { selected = optionIndex }
                        .padding(15.dp)
                ) {
                    Text("${'A' + optionIndex}. $option", fontWeight = FontWeight.SemiBold, color = KaushalNavy)
                    if (checked) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            question.feedback[optionIndex],
                            color = if (isCorrect) CorrectGreen else KaushalMuted
                        )
                    }
                }
            }

            if (!checked) {
                KaushalButton(
                    text = stringResource(R.string.btn_check_answer),
                    onClick = {
                        if (selected == question.correctOption) score += 1
                        checked = true
                    },
                    enabled = selected >= 0
                )
            } else {
                KaushalButton(
                    text = stringResource(if (index == assessmentQuestions.lastIndex) R.string.btn_view_result else R.string.btn_next_question),
                    onClick = {
                        if (index == assessmentQuestions.lastIndex) {
                            finished = true
                        } else {
                            index += 1
                            selected = -1
                            checked = false
                        }
                    }
                )
            }
            Spacer(Modifier.height(48.dp))
        }
    }
}
