package com.example.myautoo.uiTest.detail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myautoo.data.model.CarModel
import com.example.myautoo.data.remote.dto.MarcaDto
import com.example.myautoo.ui.feature.detail.DetailScreen
import com.example.myautoo.ui.viewModel.CartViewModel
import com.example.myautoo.ui.viewModel.CategoryViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var cartViewModel: CartViewModel

    private val fakeCar = CarModel(
        id = 1,
        title = "Toyota Corolla",
        description = "Sedán confiable",
        price = 25000.0,
        brand = "Toyota",
        rating = 4.5,
        picUrl = ""
    )

    @Before
    fun setup() {

        // Mock CategoryViewModel
        categoryViewModel = mockk(relaxed = true)
        every { categoryViewModel.categories } returns mutableStateOf(
            listOf(
                MarcaDto(
                    id = 1,
                    nombre = "Toyota",
                    imagenMarca = ""
                )
            )
        )
        every { categoryViewModel.isLoading } returns mutableStateOf(false)
        every { categoryViewModel.error } returns mutableStateOf(null)

        // Mock CartViewModel
        cartViewModel = mockk(relaxed = true)

        // regla del Compose
        composeRule.setContent {
            DetailScreen(
                car = fakeCar,
                categoryViewModel = categoryViewModel,
                cartViewModel = cartViewModel,
                onBack = {}
            )
        }
    }

    @Test
    fun muestra_titulo_y_descripcion() {
        composeRule.onNodeWithText("Toyota Corolla").assertIsDisplayed()
        composeRule.onNodeWithText("Sedán confiable").assertIsDisplayed()
    }

    @Test
    fun muestra_rating() {
        composeRule.onNodeWithText("Rating").assertIsDisplayed()
        composeRule.onNodeWithText("4.5").assertIsDisplayed()
    }

    @Test
    fun muestra_precio() {
        composeRule.onNodeWithText("$25,000").assertIsDisplayed()
    }

    @Test
    fun click_en_añadir_al_carrito_llama_viewmodel() {
        composeRule
            .onNodeWithText("Añadir al carrito")
            .performClick()

        io.mockk.verify {
            cartViewModel.addToCart(fakeCar)
        }
    }
}

