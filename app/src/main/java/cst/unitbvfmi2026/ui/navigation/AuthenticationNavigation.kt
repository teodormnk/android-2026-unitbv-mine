package cst.unitbvfmi2026.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cst.unitbvfmi2026.ui.screens.LogInScreen
import cst.unitbvfmi2026.ui.screens.RegisterScreen
import cst.unitbvfmi2026.viewModels.AuthViewModel

@Composable
fun AuthenticationNavigation(
    authViewModel: AuthViewModel = viewModel()
){
    val navController=rememberNavController()
    val authState by authViewModel.authState.collectAsState()
    NavHost (
        navController= navController,
        startDestination = "login"
    ){
        composable("login") {
            LogInScreen(
                onRegisterClick = {
                    navController.navigate("register")
                },
                onLoginClick = {
                    authViewModel.login("test@test.com","000000")
                },
                isLoading = authState.isLoading,
                errorMsg = authState.errorMsg
            )
        }
        composable("register") {
            RegisterScreen(
                onLoginClick = {
                    navController.popBackStack()
                },
                onRegisterClick = {
                    authViewModel.register("test@test.com", "000000")
                }
            )
        }
    }

}