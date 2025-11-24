package com.example.myautoo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myautoo.data.model.CarModel
import com.example.myautoo.data.repository.CartRepository
import com.example.myautoo.ui.feature.auth.LoginScreen
import com.example.myautoo.ui.feature.auth.ProfileScreen
import com.example.myautoo.ui.feature.auth.RegisterScreen
import com.example.myautoo.ui.feature.cart.CartScreen
import com.example.myautoo.ui.feature.detail.DetailScreen
import com.example.myautoo.ui.feature.home.MainScreen
import com.example.myautoo.ui.viewModel.AuthViewModel
import com.example.myautoo.ui.viewModel.CarViewModel
import com.example.myautoo.ui.viewModel.CartViewModel
import com.example.myautoo.ui.viewModel.CartViewModelFactory
import com.example.myautoo.ui.viewModel.CategoryViewModel
import com.example.myautoo.data.local.database.CarDatabase
import com.example.myautoo.data.remote.api.RetrofitClient
import com.example.myautoo.data.repository.MarcaRepository
import com.example.myautoo.data.repository.UserPhotoRepository
import com.example.myautoo.ui.feature.admin.MarcaAdminScreen
import com.example.myautoo.ui.viewModel.MarcaAdminViewModel
import com.example.myautoo.ui.viewModel.MarcaAdminViewModelFactory
import com.example.myautoo.ui.viewModel.UserPhotoViewModel
import com.example.myautoo.ui.viewModel.UserPhotoViewModelFactory

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    // Instanciación de ViewModels sin dependencias
    val categoryViewModel: CategoryViewModel = viewModel()
    val carViewModel: CarViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    // 5. Creación de la Factory para CartViewModel
    val context = LocalContext.current
    val db = CarDatabase.getDatabase(context)
    val cartRepository = CartRepository(db.cartDao())
    val cartViewModelFactory = CartViewModelFactory(cartRepository)

    //5.5 Creacion de la Factory para UserPhotoViewModel
    val userPhotoRepository = UserPhotoRepository(db.userPhotoDao())
    val userPhotoViewModelFactory = UserPhotoViewModelFactory(userPhotoRepository)
    val userPhotoViewModel: UserPhotoViewModel = viewModel(factory = userPhotoViewModelFactory)


    // 6. Instanciación de CartViewModel usando la Factory
    val cartViewModel: CartViewModel = viewModel(factory = cartViewModelFactory)

    NavHost(navController = navController, startDestination = Screens.LOGIN) {
        composable(Screens.LOGIN) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel)
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
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
                userPhotoViewModel = userPhotoViewModel
            )
        }
        composable(Screens.DETAIL) {
            val car = navController.previousBackStackEntry?.savedStateHandle?.get<CarModel>("car")
            if (car != null) {
                DetailScreen(
                    car = car,
                    onBack = { navController.popBackStack() },
                    categoryViewModel = categoryViewModel,
                    cartViewModel = cartViewModel
                )
            }
        }
        composable(Screens.CART) {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                authViewModel = authViewModel
            )
        }
        composable(Screens.ADMIN) {
            // Instancia el MarcaAdminViewModel con su repositorio
            val marcaAdminViewModel: MarcaAdminViewModel = viewModel(
                factory = MarcaAdminViewModelFactory(MarcaRepository(RetrofitClient.marcaApi))
            )
            MarcaAdminScreen(
                viewModel = marcaAdminViewModel,
                onBackClick = { navController.popBackStack() }
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
    const val ADMIN = "admin"
}
