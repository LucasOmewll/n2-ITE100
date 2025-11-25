package com.fatec.salafacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fatec.salafacil.ui.routes.AppRoutes
import com.fatec.salafacil.ui.screens.home.HomeScreen
import com.fatec.salafacil.ui.screens.login.LoginScreen
import com.fatec.salafacil.ui.screens.onboarding.OnboardingScreen
import com.fatec.salafacil.ui.screens.passwordrecovery.PasswordRecoveryScreen
import com.fatec.salafacil.ui.screens.welcome.WelcomeScreen
import com.fatec.salafacil.ui.theme.SalaFacilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SalaFacilTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.WELCOME_SCREEN
    ) {

        // Navegação da tela de boas vindas
        composable(AppRoutes.WELCOME_SCREEN) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.LOGIN)
                }
            )
        }

        // Navegação da tela de login
        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(AppRoutes.ONBOARDING)
                },
                onLoginSuccess = {
                    navController.navigate(AppRoutes.HOME)
                },
                onPasswordRecoveryClick = {
                    navController.navigate(AppRoutes.PASSWORD_RECOVERY)
                }
            )
        }

        // Navegação da tela de onboarding
        composable(AppRoutes.ONBOARDING) {
            OnboardingScreen(
                onLoginClick = {
                    navController.navigate(AppRoutes.LOGIN)
                },
                onSignUpSuccess = {
                    navController.navigate(AppRoutes.LOGIN)
                }
            )
        }

        // Navegação da tela de recuperação de senha
        composable(AppRoutes.PASSWORD_RECOVERY) {
            PasswordRecoveryScreen(
                onBackClick = {
                    navController.navigate(AppRoutes.LOGIN)
                },
                onSendRecoveryEmail = { email ->
                    // TODO: recuperação de senha
                }
            )
        }

        // Navegação para a tela home
        composable(AppRoutes.HOME) {
            HomeScreen(
                onLogOutSuccess = {
                    navController.navigate(AppRoutes.LOGIN)
                }
            )
        }
    }
}