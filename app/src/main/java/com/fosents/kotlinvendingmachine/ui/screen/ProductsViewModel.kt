package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.data.DataRepo
import com.fosents.kotlinvendingmachine.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val dataRepo: DataRepo
): ViewModel() {

    private val _vendingId = MutableStateFlow("")
    private val vendingId: StateFlow<String> = _vendingId

    val getProducts = dataRepo.getProducts()

    private val _coinsStorage = MutableStateFlow<List<Coin>>(emptyList())
//    val coinsStorage: StateFlow<List<Coin>> = _coinsStorage

    private val _outOfOrder = MutableStateFlow(false)
    val outOfOrder: StateFlow<Boolean> = _outOfOrder

    private val _noConnection = MutableStateFlow(false)
    val noConnection: StateFlow<Boolean> = _noConnection

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _vendingId.value = dataRepo.readVendingId().stateIn(viewModelScope).value
            if (vendingId.value == "") {
                dataRepo.generateVendingId()
            }
        }
        fetchRemoteData()
    }

    fun fetchRemoteData() {
        _isLoading.value = true
        _noConnection.value = false
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(2000)
                dataRepo.fetchRemoteData()
                _isLoading.value = false
            } catch (e: IOException) {
                e.printStackTrace()
                _noConnection.value = true
            }
        }
    }

    fun fetchCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            _coinsStorage.value = dataRepo.getCoins()
            if (_coinsStorage.value.isNotEmpty())
                _outOfOrder.value = _coinsStorage.value[0].quantity < 40
        }
    }
}
