package com.example.myautoo.uiTest.cart

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myautoo.data.local.entity.CartItemEntity
import com.example.myautoo.navigation.Screens
import com.example.myautoo.ui.feature.cart.CartScreen
import com.example.myautoo.ui.viewModel.AuthViewModel
import com.example.myautoo.ui.viewModel.CartViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var cartViewModel: CartViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: TestNavHostController

    private val fakeItem = CartItemEntity(
        id = 1,
        carId = 10,
        title = "Toyota Corolla",
        price = 12000.0
    )

    @Before
    fun setup() {
        cartViewModel = mockk(relaxed = true)
        authViewModel = mockk(relaxed = true)

        every { cartViewModel.cartItems } returns
                MutableStateFlow(listOf(fakeItem))

        every { cartViewModel.totalPrice } returns
                MutableStateFlow(12000.0)

        every { authViewModel.currentUser } returns
                MutableStateFlow(mockk(relaxed = true))

        composeRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                authViewModel = authViewModel
            )
        }
    }

    @Test
    fun muestra_items_del_carrito() {
        composeRule
            .onNodeWithText("Toyota Corolla")
            .assertIsDisplayed()

        composeRule
            .onNodeWithText("$12,000")
            .assertIsDisplayed()
    }

    @Test
    fun boton_comprar_visible() {
        composeRule
            .onNodeWithText("Comprar")
            .assertIsDisplayed()
    }

    @Test
    fun eliminar_item_llama_al_viewModel() {
        composeRule
            .onNodeWithContentDescription("Eliminar")
            .performClick()

        verify { cartViewModel.removeFromCart(fakeItem) }
    }
}
