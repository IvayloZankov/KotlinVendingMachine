package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Coin
import org.junit.Assert.assertEquals
import org.junit.Test

class InsertUserCoinsUseCaseTest {

    private val useCase = InsertUserCoinsUseCase()

    @Test
    fun `invoke should add user coins to storage`() {
        val storageCoins = listOf(
            Coin(1, "Toonie", 2.0, 10),
            Coin(2, "Loonie", 1.0, 10)
        )
        val userCoins = listOf(
            Coin(1, "Toonie", 2.0, 2),
            Coin(2, "Loonie", 1.0, 3)
        )

        val result = useCase(userCoins, storageCoins)

        assertEquals(12, result[0].quantity)
        assertEquals(13, result[1].quantity)
    }

    @Test
    fun `invoke should handle empty user coins`() {
        val storageCoins = listOf(
            Coin(1, "Toonie", 2.0, 10),
            Coin(2, "Loonie", 1.0, 10)
        )
        val userCoins = emptyList<Coin>()

        val result = useCase(userCoins, storageCoins)

        assertEquals(10, result[0].quantity)
        assertEquals(10, result[1].quantity)
    }
}
