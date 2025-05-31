package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.StringResource

interface SnackbarChannelOwner {
    val snackbarFlow: Flow<SnackbarModel>

    /**
     * @param message text to be shown in the Snackbar
     * @param actionLabel optional action label to show as button in the Snackbar
     * @param withDismissAction a boolean to show a dismiss action in the Snackbar. This is
     *   recommended to be set to true for better accessibility when a Snackbar is set with a
     *   [SnackbarDuration.Indefinite]
     * @param duration duration to control how long snackbar will be shown in [SnackbarHost], either
     *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite].
     * @param onActionPerform called when action is clicked
     * @param onDismiss called if snackbar is dismissed via timeout or by the user
     */
    fun showSnackBar(
        message: StringResource,
        actionLabel: StringResource? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
        onActionPerform: () -> Unit = {},
        onDismiss: () -> Unit = {},
    ): ChannelResult<Unit>

    /**
     * @param message text to be shown in the Snackbar
     * @param actionLabel optional action label to show as button in the Snackbar
     * @param withDismissAction a boolean to show a dismiss action in the Snackbar. This is
     *   recommended to be set to true for better accessibility when a Snackbar is set with a
     *   [SnackbarDuration.Indefinite]
     * @param duration duration to control how long snackbar will be shown in [SnackbarHost], either
     *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite].
     * @param onActionPerform called when action is clicked
     * @param onDismiss called if snackbar is dismissed via timeout or by the user
     */
    fun showSnackBar(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
        onActionPerform: () -> Unit = {},
        onDismiss: () -> Unit = {},
    ): ChannelResult<Unit>

    /**
     * @param message text to be shown in the Snackbar
     * @param actionLabel optional action label to show as button in the Snackbar
     * @param withDismissAction a boolean to show a dismiss action in the Snackbar. This is
     *   recommended to be set to true for better accessibility when a Snackbar is set with a
     *   [SnackbarDuration.Indefinite]
     * @param duration duration to control how long snackbar will be shown in [SnackbarHost], either
     *   [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite].
     * @param onActionPerform called when action is clicked
     * @param onDismiss called if snackbar is dismissed via timeout or by the user
     */
    fun showSnackBar(
        message: SnackbarString,
        actionLabel: SnackbarString? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
        onActionPerform: () -> Unit = {},
        onDismiss: () -> Unit = {},
    ): ChannelResult<Unit>
}
