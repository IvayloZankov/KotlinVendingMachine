package com.fosents.kotlinvendingmachine.data.remote.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Make backend requests. Coroutine scope using Dispatchers.IO and default handler if not provided.
 */
fun CoroutineScope.request(
    handler: CoroutineExceptionHandler = ExceptionHandler.handler,
    block: suspend CoroutineScope.() -> Unit
): Job = launch(Dispatchers.IO + handler, block = block)
