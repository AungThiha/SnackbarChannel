package io.github.aungthiha.snackbar

import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import kotlin.jvm.JvmInline

sealed interface SnackbarString {
    @JvmInline
    value class Literal(val value: String) : SnackbarString

    @JvmInline
    value class Resource(val value: StringResource) : SnackbarString
}

suspend fun SnackbarString.unpackString(): String = when (this) {
    is SnackbarString.Literal -> value
    is SnackbarString.Resource -> getString(value)
}

fun SnackbarString(value: String) = SnackbarString.Literal(value)

fun SnackbarString(value: StringResource) = SnackbarString.Resource(value)
