package com.kaushal.worker

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.kaushal.worker.data.model.UserType
import com.kaushal.worker.data.model.WorkerVerificationStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TemporaryUserProfile(
    val name: String = "",
    val mobileNumber: String = "",
    val language: String = "",
    val userType: UserType = UserType.LEARNER,
    val industrialSector: String = "",
    val subSector: String = "",
    val department: String = "",
    val role: String = "",
    val workerId: String = "",
    val workerVerificationStatus: WorkerVerificationStatus = WorkerVerificationStatus.NOT_SUBMITTED,
    val profilePhotoUri: String? = null
)

data class Module1ProgressState(
    val completedScreens: Set<String> = emptySet(),
    val completedChapters: Set<String> = emptySet(),
    val decisionAttempts: Int = 0,
    val correctDecisionAttempts: Int = 0,
    val assessmentStarted: Boolean = false,
    val assessmentCompleted: Boolean = false,
    val assessmentScore: Int? = null,
    val assessmentTotal: Int? = null,
    val checkpointChapterId: String? = null,
    val checkpointScreenId: String? = null,
    val quickSummaryCompleted: Boolean = false,
    val arTrainingCompleted: Boolean = false
) {
    val learningCompleted: Boolean get() = completedScreens.size >= 70
    val completedPrerequisites: Int
        get() = listOf(learningCompleted, arTrainingCompleted, quickSummaryCompleted).count { it }
    val assessmentUnlocked: Boolean get() = completedPrerequisites >= 2
}

data class TemporarySession(
    val isTemporarilyAuthenticated: Boolean = false,
    val isNewUser: Boolean = false,
    val profile: TemporaryUserProfile = TemporaryUserProfile(),
    val module1Progress: Module1ProgressState = Module1ProgressState()
)

class TemporaryAppViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _session = MutableStateFlow(loadSession())
    val session: StateFlow<TemporarySession> = _session.asStateFlow()

    fun setLanguage(language: String) = updateSession {
        it.copy(profile = it.profile.copy(language = language))
    }

    fun setProfilePhoto(photoUri: String?) = updateSession {
        it.copy(profile = it.profile.copy(profilePhotoUri = photoUri))
    }

    fun setRegistration(name: String, mobile: String, photoUri: String? = null) = updateSession {
        it.copy(profile = it.profile.copy(
            name = name,
            mobileNumber = mobile,
            profilePhotoUri = photoUri ?: it.profile.profilePhotoUri
        ))
    }

    fun authenticateExistingUser(mobile: String) = updateSession {
        it.copy(isTemporarilyAuthenticated = true, isNewUser = false,
            profile = it.profile.copy(mobileNumber = mobile))
    }

    fun authenticateNewUser() = updateSession {
        it.copy(isTemporarilyAuthenticated = true, isNewUser = true)
    }

    fun updateProfile(language: String, sector: String, subSector: String, workerId: String) = updateSession {
        it.copy(
            isNewUser = false,
            profile = it.profile.copy(
                language = language,
                industrialSector = sector,
                subSector = subSector,
                workerId = workerId,
                workerVerificationStatus = if (workerId.isBlank()) WorkerVerificationStatus.NOT_SUBMITTED else WorkerVerificationStatus.PENDING
            )
        )
    }

    fun completeModule1Screen(screenId: String) = updateProgress {
        it.copy(completedScreens = it.completedScreens + screenId)
    }

    fun completeModule1Chapter(chapterId: String) = updateProgress {
        it.copy(completedChapters = it.completedChapters + chapterId)
    }

    fun recordModule1Decision(screenId: String, selectedOption: Int, isCorrect: Boolean) = updateProgress {
        it.copy(
            decisionAttempts = it.decisionAttempts + 1,
            correctDecisionAttempts = it.correctDecisionAttempts + if (isCorrect) 1 else 0
        )
    }

    fun setModule1Checkpoint(chapterId: String, screenId: String) = updateProgress {
        it.copy(checkpointChapterId = chapterId, checkpointScreenId = screenId)
    }

    fun clearModule1Checkpoint() = updateProgress {
        it.copy(checkpointChapterId = null, checkpointScreenId = null)
    }

    fun completeQuickSummary() = updateProgress { it.copy(quickSummaryCompleted = true) }

    fun completeArTraining() = updateProgress { it.copy(arTrainingCompleted = true) }

    fun startModule1Assessment() = updateProgress { it.copy(assessmentStarted = true) }

    fun completeModule1Assessment(score: Int, total: Int) = updateProgress {
        it.copy(assessmentStarted = true, assessmentCompleted = true, assessmentScore = score, assessmentTotal = total)
    }

    fun logout() {
        prefs.edit().clear().apply()
        _session.value = TemporarySession()
    }

    private fun updateProgress(transform: (Module1ProgressState) -> Module1ProgressState) {
        updateSession { it.copy(module1Progress = transform(it.module1Progress)) }
    }

    private fun updateSession(transform: (TemporarySession) -> TemporarySession) {
        _session.value = transform(_session.value)
        persistProgress(_session.value.module1Progress)
    }

    private fun persistProgress(p: Module1ProgressState) {
        prefs.edit()
            .putStringSet(KEY_COMPLETED_SCREENS, p.completedScreens)
            .putStringSet(KEY_COMPLETED_CHAPTERS, p.completedChapters)
            .putInt(KEY_DECISION_ATTEMPTS, p.decisionAttempts)
            .putInt(KEY_CORRECT_DECISION_ATTEMPTS, p.correctDecisionAttempts)
            .putBoolean(KEY_ASSESSMENT_STARTED, p.assessmentStarted)
            .putBoolean(KEY_ASSESSMENT_COMPLETED, p.assessmentCompleted)
            .putInt(KEY_ASSESSMENT_SCORE, p.assessmentScore ?: -1)
            .putInt(KEY_ASSESSMENT_TOTAL, p.assessmentTotal ?: -1)
            .putString(KEY_CHECKPOINT_CHAPTER, p.checkpointChapterId)
            .putString(KEY_CHECKPOINT_SCREEN, p.checkpointScreenId)
            .putBoolean(KEY_QUICK_SUMMARY, p.quickSummaryCompleted)
            .putBoolean(KEY_AR_COMPLETED, p.arTrainingCompleted)
            .apply()
    }

    private fun loadSession(): TemporarySession {
        val p = Module1ProgressState(
            completedScreens = prefs.getStringSet(KEY_COMPLETED_SCREENS, emptySet()) ?: emptySet(),
            completedChapters = prefs.getStringSet(KEY_COMPLETED_CHAPTERS, emptySet()) ?: emptySet(),
            decisionAttempts = prefs.getInt(KEY_DECISION_ATTEMPTS, 0),
            correctDecisionAttempts = prefs.getInt(KEY_CORRECT_DECISION_ATTEMPTS, 0),
            assessmentStarted = prefs.getBoolean(KEY_ASSESSMENT_STARTED, false),
            assessmentCompleted = prefs.getBoolean(KEY_ASSESSMENT_COMPLETED, false),
            assessmentScore = prefs.getInt(KEY_ASSESSMENT_SCORE, -1).takeIf { it >= 0 },
            assessmentTotal = prefs.getInt(KEY_ASSESSMENT_TOTAL, -1).takeIf { it >= 0 },
            checkpointChapterId = prefs.getString(KEY_CHECKPOINT_CHAPTER, null),
            checkpointScreenId = prefs.getString(KEY_CHECKPOINT_SCREEN, null),
            quickSummaryCompleted = prefs.getBoolean(KEY_QUICK_SUMMARY, false),
            arTrainingCompleted = prefs.getBoolean(KEY_AR_COMPLETED, false)
        )
        return TemporarySession(module1Progress = p)
    }

    private companion object {
        const val PREFS_NAME = "kaushal_local_learning"
        const val KEY_COMPLETED_SCREENS = "module1_completed_screens"
        const val KEY_COMPLETED_CHAPTERS = "module1_completed_chapters"
        const val KEY_DECISION_ATTEMPTS = "module1_decision_attempts"
        const val KEY_CORRECT_DECISION_ATTEMPTS = "module1_correct_decision_attempts"
        const val KEY_ASSESSMENT_STARTED = "module1_assessment_started"
        const val KEY_ASSESSMENT_COMPLETED = "module1_assessment_completed"
        const val KEY_ASSESSMENT_SCORE = "module1_assessment_score"
        const val KEY_ASSESSMENT_TOTAL = "module1_assessment_total"
        const val KEY_CHECKPOINT_CHAPTER = "module1_checkpoint_chapter"
        const val KEY_CHECKPOINT_SCREEN = "module1_checkpoint_screen"
        const val KEY_QUICK_SUMMARY = "module1_quick_summary_completed"
        const val KEY_AR_COMPLETED = "module1_ar_completed"
    }
}
