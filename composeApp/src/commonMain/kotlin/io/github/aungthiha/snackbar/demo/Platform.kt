package io.github.aungthiha.snackbar.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform