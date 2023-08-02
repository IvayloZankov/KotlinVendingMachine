package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.DataRepo
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    private val dataRepo: DataRepo
): ViewModel() {

    fun initReset(resource: Int) {
        viewModelScope.request {
            if (resource == R.string.maintenance_products_reset) {
                dataRepo.resetProducts()
            } else if (resource == R.string.maintenance_coins_reset) {
                dataRepo.resetCoins()
            }
        }
    }
}
