package com.silkfinik.vinylcatalog.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class VinylCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun vinylCard_displaysTitleAndArtist_andRespondsToClick() {
        var clicked = false

        composeTestRule.setContent {
            VinylCard(
                title = "Random Access Memories",
                artist = "Daft Punk",
                coverUrl = null,
                onClick = { clicked = true }
            )
        }

        // Assert Display
        composeTestRule.onNodeWithText("Random Access Memories").assertIsDisplayed()
        composeTestRule.onNodeWithText("Daft Punk").assertIsDisplayed()

        // Assert Interaction
        composeTestRule.onNodeWithText("Random Access Memories").performClick()
        assertTrue(clicked)
    }
}
