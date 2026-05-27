package cst.unitbvfmi2026.ui.navigation

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cst.unitbvfmi2026.MainActivity
import cst.unitbvfmi2026.ui.screens.AddressesScreen
import cst.unitbvfmi2026.ui.screens.HomeScreen
import cst.unitbvfmi2026.ui.screens.LogInScreen
import cst.unitbvfmi2026.ui.screens.RegisterScreen
import cst.unitbvfmi2026.ui.screens.UsersScreen
import cst.unitbvfmi2026.viewModels.AuthViewModel

@Composable
fun AuthenticationNavigation(
    authViewModel: AuthViewModel= viewModel()

){
    val navController=rememberNavController()
    val authState by authViewModel.authState.collectAsState()//state-ul ajuta la actualiz UI-ului la modif param respectiv
    val hasAuthState by authViewModel.hasAuth.collectAsState()
    val navigateToHome: () -> Unit = {
        navController.navigate("homeScreen") {
            popUpTo("login") {
                inclusive =
                    true // necesar pt inchidere login} //popUpTo = inchide login la parasire
            }
        }
    }//= {} pt ca e param de tip lambda func si il ia default
    val startDestination = if (authViewModel.isLoggedIn || hasAuthState != null) "homeScreen" else "login"
    NavHost (
        navController= navController,
        startDestination = startDestination
    ){
        composable("login") {
            LogInScreen(
                onRegisterClick = {
                    navController.navigate("register")
                },
                onLoginClick = { email, password ->
                    //authViewModel.login(email, password, navigateToHome) - logare Firebase
                    authViewModel.loginApi(email, password, navigateToHome) // logare API
                },
                isLoading = authState.isLoading,
                errorMessage = authState.errorMessage

            )
        }
        composable("register") {
            RegisterScreen(
                onLoginClick = {
                    navController.popBackStack()
                },
                onRegisterClick = { email, password ->
                    authViewModel.register(email, password, navigateToHome)
                },
                isLoading = authState.isLoading,
                errorMessage = authState.errorMessage
            )
        }
        composable("usersScreen/{addressId}", //mod preluare param si il dam apelului de functie Composable
            arguments = listOf(
                navArgument("addressId"){
                    type = NavType.LongType
                }
            ))
        {
            val addressId = it.arguments?.getLong("addressId") ?: 0
            UsersScreen(addressId = addressId)
        }
        composable("addressesScreen")
        {
            AddressesScreen{ address -> //rename "it"
                navController.navigate("usersScreen/${address.id}")//merge direct asa, la lambda
            }
        }
        composable("homeScreen") {
            //HomeScreen(authViewModel::logout)//referinta catre logout din AuthViewModel, care are aceeasi semnatura => nu mai trebuie declarata alta functie lambda pt asta
            val context = LocalContext.current//definit de componenta in care se afla authNav
            HomeScreen(
                goToAddresses = {
                    navController.navigate("addressesScreen")
                },
                logout = {
                    authViewModel.logout()
                    val intent = Intent(context, MainActivity::class.java)
                    (context as? Activity)?.apply { //cast ca sa ne asiguram ca context-ul este o activitate; as? = daca crapa cast-ul, return null => nu se apeleaza apply
                        this.startActivity(intent)//this = val care s-a accesat cu succes inaintea instructiunii de apply; porneste activ pe baza intentului definit
                        this.finish()//distruge activ curenta pt a nu ramane in backstack
                    }
                }
            )
        }
    }
}