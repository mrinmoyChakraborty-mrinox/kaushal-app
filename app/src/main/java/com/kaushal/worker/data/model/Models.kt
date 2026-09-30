package com.kaushal.worker.data.model

enum class UserType { LEARNER, WORKER }

enum class WorkerVerificationStatus { NOT_APPLICABLE, NOT_SUBMITTED, PENDING, VERIFIED }

data class TrainingModule(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val hasAr: Boolean = false
)

val staticModules = listOf(
    TrainingModule("fire", "Fire & Explosion Response", "Learn fire safety, prevention and emergency response procedures.", "🔥", true),
    TrainingModule("gas", "Gas Leak & Confined Space", "Stay safe in hazardous gas and confined environments.", "🫁", true),
    TrainingModule("machinery", "Machinery Safety", "Learn safe operation and maintenance practices.", "⚙️"),
    TrainingModule("ppe", "PPE & Workplace Safety", "Use the right protective equipment for your work.", "🦺", true),
    TrainingModule("laws", "Mine Laws & Workers' Rights", "Understand worker rights, rules and legal safety requirements.", "⚖️"),
    TrainingModule("emergency", "Emergency Response", "Know what to do when a workplace emergency occurs.", "🚨")
)
