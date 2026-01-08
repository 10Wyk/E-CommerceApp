package com.e_commerce.shared.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsOneTimeEvent(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
            collect { action ->
                action?.let {
                    collector.invoke(action)
                }
            }
        }
    }
}