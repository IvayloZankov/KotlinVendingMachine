package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.data.DataRepo
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import com.fosents.kotlinvendingmachine.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val dataRepo: DataRepo
): ViewModel() {

    private val _vendingId = MutableStateFlow("")
    private val vendingId = _vendingId.asStateFlow()

    val getProducts = dataRepo.getProducts().map { list -> list.filter { it.quantity > 0 } }

    private val _coinsStorage = MutableStateFlow<List<Coin>>(emptyList())
//    val coinsStorage: StateFlow<List<Coin>> = _coinsStorage

    private val _outOfOrder = MutableStateFlow(false)
    val outOfOrder = _outOfOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _vendingId.value = dataRepo.readVendingId().stateIn(viewModelScope).value
            if (vendingId.value == "") {
                dataRepo.generateVendingId()
            }
        }
        fetchRemoteData()
    }

    fun fetchRemoteData() {
        _isLoading.value = true
        viewModelScope.request {
            dataRepo.fetchRemoteData()
            _isLoading.value = false
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
