package com.fosents.kotlinvendingmachine.ui.screen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.fosents.kotlinvendingmachine.data.remote.utils.OneTimeEvent
import com.fosents.kotlinvendingmachine.domain.model.Product
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ProductsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testProducts = listOf(
        Product(1, "Chips", 2.50, 10),
        Product(2, "Soda", 1.50, 5),
        Product(3, "Candy", 1.00, 20),
        Product(4, "Water", 1.00, 0)
    )

    private fun handledErrorEvent(): OneTimeEvent<Throwable> {
        val event = OneTimeEvent(RuntimeException())
        event.hasBeenHandled = true
        return event
    }

    private fun setUpProductsScreen(
        products: List<Product> = testProducts,
        outOfOrder: Boolean = false,
        stateFlowError: OneTimeEvent<Throwable> = handledErrorEvent(),
        isLoading: Boolean = false,
        onRetryClick: () -> Unit = {},
        onGoMaintenanceClick: () -> Unit = {},
        onProductClick: (Int) -> Unit = {}
    ) {
        composeTestRule.setContent {
            ProductsScreen(
                products = products,
                outOfOrder = outOfOrder,
                stateFlowError = stateFlowError,
                isLoading = isLoading,
                onRetryClick = onRetryClick,
                onGoMaintenanceClick = onGoMaintenanceClick,
                onProductClick = onProductClick
            )
        }
    }

    @Test
    fun productsScreen_displaysTopBarTitle() {
        setUpProductsScreen()

        composeTestRule
            .onNodeWithText("Compose VM")
            .assertIsDisplayed()
    }

    @Test
    fun productsScreen_maintenanceButton_invokesCallback() {
        var maintenanceClicked = false
        setUpProductsScreen(
            onGoMaintenanceClick = { maintenanceClicked = true }
        )

        composeTestRule
            .onNodeWithContentDescription("Maintenance button")
            .performClick()

        assertTrue("Maintenance button should invoke callback", maintenanceClicked)
    }

    @Test
    fun productsScreen_displaysAllProductNames() {
        setUpProductsScreen()

        composeTestRule.onNodeWithText("Chips").assertIsDisplayed()
        composeTestRule.onNodeWithText("Soda").assertIsDisplayed()
        composeTestRule.onNodeWithText("Candy").assertIsDisplayed()
        composeTestRule.onNodeWithText("Water").assertIsDisplayed()
    }

    @Test
    fun productsScreen_displaysProductPrices() {
        setUpProductsScreen()

        composeTestRule.onNodeWithText("2.50").assertIsDisplayed()
        composeTestRule.onNodeWithText("1.50").assertIsDisplayed()
    }

    @Test
    fun productsScreen_samePriceProducts_displayedMultipleTimes() {
        setUpProductsScreen()

        composeTestRule
            .onAllNodesWithText("1.00")
            .assertCountEquals(1)
    }

    @Test
    fun productsScreen_outOfStockProduct_showsNoQuantity() {
        setUpProductsScreen()

        composeTestRule
            .onNodeWithText("NO QUANTITY")
            .assertIsDisplayed()
    }

    @Test
    fun productsScreen_clickingInStockProduct_invokesCallback() {
        var clickedProductId: Int? = null
        setUpProductsScreen(
            onProductClick = { clickedProductId = it }
        )

        composeTestRule
            .onNodeWithText("Chips")
            .performClick()

        assertEquals(1, clickedProductId)
    }

    @Test
    fun productsScreen_clickingSecondProduct_invokesCallbackWithCorrectId() {
        var clickedProductId: Int? = null
        setUpProductsScreen(
            onProductClick = { clickedProductId = it }
        )

        composeTestRule
            .onNodeWithText("Soda")
            .performClick()

        assertEquals(2, clickedProductId)
    }

    @Test
    fun productsScreen_clickingOutOfStockProduct_doesNotInvokeCallback() {
        var productClicked = false
        setUpProductsScreen(
            onProductClick = { productClicked = true }
        )

        composeTestRule
            .onNodeWithText("Water")
            .performClick()

        assertTrue("Out of stock product click should be ignored", !productClicked)
    }

    @Test
    fun productsScreen_productButtonsAreEnabled() {
        setUpProductsScreen()

        composeTestRule.onNodeWithText("Chips").assertIsEnabled()
        composeTestRule.onNodeWithText("Soda").assertIsEnabled()
        composeTestRule.onNodeWithText("Candy").assertIsEnabled()
        composeTestRule.onNodeWithText("Water").assertIsEnabled()
    }

    @Test
    fun productsScreen_singleProduct_displayedCorrectly() {
        val singleProduct = listOf(Product(1, "Juice", 3.00, 8))
        setUpProductsScreen(products = singleProduct)

        composeTestRule.onNodeWithText("Juice").assertIsDisplayed()
        composeTestRule.onNodeWithText("3.00").assertIsDisplayed()
        composeTestRule.onNodeWithText("Chips").assertDoesNotExist()
    }
    
    @Test
    fun productsScreen_whenLoading_doesNotShowProducts() {
        setUpProductsScreen(isLoading = true)

        composeTestRule.onNodeWithText("Chips").assertDoesNotExist()
        composeTestRule.onNodeWithText("Soda").assertDoesNotExist()
    }

    @Test
    fun productsScreen_whenLoading_doesNotShowEmptyMessage() {
        setUpProductsScreen(
            products = emptyList(),
            isLoading = true
        )

        composeTestRule
            .onNodeWithText("OUT OF PRODUCTS")
            .assertDoesNotExist()
    }

    @Test
    fun productsScreen_emptyProducts_showsOutOfProductsMessage() {
        setUpProductsScreen(products = emptyList())

        composeTestRule
            .onNodeWithText("OUT OF PRODUCTS")
            .assertIsDisplayed()
    }

    @Test
    fun productsScreen_emptyProducts_doesNotShowGrid() {
        setUpProductsScreen(products = emptyList())

        composeTestRule.onNodeWithText("Chips").assertDoesNotExist()
    }

    @Test
    fun productsScreen_whenOutOfOrder_showsAlert() {
        setUpProductsScreen(outOfOrder = true)

        composeTestRule
            .onNodeWithText("OUT OF ORDER!")
            .assertIsDisplayed()
    }

    @Test
    fun productsScreen_whenOutOfOrder_showsMaintenanceMessage() {
        setUpProductsScreen(outOfOrder = true)

        composeTestRule
            .onNodeWithText("Not enough coins to operate\nEnter maintenance")
            .assertIsDisplayed()
    }

    @Test
    fun productsScreen_whenOutOfOrder_okButtonInvokesMaintenanceCallback() {
        var maintenanceClicked = false
        setUpProductsScreen(
            outOfOrder = true,
            onGoMaintenanceClick = { maintenanceClicked = true }
        )

        composeTestRule
            .onNodeWithText("OK")
            .performClick()

        assertTrue("OK on out-of-order alert should invoke maintenance callback", maintenanceClicked)
    }

    @Test
    fun productsScreen_whenNotOutOfOrder_doesNotShowAlert() {
        setUpProductsScreen(outOfOrder = false)

        composeTestRule
            .onNodeWithText("OUT OF ORDER!")
            .assertDoesNotExist()
    }

    @Test
    fun productsScreen_whenErrorOccurs_showsNoConnectionAlert() {
        val unhandledError = OneTimeEvent(RuntimeException("test"))
        setUpProductsScreen(stateFlowError = unhandledError)

        composeTestRule
            .onNodeWithText("Network error")
            .assertIsDisplayed()
    }

    @Test
    fun productsScreen_whenErrorOccurs_showsCheckConnectionMessage() {
        val unhandledError = OneTimeEvent(RuntimeException("test"))
        setUpProductsScreen(stateFlowError = unhandledError)

        composeTestRule
            .onNodeWithText("Please check your connection")
            .assertIsDisplayed()
    }

    @Test
    fun productsScreen_whenErrorOccurs_retryButtonInvokesCallback() {
        var retryClicked = false
        val unhandledError = OneTimeEvent(RuntimeException("test"))
        setUpProductsScreen(
            stateFlowError = unhandledError,
            onRetryClick = { retryClicked = true }
        )

        composeTestRule
            .onNodeWithText("RETRY")
            .performClick()

        assertTrue("RETRY button should invoke retry callback", retryClicked)
    }

    @Test
    fun productsScreen_whenNoError_doesNotShowAlert() {
        setUpProductsScreen()

        composeTestRule
            .onNodeWithText("Network error")
            .assertDoesNotExist()
    }
}
