package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.model.insertCoin
import com.fosents.kotlinvendingmachine.domain.usecase.ExecutePurchaseOrderUseCase
import com.fosents.kotlinvendingmachine.domain.usecase.GetProductAndCoinsUseCase
import com.fosents.kotlinvendingmachine.sound.SoundManager
import com.fosents.kotlinvendingmachine.ui.navigation.ARG_PRODUCT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val getProductAndCoinsUseCase: GetProductAndCoinsUseCase,
    private val executePurchaseOrderUseCase: ExecutePurchaseOrderUseCase,
    private val soundManager: SoundManager,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _stateFlowCoinsStorage = MutableStateFlow<List<Coin>>(emptyList())
    val stateFlowCoinsStorage = _stateFlowCoinsStorage.asStateFlow()

    private val _stateFlowSelectedProduct = MutableStateFlow<Product?>(null)
    val stateFlowSelectedProduct = _stateFlowSelectedProduct.asStateFlow()

    private val _stateFlowInsertedAmount = MutableStateFlow("0.00")
    val stateFlowInsertedAmount = _stateFlowInsertedAmount.asStateFlow()

    private val _stateFlowListCoinsUser = MutableStateFlow(listOf<Coin>())
    val stateFlowListCoinsUser = _stateFlowListCoinsUser.asStateFlow()

    private val _stateFlowListChange = MutableStateFlow(listOf<Coin>())
    val stateFlowListChange = _stateFlowListChange.asStateFlow()

    private val _stateFlowPriceMet = MutableStateFlow(false)
    val stateFlowPriceMet = _stateFlowPriceMet.asStateFlow()

    private val _stateFlowChangeCalculated = MutableStateFlow(false)
    val stateFlowChangeCalculated = _stateFlowChangeCalculated.asStateFlow()

    private val _stateFlowOrderCancelled = MutableStateFlow(false)
    val stateflowOrderCancelled = _stateFlowOrderCancelled.asStateFlow()

    private val productId = savedStateHandle.get<Int>(ARG_PRODUCT_ID) ?: -1

    init {
        viewModelScope.request {

            val data = getProductAndCoinsUseCase(productId)

            _stateFlowSelectedProduct.update {
                data.selectedProduct
            }
            _stateFlowCoinsStorage.update {
                data.coins
            }
        }
    }

    fun addUserCoin(coin: Coin) {
        soundManager.play(SoundManager.SoundType.COIN)
        _stateFlowInsertedAmount.update {
            BigDecimal(it).add(BigDecimal.valueOf(coin.price)).toString()
        }
        val coinUser = coin.copy(quantity = 1)
        _stateFlowListCoinsUser.update { list ->
            list.insertCoin(coinUser)
        }

        if (stateFlowInsertedAmount.value.toDouble() >= stateFlowSelectedProduct.value!!.price)
            _stateFlowPriceMet.update { true }
    }

    fun addUserCoins() {
        viewModelScope.request {
            val currentProduct = _stateFlowSelectedProduct.value ?: return@request
            val currentStorage = _stateFlowCoinsStorage.value
            val userCoins = _stateFlowListCoinsUser.value
            val totalAmount = stateFlowInsertedAmount.value

            val result = executePurchaseOrderUseCase(
                product = currentProduct,
                currentStorageCoins = currentStorage,
                userInsertedCoins = userCoins,
                totalInsertedAmount = totalAmount
            )

            _stateFlowSelectedProduct.value = result.updatedProduct
            _stateFlowCoinsStorage.value = result.updatedCoins
            _stateFlowListChange.value = result.change

            _stateFlowListCoinsUser.value = emptyList()
            _stateFlowChangeCalculated.value = true
        }
    }

    fun cancelOrder() {
        _stateFlowListChange.update {
            _stateFlowListCoinsUser.value
        }
        _stateFlowListCoinsUser.update {
            emptyList()
        }
        _stateFlowOrderCancelled.value = true
    }

    fun playClickSound() {
        soundManager.play(SoundManager.SoundType.CLICK)
    }
}
