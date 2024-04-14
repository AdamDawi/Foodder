package com.example.foodder.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodder.common.Resource
import com.example.foodder.domain.model.Meal
import com.example.foodder.domain.use_case.AddMealUseCase
import com.example.foodder.domain.use_case.GetRandomFoodUseCase
import com.example.foodder.presentation.ui.theme.GreenBlue
import com.example.foodder.presentation.ui.theme.RedPink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val OFFSET_LIMIT = 120
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRandomFoodUseCase: GetRandomFoodUseCase,
    private val addMealUseCase: AddMealUseCase
): ViewModel() {
    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private val _rightSwipeCounter = mutableIntStateOf(0)
    val rightSwipeCounter = _rightSwipeCounter

    private val _leftSwipeCounter = mutableIntStateOf(0)
    val leftSwipeCounter = _leftSwipeCounter

    private var getRandomFoodJob: Job? = null

    init {
        getRandomFood()
    }

    private fun getRandomFood(){
        getRandomFoodJob?.cancel()
        getRandomFoodJob = getRandomFoodUseCase().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = MainState(meal = result.data?.get(0) ?: Meal())
                }
                is Resource.Error -> {
                    _state.value = MainState(errorMessage = result.message ?: "An unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = MainState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MainEvent){
        when(event){
            is MainEvent.OnCardDrag ->{
                onCardDrag(event.dragAmount)
            }
            is MainEvent.OnCardClicked ->{
                changeIsCardFlippedState()
            }
            is MainEvent.OnCardDragEnd ->{
                checkSwipeBounds()
            }
        }
    }

    private fun onCardDrag(dragAmount: Offset) {
        //change offset of card
        _state.value = _state.value.copy(
            cardOffset =
            Offset(
                _state.value.cardOffset.x+dragAmount.x,
                _state.value.cardOffset.y+dragAmount.y
            )
        )
        //close to right
        if(state.value.cardOffset.x>OFFSET_LIMIT){
            setCardBorderColorState(GreenBlue)
            _state.value = _state.value.copy(isSwipeToRightShaking = true)
        }
        //close to left
        else if(state.value.cardOffset.x<-OFFSET_LIMIT){
            setCardBorderColorState(RedPink)
            _state.value = _state.value.copy(isSwipeToLeftShaking = true)
        }
        //neutral
        else {
            setCardBorderColorState(Color.Transparent)
            _state.value = _state.value.copy(isSwipeToRightShaking = false, isSwipeToLeftShaking = false)
        }
    }
    private fun checkSwipeBounds() {
        //Swipe right
        if(state.value.cardOffset.x>OFFSET_LIMIT && !state.value.isLoading){
            viewModelScope.launch {
                addMealUseCase(state.value.meal)
            }
            getRandomFood()
            resetImageOffset()
            setCardBorderColorState(Color.Transparent)
            _rightSwipeCounter.intValue++
        }
        //Swipe left
        else if(state.value.cardOffset.x<-OFFSET_LIMIT && !state.value.isLoading){
            getRandomFood()
            resetImageOffset()
            setCardBorderColorState(Color.Transparent)
            _leftSwipeCounter.intValue++
        }
        else resetImageOffset()
    }

    private fun changeIsCardFlippedState(){
        _state.value = _state.value.copy(isCardFlipped = !_state.value.isCardFlipped)
    }

    private fun setCardBorderColorState(color: Color){
        _state.value = _state.value.copy(cardBorderColor = color)
    }

    private fun resetImageOffset(){
        _state.value = _state.value.copy(cardOffset = Offset(0f, 0f))
    }


}