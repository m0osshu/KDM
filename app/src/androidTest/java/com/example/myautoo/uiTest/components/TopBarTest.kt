package com.example.myautoo.uiTest.componentsTest

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.myautoo.R
import com.example.myautoo.ui.components.TopBar
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class TopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun topBar_DisplaysTitleAndBackButton_AndHandlesBackClick() {
        // 1. Arrange: Preparamos el estado
        var backClicked = false
        val title = "Test Title"

        // 2. Act: Renderizamos el componente
        composeTestRule.setContent {
            TopBar(
                title = title,
                backIconRes = R.drawable.back1, // Usamos un recurso de drawable real
                onBack = { backClicked = true }
            )
        }

        // 3. Assert: Verificamos la UI y las interacciones
        // Comprueba que el título se muestra
        composeTestRule.onNodeWithText(title).assertIsDisplayed()

        // Comprueba que el botón de atrás se muestra
        val backButton = composeTestRule.onNodeWithContentDescription("Back")
        backButton.assertIsDisplayed()

        // Comprueba que el icono de acción NO se muestra
        composeTestRule.onNodeWithContentDescription("Trailing Action").assertDoesNotExist()

        // Simula un clic en el botón de atrás
        backButton.performClick()

        // Verifica que la función onBack fue llamada
        assertTrue(backClicked)
    }

    @Test
    fun topBar_DisplaysTrailingIcon_AndHandlesTrailingClick() {
        // 1. Arrange
        var trailingClicked = false
        val title = "Test With Trailing"

        // 2. Act
        composeTestRule.setContent {
            TopBar(
                title = title,
                backIconRes = R.drawable.back1,
                onBack = { },
                trailingIconRes = R.drawable.logokmd, // Un drawable real para el icono de acción
                onTrailingClick = { trailingClicked = true }
            )
        }

        // 3. Assert
        // Comprueba que el título se muestra
        composeTestRule.onNodeWithText(title).assertIsDisplayed()

        // Comprueba que el botón de atrás se muestra
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()

        // Comprueba que el icono de acción SÍ se muestra
        val trailingButton = composeTestRule.onNodeWithContentDescription("Trailing Action")
        trailingButton.assertIsDisplayed()

        // Simula un clic en el icono de acción
        trailingButton.performClick()

        // Verifica que la función onTrailingClick fue llamada
        assertTrue(trailingClicked)
    }
}