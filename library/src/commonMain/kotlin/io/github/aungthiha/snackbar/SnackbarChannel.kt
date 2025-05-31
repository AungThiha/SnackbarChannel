package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource

/**
 * @param capacity - either a positive channel capacity or one of the constants defined in Channel. Factory.
 * */
class SnackbarChannel(
    capacity: Int = Channel.UNLIMITED
) : SnackbarChannelOwner {

    private val snackbarMessages: Channel<SnackbarModel> = Channel(capacity)
    override val snackbarFlow = snackbarMessages.receiveAsFlow()

    override fun showSnackBar(
        message: StringResource,
        actionLabel: StringResource?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit,
    ): ChannelResult<Unit> = snackbarMessages.trySend(
        SnackbarModel(
            message = SnackbarString.Resource(message),
            actionLabel = actionLabel?.let(SnackbarString::Resource),
            withDismissAction = withDismissAction,
            duration = duration,
            onActionPerform = onActionPerform,
            onDismiss = onDismiss,
        )
    )

    override fun showSnackBar(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit
    ): ChannelResult<Unit> = snackbarMessages.trySend(
        SnackbarModel(
            message = SnackbarString.Literal(message),
            actionLabel = actionLabel?.let(SnackbarString::Literal),
            withDismissAction = withDismissAction,
            duration = duration,
            onActionPerform = onActionPerform,
            onDismiss = onDismiss,
        )
    )

    override fun showSnackBar(
        message: SnackbarString,
        actionLabel: SnackbarString?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit
    ): ChannelResult<Unit> = snackbarMessages.trySend(
        SnackbarModel(
            message = message,
            actionLabel = actionLabel,
            withDismissAction = withDismissAction,
            duration = duration,
            onActionPerform = onActionPerform,
            onDismiss = onDismiss,
        )
    )
}
