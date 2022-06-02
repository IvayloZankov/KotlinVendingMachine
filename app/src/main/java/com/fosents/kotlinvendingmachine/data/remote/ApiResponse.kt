package com.fosents.kotlinvendingmachine.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: List<T> = emptyList()
)
