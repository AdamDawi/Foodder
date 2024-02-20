package com.example.foodder.presentation.favourite_food_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodder.presentation.favourite_food_screen.components.FoodCard
import com.example.foodder.common.TopAppBarView
import com.example.foodder.presentation.favourite_food_screen.components.SwipeToDismissBg
import com.example.foodder.presentation.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteFoodScreen(
    viewModel: FavouriteFoodViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBarView(
                onBack = { navController.navigateUp() },
                title = "Favourite food"
            )
        }
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(it),
            contentPadding = PaddingValues(10.dp)
        ){
            items(state.meals.size, key = {i -> state.meals[i].id}){ id ->
                var show by remember { mutableStateOf(true) }
                val currentItem by rememberUpdatedState(state.meals[id])
                val dismissState = rememberDismissState(
                    confirmValueChange = {dismiss ->
                        if (dismiss == DismissValue.DismissedToStart){
                            show = false
                        }
                        true
                    },
                    positionalThreshold = { fl ->
                        return@rememberDismissState fl*0.4f
                    }
                )
                AnimatedVisibility(
                    show,exit = fadeOut(spring())
                ) {
                    SwipeToDismiss(state = dismissState,
                        background = {
                            SwipeToDismissBg(dismissState = dismissState)
                        },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissContent = {
                            FoodCard(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .clickable {
                                        navController.navigate(
                                            Screen.FoodDetailScreen.route +
                                                    "/${state.meals[id].id}"
                                        )
                                    },
                                foodName = state.meals[id].strMeal,
                                photo = state.meals[id].strMealThumb
                            )
                        }
                    )
                }
                LaunchedEffect(show) {
                    if (!show) {
                        scope.launch {
                            snackBarHostState.showSnackbar("Food deleted", duration = SnackbarDuration.Short)
                        }
                        delay(1000)
                        viewModel.deleteMeal(currentItem)
                    }
                }
            }
        }
    }
}