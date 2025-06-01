package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource

/**
 * @param capacity - either a positive channel capacity or one of the constants defined in Channel. Factory.
 * @param onBufferOverflow configures an action on buffer overflow
 * @throws IllegalArgumentException when [capacity] < -2
 * */
class SnackbarChannel(
    capacity: Int = Channel.UNLIMITED,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
) : SnackbarChannelOwner {

    private val snackbarMessages: Channel<SnackbarModel> = Channel(capacity, onBufferOverflow)
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
        message: StringResource,
        actionLabel: String,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit
    ): ChannelResult<Unit> = snackbarMessages.trySend(
        SnackbarModel(
            message = SnackbarString.Resource(message),
            actionLabel = SnackbarString.Literal(actionLabel),
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
        message: String,
        actionLabel: StringResource,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit
    ): ChannelResult<Unit> = snackbarMessages.trySend(
        SnackbarModel(
            message = SnackbarString.Literal(message),
            actionLabel = SnackbarString.Resource(actionLabel),
            withDismissAction = withDismissAction,
            duration = duration,
            onActionPerform = onActionPerform,
            onDismiss = onDismiss,
        )
    )

    @Deprecated(
        "Use showSnackBar with specific parameter types (String/StringResource) instead of SnackbarString for simpler usage",
        level = DeprecationLevel.WARNING
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
