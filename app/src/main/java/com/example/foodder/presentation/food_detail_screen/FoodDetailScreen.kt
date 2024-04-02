package com.example.foodder.presentation.food_detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.foodder.common.TestTags
import com.example.foodder.common.TopAppBarView
import com.example.foodder.presentation.food_detail_screen.components.InformationRow
import com.example.foodder.presentation.food_detail_screen.components.IngredientRow
import com.example.foodder.presentation.ui.theme.LocalSpacing
import com.example.foodder.presentation.ui.theme.OrangePumpkin
import kotlin.math.max
import kotlin.math.min
//Element sizes
private val MinTitleOffset = 12.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = 310.dp
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val ScreenPadding = 10.dp
//Font sizes
private val SectionTitleSize = 22.sp
private val MainTitleSize = 24.sp

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
    val uriHandler = LocalUriHandler.current
    Scaffold(
        topBar = {
            TopAppBarView(
                onBack = { navController.navigateUp() },
                title = "Details",
                containerColor = OrangePumpkin,
                themeColor = Color.White
            )
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(start = ScreenPadding, end = ScreenPadding),
        ) {
            CollapsingImageAndTitleLayout(
                collapseFractionProvider = collapseFractionProvider
            ) {
                AsyncImage(
                    model = state.meal.strMealThumb,
                    contentDescription = state.meal.strMeal,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(ExpandedImageSize),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = state.meal.strMeal,
                    color = Color.Black,
                    modifier = Modifier
                        .widthIn(max = screenWidth / 2)
                        .testTag(TestTags.DETAIL_SCREEN_FOOD),
                    fontWeight = FontWeight.Bold,
                    fontSize = MainTitleSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Divider(
                Modifier
                    .fillMaxWidth(),
                color = Color.Gray
            )
            Column(
                modifier = Modifier
                    .verticalScroll(scroll)
            ) {
                Text(
                    text = "Information",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalSpacing.current.small),
                    fontSize = SectionTitleSize,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                InformationRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp),
                    strArea = state.meal.strArea,
                    strCategory = state.meal.strCategory
                )
                Text(
                    text = "Ingredients",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalSpacing.current.small),
                    fontSize = SectionTitleSize,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                for (i in 0 until state.meal.strIngredients.size) {
                    IngredientRow(
                        uriHandler = uriHandler,
                        ingredient = state.meal.strIngredients[i],
                        measurement = state.meal.strMeasurements[i]
                    )
                    Divider(color = Color.Gray)
                }
                Text(
                    text = "Instruction",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalSpacing.current.small),
                    fontSize = SectionTitleSize,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                Text(
                    text = state.meal.strInstructions,
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
private fun CollapsingImageAndTitleLayout(
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
        val titleY =
            lerp(ExpandedImageSize.roundToPx() + 30, MinTitleOffset.roundToPx(), collapseFraction)
        layout(
            width = constraints.maxWidth,
            height = max(imageY + imageWidth, titleY + titlePlaceable.height) + 20 //+20 is padding
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
            titlePlaceable.place(0, titleY)
        }
    }
}