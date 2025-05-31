package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarDuration

data class SnackbarModel(
    val message: SnackbarString,
    val actionLabel: SnackbarString?,
    val withDismissAction: Boolean,
    val duration: SnackbarDuration,
    val onActionPerform: () -> Unit,
    val onDismiss: () -> Unit,
)
