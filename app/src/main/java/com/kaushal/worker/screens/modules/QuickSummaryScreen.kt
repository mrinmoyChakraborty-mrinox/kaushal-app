package com.kaushal.worker.screens.modules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaushal.worker.ui.components.ScreenTopBar
import com.kaushal.worker.ui.theme.*

private data class SummarySection(val title: String, val bullets: List<String>)

private val pages = listOf(
    listOf(
        SummarySection("1. THE CORE RULE", listOf(
            "Notice a hazard early → report it → follow the workplace/site procedure.",
            "Do not wait for flames, smoke or a major incident before responding to a known unsafe condition.",
            "Do not investigate a suspected fire or hazardous atmosphere by moving closer or touching equipment.",
            "During an emergency, move to safety using the designated emergency route and follow instructions."
        )),
        SummarySection("2. BEFORE A FIRE STARTS — PREVENTION", listOf(
            "Keep combustible material away from heat and possible ignition sources.",
            "Report unsafe conditions such as oily/combustible material near hot equipment.",
            "Keep passages and emergency routes clear.",
            "Let the responsible/trained person make the area safe when the procedure requires it.",
            "A hazard can exist before smoke or flames appear."
        )),
        SummarySection("3. WHEN SMOKE OR FIRE APPEARS", listOf(
            "Stop and keep away from the danger area.",
            "Raise the alarm/report the situation according to site procedure.",
            "Follow the designated emergency route; do not choose a shortcut just because it looks faster.",
            "Go to the designated assembly point and stay there.",
            "Wait for instructions. Do not return for belongings or re-enter the affected area without authorization.",
            "If another worker is missing, inform responsible emergency personnel — do not send yourself or untrained workers back."
        ))
    ),
    listOf(
        SummarySection("4. FIRE EXTINGUISHER — IMPORTANT LIMIT", listOf(
            "A nearby extinguisher does NOT mean every worker should use it.",
            "Use an extinguisher only when appropriately trained/authorized, the situation is suitable, and site procedure permits it.",
            "The correct extinguisher matters because different fire situations require appropriate firefighting methods.",
            "An untrained worker should remain at a safe location and follow responsible-person instructions."
        )),
        SummarySection("5. WHEN THERE MAY BE AN EXPLOSION / FLAMMABLE-ATMOSPHERE RISK", listOf(
            "If you suspect a flammable atmosphere, move away and report the hazard.",
            "Do NOT create an ignition source or take an unauthorized action that could create one.",
            "Do not enter a restricted area just to look.",
            "Ventilation and atmospheric conditions may need assessment by responsible personnel.",
            "Stay outside until required checks and controls are completed and you are authorized to return.",
            "No visible flame does not mean the area is safe."
        )),
        SummarySection("6. EMERGENCY EVACUATION — REMEMBER THIS SEQUENCE", listOf(
            "1. Alarm / emergency reported",
            "2. Stop normal work and follow the emergency procedure",
            "3. Use the designated emergency route",
            "4. Do not run, push, improvise or take an unapproved shortcut",
            "5. Reach the assembly point",
            "6. Stay there for attendance/checking and instructions",
            "7. Do not re-enter until authorized"
        ))
    ),
    listOf(
        SummarySection("7. THE 7 DECISIONS TO REMEMBER", listOf(
            "Oily/combustible material near heat? Report it and follow site procedure.",
            "Smoke from equipment? Raise the alarm/report it and keep away.",
            "Emergency route vs shortcut? Use the designated emergency route.",
            "Reached assembly point? Stay and wait for instructions.",
            "Small fire + extinguisher? Only trained/authorized people should consider using it when suitable.",
            "Possible flammable atmosphere? Avoid ignition sources and unauthorized actions; report and move to safety.",
            "Belongings left behind / someone missing? Do not re-enter yourself; inform responsible emergency personnel."
        )),
        SummarySection("8. ONE-LINE MEMORY RULE", listOf(
            "SEE HAZARD → REPORT → MOVE TO SAFETY → FOLLOW THE DESIGNATED ROUTE → ASSEMBLE → WAIT FOR INSTRUCTIONS → RE-ENTER ONLY WHEN AUTHORIZED"
        ))
    )
)

@Composable
fun QuickSummaryScreen(
    completed: Boolean,
    onBack: () -> Unit,
    onCompleted: () -> Unit
) {
    var page by rememberSaveable { mutableIntStateOf(0) }
    val sections = pages[page]

    Column(
        modifier = Modifier.fillMaxSize().background(KaushalCream)
    ) {
        ScreenTopBar("Quick Summary", onBack)
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(18.dp)
        ) {
            Text("Module 1 — Fire & Explosion Response", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = KaushalNavy)
            Text("Page ${page + 1} of 3", color = KaushalMuted, modifier = Modifier.padding(top = 4.dp))
            Spacer(Modifier.height(16.dp))

            sections.forEach { section ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(section.title, fontWeight = FontWeight.Bold, color = KaushalOrange)
                        Spacer(Modifier.height(8.dp))
                        section.bullets.forEach { bullet ->
                            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                Text("•", color = KaushalOrange, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.width(8.dp))
                                Text(bullet, color = KaushalNavy)
                            }
                        }
                    }
                }
            }

            LaunchedEffect(page, completed) {
                if (page == pages.lastIndex && !completed) onCompleted()
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = { if (page > 0) page-- else onBack() }, modifier = Modifier.weight(1f)) { Text("BACK") }
                if (page < pages.lastIndex) {
                    Button(onClick = { page++ }, modifier = Modifier.weight(1f)) { Text("NEXT") }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
