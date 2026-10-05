package com.kaushal.worker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kaushal.worker.TemporaryAppViewModel
import com.kaushal.worker.screens.auth.LoginOtpScreen
import com.kaushal.worker.screens.auth.LoginScreen
import com.kaushal.worker.screens.auth.RegisterOtpScreen
import com.kaushal.worker.screens.auth.RegisterScreen
import com.kaushal.worker.screens.onboarding.LanguageScreen
import com.kaushal.worker.screens.onboarding.TrainingBenefitsScreen
import com.kaushal.worker.screens.onboarding.WelcomeScreen
import com.kaushal.worker.screens.trainingprofile.TrainingProfileScreen
import com.kaushal.worker.screens.home.DashboardScreen
import com.kaushal.worker.screens.modules.LearningModulesScreen
import com.kaushal.worker.screens.modules.ModuleDetailScreen
import com.kaushal.worker.screens.fieldbook.FieldBookScreen
import com.kaushal.worker.screens.ar.ArTrainingScreen
import com.kaushal.worker.screens.assessment.AssessmentScreen
import com.kaushal.worker.screens.modules.Module1ChapterListScreen
import com.kaushal.worker.screens.modules.Module1StoryPlayerScreen
import com.kaushal.worker.screens.modules.QuickSummaryScreen
import com.kaushal.worker.screens.certificates.CertificatesScreen
import com.kaushal.worker.screens.progress.ProgressScreen
import com.kaushal.worker.screens.profile.ProfileScreen

