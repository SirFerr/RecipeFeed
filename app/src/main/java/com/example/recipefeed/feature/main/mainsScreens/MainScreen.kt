package com.example.recipefeed.view.mainMenu.mainsScreens

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.CircularLoad
import com.example.recipefeed.feature.composable.ErrorNetworkCard
import com.example.recipefeed.feature.composable.MainScreenButtons
import com.example.recipefeed.feature.composable.cards.MainScreenCard
import com.example.recipefeed.feature.composable.cards.SwipeCard

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    onRecipeClick: () -> Unit,
) {
    var isSwiping by remember { mutableStateOf(false) }
    val offsetY by animateDpAsState(
        targetValue = if (isSwiping) (-300).dp else 0.dp,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 300),
        finishedListener = { isSwiping = false }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.weight(10f), contentAlignment = Alignment.Center) {
            when (val state = viewModel.mainState.value) {
                is MainState.Success -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        viewModel.mainState.value.let { success ->
                            val currentRecipe = success as MainState.Success
                            SwipeCard(
                                onSwipeRight = {
                                    isSwiping = true
                                    viewModel.onSwipeRight()
                                },
                                onSwipeLeft = {
                                    isSwiping = true
                                    viewModel.onSwipeLeft()
                                },
                                modifier = Modifier.offset(y = offsetY)
                            ) {
                                MainScreenCard(
                                    recipe = currentRecipe.currentRecipe,
                                    onRecipeClick = onRecipeClick,
                                    modifier = Modifier.fillMaxSize(),

                                )
                            }
                        }
                    }
                }

                is MainState.Error -> ErrorNetworkCard(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxWidth(0.5f)
                        .padding(dimensionResource(id = R.dimen.main_padding)),
                    onRetry = { viewModel.preloadRecipes() }
                )

                is MainState.Loading -> CircularLoad()

                is MainState.AllFavourited -> Text(
                    text = "You added all to favorite",
                    modifier = Modifier.clickable { viewModel.onSwipeLeft() }
                        .wrapContentSize()
                        .padding(dimensionResource(id = R.dimen.main_padding)),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground

                )


                else -> {}
            }
        }

        if (viewModel.mainState.value is MainState.Success) {
            MainScreenButtons(
                onSwipeLeft = {
                    isSwiping = true
                    viewModel.onSwipeLeft()
                },
                onSwipeRight = {
                    isSwiping = true
                    viewModel.onSwipeRight()
                }
            )
        }
    }
}