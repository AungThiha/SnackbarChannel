# SnackbarChannel

A lightweight, lifecycle-safe snackbar event dispatcher for Compose Multiplatform.

## Why use SnackbarChannel?

`SnackbarChannel` addresses the common pitfalls of using `StateFlow`, `SharedFlow`, or even `StateFlow<List<Event>>`.

- `StateFlow` re-emits on config changes, leading to duplicate snackbars.
- `SharedFlow(replay = 0)` may drop events when no collector is active.
- `SharedFlow(replay = 1)` can treat each lifecycle change as a new subscription, re-emitting events.
- List-based workarounds (e.g., `StateFlow<List<String>>`) require manual state updates to remove consumed items, adding extra complexity and visual noise to the `ViewModel`.

`SnackbarChannel` avoids these issues:

- Emits events reliably even across lifecycle changes.
- No risk of duplicates or dropped events.
- No manual list mutation or bookkeeping required.

It’s a focused solution that keeps your snackbar logic clean, lifecycle-aware, and easy to use.

---

## Features

- One-liner API for triggering snackbars from your `ViewModel`
- No more missed or duplicated snackbars
- Lifecycle-aware: events are only collected when the UI is active
- Works seamlessly with `SnackbarHostState.showSnackbar(...)`
- No brittle base classes - favors composition over inheritance using Kotlin delegation
- Compose Multiplatform support

---

## Installation

```kotlin
commonMain.dependencies {
    implementation("io.github.aungthiha:snackbar-channel:1.0.2")
}
```

---

## Setup

### 1. Add `SnackbarChannel` to your `ViewModel`

```kotlin
import io.github.aungthiha.snackbar.SnackbarChannel
import io.github.aungthiha.snackbar.SnackbarChannelOwner

class MyViewModel(
    private val snackbarChannel: SnackbarChannel = SnackbarChannel()
) : ViewModel(), SnackbarChannelOwner by snackbarChannel {

    fun showSimpleSnackbar() {
        showSnackBar(message = Res.string.hello_world)
    }
}
```

### 2. Observe snackbars in your Composable
```kotlin
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import io.github.aungthiha.snackbar.collectWithLifecycle
import io.github.aungthiha.snackbar.showSnackbar

@Composable
fun MyScreen(viewModel: MyViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.snackbarFlow.collectWithLifecycle {
        snackbarHostState.showSnackbar(it)
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Button(onClick = { viewModel.showSimpleSnackbar() }) {
            Text("Show Snackbar")
        }
    }
}
```

---

## API Overview
Use showSnackBar(...) from your ViewModel. You can pass string resources, string literals, or even mix both using SnackbarString.
```kotlin
// All parameters
showSnackBar(
    message = Res.string.hello_world, // can be either StringResource, String or SnackbarString
    actionLabel = SnackbarString("ok"), // can be either StringResource, String or SnackbarString
    withDismissAction = true,
    duration = SnackbarDuration.Indefinite,
    onActionPerform = { /* handle action */ },
    onDismiss = { /* handle dismiss */ }
)

// Using a string resource
showSnackBar(
    message = Res.string.hello_world,
    actionLabel = Res.string.ok
)

// Using a raw string (e.g., from backend or dynamic input)
showSnackBar(
    message = "Something went wrong!",
    actionLabel = "Retry"
)

// Mixing types with SnackbarString
showSnackBar(
    message = SnackbarString("မင်္ဂလာပါ"),
    actionLabel = SnackbarString(Res.string.ok)
)

showSnackBar(
    message = SnackbarString(Res.string.hello_world),
    actionLabel = SnackbarString("ok")
)
```
All parameters are optional except the message.   
For more example usages, see [AppViewModel.kt](./composeApp/src/commonMain/kotlin/io/github/aungthiha/snackbar/demo/AppViewModel.kt)

---

## Compose Multiplatform Ready     
Tested with:
- Android
- iOS   

(Other targets are available but not tested yet)

---

## Contributing      
PRs and feedback welcome!

---

## License     
MIT
