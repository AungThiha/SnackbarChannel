package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarDuration
import org.jetbrains.compose.resources.StringResource

data class SnackbarModel(
    val message: StringResource,
    val actionLabel: StringResource?,
    val withDismissAction: Boolean,
    val duration: SnackbarDuration,
    val onActionPerform: () -> Unit,
    val onDismiss: () -> Unit,
)
