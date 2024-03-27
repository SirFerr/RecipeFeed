package com.example.recipefeed.ui.view.screens.mainMenu.mainsScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.ui.viewModel.RecipeViewModel


@Composable
fun mainScreen(
    navController: NavHostController,
    recipeViewModel: RecipeViewModel = hiltViewModel(navController.getBackStackEntry("mainScreen"))
) {
    val recipe by recipeViewModel.randomRecipe.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.main_padding)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(Modifier.weight(10f), contentAlignment = Alignment.Center) {
            mainScreenCard(navController, recipe)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(modifier = Modifier, onClick = {
                recipeViewModel.getRandomRecipe()
            }) {
                Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = null)
            }
            Button(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
            }
        }
    }
}
