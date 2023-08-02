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

    private val _coinsStorage = MutableStateFlow<List<Coin>>(emptyList())
    val coinsStorage = _coinsStorage.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _insertedAmount = MutableStateFlow("0.00")
    val insertedAmount = _insertedAmount.asStateFlow()

    private val coinsUser = mutableListOf<Coin>()
    val coinsForReturn = mutableListOf<Coin>()

    private val _priceMet = MutableStateFlow(false)
    val priceMet = _priceMet.asStateFlow()

    private val _changeCalculated = MutableStateFlow(false)
    val changeCalculated = _changeCalculated.asStateFlow()

    private val _orderCancelled = MutableStateFlow(false)
    val orderCancelled = _orderCancelled.asStateFlow()

    init {
        viewModelScope.request {
            val productId = savedStateHandle.get<Int>(ARG_PRODUCT_ID)
            _selectedProduct.value = productId?.let {
                dataRepo.getSelectedProduct(it)
            }
            _coinsStorage.value = dataRepo.getCoins()
        }
    }

    fun addUserCoin(it: Coin) {
        _insertedAmount.value =
            BigDecimal(_insertedAmount.value).add(BigDecimal.valueOf(it.price)).toString()
        val coinUser = it.copy(quantity = 1)
        insertCoin(coinUser, coinsUser)
        _priceMet.value = insertedAmount.value.toDouble() >= selectedProduct.value!!.price
    }

    fun addUserCoins() {
        viewModelScope.request {
            selectedProduct.value?.let {
                val product = it.copy(quantity = it.quantity.minus(1))
                dataRepo.updateProduct(product)
            }
            insertUserCoins(coinsUser, _coinsStorage.value)
            coinsUser.clear()
            selectedProduct.value?.let {
                coinsForReturn.addAll(getCoinsForReturn(
                    insertedAmount.value,
                    it.price,
                    _coinsStorage.value
                ))
                _changeCalculated.value = true
            }
            dataRepo.updateCoins(_coinsStorage.value)
        }
    }

    fun cancelOrder() {
        coinsForReturn.addAll(coinsUser)
        coinsUser.clear()
        _orderCancelled.value = true
    }
}
