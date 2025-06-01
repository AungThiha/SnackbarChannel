package io.github.aungthiha.snackbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

@Composable
fun Flow<SnackbarModel>.observeWithLifecycle(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<SnackbarModel>,
) {
    LaunchedEffect(this) {
        flowWithLifecycle(lifecycle, minActiveState)
            .collect(collector)
    }
}

@Deprecated(
    message = "Renamed to observeWithLifecycle for better Compose conventions",
    replaceWith = ReplaceWith(
        "observeWithLifecycle(collector)"
    ),
    level = DeprecationLevel.WARNING
)
@Composable
fun Flow<SnackbarModel>.collectWithLifecycle(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<SnackbarModel>,
) {
    LaunchedEffect(this) {
        flowWithLifecycle(lifecycle, minActiveState)
            .collect(collector)
    }
}
