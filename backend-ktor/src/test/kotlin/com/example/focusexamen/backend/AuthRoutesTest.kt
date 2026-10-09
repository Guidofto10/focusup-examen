package com.example.focusexamen.backend

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class AuthRoutesTest {

    @Test
    fun testValidLoginAdmin() = testApplication {
        application {
            module()
        }

        val response = client.post("/login") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("""{"username":"admin","password":"admin123"}""")
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("\"success\": true") || body.contains("\"success\":true"))
        assertTrue(body.contains("\"role\": \"admin\"") || body.contains("\"role\":\"admin\""))
    }

    @Test
    fun testValidLoginEstudiante() = testApplication {
        application {
            module()
        }

        val response = client.post("/login") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("""{"username":"estudiante","password":"estudiante123"}""")
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("\"success\": true") || body.contains("\"success\":true"))
        assertTrue(body.contains("\"role\": \"estudiante\"") || body.contains("\"role\":\"estudiante\""))
    }

    @Test
    fun testValidLoginDocente() = testApplication {
        application {
            module()
        }

        val response = client.post("/login") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("""{"username":"docente","password":"docente123"}""")
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("\"success\": true") || body.contains("\"success\":true"))
        assertTrue(body.contains("\"role\": \"docente\"") || body.contains("\"role\":\"docente\""))
    }

    @Test
    fun testInvalidLogin() = testApplication {
        application {
            module()
        }

        val response = client.post("/login") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody("""{"username":"admin","password":"wrongpassword"}""")
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
        val body = response.bodyAsText()
        assertTrue(body.contains("\"success\": false") || body.contains("\"success\":false"))
    }
}
