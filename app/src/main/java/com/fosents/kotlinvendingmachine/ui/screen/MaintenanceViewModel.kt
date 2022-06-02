package com.fosents.kotlinvendingmachine.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
    application: Application,
    private val dataRepo: DataRepo
): AndroidViewModel(application) {

    private val _noConnection = MutableStateFlow(false)
    val noConnection: StateFlow<Boolean> = _noConnection

    fun initReset(s: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (s == getApplication<Application>().getString(R.string.maintenance_products_reset)) {
                    dataRepo.resetProducts()
                } else if (s == getApplication<Application>().getString(R.string.maintenance_coins_reset)) {
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
