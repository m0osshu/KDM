package com.example.myautoo.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myautoo.data.model.CarModel
import com.example.myautoo.ui.feature.auth.LoginScreen
import com.example.myautoo.ui.feature.auth.ProfileScreen
import com.example.myautoo.ui.feature.auth.RegisterScreen
import com.example.myautoo.ui.feature.cart.CartScreen
import com.example.myautoo.ui.feature.detail.DetailScreen
import com.example.myautoo.ui.feature.home.MainScreen
import com.example.myautoo.ui.viewModel.AuthViewModel
import com.example.myautoo.ui.viewModel.CarViewModel
import com.example.myautoo.ui.viewModel.CartViewModel
import com.example.myautoo.ui.viewModel.CategoryViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val categoryViewModel: CategoryViewModel = viewModel()
    val carViewModel: CarViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screens.HOME) {
        composable(Screens.LOGIN) {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.REGISTER) {
            RegisterScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.HOME) {
            MainScreen(
                onProfileClick = { navController.navigate(Screens.PROFILE) },
                onCarClick = { car ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("car", car)
                    navController.navigate(Screens.DETAIL) },
                onCartClick = { navController.navigate(Screens.CART) },
                carViewModel = carViewModel,
                categoryViewModel = categoryViewModel
            )
        }
        composable(Screens.PROFILE) {
             ProfileScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screens.DETAIL) {
            val car = navController.previousBackStackEntry?.savedStateHandle?.get<CarModel>("car")
            if (car != null) {
                DetailScreen(
                    car = car,
                    onBack = { navController.popBackStack() },
                    onNavigateToCart = { navController.navigate(Screens.CART) },
                    cartViewModel = cartViewModel
                )
            }
        }
        composable(Screens.CART) {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                authViewModel = authViewModel // Pasando el AuthViewModel
            )
        }
    }
}

object Screens {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val DETAIL = "detail"
    const val CART = "cart"
}