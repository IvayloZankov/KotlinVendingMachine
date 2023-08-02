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

    private val _noConnection = MutableStateFlow(false)
    val noConnection: StateFlow<Boolean> = _noConnection

    fun initReset(resource: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (resource == R.string.maintenance_products_reset) {
                    dataRepo.resetProducts()
                } else if (resource == R.string.maintenance_coins_reset) {
                    dataRepo.resetCoins()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                _noConnection.value = true
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
