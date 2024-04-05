package com.example.recipefeed.screens.mainMenu.addedRecipesScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.screens.ErrorNetworkCard
import com.example.recipefeed.screens.mainMenu.listItem
import com.example.recipefeed.screens.updateBox


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun addedRecipesScreen(
    navController: NavHostController, viewModel: AddedRecipesViewModel =  hiltViewModel(navController.currentBackStackEntry!!)
) {
    val recipes by viewModel.recipes.collectAsState()

    val isSuccessful by viewModel.isSuccessful.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    updateBox(isLoading = isLoading, exec = { viewModel.getAllRecipes() }) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            item { }

            if (isSuccessful) {
                items(recipes, key = { it.id }) {
                    listItem(it, navController, Icons.Filled.Edit)
                }
            } else {
                item {
                    ErrorNetworkCard {
                        viewModel.getAllRecipes()
                    }
                }
            }
            item { }
        }
    }
}




