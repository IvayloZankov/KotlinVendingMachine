package com.fosents.kotlinvendingmachine.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.DataRepo
import com.fosents.kotlinvendingmachine.data.remote.utils.request
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    application: Application,
    private val dataRepo: DataRepo
): AndroidViewModel(application) {

    fun initReset(s: String) {
        viewModelScope.request {
            if (s == getApplication<Application>().getString(R.string.maintenance_products_reset)) {
                dataRepo.resetProducts()
            } else if (s == getApplication<Application>().getString(R.string.maintenance_coins_reset)) {
                dataRepo.resetCoins()
            }
        }
    }
}
