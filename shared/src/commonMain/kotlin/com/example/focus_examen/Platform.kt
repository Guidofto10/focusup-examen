package com.example.focus_examen

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform