package io.github.aungthiha.snackbar.demo

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import demo.composeapp.generated.resources.Res
import demo.composeapp.generated.resources.hello_from_SnackbarChannel
import demo.composeapp.generated.resources.ok
import io.github.aungthiha.snackbar.SnackbarChannel
import io.github.aungthiha.snackbar.SnackbarChannelOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AppViewModel(
    private val snackbarChannel: SnackbarChannel = SnackbarChannel()
) : ViewModel(), SnackbarChannelOwner by snackbarChannel {

    private val mutableOnActionPerformCalled: MutableStateFlow<Int> = MutableStateFlow(0)
    val onActionPerformCalled: StateFlow<Int> = mutableOnActionPerformCalled

    private val mutableOnDismissedCalled: MutableStateFlow<Int> = MutableStateFlow(0)
    val onDismissedCalled: StateFlow<Int> = mutableOnDismissedCalled

    fun simpleSnackbar() {
        showSnackBar(
            message = Res.string.hello_from_SnackbarChannel
        )
    }

    fun snackbarWithAction() {
        showSnackBar(
            message = Res.string.hello_from_SnackbarChannel,
            actionLabel = Res.string.ok
        )
    }

    fun snackbarWithDismissAction() {
        showSnackBar(
            message = Res.string.hello_from_SnackbarChannel,
            withDismissAction = true
        )
    }

    fun snackbarWithOnActionPerformCallback() {
        showSnackBar(
            message = Res.string.hello_from_SnackbarChannel,
            actionLabel = Res.string.ok,
            onActionPerform = {
                mutableOnActionPerformCalled.update { it + 1 }
            }
        )
    }

    fun snackbarWithOnDismissCallback() {
        showSnackBar(
            message = Res.string.hello_from_SnackbarChannel,
            onDismiss = {
                mutableOnDismissedCalled.update { it + 1 }
            }
        )
    }

    fun indefiniteSnackbar() {
        showSnackBar(
            message = Res.string.hello_from_SnackbarChannel,
            withDismissAction = true,
            duration = SnackbarDuration.Indefinite
        )
    }
}
