package com.example.myautoo.authTest

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myautoo.ui.feature.auth.RegisterScreen
import com.example.myautoo.ui.viewModel.AuthResult
import com.example.myautoo.ui.viewModel.AuthUiState
import com.example.myautoo.ui.viewModel.AuthViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        authViewModel = mockk(relaxed = true)

        every { authViewModel.uiState } returns MutableStateFlow(
            AuthUiState(
                email = "",
                password = "",
                confirmPassword = "",
                isFormValid = false
            )
        )

        every { authViewModel.isLoading } returns MutableStateFlow(false)
        every { authViewModel.authResult } returns MutableStateFlow(AuthResult.Idle)

        composeRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }

    @Test
    fun register_muestra_elementos_principales() {
        composeRule.onNodeWithText("Crea tu Cuenta").assertIsDisplayed()
        composeRule.onNodeWithText("Email").assertIsDisplayed()
        composeRule.onNodeWithText("Contraseña").assertIsDisplayed()
        composeRule.onNodeWithText("Confirmar Contraseña").assertIsDisplayed()
        composeRule.onNodeWithText("Registrar").assertIsDisplayed()
        composeRule.onNodeWithText("¿Ya tienes una cuenta? ").assertIsDisplayed()
        composeRule.onNodeWithText("Ingresa").assertIsDisplayed()
    }

    @Test
    fun click_en_ingresa_no_crashea() {
        composeRule
            .onNodeWithText("Ingresa")
            .performClick()
    }
}