@Composable
fun AppNavigation(appState: TemporaryAppViewModel) {
    val session by appState.session.collectAsState()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Welcome,
    ) {
        composable(Routes.Welcome) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(Routes.Language) },
                onLogin = { navController.navigate(Routes.Login) },
            )
        }

        composable(Routes.Language) {
            LanguageScreen(
                selected = session.profile.language,
                onSelect = appState::setLanguage,
                onContinue = {
                    if (session.isTemporarilyAuthenticated) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(Routes.Benefits)
                    }
                }
            ) { navController.popBackStack() }
        }

        composable(Routes.Benefits) {
            TrainingBenefitsScreen(
                onContinue = { navController.navigate(Routes.Register) }
            ) { navController.popBackStack() }
        }

        composable(Routes.Login) {
            LoginScreen(
                onSendOtp = { mobile ->
                    navController.navigate("${Routes.LoginOtp}?mobile=$mobile")
                },
                onRegister = { navController.navigate(Routes.Register) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${Routes.LoginOtp}?mobile={mobile}",
            arguments = listOf(
                navArgument("mobile") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            LoginOtpScreen(
                mobile = entry.arguments?.getString("mobile").orEmpty(),
                onVerify = { mobile ->
                    appState.authenticateExistingUser(mobile)
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.Welcome) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Register) {
            RegisterScreen(
                onCreateAccount = { name, mobile, photoUri ->
                    appState.setRegistration(name, mobile, photoUri)
                    navController.navigate(Routes.RegisterOtp)
                },
                onLogin = { navController.navigate(Routes.Login) },
                onBack = {
                    if (!navController.popBackStack(Routes.Login, inclusive = false)) {
                        navController.navigate(Routes.Login) {
                            popUpTo(Routes.Register) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Routes.RegisterOtp) {
            RegisterOtpScreen(
                mobile = session.profile.mobileNumber,
                onVerify = {
                    appState.authenticateNewUser()
                    navController.navigate(Routes.TrainingProfile) {
                        popUpTo(Routes.Register) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.TrainingProfile) {
            TrainingProfileScreen(
                initial = session.profile,
                onBack = { navController.popBackStack() },
                onContinue = { language, sector, subSector, workerId ->
                    appState.updateProfile(language, sector, subSector, workerId)
                    navController.navigate(Routes.Dashboard) {
                        popUpTo(Routes.Welcome) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Dashboard) {
            DashboardScreen(
                profile = session.profile,
                completedScreens = session.module1Progress.completedScreens.size,
                checkpointChapterId = session.module1Progress.checkpointChapterId,
                checkpointScreenId = session.module1Progress.checkpointScreenId,
                onContinueLearning = {
                    val chapter = session.module1Progress.checkpointChapterId
                    if (chapter != null) {
                        navController.navigate(Routes.Module1Story.replace("{chapterId}", chapter))
                    } else {
                        navController.navigate(Routes.Module1Chapters)
                    }
                },
                onNavigate = { target ->
                    when (target) {
                        "Home" -> {
                            if (navController.currentDestination?.route != Routes.Dashboard) {
                                navController.navigate(Routes.Dashboard) {
                                    popUpTo(Routes.Dashboard) { inclusive = true }
                                }
                            }
                        }
                        "Learn" -> navController.navigate(Routes.LearningModules)
                        "Progress" -> navController.navigate(Routes.Progress)
                        "Profile" -> navController.navigate(Routes.Profile)
                        "Language" -> navController.navigate(Routes.Language)
                        "Field Book" -> navController.navigate(Routes.FieldBook)
                        "AR Training" -> navController.navigate(Routes.ArTraining.replace("{moduleId}", "fire"))
                        "Certificates" -> navController.navigate(Routes.Certificates)
                        "Safety Passport" -> navController.navigate(Routes.Certificates)
                    }
                }
            )
        }

        composable(Routes.LearningModules) {
            LearningModulesScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { target ->
                    when (target) {
                        "Home" -> navController.navigate(Routes.Dashboard)
                        "Learn" -> {
                            if (navController.currentDestination?.route != Routes.LearningModules) {
                                navController.navigate(Routes.LearningModules) {
                                    popUpTo(Routes.LearningModules) { inclusive = true }
                                }
                            }
                        }
                        "Progress" -> navController.navigate(Routes.Progress)
                        "Profile" -> navController.navigate(Routes.Profile)
                    }
                },
                onModule = { id -> navController.navigate(Routes.ModuleDetail.replace("{moduleId}", id)) }
            )
        }

        composable(
            Routes.ModuleDetail,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { entry ->
            ModuleDetailScreen(
                moduleId = entry.arguments?.getString("moduleId").orEmpty(),
                learningCompleted = session.module1Progress.learningCompleted,
                arTrainingCompleted = session.module1Progress.arTrainingCompleted,
                quickSummaryCompleted = session.module1Progress.quickSummaryCompleted,
                onBack = { navController.popBackStack() },
                onAr = { id -> navController.navigate(Routes.ArTraining.replace("{moduleId}", id)) },
                onQuickSummary = { id -> if (id == "fire") navController.navigate(Routes.QuickSummary) },
                onAssessment = { id ->
                    if (id == "fire" && session.module1Progress.assessmentUnlocked) {
                        appState.startModule1Assessment()
                        navController.navigate(Routes.Assessment.replace("{moduleId}", id))
                    }
                },
                onStartModule = { id ->
                    if (id == "fire") navController.navigate(Routes.Module1Chapters)
                }
            )
        }


        composable(Routes.Module1Chapters) {
            Module1ChapterListScreen(
                languageCode = session.profile.language,
                onBack = { navController.popBackStack() },
                onChapter = { chapterId -> navController.navigate(Routes.Module1Story.replace("{chapterId}", chapterId)) },
                completedChapters = session.module1Progress.completedChapters,
                assessmentUnlocked = session.module1Progress.assessmentUnlocked,
                onAssessment = {
                    if (session.module1Progress.assessmentUnlocked) {
                        appState.startModule1Assessment()
                        navController.navigate(Routes.Assessment.replace("{moduleId}", "fire"))
                    }
                }
            )
        }

        composable(
            Routes.Module1Story,
            arguments = listOf(navArgument("chapterId") { type = NavType.StringType })
        ) { entry ->
            val chapterId = entry.arguments?.getString("chapterId").orEmpty()
            Module1StoryPlayerScreen(
                chapterId = chapterId,
                languageCode = session.profile.language,
                initialScreenId = session.module1Progress.checkpointScreenId.takeIf { session.module1Progress.checkpointChapterId == chapterId },
                onCheckpoint = appState::setModule1Checkpoint,
                onBack = { navController.popBackStack() },
                onPracticeAr = { id -> navController.navigate(Routes.ArTraining.replace("{moduleId}", id)) },
                onChapterComplete = {
                    appState.completeModule1Chapter(chapterId)
                    navController.popBackStack()
                },
                onDecisionAnswered = { screenId, selectedOption, isCorrect ->
                    appState.recordModule1Decision(screenId, selectedOption, isCorrect)
                },
                onScreenCompleted = { screenId -> appState.completeModule1Screen(screenId) }
            )
        }

        composable(Routes.QuickSummary) {
            QuickSummaryScreen(
                completed = session.module1Progress.quickSummaryCompleted,
                onBack = { navController.popBackStack() },
                onCompleted = appState::completeQuickSummary
            )
        }

        composable(Routes.FieldBook) {
            FieldBookScreen(onBack = { navController.popBackStack() })
        }

        composable(
            Routes.ArTraining,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) {
            Box(Modifier.fillMaxSize()) {
                ArTrainingScreen(onBack = { navController.popBackStack() })
                Button(
                    onClick = {
                        appState.completeArTraining()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text("COMPLETE AR TRAINING")
                }
            }
        }

        composable(
            Routes.Assessment,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { entry ->
            AssessmentScreen(
                moduleId = entry.arguments?.getString("moduleId").orEmpty(),
                languageCode = session.profile.language,
                onBack = { navController.popBackStack() },
                onCompleted = { score, total -> appState.completeModule1Assessment(score, total) }
            )
        }

        composable(Routes.Certificates) {
            CertificatesScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.Progress) {
            ProgressScreen(
                appState = appState,
                onBack = { navController.popBackStack() },
                onNavigate = { target ->
                    when (target) {
                        "Home" -> navController.navigate(Routes.Dashboard)
                        "Learn" -> navController.navigate(Routes.LearningModules)
                        "Progress" -> {
                            if (navController.currentDestination?.route != Routes.Progress) {
                                navController.navigate(Routes.Progress) {
                                    popUpTo(Routes.Progress) { inclusive = true }
                                }
                            }
                        }
                        "Profile" -> navController.navigate(Routes.Profile)
                    }
                }
            )
        }

        composable(Routes.Profile) {
            ProfileScreen(
                profile = session.profile,
                onBack = { navController.popBackStack() },
                onNavigate = { target ->
                    when (target) {
                        "Home" -> navController.navigate(Routes.Dashboard)
                        "Learn" -> navController.navigate(Routes.LearningModules)
                        "Progress" -> navController.navigate(Routes.Progress)
                        "Profile" -> {
                            if (navController.currentDestination?.route != Routes.Profile) {
                                navController.navigate(Routes.Profile) {
                                    popUpTo(Routes.Profile) { inclusive = true }
                                }
                            }
                        }
                        "Language" -> navController.navigate(Routes.Language)
                    }
                },
                onUpdatePhoto = appState::setProfilePhoto,
                onLogout = {
                    appState.logout()
                    navController.navigate(Routes.Welcome) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
