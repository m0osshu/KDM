package com.example.myautoo.uiTest.auth

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.myautoo.ui.feature.auth.AuthHeader
import org.junit.Rule
import org.junit.Test

class AuthHeaderTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun authHeader_sinTitulo_muestraLogo() {
        composeRule.setContent {
            AuthHeader()
        }

        composeRule
            .onNodeWithContentDescription("logo")
            .assertIsDisplayed()
    }

    @Test
    fun authHeader_conTitulo_muestraTopBar() {
        var backClicked = false

        composeRule.setContent {
            AuthHeader(
                title = "Registro",
                backIconRes = 1, // recurso dummy
                onBack = { backClicked = true }
            )
        }

        composeRule
            .onNodeWithText("Registro")
            .assertIsDisplayed()
    }
}
