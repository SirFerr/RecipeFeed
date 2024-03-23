@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.ui.view.screens.mainMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import com.example.recipefeed.ui.view.screens.mainMenu.list.listItem
import com.example.recipefeed.ui.viewModel.RecipeViewModel


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun favoriteScreen(
    navController: NavHostController? = null,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.mainPadding))
    ) {


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.mainPadding)),

            ) {
            item { }

            items(100, key = { it }) {
                listItem(navController = navController, recipe = Recipe(id = it))
            }
            item { }

        }

    }
}