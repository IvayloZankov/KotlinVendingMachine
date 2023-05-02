package com.fosents.kotlinvendingmachine.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.data.DataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
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
            }
        }
    }

    fun resetNoConnection() {
        _noConnection.value = false
    }
}
