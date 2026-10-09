package com.example.focusexamen.backend.routes

import com.example.focusexamen.backend.models.LoginRequest
import com.example.focusexamen.backend.models.LoginResponse
import com.example.focusexamen.backend.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

private val jsonParser = Json {
    ignoreUnknownKeys = true
    isLenient = true
    explicitNulls = true
}

private val usersList: List<User> by lazy {
    try {
        val stream = Thread.currentThread().contextClassLoader.getResourceAsStream("users.json")
        if (stream != null) {
            val jsonText = stream.bufferedReader().use { it.readText() }
            jsonParser.decodeFromString<List<User>>(jsonText)
        } else {
            fallbackUsers()
        }
    } catch (e: Exception) {
        println("⚠️ Error al cargar users.json (${e.message}), usando usuarios por defecto.")
        fallbackUsers()
    }
}

private fun fallbackUsers() = listOf(
    User("admin", "admin123", "admin"),
    User("estudiante", "estudiante123", "estudiante"),
    User("docente", "docente123", "docente")
)

fun Application.configureAuthRoutes() {
    routing {
        post("/login") {
            try {
                val request = call.receive<LoginRequest>()

                val foundUser = usersList.find {
                    it.username.equals(request.username, ignoreCase = true) &&
                            it.password == request.password
                }

                if (foundUser != null) {
                    call.respond(
                        HttpStatusCode.OK,
                        LoginResponse(
                            success = true,
                            message = "Inicio de sesión correcto.",
                            role = foundUser.role
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        LoginResponse(
                            success = false,
                            message = "Credenciales incorrectas.",
                            role = null
                        )
                    )
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    LoginResponse(
                        success = false,
                        message = "Formato de solicitud no válido: ${e.message}",
                        role = null
                    )
                )
            }
        }
    }
}
