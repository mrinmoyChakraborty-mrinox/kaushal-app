package com.kaushal.worker.screens.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kaushal.worker.R
import com.kaushal.worker.data.model.*
import com.kaushal.worker.ui.components.KaushalButton
import com.kaushal.worker.ui.components.ScreenTopBar
import com.kaushal.worker.ui.theme.*

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.ui.res.stringResource
import com.kaushal.worker.tts.rememberTtsManager

private val DecisionGreen = Color(0xFF2E9B63)

private fun drawableId(name: String, context: android.content.Context): Int =
    context.resources.getIdentifier(name, "drawable", context.packageName)

@Composable
private fun StorySceneVisual(screen: Module1StoryScreen, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.4f)
            .clip(RoundedCornerShape(18.dp))
            .border(1.dp, KaushalBorder, RoundedCornerShape(18.dp))
            .background(Color.White)
    ) {
        val bg = drawableId(screen.backgroundAsset, context)
        if (bg != 0) {
            androidx.compose.foundation.Image(
                painter = painterResource(bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 12.dp, end = 12.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            screen.characterAssets.forEachIndexed { index, token ->
                val parts = token.split(":")
                val character = parts.getOrNull(0).orEmpty()
                val pose = parts.getOrNull(1).orEmpty()
                val asset = "char_${character}_${pose}"
                val id = drawableId(asset, context)
                if (id != 0) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(id),
                        contentDescription = character,
                        modifier = Modifier
                            .width(if (screen.characterAssets.size > 1) 120.dp else 150.dp)
                            .fillMaxHeight(0.88f)
                            .padding(horizontal = if (index == 0) 2.dp else 6.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

        if (screen.objectAssets.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                screen.objectAssets.take(3).forEach { asset ->
                    val id = drawableId(asset, context)
                    if (id != 0) {
                        androidx.compose.foundation.Image(
                            painter = painterResource(id),
                            contentDescription = null,
                            modifier = Modifier.size(56.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Module1ChapterListScreen(
    languageCode: String = "en",
    onBack: () -> Unit,
    onChapter: (String) -> Unit,
    completedChapters: Set<String>,
    onAssessment: () -> Unit
) {
    val chapters = remember(languageCode) { getLocalizedModule1Chapters(languageCode) }

    Scaffold(
        topBar = {
            ScreenTopBar(stringResource(R.string.module_fire_title), onBack)
        },
        containerColor = KaushalCream
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 18.dp, top = 12.dp, end = 18.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(stringResource(R.string.m1_header), style = MaterialTheme.typography.labelLarge, color = KaushalOrange)
                Text(
                    stringResource(R.string.m1_subtitle),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = KaushalNavy
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    stringResource(R.string.m1_chapter_desc),
                    color = KaushalMuted
                )
            }
            items(chapters) { chapter ->
                val done = chapter.id in completedChapters
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .border(1.dp, if (done) DecisionGreen else KaushalBorder, RoundedCornerShape(18.dp))
                        .clickable { onChapter(chapter.id) }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (done) DecisionGreen.copy(alpha = .12f) else KaushalCream
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (done) {
                            Icon(Icons.Default.CheckCircle, null, tint = DecisionGreen)
                        } else {
                            Text(chapter.id.takeLast(2), color = KaushalOrange, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(chapter.title, fontWeight = FontWeight.Bold, color = KaushalNavy)
                        Text(stringResource(R.string.m1_screens_count), style = MaterialTheme.typography.bodySmall, color = KaushalMuted)
                    }
                    Icon(Icons.Default.PlayArrow, null, tint = KaushalOrange)
                }
            }
            item {
                Spacer(Modifier.height(4.dp))
                HorizontalDivider(color = KaushalBorder)
                Spacer(Modifier.height(12.dp))
                Text(stringResource(R.string.m1_ch8_header), style = MaterialTheme.typography.labelLarge, color = KaushalOrange)
                Text(stringResource(R.string.m1_ch8_title), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.m1_ch8_desc), color = KaushalMuted)
                Spacer(Modifier.height(10.dp))
                KaushalButton(stringResource(R.string.btn_take_assessment), onAssessment)
            }
        }
    }
}

@Composable
fun Module1StoryPlayerScreen(
    chapterId: String,
    languageCode: String = "en",
    onBack: () -> Unit,
    onPracticeAr: (scenarioId: String, screenId: String) -> Unit,
    onChapterComplete: () -> Unit,
    onDecisionAnswered: (screenId: String, selectedOption: Int, isCorrect: Boolean) -> Unit,
    onScreenCompleted: (screenId: String) -> Unit
) {
    val localizedChapters = remember(languageCode) { getLocalizedModule1Chapters(languageCode) }
    val chapter = localizedChapters.firstOrNull { it.id == chapterId } ?: localizedChapters.first()
    var screenIndex by rememberSaveable(chapterId) { mutableIntStateOf(0) }
    var selectedOption by rememberSaveable(chapterId, screenIndex) { mutableStateOf<Int?>(null) }
    var feedbackVisible by rememberSaveable(chapterId, screenIndex) { mutableStateOf(false) }
    val screen = chapter.screens[screenIndex]
    val isLast = screenIndex == chapter.screens.lastIndex

    val ttsManager = rememberTtsManager()
    val scrollState = rememberScrollState()

    // Reset scroll and stop speech when changing screens
    LaunchedEffect(screenIndex) {
        scrollState.scrollTo(0)
        ttsManager.stop()
    }

    LaunchedEffect(feedbackVisible) {
        if (feedbackVisible) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    val handleBack = {
        ttsManager.stop()
        if (screenIndex > 0) {
            screenIndex -= 1
            selectedOption = null
            feedbackVisible = false
        } else {
            onBack()
        }
    }

    BackHandler(enabled = true, onBack = handleBack)

    fun continueStory() {
        ttsManager.stop()
        onScreenCompleted(screen.id)
        if (isLast) {
            onChapterComplete()
        } else {
            screenIndex += 1
            selectedOption = null
            feedbackVisible = false
        }
    }

    Scaffold(
        topBar = {
            ScreenTopBar(
                title = chapter.title,
                onBack = handleBack,
                actions = {
                    IconButton(
                        onClick = {
                            val textToRead = buildString {
                                append(screen.title)
                                append(". ")
                                append(screen.storyText)
                                if (screen.isDecisionPoint && screen.decision != null) {
                                    append(". ")
                                    append(screen.decision.question)
                                    screen.decision.options.forEachIndexed { i, opt ->
                                        append(". ")
                                        append("${('A' + i)}: $opt")
                                    }
                                    if (feedbackVisible && selectedOption != null) {
                                        append(". ")
                                        append(screen.decision.feedback.getOrElse(selectedOption!!) { "" })
                                    }
                                }
                            }
                            ttsManager.speak(textToRead, languageCode)
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (ttsManager.isSpeaking) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeOff,
                            contentDescription = "Read Aloud",
                            tint = if (ttsManager.isSpeaking) KaushalOrange else KaushalNavy
                        )
                    }
                }
            )
        },
        containerColor = KaushalCream
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 18.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.m1_screen_label, screen.screenNumber, chapter.screens.size),
                    color = KaushalOrange,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    stringResource(R.string.m1_chapter_label, chapter.id.takeLast(2)),
                    color = KaushalMuted,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            LinearProgressIndicator(
                progress = { (screen.screenNumber / 10f).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                color = KaushalOrange,
                trackColor = KaushalBorder
            )

            Text(screen.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = KaushalNavy)
            StorySceneVisual(screen)
            Text(screen.storyText, style = MaterialTheme.typography.bodyLarge, color = KaushalText)

            if (screen.isDecisionPoint && screen.decision != null) {
                if (screen.arAvailable) {
                    Button(
                        onClick = {
                            ttsManager.stop()
                            onPracticeAr(screen.arScenarioId ?: "module1", screen.id)
                        },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DecisionGreen)
                    ) {
                        Text(stringResource(R.string.btn_practice_ar), fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Text(screen.decision.question, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = KaushalNavy)
                screen.decision.options.forEachIndexed { index, option ->
                    val selected = selectedOption == index
                    val isCorrect = index == screen.decision.correctOption
                    val borderColor = when {
                        feedbackVisible && selected && isCorrect -> DecisionGreen
                        feedbackVisible && selected && !isCorrect -> KaushalOrange
                        selected -> KaushalOrange
                        else -> KaushalBorder
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selected) Color(0xFFFFF4EA) else Color.White)
                            .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
                            .clickable(enabled = !feedbackVisible) {
                                selectedOption = index
                                feedbackVisible = false
                            }
                            .padding(15.dp)
                    ) {
                        Text("${'A' + index}. $option", fontWeight = FontWeight.SemiBold, color = KaushalNavy)
                    }
                }

                if (!feedbackVisible) {
                    KaushalButton(
                        text = stringResource(R.string.btn_submit),
                        enabled = selectedOption != null,
                        onClick = {
                            val chosen = selectedOption ?: return@KaushalButton
                            feedbackVisible = true
                            onDecisionAnswered(screen.id, chosen, chosen == screen.decision.correctOption)
                        }
                    )
                }

                if (feedbackVisible && selectedOption != null) {
                    val chosenIndex = selectedOption!!
                    val isChosenCorrect = chosenIndex == screen.decision.correctOption

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isChosenCorrect) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
                        ),
                        border = BorderStroke(1.dp, if (isChosenCorrect) DecisionGreen else KaushalOrange)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = if (isChosenCorrect) stringResource(R.string.feedback_correct) else stringResource(R.string.feedback_incorrect),
                                fontWeight = FontWeight.Bold,
                                color = if (isChosenCorrect) DecisionGreen else KaushalOrangeDark
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = screen.decision.feedback.getOrElse(chosenIndex) { "" },
                                color = KaushalNavy,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    KaushalButton(
                        text = stringResource(if (isLast) R.string.btn_complete_chapter else R.string.action_continue),
                        onClick = ::continueStory
                    )
                }
            } else {
                KaushalButton(
                    text = stringResource(if (isLast) R.string.btn_complete_chapter else R.string.action_continue),
                    onClick = ::continueStory
                )
            }
            Spacer(Modifier.height(48.dp))
        }
    }
}

