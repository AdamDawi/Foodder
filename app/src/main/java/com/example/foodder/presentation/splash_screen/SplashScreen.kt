package com.example.foodder.presentation.splash_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.foodder.R
import com.example.foodder.presentation.util.Screen

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    val loading by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splashscreen))
    val logoAnimationState = animateLottieCompositionAsState(
        composition = loading
    )

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            modifier = Modifier
                .padding(start = 60.dp, end = 60.dp)
                .fillMaxSize(),
            composition = loading
        )
    }

    if(logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
        navController.popBackStack()
        navController.navigate(Screen.MainScreen.route)
    }
}