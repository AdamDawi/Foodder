package com.example.foodder.presentation.food_detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.foodder.common.TopAppBarView
import com.example.foodder.presentation.ui.theme.OrangePumpkin
import kotlin.math.max
import kotlin.math.min

private val MinTitleOffset = 12.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = 310.dp
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp

@Composable
fun FoodDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    viewModel.getMealById(id)
    val state = viewModel.state.value
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val scroll = rememberScrollState(0)
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scroll.value / collapseRange).coerceIn(0f, 1f)
    }
    Scaffold(
        topBar = { TopAppBarView(onBack = {navController.navigateUp()},
            title = "",
            containerColor = OrangePumpkin,
            themeColor = Color.White)
        },
        containerColor = Color.White
    ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(start = 10.dp, end = 10.dp),
        ) {
                    CollapsingImageLayout(
                        collapseFractionProvider = collapseFractionProvider
                    ) {
                        AsyncImage(model = state.meal.strMealThumb,
                            contentDescription = "Meal",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(ExpandedImageSize),
                            contentScale = ContentScale.Crop
                        )
                        Text(text = state.meal.strMeal,
                            color = Color.Black,
                            modifier = Modifier
                                .widthIn(max = screenWidth / 2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
//                    if(collapseFractionProvider()==1f){
//                        Divider(
//                            Modifier
//                            .requiredWidth(1000.dp)
//                        )
//                    }
                    Column(modifier = Modifier
                        .verticalScroll(scroll)
                    ) {
                        Text(
                            text = state.meal.strInstructions,
                            color = Color.Black,
                            fontSize = 22.sp
                        )
                    }
            }
        }
    }

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { (image, title), constraints ->
        val collapseFraction = collapseFractionProvider()

        //measurements
        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = image.measure(Constraints.fixed(imageWidth, imageWidth))

        val titlePlaceable = title.measure(constraints)

        //placement
        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        val titleY = lerp(ExpandedImageSize.roundToPx(), 20, collapseFraction)
        layout(
            width = constraints.maxWidth,
            height = max(imageY + imageWidth, titleY+titlePlaceable.height) +20 //+20 is padding
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
            titlePlaceable.place(0, titleY)
        }
    }
}

//@Composable
//private fun CollapsingImageLayout(
//    collapseFractionProvider: () -> Float,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    Layout(
//        modifier = modifier,
//        content = content
//    ) { image, constraints ->
//        val collapseFraction = collapseFractionProvider()
//
//        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
//        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
//        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
//        val imagePlaceable = image[0].measure(Constraints.fixed(imageWidth, imageWidth))
//
//        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
//        val imageX = lerp(
//            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
//            constraints.maxWidth - imageWidth, // right aligned when collapsed
//            collapseFraction
//        )
//        layout(
//            width = constraints.maxWidth,
//            height = imageY + imageWidth
//        ) {
//            imagePlaceable.placeRelative(imageX, imageY)
//        }
//    }
//}
@Preview
@Composable
fun FoodDetailScreenPreview() {
    FoodDetailScreen(id = 200, navController = rememberNavController())
}