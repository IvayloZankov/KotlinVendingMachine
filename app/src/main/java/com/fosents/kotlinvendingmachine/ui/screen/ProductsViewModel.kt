package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.usecase.CheckOutOfOrderUseCase
import com.fosents.kotlinvendingmachine.domain.usecase.GetAvailableProductsUseCase
import com.fosents.kotlinvendingmachine.domain.usecase.ManageVendingIdUseCase
import com.fosents.kotlinvendingmachine.domain.usecase.SyncRemoteDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getAvailableProductsUseCase: GetAvailableProductsUseCase,
    private val checkOutOfOrderUseCase: CheckOutOfOrderUseCase,
    private val syncRemoteDataUseCase: SyncRemoteDataUseCase,
    private val manageVendingIdUseCase: ManageVendingIdUseCase
): ViewModel() {

    private val _stateFlowProducts = MutableStateFlow<List<Product>>(emptyList())
    val stateFlowProducts = _stateFlowProducts.asStateFlow()

    private val _outOfOrder = MutableStateFlow(false)
    val outOfOrder = _outOfOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            manageVendingIdUseCase()
        }
        fetchRemoteData()
    }

    fun fetchRemoteData() {
        viewModelScope.request {
            syncRemoteDataUseCase()
            _isLoading.value = false
        }
    }

    fun fetchCoins() {
        viewModelScope.launch {
            _outOfOrder.update {
                checkOutOfOrderUseCase()
            }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _stateFlowProducts.update {
                getAvailableProductsUseCase()
            }
        }
    }
}
