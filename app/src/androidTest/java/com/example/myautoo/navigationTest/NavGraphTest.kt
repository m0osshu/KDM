package com.example.myautoo.navigationTest

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.myautoo.navigation.AppNavGraph
import com.example.myautoo.navigation.Screens
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.setContent {
            // Creamos un TestNavHostController
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            // Pasamos el controlador de prueba a nuestro grafo
            AppNavGraph(navController = navController)
        }
    }

    @Test
    fun muestraPantallaLogin_alIniciar() {
        // La ruta inicial es LOGIN, verificamos que se muestra
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun muestraPantallaRegister() {
        // Navegamos a la pantalla de registro
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.REGISTER)
        }
        // Verificamos que se muestra el título de la pantalla de registro
        composeTestRule.onNodeWithText("Regístrate").assertIsDisplayed()
    }

    @Test
    fun muestraPantallaHome() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.HOME)
        }
        // "Marcas" es un texto clave en la MainScreen
        composeTestRule.onNodeWithText("Marcas").assertIsDisplayed()
    }

    @Test
    fun muestraPantallaProfile() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.PROFILE)
        }
        composeTestRule.onNodeWithText("Mi Perfil").assertIsDisplayed()
    }

    @Test
    fun muestraPantallaCart() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.CART)
        }
        composeTestRule.onNodeWithText("Mi Carrito").assertIsDisplayed()
    }

    @Test
    fun muestraPantallaAdmin() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.ADMIN)
        }
        composeTestRule.onNodeWithText("Panel de Marcas").assertIsDisplayed()
    }
}
