package com.example.recipefeed.ui.view.screens.mainMenu.accountScreens.addedRecipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.ui.view.screens.mainMenu.list.listItem
import com.example.recipefeed.ui.viewModel.AddedRecipesViewModel

@Composable
fun addedRecipesScreen(
    navController: NavHostController, addedRecipesViewModel: AddedRecipesViewModel = hiltViewModel()
) {
    val recipes by addedRecipesViewModel.addedRecipes.collectAsState()
    LaunchedEffect(navController) {
        addedRecipesViewModel.getAllRecipes()
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),

            ) {
            item { }

            items(recipes, key = { it.id }) {
                listItem(
                    navController = navController,
                    recipe = it,
                    icon = Icons.Filled.Edit
                )
            }
            item { }

        }

    }
}