package com.kaushal.worker.data.model

import androidx.annotation.StringRes
import com.kaushal.worker.R

enum class UserType { LEARNER, WORKER }

enum class WorkerVerificationStatus { NOT_APPLICABLE, NOT_SUBMITTED, PENDING, VERIFIED }

data class TrainingModule(
    val id: String,
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int,
    val icon: String,
    val hasAr: Boolean = false
)

val staticModules = listOf(
    TrainingModule("fire", R.string.module_fire_title, R.string.module_fire_desc, "🔥", true),
    TrainingModule("gas", R.string.module_gas_title, R.string.module_gas_desc, "🫁", true),
    TrainingModule("machinery", R.string.module_machinery_title, R.string.module_machinery_desc, "⚙️"),
    TrainingModule("ppe", R.string.module_ppe_title, R.string.module_ppe_desc, "🦺", true),
    TrainingModule("laws", R.string.module_laws_title, R.string.module_laws_desc, "⚖️")
)
