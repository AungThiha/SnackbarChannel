package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import org.jetbrains.compose.resources.getString

suspend fun SnackbarHostState.showSnackbar(snackbarModel: SnackbarModel) {
    val result = showSnackbar(
        getString(snackbarModel.message),
        snackbarModel.actionLabel?.let { getString(it) },
        snackbarModel.withDismissAction,
        snackbarModel.duration,
    )
    when (result) {
        SnackbarResult.Dismissed -> snackbarModel.onDismiss()
        SnackbarResult.ActionPerformed -> snackbarModel.onActionPerform()
    }
}
