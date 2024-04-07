package com.example.foodder.presentation.food_detail_screen

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import com.example.foodder.common.TopAppBarView
import com.example.foodder.presentation.food_detail_screen.components.InformationColumn
import com.example.foodder.presentation.food_detail_screen.components.IngredientRowSearch
import com.example.foodder.presentation.ui.theme.LocalSpacing
import com.example.foodder.presentation.ui.theme.OrangePumpkin
import kotlin.math.max
import kotlin.math.min


//Element sizes
//Image:
private val MaxImageXOffset = 30.dp
private val MinImageYOffset = 0.dp
private val MaxImageYOffset = 60.dp
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 200.dp
private const val ImageRotationDegree = 20
//Others:
private val ScreenPadding = 8.dp
private val TextPadding = ScreenPadding+8.dp
private val BgOverlapSize = 70.dp
//Information title:
private val MaxInfoTitleYOffset = 20.dp
private val MinInfoTitleYOffset = MinImageYOffset
private val MaxInfoTitleXOffset = ScreenPadding+10.dp
private val InfoTitleSize = 150.dp

//Font sizes
private val SectionTitleSize = 22.sp

@Composable
fun FoodDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: FoodDetailViewModel = hiltViewModel()
) {
    viewModel.getMealById(id)
    val state = viewModel.state.value
    val scroll = rememberScrollState(0)
    val collapseRange = with(LocalDensity.current) { (ExpandedImageSize - MinImageYOffset).toPx() }
    val collapseFractionProvider = {
        (scroll.value / collapseRange).coerceIn(0f, 1f)
    }

    Scaffold(
        topBar = {
            TopAppBarView(
                onBack = { navController.navigateUp() },
                title = state.meal.strMeal,
                containerColor = Color.Transparent,
                themeColor = Color.Black
            )
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            CollapsingImageAndTitleLayout(
                collapseFractionProvider = collapseFractionProvider
            ) {
                AsyncImage(
                    model = state.meal.strMealThumb,
                    contentDescription = state.meal.strMeal,
                    modifier = Modifier
                        .size(ExpandedImageSize)
                        .circleShadow(
                            color = Color.Gray,
                            blurRadius = 8.dp,
                        )
                        .clip(CircleShape)
                        .rotate(collapseFractionProvider() * ImageRotationDegree - ImageRotationDegree),
                    contentScale = ContentScale.Crop
                )
                InformationColumn(
                    modifier = Modifier
                        .height(InfoTitleSize),
                    strArea = state.meal.strArea,
                    strCategory = state.meal.strCategory
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                    .background(OrangePumpkin)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scroll)
                            .padding(start = TextPadding, end = TextPadding, bottom = TextPadding +8.dp + CollapsedImageSize) //because Y of Column was moved by CollapsedImageSize
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = "Ingredients:",
                            modifier = Modifier
                                .padding(LocalSpacing.current.small),
                            fontSize = SectionTitleSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                        for (i in 0 until min(
                            state.meal.strIngredients.size,
                            state.meal.strMeasurements.size)
                        ) {
                            key(state.meal.id) {
                                IngredientRowSearch(
                                    ingredient = state.meal.strIngredients[i],
                                    measurement = state.meal.strMeasurements[i],
                                )
                            }
                            Divider(color = Color.Black)
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
                            fontSize = 18.sp
                        )
                    }
                }
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
    ) { (image, infoTitle, details), constraints ->
        val collapseFraction = collapseFractionProvider()

        //measurements
        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = image.measure(Constraints.fixed(imageWidth, imageWidth))

        val infoTitlePlaceable = infoTitle.measure(constraints)
        val detailsPlaceable = details.measure(constraints)

        //setting (X, Y)
        val infoTitleX = lerp(MaxInfoTitleXOffset, ScreenPadding, collapseFraction).roundToPx()
        val infoTitleY = lerp(MaxInfoTitleYOffset, MinInfoTitleYOffset, collapseFraction).roundToPx()

        val imageY = lerp(MaxImageYOffset, MinImageYOffset, collapseFraction).roundToPx()
        val startImageX =
            if(constraints.maxWidth - imageWidth + MaxImageXOffset.roundToPx()>infoTitleX+infoTitlePlaceable.width) // right aligned when collapsed but must be right to info title
                constraints.maxWidth - imageWidth + MaxImageXOffset.roundToPx()
            else infoTitleX+infoTitlePlaceable.width
        val imageX = lerp(
            startImageX,
            infoTitlePlaceable.width-imageWidth/2+(constraints.maxWidth-infoTitlePlaceable.width)/2,  // centered beetween info title and end of the screen when expanded
            collapseFraction
        )

        val detailsY = lerp(imageY+imageMaxSize/2 + BgOverlapSize.roundToPx(), imageY+imageMinSize+50, collapseFraction)

        //placement
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            detailsPlaceable.placeRelative(0, detailsY)
            imagePlaceable.placeRelative(imageX, imageY)
            infoTitlePlaceable.placeRelative(infoTitleX, infoTitleY)
        }
    }
}

fun Modifier.circleShadow(
    color: Color = Color.Black,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
            }
            frameworkPaint.color = color.toArgb()

            val radius = size.width / 2.0f

            canvas.drawCircle(
                center = Offset(size.width / 2.0f, size.height / 2.0f),
                radius = radius,
                paint = paint
            )
        }
    }
)