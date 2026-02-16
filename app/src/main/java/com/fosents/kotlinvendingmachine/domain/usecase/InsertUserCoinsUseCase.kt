package com.fosents.kotlinvendingmachine.domain.usecase

import com.fosents.kotlinvendingmachine.domain.model.Coin
import javax.inject.Inject

class InsertUserCoinsUseCase @Inject constructor() {

    operator fun invoke(listUser: List<Coin>, listStorage: List<Coin>) {
        listUser.forEach { coinUser ->
            for (index in listStorage.indices) {
                if (coinUser.id == listStorage[index].id) {
                    listStorage[index].quantity = listStorage[index].quantity.plus(coinUser.quantity)
                    break
                }
            }
        }
    }
}
