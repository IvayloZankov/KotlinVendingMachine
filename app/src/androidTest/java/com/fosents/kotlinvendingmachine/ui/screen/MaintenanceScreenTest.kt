package com.fosents.kotlinvendingmachine.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.remote.utils.OneTimeEvent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MaintenanceScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun handledErrorEvent(): OneTimeEvent<Throwable> {
        val event = OneTimeEvent(RuntimeException())
        event.hasBeenHandled = true
        return event
    }

    private fun setUpMaintenanceScreen(
        stateFlowError: OneTimeEvent<Throwable> = handledErrorEvent(),
        onResetClick: (Int) -> Unit = {},
        onBackClick: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            MaintenanceScreen(
                stateFlowError = stateFlowError,
                onResetClick = onResetClick,
                onBackClick = onBackClick
            )
        }
    }

    @Test
    fun maintenanceScreen_displaysTopBarTitle() {
        setUpMaintenanceScreen()

        composeTestRule
            .onNodeWithText("Compose VM")
            .assertIsDisplayed()
    }

    @Test
    fun maintenanceScreen_backButton_invokesCallback() {
        var backClicked = false
        setUpMaintenanceScreen(
            onBackClick = { backClicked = true }
        )

        // The content description used in MaintenanceScreen for back button is "Maintenance button"
        composeTestRule
            .onNodeWithContentDescription("Maintenance button")
            .performClick()

        assertTrue("Back button should invoke callback", backClicked)
    }

    @Test
    fun maintenanceScreen_displaysResetProductsOption() {
        setUpMaintenanceScreen()

        composeTestRule
            .onNodeWithText("Reset products")
            .assertIsDisplayed()
    }

    @Test
    fun maintenanceScreen_displaysResetCoinsOption() {
        setUpMaintenanceScreen()

        composeTestRule
            .onNodeWithText("Reset coins")
            .assertIsDisplayed()
    }

    @Test
    fun maintenanceScreen_clickingResetProducts_invokesCallbackWithCorrectId() {
        var clickedOptionId: Int? = null
        setUpMaintenanceScreen(
            onResetClick = { clickedOptionId = it }
        )

        composeTestRule
            .onNodeWithText("Reset products")
            .performClick()

        assertEquals(R.string.maintenance_products_reset, clickedOptionId)
    }

    @Test
    fun maintenanceScreen_clickingResetCoins_invokesCallbackWithCorrectId() {
        var clickedOptionId: Int? = null
        setUpMaintenanceScreen(
            onResetClick = { clickedOptionId = it }
        )

        composeTestRule
            .onNodeWithText("Reset coins")
            .performClick()

        assertEquals(R.string.maintenance_coins_reset, clickedOptionId)
    }

    @Test
    fun maintenanceScreen_whenErrorOccurs_showsNoConnectionAlert() {
        val unhandledError = OneTimeEvent(RuntimeException("test"))
        setUpMaintenanceScreen(stateFlowError = unhandledError)

        composeTestRule
            .onNodeWithText("Network error")
            .assertIsDisplayed()
    }

    @Test
    fun maintenanceScreen_whenErrorOccurs_showsCheckConnectionMessage() {
        val unhandledError = OneTimeEvent(RuntimeException("test"))
        setUpMaintenanceScreen(stateFlowError = unhandledError)

        composeTestRule
            .onNodeWithText("Please check your connection")
            .assertIsDisplayed()
    }

    @Test
    fun maintenanceScreen_whenNoError_doesNotShowAlert() {
        setUpMaintenanceScreen()

        composeTestRule
            .onNodeWithText("Network error")
            .assertDoesNotExist()
    }
}
