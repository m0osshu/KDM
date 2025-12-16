package com.example.myautoo.navigationTest

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
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
    fun startDestination_home() {
        assertEquals(
            Screens.HOME,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    @Test
    fun navegar_register() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.REGISTER)
        }

        assertEquals(
            Screens.REGISTER,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    @Test
    fun navegar_profile() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.PROFILE)
        }

        assertEquals(
            Screens.PROFILE,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    @Test
    fun navegar_cart() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.CART)
        }

        assertEquals(
            Screens.CART,
            navController.currentBackStackEntry?.destination?.route
        )
    }

    @Test
    fun navegar_admin() {
        composeTestRule.runOnUiThread {
            navController.navigate(Screens.ADMIN)
        }

        assertEquals(
            Screens.ADMIN,
            navController.currentBackStackEntry?.destination?.route
        )
    }
}
