package com.fosents.kotlinvendingmachine.data.remote.utils

class OneTimeEvent<out T>(
    private val content: T
    ) {

    var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? = if (hasBeenHandled) { null } else {
            hasBeenHandled = true
            content
        }

}