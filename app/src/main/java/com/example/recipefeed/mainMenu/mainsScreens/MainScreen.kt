package com.example.recipefeed.view.mainMenu.mainsScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.view.mainMenu.CircularLoad
import com.example.recipefeed.view.mainMenu.ErrorNetworkCard


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
                    MainScreenCard(navController, state.recipe)

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
            }) {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null)
        }
        IconButton(modifier = Modifier.wrapContentSize(),
            onClick = {
                viewModel.addToFavourites()
                viewModel.getRandomRecipe()
            }) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
        }
    }
}
