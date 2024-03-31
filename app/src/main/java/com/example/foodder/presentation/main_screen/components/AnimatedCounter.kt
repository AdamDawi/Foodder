package com.example.foodder.presentation.main_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
) {
    var oldCount by remember{
        mutableIntStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(
        modifier = modifier
    ) {
        val countString = count.toString()
        val oldCountString = oldCount.toString()
        for(i in countString.indices){
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if(oldChar==newChar){
                oldCountString[i]
            }else{
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it*2 } togetherWith slideOutVertically { -it*2 }
                    },
                label = ""
            ) {
                Text(
                    text = it.toString(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    softWrap = false,
                    maxLines = 1,
                    modifier = textModifier
                )
            }
        }
    }
}