package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import com.fosents.kotlinvendingmachine.domain.model.Product
import com.fosents.kotlinvendingmachine.domain.usecase.CheckOutOfOrderUseCase
import com.fosents.kotlinvendingmachine.domain.usecase.GetAvailableProductsUseCase
import com.fosents.kotlinvendingmachine.domain.usecase.ManageVendingIdUseCase
import com.fosents.kotlinvendingmachine.domain.usecase.SyncRemoteDataUseCase
import com.fosents.kotlinvendingmachine.sound.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    getAvailableProductsUseCase: GetAvailableProductsUseCase,
    private val checkOutOfOrderUseCase: CheckOutOfOrderUseCase,
    private val syncRemoteDataUseCase: SyncRemoteDataUseCase,
    private val manageVendingIdUseCase: ManageVendingIdUseCase,
    private val soundManager: SoundManager
): ViewModel() {

    val stateFlowProducts: StateFlow<List<Product>> = getAvailableProductsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

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
            val outOfOrder = checkOutOfOrderUseCase()
            if (outOfOrder) {
                soundManager.play(SoundManager.SoundType.ERROR)
            }
            _outOfOrder.update {
                outOfOrder
            }
        }
    }

    fun playClickSound() {
        soundManager.play(SoundManager.SoundType.CLICK)
    }
}
