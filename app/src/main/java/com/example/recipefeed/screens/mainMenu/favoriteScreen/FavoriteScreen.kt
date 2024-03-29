@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.screens.mainMenu.favoriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.screens.mainMenu.list.listItem


@Composable
fun favoriteScreen(
    navController: NavHostController,
    viewModel: FavoriteRecipesViewModel
) {

    val recipes by viewModel.favoriteRecipes.collectAsState()
    LaunchedEffect(navController) {
        viewModel.getAllRecipes()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            item { }
            items(recipes, key = { it.id }) {
                listItem(it, navController)
            }
            item { }
        }

    }
}

