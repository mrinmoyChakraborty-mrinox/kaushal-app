package com.kaushal.worker

import androidx.lifecycle.ViewModel
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
    val assessmentTotal: Int? = null
)

data class TemporarySession(
    val isTemporarilyAuthenticated: Boolean = false,
    val isNewUser: Boolean = false,
    val profile: TemporaryUserProfile = TemporaryUserProfile(),
    val module1Progress: Module1ProgressState = Module1ProgressState()
)

class TemporaryAppViewModel : ViewModel() {
    private val _session = MutableStateFlow(TemporarySession())
    val session: StateFlow<TemporarySession> = _session.asStateFlow()

    fun setLanguage(language: String) {
        _session.value = _session.value.copy(
            profile = _session.value.profile.copy(language = language)
        )
    }

    fun setProfilePhoto(photoUri: String?) {
        _session.value = _session.value.copy(
            profile = _session.value.profile.copy(profilePhotoUri = photoUri)
        )
    }

    fun setRegistration(name: String, mobile: String, photoUri: String? = null) {
        _session.value = _session.value.copy(
            profile = _session.value.profile.copy(
                name = name,
                mobileNumber = mobile,
                profilePhotoUri = photoUri ?: _session.value.profile.profilePhotoUri
            )
        )
    }

    fun authenticateExistingUser(mobile: String) {
        _session.value = TemporarySession(
            isTemporarilyAuthenticated = true,
            isNewUser = false,
            profile = _session.value.profile.copy(mobileNumber = mobile)
        )
    }

    fun authenticateNewUser() {
        _session.value = _session.value.copy(
            isTemporarilyAuthenticated = true,
            isNewUser = true
        )
    }

    fun updateProfile(
        language: String,
        sector: String,
        subSector: String,
        workerId: String
    ) {
        _session.value = _session.value.copy(
            isNewUser = false,
            profile = _session.value.profile.copy(
                language = language,
                industrialSector = sector,
                subSector = subSector,
                workerId = workerId,
                workerVerificationStatus = if (workerId.isBlank()) {
                    WorkerVerificationStatus.NOT_SUBMITTED
                } else {
                    WorkerVerificationStatus.PENDING
                }
            )
        )
    }

    fun completeModule1Screen(screenId: String) {
        val p = _session.value.module1Progress
        _session.value = _session.value.copy(
            module1Progress = p.copy(completedScreens = p.completedScreens + screenId)
        )
    }

    fun completeModule1Chapter(chapterId: String) {
        val p = _session.value.module1Progress
        _session.value = _session.value.copy(
            module1Progress = p.copy(completedChapters = p.completedChapters + chapterId)
        )
    }

    fun recordModule1Decision(screenId: String, selectedOption: Int, isCorrect: Boolean) {
        val p = _session.value.module1Progress
        _session.value = _session.value.copy(
            module1Progress = p.copy(
                decisionAttempts = p.decisionAttempts + 1,
                correctDecisionAttempts = p.correctDecisionAttempts + if (isCorrect) 1 else 0
            )
        )
    }

    fun startModule1Assessment() {
        val p = _session.value.module1Progress
        if (!p.assessmentStarted) {
            _session.value = _session.value.copy(
                module1Progress = p.copy(assessmentStarted = true)
            )
        }
    }

    fun completeModule1Assessment(score: Int, total: Int) {
        val p = _session.value.module1Progress
        _session.value = _session.value.copy(
            module1Progress = p.copy(
                assessmentStarted = true,
                assessmentCompleted = true,
                assessmentScore = score,
                assessmentTotal = total
            )
        )
    }

    fun logout() {
        _session.value = TemporarySession()
    }
}
