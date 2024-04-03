package com.example.foodder.presentation.main_screen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity

private const val FLIP_CARD_ANIMATION_TIME = 400
@Composable
fun buildRotationAnimation(targetValue: Float) =
    animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = FLIP_CARD_ANIMATION_TIME,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    ).value

@Composable
fun buildAlphaAnimation(targetValue: Float) =
    animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = FLIP_CARD_ANIMATION_TIME - 150,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    ).value

@Composable
fun buildOffsetAnimation(targetValue: Offset) =
    animateOffsetAsState(
        targetValue = targetValue,
        label = ""
    ).value

@Composable
fun buildShakeAnimation(isShaking: Boolean) =
    rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f,
        targetValue = if (isShaking) 10f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value

@Composable
fun Modifier.shake(translationX: Float): Modifier {
    return this.then(
        Modifier.graphicsLayer(
            translationX = (LocalDensity.current).run{ translationX }
        )
    )
}