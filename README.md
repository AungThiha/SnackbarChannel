# SnackbarChannel

A lightweight, lifecycle-safe snackbar event dispatcher for Compose Multiplatform that addresses common pitfalls of using SharedFlow and StateFlow.

Got it! Here's the revised version with your preferred phrasing:

> ⚠️ **Using Jetpack Compose for Android only?**    
> This library relies on `StringResource` and `getString` from `org.jetbrains.compose.resources`, which are **not supported in pure Android projects**. Please refer to the Android-specific version instead: [AndroidSnackbarChannel](https://github.com/AungThiha/AndroidSnackbarChannel).

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
- Fully unit-testable

---

## Installation

```kotlin
commonMain.dependencies {
    implementation("io.github.aungthiha:snackbar-channel:1.0.5")
}
```

---

## Setup

### 1. Add `SnackbarChannel` to your `ViewModel`
By default, SnackbarChannel uses `Channel.UNLIMITED` and `BufferOverflow.SUSPEND` to ensure no snackbar events are dropped.
```kotlin
import io.github.aungthiha.snackbar.SnackbarChannel
import io.github.aungthiha.snackbar.SnackbarChannelOwner

class MyViewModel(
    private val snackbarChannel: SnackbarChannel = SnackbarChannel() // Default: Channel.UNLIMITED
) : ViewModel(), SnackbarChannelOwner by snackbarChannel {

    fun showSimpleSnackbar() {
        showSnackBar(message = Res.string.hello_world)
    }
}
```
While the defaults are recommended for most use cases, both the channel capacity and `onBufferOverflow` strategy are configurable:
```kotlin
SnackbarChannel(
    capacity = Channel.RENDEZVOUS, // Or Channel.BUFFERED, etc.
    onBufferOverflow = BufferOverflow.DROP_OLDEST, // Or BufferOverflow.DROP_LATEST
) 
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

## Unit Testing
You can test snackbar emissions using `runTest` and collecting from `snackbarFlow`.

```kotlin
class MyViewModelTest {

    private val viewModel = MyViewModel()

    @Test
    fun snackbar_is_emitted() = runTest {
        viewModel.showSimpleSnackbar()

        val snackbarModel = viewModel.snackbarFlow.first()

        assertEquals(
            SnackbarString(Res.string.hello_world),
            snackbarModel.message
        )
    }
}
```
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
