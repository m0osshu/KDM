package com.example.myautoo.uiTest.componentsTest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.myautoo.R
import com.example.myautoo.ui.components.SpecCard
import org.junit.Rule
import org.junit.Test

class SpecCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun specCard_DisplaysAllInformation() {
        // 1. Arrange
        val title = "Horsepower"
        val value = "250 HP"
        val iconRes = R.drawable.logokmd // Usamos un drawable real

        // 2. Act
        composeTestRule.setContent {
            SpecCard(
                iconRes = iconRes,
                title = title,
                value = value
            )
        }

        // 3. Assert
        // Comprueba que el título se muestra
        composeTestRule.onNodeWithText(title).assertIsDisplayed()

        // Comprueba que el valor se muestra
        composeTestRule.onNodeWithText(value).assertIsDisplayed()

        // Comprueba que el icono se muestra (buscándolo por su descripción)
        composeTestRule.onNodeWithContentDescription(title).assertIsDisplayed()
    }
}