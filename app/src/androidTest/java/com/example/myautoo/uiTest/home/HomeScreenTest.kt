package com.example.myautoo.homeTest

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myautoo.data.model.CarModel
import com.example.myautoo.data.remote.dto.MarcaDto
import com.example.myautoo.ui.feature.home.MainScreen
import com.example.myautoo.ui.viewModel.CarViewModel
import com.example.myautoo.ui.viewModel.CategoryViewModel
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.MutableStateFlow

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var carViewModel: CarViewModel
    private lateinit var categoryViewModel: CategoryViewModel

    private var carClicked: CarModel? = null
    private var profileClicked = false
    private var cartClicked = false

    @Before
    fun setup() {

        //  Mock del CarViewModel
        carViewModel = mockk(relaxed = true)

        val fakeCars = listOf(
            CarModel(
                id = 1,
                title = "Toyota Corolla",
                price = 12000.0,
                picUrl = "",
                brand = "Toyota"
            )
        )

        every { carViewModel.cars } returns MutableStateFlow(fakeCars)
        every { carViewModel.searchText } returns MutableStateFlow("")
        every { carViewModel.isLoading } returns mutableStateOf(false)
        every { carViewModel.error } returns mutableStateOf(null)

        // Mock del CategoryViewModel
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

        // compose final
        composeTestRule.setContent {
            MainScreen(
                onProfileClick = { profileClicked = true },
                onCarClick = { carClicked = it },
                onCartClick = { cartClicked = true },
                carViewModel = carViewModel,
                categoryViewModel = categoryViewModel
            )
        }
    }

    @Test
    fun home_se_muestra_correctamente() {
        composeTestRule
            .onNodeWithText("Mostrar todos")
            .assertIsDisplayed()
    }

    @Test
    fun search_input_visible() {
        composeTestRule
            .onNodeWithText("buscar auto...")
            .assertIsDisplayed()
    }

    @Test
    fun categoria_visible() {
        composeTestRule
            .onNodeWithText("Toyota")
            .assertIsDisplayed()
    }

    @Test
    fun auto_visible_en_catalogo() {
        composeTestRule
            .onNodeWithText("Toyota Corolla")
            .assertIsDisplayed()
    }

    @Test
    fun click_en_auto_dispara_callback() {
        composeTestRule
            .onNodeWithText("Toyota Corolla")
            .performClick()

        assertNotNull(carClicked)
        assertEquals("Toyota Corolla", carClicked?.title)
    }

    @Test
    fun click_en_carrito_funciona() {
        composeTestRule
            .onNodeWithContentDescription("Carrito")
            .performClick()

        assertTrue(cartClicked)
    }

    @Test
    fun click_en_perfil_funciona() {
        composeTestRule
            .onNodeWithContentDescription("Perfil")
            .performClick()

        assertTrue(profileClicked)
    }
}
