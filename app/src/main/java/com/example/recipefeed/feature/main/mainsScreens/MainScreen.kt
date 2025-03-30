package com.example.recipefeed.view.mainMenu.mainsScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
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
    viewModel: MainScreenViewModel = hiltViewModel(),onRecipeClick: ()->Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.weight(10f), contentAlignment = Alignment.Center) {
            when (val state = viewModel.mainState.value) {
                is MainState.Success -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    MainScreenCard(viewModel.nextRecipe.value,
                        onRecipeClick = {})
                    SwipeCard(onSwipeRight = {
                        viewModel.onSwipeRight()
                    }, onSwipeLeft = {
                        viewModel.onSwipeLeft()
                    }) {
                        MainScreenCard(state.recipe, onRecipeClick)
                    }
                }

                is MainState.Error -> ErrorNetworkCard(
                    Modifier
                        .wrapContentSize()
                        .fillMaxWidth(0.5f)
                        .padding(dimensionResource(id = R.dimen.main_padding))
                ) {
                    viewModel.onSwipeLeft()
                }

                is MainState.Loading -> CircularLoad()

                else -> {}
            }
        }
        if (viewModel.mainState.value is MainState.Success) MainScreenButtons(
            onSwipeLeft = {
                viewModel.onSwipeLeft()
            },
            onSwipeRight = {
                viewModel.onSwipeRight()
            },
            )
    }

}




