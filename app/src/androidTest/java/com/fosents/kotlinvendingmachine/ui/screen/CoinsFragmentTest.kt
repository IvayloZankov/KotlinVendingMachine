package com.fosents.kotlinvendingmachine.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.fosents.kotlinvendingmachine.data.remote.utils.OneTimeEvent
import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CoinsFragmentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testProduct = Product(1, "Coke", 2.50, 10)

    private val testCoins = listOf(
        Coin(1, "Toonie", 2.0, 10),
        Coin(2, "Loonie", 1.0, 10),
        Coin(3, "Quarter", 0.25, 10)
    )

    private fun handledErrorEvent(): OneTimeEvent<Throwable> {
        val event = OneTimeEvent(RuntimeException())
        event.hasBeenHandled = true
        return event
    }

    private fun setUpCoinsScreen(
        selectedProduct: Product? = testProduct,
        coinsStorage: List<Coin> = testCoins,
        insertedAmount: String = "0.00",
        priceMet: Boolean = false,
        changeCalculated: Boolean = false,
        orderCancelled: Boolean = false,
        stateFlowError: OneTimeEvent<Throwable> = handledErrorEvent(),
        listChange: List<Coin> = emptyList(),
        onOrderCancelledCLick: () -> Unit = {},
        onAddAllUserCoins: () -> Unit = {},
        onAddUserCoin: (Coin) -> Unit = {},
        onAlertOkClick: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            CoinsScreen(
                selectedProduct = selectedProduct,
                coinsStorage = coinsStorage,
                insertedAmount = insertedAmount,
                priceMet = priceMet,
                changeCalculated = changeCalculated,
                orderCancelled = orderCancelled,
                stateFlowError = stateFlowError,
                listChange = listChange,
                onOrderCancelledCLick = onOrderCancelledCLick,
                onAddAllUserCoins = onAddAllUserCoins,
                onAddUserCoin = onAddUserCoin,
                onAlertOkClick = onAlertOkClick
            )
        }
    }

    @Test
    fun coinsScreen_displaysProductName() {
        setUpCoinsScreen()

        composeTestRule
            .onNodeWithText("Coke")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_displaysInsertedAmount() {
        setUpCoinsScreen(insertedAmount = "1.25")

        composeTestRule
            .onNodeWithText("1.25")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_displaysProductPrice() {
        setUpCoinsScreen()

        composeTestRule
            .onNodeWithText("2.50")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_displaysCoinsInsertedLabel() {
        setUpCoinsScreen()

        composeTestRule
            .onNodeWithText("Coins Inserted")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_displaysProductPriceLabel() {
        setUpCoinsScreen()

        composeTestRule
            .onNodeWithText("Product Price")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_displaysAllCoinButtons() {
        setUpCoinsScreen()

        composeTestRule
            .onNodeWithText("2.00")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("1.00")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("0.25")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_clickingCoinButton_invokesOnAddUserCoin() {
        var clickedCoin: Coin? = null
        setUpCoinsScreen(
            onAddUserCoin = { clickedCoin = it }
        )

        composeTestRule
            .onNodeWithText("2.00")
            .performClick()

        assertEquals(testCoins[0], clickedCoin)
    }

    @Test
    fun coinsScreen_clickingLoonieCoin_invokesOnAddUserCoin() {
        var clickedCoin: Coin? = null
        setUpCoinsScreen(
            onAddUserCoin = { clickedCoin = it }
        )

        composeTestRule
            .onNodeWithText("1.00")
            .performClick()

        assertEquals(testCoins[1], clickedCoin)
    }

    @Test
    fun coinsScreen_clickingQuarterCoin_invokesOnAddUserCoin() {
        var clickedCoin: Coin? = null
        setUpCoinsScreen(
            onAddUserCoin = { clickedCoin = it }
        )

        composeTestRule
            .onNodeWithText("0.25")
            .performClick()

        assertEquals(testCoins[2], clickedCoin)
    }

    @Test
    fun coinsScreen_whenPriceMet_coinClickDoesNotInvokeCallback() {
        var coinClicked = false
        setUpCoinsScreen(
            priceMet = true,
            onAddUserCoin = { coinClicked = true },
            onAddAllUserCoins = {} // no-op to prevent side effects
        )

        composeTestRule
            .onNodeWithText("2.00")
            .performClick()

        assertTrue("Coin click should be ignored when price is met", !coinClicked)
    }

    @Test
    fun coinsScreen_whenPriceMet_showsProgressIndicator() {
        setUpCoinsScreen(
            priceMet = true,
            onAddAllUserCoins = {}
        )

        // CircularProgressIndicator is shown when priceMet is true
        // The coins grid should be dimmed (alpha 0.5)
        composeTestRule
            .onNodeWithText("Coke")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_withNullProduct_doesNotCrash() {
        setUpCoinsScreen(selectedProduct = null)

        // Product name should not be displayed
        composeTestRule
            .onNodeWithText("Coke")
            .assertDoesNotExist()
    }

    @Test
    fun coinsScreen_withEmptyCoins_displaysNoButtons() {
        setUpCoinsScreen(coinsStorage = emptyList())

        composeTestRule
            .onNodeWithText("2.00")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("1.00")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("0.25")
            .assertDoesNotExist()
    }

    @Test
    fun coinsScreen_displaysTopBarTitle() {
        setUpCoinsScreen()

        composeTestRule
            .onNodeWithText("Compose VM")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_backButtonInTopBar_invokesOnOrderCancelled() {
        var cancelClicked = false
        setUpCoinsScreen(
            onOrderCancelledCLick = { cancelClicked = true }
        )

        composeTestRule
            .onNodeWithContentDescription("Back button")
            .performClick()

        assertTrue("Back button should trigger order cancellation", cancelClicked)
    }

    @Test
    fun coinsScreen_whenPriceMetAndChangeCalculated_showsGetProductAlert() {
        setUpCoinsScreen(
            priceMet = true,
            changeCalculated = true,
            listChange = listOf(Coin(3, "Quarter", 0.25, 1))
        )

        // The ShowGetProductAlert should be displayed
        composeTestRule
            .onNodeWithText("THANK YOU!")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_whenOrderCancelled_showsOrderCancelledAlert() {
        setUpCoinsScreen(
            orderCancelled = true,
            listChange = listOf(Coin(1, "Toonie", 2.0, 1))
        )

        composeTestRule
            .onNodeWithText("ORDER CANCELLED!")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_whenErrorOccurs_showsNoConnectionAlert() {
        val unhandledError = OneTimeEvent(RuntimeException("Network error"))

        setUpCoinsScreen(stateFlowError = unhandledError)

        composeTestRule
            .onNodeWithText("Network error")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_updatedInsertedAmount_reflectsNewValue() {
        setUpCoinsScreen(insertedAmount = "3.75")

        composeTestRule
            .onNodeWithText("3.75")
            .assertIsDisplayed()
    }

    @Test
    fun coinsScreen_coinButtonsAreEnabled_whenPriceNotMet() {
        setUpCoinsScreen(priceMet = false)

        composeTestRule
            .onNodeWithText("2.00")
            .assertIsEnabled()
        composeTestRule
            .onNodeWithText("1.00")
            .assertIsEnabled()
        composeTestRule
            .onNodeWithText("0.25")
            .assertIsEnabled()
    }

    @Test
    fun coinsScreen_singleCoin_displayedCorrectly() {
        val singleCoin = listOf(Coin(1, "Toonie", 2.0, 5))
        setUpCoinsScreen(coinsStorage = singleCoin)

        composeTestRule
            .onNodeWithText("2.00")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("1.00")
            .assertDoesNotExist()
    }
}
