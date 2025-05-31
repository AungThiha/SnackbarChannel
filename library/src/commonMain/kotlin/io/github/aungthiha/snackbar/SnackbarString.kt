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

/**
 * Creates a [SnackbarString.Literal] from a raw [String].
 *
 * Allows developers to instantiate either a [Literal] or [Resource] using the same `SnackbarString(...)` function name,
 * improving discoverability and reducing cognitive overhead.
 */
fun SnackbarString(value: String) = SnackbarString.Literal(value)

/**
 * Creates a [SnackbarString.Resource] from a [StringResource].
 *
 * Allows developers to instantiate either a [Literal] or [Resource] using the same `SnackbarString(...)` function name,
 * improving discoverability and reducing cognitive overhead.
 */
fun SnackbarString(value: StringResource) = SnackbarString.Resource(value)
