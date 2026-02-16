package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.domain.repository.SettingsRepository
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import com.fosents.kotlinvendingmachine.domain.model.Coin
import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val vendingRepository: VendingRepository,
    private val settingsRepository: SettingsRepository
): ViewModel() {

    private val _vendingId = MutableStateFlow("")
    private val vendingId = _vendingId.asStateFlow()

    private val _coinsStorage = MutableStateFlow<List<Coin>>(emptyList())
//    val coinsStorage: StateFlow<List<Coin>> = _coinsStorage

    private val _stateFlowProducts = MutableStateFlow<List<Product>>(emptyList())
    val stateFlowProducts = _stateFlowProducts.asStateFlow()

    private val _outOfOrder = MutableStateFlow(false)
    val outOfOrder = _outOfOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _vendingId.value = settingsRepository.readVendingId().stateIn(viewModelScope).value
            if (vendingId.value == "") {
                settingsRepository.generateVendingId()
            }
        }
        fetchRemoteData()
    }

    fun fetchRemoteData() {
        _isLoading.value = true
        viewModelScope.request {
            vendingRepository.fetchRemoteData()
            _isLoading.value = false
        }
    }

    fun fetchCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            _coinsStorage.value = vendingRepository.getCoins()
            if (_coinsStorage.value.isNotEmpty())
                _outOfOrder.value = _coinsStorage.value[0].quantity < 40
        }
    }

    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _stateFlowProducts.value = vendingRepository.getProducts().filter { it.quantity > 0 }
        }
    }
}
