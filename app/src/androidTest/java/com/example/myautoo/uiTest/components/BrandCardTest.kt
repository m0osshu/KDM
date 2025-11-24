package com.example.myautoo.uiTest.componentsTest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.myautoo.ui.components.BrandCard
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class BrandCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun brandCard_DisplaysNameAndImage_AndHandlesClick() {
        // 1. Arrange
        var cardClicked = false
        val brandName = "Test Brand"
        val brandImageUrl = ""

        // 2. Act
        composeTestRule.setContent {
            BrandCard(
                brandImageUrl = brandImageUrl,
                brandName = brandName,
                onClick = { cardClicked = true }
            )
        }

        // 3. Assert
        // Comprueba que el nombre se muestra
        composeTestRule.onNodeWithText(brandName).assertIsDisplayed()

        // Comprueba que la imagen se muestra (buscándola por su descripción)
        composeTestRule.onNodeWithContentDescription(brandName).assertIsDisplayed()

        // Simula un clic en el componente
        composeTestRule.onNodeWithText(brandName).performClick()

        // Verifica que la función onClick fue llamada
        assertTrue(cardClicked)
    }
}