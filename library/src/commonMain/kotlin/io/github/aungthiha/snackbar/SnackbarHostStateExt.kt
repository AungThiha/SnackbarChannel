package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

suspend fun SnackbarHostState.showSnackbar(snackbarModel: SnackbarModel) {
    val result = showSnackbar(
        snackbarModel.message.unpackString(),
        snackbarModel.actionLabel?.unpackString(),
        snackbarModel.withDismissAction,
        snackbarModel.duration,
    )
    when (result) {
        SnackbarResult.Dismissed -> snackbarModel.onDismiss()
        SnackbarResult.ActionPerformed -> snackbarModel.onActionPerform()
    }
}
