package com.example.myautoo.authTest

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myautoo.ui.feature.auth.LoginScreen
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
class LoginScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setup() {
        authViewModel = mockk(relaxed = true)

        every { authViewModel.uiState } returns MutableStateFlow(
            AuthUiState(
                email = "test@test.com",
                password = "123456",
                isFormValid = true
            )
        )

        every { authViewModel.authResult } returns MutableStateFlow(AuthResult.Idle)
        every { authViewModel.currentUser } returns MutableStateFlow(null)
        every { authViewModel.isLoading } returns MutableStateFlow(false)

        composeRule.setContent {
            LoginScreen(
                navController = rememberNavController(),
                authViewModel = authViewModel
            )
        }
    }

    @Test
    fun muestra_titulo_login() {
        composeRule
            .onNodeWithText("Login")
            .assertIsDisplayed()
    }

    @Test
    fun boton_ingresar_visible() {
        composeRule
            .onNodeWithText("Ingresar")
            .assertIsDisplayed()
    }
}

