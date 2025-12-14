package com.example.myautoo.authTest

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myautoo.navigation.Screens
import com.example.myautoo.ui.feature.auth.ProfileScreen
import com.example.myautoo.ui.viewModel.AuthViewModel
import com.example.myautoo.ui.viewModel.UserPhotoViewModel
import com.example.myautoo.data.repository.UserPhotoRepository
import com.example.myautoo.ui.viewModel.AuthResult
import com.example.myautoo.ui.viewModel.AuthUiState
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userPhotoViewModel: UserPhotoViewModel

    @Before
    fun setup() {
        authViewModel = mockk(relaxed = true)
        userPhotoViewModel = mockk(relaxed = true)

        //  Fake FirebaseUser
        val fakeUser = mockk<FirebaseUser>(relaxed = true)
        every { fakeUser.uid } returns "123"

        // diferentes StateFlows
        every { authViewModel.currentUser } returns MutableStateFlow(fakeUser)
        every { authViewModel.uiState } returns MutableStateFlow(AuthUiState())
        every { authViewModel.authResult } returns MutableStateFlow(AuthResult.Idle)
        every { authViewModel.isLoading } returns MutableStateFlow(false)

        every { userPhotoViewModel.photoUri } returns MutableStateFlow(null)

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
                userPhotoViewModel = userPhotoViewModel
            )
        }

    }

    @Test
    fun profile_muestra_elementos_principales() {
        composeTestRule.onNodeWithText("Seleccionar desde galería").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tomar foto con cámara").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cerrar Sesión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Panel de Marcas (Admin)").assertIsDisplayed()
    }

    @Test
    fun cerrar_sesion_navega_a_login() {
        composeTestRule.onNodeWithText("Cerrar Sesión").performClick()

        assert(navController.currentDestination?.route == Screens.LOGIN)
    }


    @Test
    fun navega_a_panel_admin() {
        composeTestRule.onNodeWithText("Panel de Marcas (Admin)").performClick()

        assert(navController.currentDestination?.route == Screens.ADMIN)
    }

    @Test
    fun muestra_foto_por_defecto_si_no_hay_uri() {
        composeTestRule
            .onNodeWithContentDescription("Foto por defecto")
            .assertIsDisplayed()
    }

    @Test
    fun muestra_foto_usuario_si_existe() {
        every { userPhotoViewModel.photoUri } returns MutableStateFlow("fake://photo")

        composeTestRule.setContent {
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
                userPhotoViewModel = userPhotoViewModel
            )
        }

        composeTestRule.waitUntil(3_000) {
            composeTestRule
                .onAllNodesWithContentDescription("Foto de perfil")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onNodeWithContentDescription("Foto de perfil")
            .assertIsDisplayed()
    }






}

