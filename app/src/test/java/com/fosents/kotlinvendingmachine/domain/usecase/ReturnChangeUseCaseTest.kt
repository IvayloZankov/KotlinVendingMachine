package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Coin
import org.junit.Assert.assertEquals
import org.junit.Test

class ReturnChangeUseCaseTest {

    private val useCase = ReturnChangeUseCase()

    @Test
    fun `invoke should return correct change with descending sorted coins`() {
        
        val coinsStorage = mutableListOf(
            Coin(1, "Toonie", 2.0, 10),
            Coin(2, "Loonie", 1.0, 10),
            Coin(3, "Quarter", 0.25, 10)
        )
        val insertedAmount = "1.00"
        val productPrice = 0.50

        val result = useCase(insertedAmount, productPrice, coinsStorage)

        assertEquals(2, result.sumOf { it.quantity })
        assertEquals(0.50, result.sumOf { it.price * it.quantity }, 0.01)
        assertEquals("Quarter", result[0].name)
    }

    @Test
    fun `invoke should return correct change with ascending sorted coins`() {
        
        val coinsStorage = mutableListOf(
            Coin(3, "Quarter", 0.25, 10),
            Coin(2, "Loonie", 1.0, 10),
            Coin(1, "Toonie", 2.0, 10)
        )
        val insertedAmount = "5.00"
        val productPrice = 2.50
        
        val result = useCase(insertedAmount, productPrice, coinsStorage)
        
        val totalChange = result.sumOf { it.price * it.quantity }
        assertEquals(2.50, totalChange, 0.01)

        val toonies = result.find { it.price == 2.0 }?.quantity ?: 0
        assertEquals(1, toonies)

        val quarters = result.find { it.price == 0.25 }?.quantity ?: 0
        assertEquals(2, quarters)
    }
}
