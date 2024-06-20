package com.example.recipefeed.view.mainMenu.mainsScreens

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.view.mainMenu.CircularLoad
import com.example.recipefeed.view.mainMenu.ErrorNetworkCard
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.weight(10f), contentAlignment = Alignment.Center) {
            when (val state = viewModel.mainState.collectAsState().value) {
                is MainState.Success ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        val nextRecipe by viewModel.nextRecipe.collectAsState()

                        MainScreenCard(navController, nextRecipe)
                        SwipeCard(
                            onSwipeRight = {
                                viewModel.onSwipeRight()
                            },
                            onSwipeLeft = {
                                viewModel.onSwipeLeft()
                            }
                        ) {
                            MainScreenCard(navController, state.recipe)
                        }
                    }


                is MainState.Error ->
                    ErrorNetworkCard(
                        Modifier
                            .wrapContentSize()
                            .fillMaxWidth(0.5f)
                            .padding(dimensionResource(id = R.dimen.main_padding))
                    ) {
                        viewModel.getRandomRecipe()
                    }

                is MainState.Loading ->
                    CircularLoad()

                else -> {}
            }
        }
        if (viewModel.mainState.collectAsState().value is MainState.Success)
            MainScreenButtons(viewModel)
    }

}

@Composable
private fun MainScreenButtons(viewModel: MainScreenViewModel) {
    Row(
        Modifier
            .wrapContentSize()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.wrapContentSize(),
            onClick = {
                viewModel.getRandomRecipe()
                viewModel.getNextRandomRecipe()
            }) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null)
        }
        IconButton(modifier = Modifier.wrapContentSize(),
            onClick = {
//                viewModel.addToFavourites()
                viewModel.getRandomRecipe()
                viewModel.getNextRandomRecipe()
            }) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
        }
    }
}

@Composable
fun SwipeCard(
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    swipeThreshold: Float = 400f,
    sensitivityFactor: Float = 3f,
    content: @Composable () -> Unit
) {
    var offset by remember { mutableStateOf(0f) }
    var dismissRight by remember { mutableStateOf(false) }
    var dismissLeft by remember { mutableStateOf(false) }
    val density = LocalDensity.current.density

    LaunchedEffect(dismissRight) {
        if (dismissRight) {
            onSwipeRight.invoke()
            dismissRight = false
        }
    }

    LaunchedEffect(dismissLeft) {
        if (dismissLeft) {
            onSwipeLeft.invoke()
            dismissLeft = false
        }
    }

    Box(modifier = Modifier
        .offset { IntOffset(offset.roundToInt(), 0) }
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    when {
                        offset > swipeThreshold -> {
                            dismissRight = true
                        }

                        offset < -swipeThreshold -> {
                            dismissLeft = true
                        }
                    }
                    offset = 0f
                },
                onDragCancel = {
                    offset = 0f
                }
            ) { change, dragAmount ->
                offset += (dragAmount / density) * sensitivityFactor
                if (change.positionChange() != Offset.Zero) change.consume()
            }
        }
        .graphicsLayer(
            alpha = 10f - animateFloatAsState(if (dismissRight || dismissLeft) 1f else 0f).value,
            rotationZ = animateFloatAsState(offset / 50).value
        )) {
        content()
    }
}