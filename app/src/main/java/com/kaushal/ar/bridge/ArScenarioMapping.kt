package com.kaushal.ar.bridge

/**
 * Explicit Kotlin-scenario -> Unity-step mapping.
 *
 * Kotlin scenario ids (`m1_extinguisher`, ...) are NOT Unity step ids
 * (`select_extinguisher`, ...). This layer is the single place that translates
 * between them.
 *
 * Only mappings that correspond to a real, implemented Unity visual step are
 * listed. Scenarios without a defensible mapping resolve to null and the AR
 * screen shows a Kotlin-owned "AR scene unavailable for this scenario" state.
 *
 * Verified against:
 * D:\My project\Assets\KAUSHAL\Data\Scenarios\Scenario_Fire_Escape.asset
 *   scenarioId = fire_emergency_escape, module = fire_explosion
 *   steps: notice_early_warning, select_extinguisher, identify_exit, reach_assembly
 */
object ArScenarioMapping {

    const val FIRE_MODULE_ID = "fire_explosion"
    const val FIRE_SCENARIO_ID = "fire_emergency_escape"

    data class UnityStep(
        val moduleId: String,
        val scenarioId: String,
        val stepId: String
    )

    private val map: Map<String, UnityStep> = mapOf(
        // Kotlin ch04_s03 "Should Raju use the extinguisher?" ->
        // Unity "FIRE EMERGENCY / select_extinguisher" which spawns the real
        // electrical panel + real fire extinguisher + FX_FIRE + FX_SMOKE.
        "m1_extinguisher" to UnityStep(FIRE_MODULE_ID, FIRE_SCENARIO_ID, "select_extinguisher"),

        // Kotlin ch02_s03 "Smoke from equipment" -> Unity hazard recognition
        // near the electrical panel (FX_SMOKE + alarm button).
        "m1_smoke_equipment" to UnityStep(FIRE_MODULE_ID, FIRE_SCENARIO_ID, "notice_early_warning"),

        // Kotlin ch03_s03 / ch06_s03 designated emergency route -> Unity exit
        // identification (exit sign + escape arrow + fire/smoke).
        "m1_emergency_route" to UnityStep(FIRE_MODULE_ID, FIRE_SCENARIO_ID, "identify_exit"),
        "m1_shortcut_route" to UnityStep(FIRE_MODULE_ID, FIRE_SCENARIO_ID, "identify_exit"),

        // Kotlin ch07_s03 "Prioritize the emergency procedure and move to
        // safety" -> Unity assembly point (escape arrow + assembly point).
        "m1_fire_emergency_priority" to UnityStep(FIRE_MODULE_ID, FIRE_SCENARIO_ID, "reach_assembly")

        // m1_flammable_atmosphere intentionally has no mapping: the Unity Fire
        // scenario has no flammable-atmosphere visual step. Kotlin keeps the id
        // and the AR screen reports the scene as unavailable instead of sending
        // a wrong step.
    )

    fun resolve(kotlinScenarioId: String?): UnityStep? =
        if (kotlinScenarioId.isNullOrBlank()) null else map[kotlinScenarioId]
}