package com.example.myautoo.navigationTest

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myautoo.navigation.AppNavGraph
import com.example.myautoo.navigation.Screens
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import junit.framework.TestCase.assertEquals

@RunWith(AndroidJUnit4::class)
class NavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }

            AppNavGraph(navController = navController)
        }
    }

    @Test
    fun startDestination_esHome() {
        assertEquals(
            Screens.HOME,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    @Test
    fun navegar_aRegister() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.REGISTER)
        }

        assertEquals(
            Screens.REGISTER,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    @Test
    fun navegar_aProfile() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.PROFILE)
        }

        assertEquals(
            Screens.PROFILE,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    @Test
    fun navegar_aCart() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.CART)
        }

        assertEquals(
            Screens.CART,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    @Test
    fun navegar_aAdmin() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.ADMIN)
        }

        assertEquals(
            Screens.ADMIN,
            navController.currentBackStackEntry?.destination?.route
        )
    }
}
