package com.example.focusexamen.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val role: String? = null
)

@Serializable
data class User(
    val username: String,
    val password: String,
    val role: String
)
