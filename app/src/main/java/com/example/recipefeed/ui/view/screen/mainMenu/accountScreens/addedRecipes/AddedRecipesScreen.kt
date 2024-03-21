package com.example.recipefeed.ui.view.screen.mainMenu.accountScreens.addedRecipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipe
import com.example.recipefeed.ui.viewModel.RecipeViewModel

@Composable
fun addedRecipesScreen(
    navController: NavHostController, recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal =  dimensionResource(id = R.dimen.mainPadding))
    ) {


        LazyColumn {
            item { Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding))) }
            items(10) {
                accountListItem(navController = navController, recipe = Recipe(id = it))
                if (it != 9)
                    Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            }
        }
    }
}