package com.fosents.kotlinvendingmachine.data.remote.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ExceptionHandler {

    private val mutableStateFlowError = MutableStateFlow(OneTimeEvent(Throwable()).apply {
        hasBeenHandled = true
    })
    val stateFlowError = mutableStateFlowError.asStateFlow()

    val handler = CoroutineExceptionHandler { _, throwable -> run {
        mutableStateFlowError.value = OneTimeEvent(throwable)
    }
    }
}
