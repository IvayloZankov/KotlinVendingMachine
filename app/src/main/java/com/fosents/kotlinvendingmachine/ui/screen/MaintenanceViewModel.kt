package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import com.fosents.kotlinvendingmachine.domain.repository.VendingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    private val vendingRepository: VendingRepository
): ViewModel() {

    fun initReset(resource: Int) {
        viewModelScope.request {
            if (resource == R.string.maintenance_products_reset) {
                vendingRepository.resetProducts()
            } else if (resource == R.string.maintenance_coins_reset) {
                vendingRepository.resetCoins()
            }
        }
    }
}
