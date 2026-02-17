package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import com.fosents.kotlinvendingmachine.domain.usecase.ResetCoinsUseCase
import com.fosents.kotlinvendingmachine.domain.usecase.ResetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    private val resetProductsUseCase: ResetProductsUseCase,
    private val resetCoinsUseCase: ResetCoinsUseCase
): ViewModel() {

    fun initReset(resource: Int) {
        viewModelScope.request {
            if (resource == R.string.maintenance_products_reset) {
                resetProductsUseCase()
            } else if (resource == R.string.maintenance_coins_reset) {
                resetCoinsUseCase()
            }
        }
    }
}
