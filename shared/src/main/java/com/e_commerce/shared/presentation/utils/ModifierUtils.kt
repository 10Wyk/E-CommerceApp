package com.e_commerce.shared.presentation.utils

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.onSwipeRight(onSwipeRight: () -> Unit): Modifier {
    var dx = 0F

    return pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragEnd = {
                if (dx < 0) {
                    dx = 0F
                    onSwipeRight()
                }
            },
            onHorizontalDrag = { _, dragAmount ->
                dx = dragAmount
            })
    }
}


fun Modifier.onSwipeLeft(onSwipeRight: () -> Unit): Modifier {
    var dx = 0F

    return pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragEnd = {
                if (dx > 0) {
                    dx = 0F
                    onSwipeRight()
                }
            },
            onHorizontalDrag = { _, dragAmount ->
                dx = dragAmount
            })
    }
}