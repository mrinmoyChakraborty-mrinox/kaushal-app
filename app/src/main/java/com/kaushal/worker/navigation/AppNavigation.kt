package com.kaushal.worker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.kaushal.worker.screens.certificates.CertificatesScreen
import com.kaushal.worker.screens.progress.ProgressScreen
import com.kaushal.worker.screens.profile.ProfileScreen

@Composable
fun AppNavigation(appState: TemporaryAppViewModel) {
    val session by appState.session.collectAsState()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Welcome
    ) {
        composable(Routes.Welcome) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(Routes.Language) },
                onLogin = { navController.navigate(Routes.Login) }
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
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Benefits) {
            TrainingBenefitsScreen(
                onContinue = { navController.navigate(Routes.Register) },
                onBack = { navController.popBackStack() }
            )
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
                    if (!navController.popBackStack(Routes.Login, false)) {
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
                onBack = { navController.popBackStack() },
                onAr = { id -> navController.navigate(Routes.ArTraining.replace("{moduleId}", id)) },
                onAssessment = { id -> navController.navigate(Routes.Assessment.replace("{moduleId}", id)) }
            )
        }

        composable(Routes.FieldBook) {
            FieldBookScreen(onBack = { navController.popBackStack() })
        }

        composable(
            Routes.ArTraining,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) {
            ArTrainingScreen(onBack = { navController.popBackStack() })
        }

        composable(
            Routes.Assessment,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) {
            AssessmentScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.Certificates) {
            CertificatesScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.Progress) {
            ProgressScreen(
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
