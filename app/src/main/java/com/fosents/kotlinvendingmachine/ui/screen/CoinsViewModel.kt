package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.calc.getCoinsForReturn
import com.fosents.kotlinvendingmachine.calc.insertCoin
import com.fosents.kotlinvendingmachine.calc.insertUserCoins
import com.fosents.kotlinvendingmachine.data.DataRepo
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import com.fosents.kotlinvendingmachine.model.Coin
import com.fosents.kotlinvendingmachine.model.Product
import com.fosents.kotlinvendingmachine.util.Constants.ARG_PRODUCT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val dataRepo: DataRepo,
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

    init {
        viewModelScope.request {
            val productId = savedStateHandle.get<Int>(ARG_PRODUCT_ID)
            _stateFlowSelectedProduct.value = productId?.let {
                dataRepo.getSelectedProduct(it)
            }
            _stateFlowCoinsStorage.value = dataRepo.getCoins()
        }
    }

    fun addUserCoin(coin: Coin) {
        _stateFlowInsertedAmount.update {
            BigDecimal(it).add(BigDecimal.valueOf(coin.price)).toString()
        }
        val coinUser = coin.copy(quantity = 1)
        _stateFlowListCoinsUser.update {
            insertCoin(coinUser, it)
        }

        if (stateFlowInsertedAmount.value.toDouble() >= stateFlowSelectedProduct.value!!.price)
            _stateFlowPriceMet.update { true }
    }

    fun addUserCoins() {
        viewModelScope.request {
            _stateFlowSelectedProduct.value?.let {
                val product = it.copy(quantity = it.quantity.minus(1))
                dataRepo.updateProduct(product)
            }

            insertUserCoins(_stateFlowListCoinsUser.value, _stateFlowCoinsStorage.value)
            _stateFlowListCoinsUser.update {
                emptyList()
            }
            _stateFlowSelectedProduct.value?.let { product ->
                _stateFlowListChange.update {
                    getCoinsForReturn(
                        stateFlowInsertedAmount.value,
                        product.price,
                        _stateFlowCoinsStorage.value
                    )
                }
                _stateFlowChangeCalculated.value = true
            }
            dataRepo.updateCoins(_stateFlowCoinsStorage.value)
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
}
