package com.kaushal.worker.navigation

object Routes {
    const val Welcome = "welcome"
    const val Language = "language"
    const val Benefits = "training_benefits"
    const val Login = "login"
    const val LoginOtp = "login_otp"
    const val Register = "register"
    const val RegisterOtp = "register_otp"
    const val TrainingProfile = "training_profile"
    const val Dashboard = "dashboard"
    const val LearningModules = "learning_modules"
    const val ModuleDetail = "module_detail/{moduleId}"
    const val Module1Chapters = "module1_chapters"
    const val Module1Story = "module1_story/{chapterId}"
    const val FieldBook = "field_book"

    // AR route carries the real context: moduleId (path) plus scenarioId,
    // chapterId and screenId (query parameters with defaults so the Dashboard
    // and Module Detail entries keep working).
    const val ArTraining = "ar_training/{moduleId}?scenarioId={scenarioId}&chapterId={chapterId}&screenId={screenId}"
    const val Assessment = "assessment/{moduleId}"
    const val Certificates = "certificates"
    const val Progress = "progress"
    const val Profile = "profile"

    /**
     * Builds an AR route that preserves the full Kotlin learning context.
     * Dashboard and Module Detail entries call it with only a moduleId;
     * decision screens call it with the full context.
     */
    fun arTraining(
        moduleId: String,
        scenarioId: String = "",
        chapterId: String = "",
        screenId: String = ""
    ): String = "ar_training/$moduleId" +
        "?scenarioId=$scenarioId&chapterId=$chapterId&screenId=$screenId"
}
