package com.example.foodder.presentation.favourite_food_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodder.common.TopAppBarView
import com.example.foodder.presentation.favourite_food_screen.components.FoodCard
import com.example.foodder.presentation.favourite_food_screen.components.SwipeToDismissBg
import com.example.foodder.presentation.ui.theme.BackgroundColor
import com.example.foodder.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FavouriteFoodScreen(
    viewModel: FavouriteFoodViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.value.isLoading,
        onRefresh = { viewModel.getAllMeals()}
    )
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBarView(
                onBack = { navController.navigateUp() },
                title = "Favourite food",
                containerColor = BackgroundColor
            )
        }
    ) {


        Box(modifier = Modifier
            .pullRefresh(pullRefreshState)
        ){

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(it),
            contentPadding = PaddingValues(10.dp)
        ){
            items(state.value.meals, key = { it.id }){ meal ->
                var show by remember { mutableStateOf(true) }
                val currentItem by rememberUpdatedState(meal)
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
                                    .padding(bottom = 10.dp),
                                onClick = {
                                navController.navigate(
                                    Screen.FoodDetailScreen.route +
                                            "/${meal.id}"
                                )
                            },
                                foodName = meal.strMeal,
                                photo = meal.strMealThumb,
                                onDelete = {show=false}
                            )
                        }
                    )
                }
                LaunchedEffect(show) {
                    if (!show) {
                        scope.launch {
                            val result = snackBarHostState.showSnackbar(
                                message = "Food deleted",
                                duration = SnackbarDuration.Short,
                                withDismissAction = true,
                                actionLabel = "Undo"
                            )

                            if(result == SnackbarResult.ActionPerformed){
                                viewModel.undoDeleteMeal()
                            }
                        }
                        viewModel.deleteMeal(currentItem)
                    }
                }
            }
        }
            PullRefreshIndicator(
                refreshing = state.value.isLoading,
                state = pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}