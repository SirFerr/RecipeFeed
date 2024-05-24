@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.favoriteScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.view.mainMenu.ErrorNetworkCard
import com.example.recipefeed.view.mainMenu.ListItem
import com.example.recipefeed.view.mainMenu.UpdateBox


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteScreen(
    navController: NavHostController,
    viewModel: FavoriteRecipesViewModel = hiltViewModel()
) {

    Log.d("1", hiltViewModel<FavoriteRecipesViewModel>().toString())

    val recipes by viewModel.recipes.collectAsState()
    val isSuccessful by viewModel.isSuccessful.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    UpdateBox(isLoading = isLoading, exec = { viewModel.getFavouritesRecipes() }) {
        LazyColumn(
            state = rememberLazyListState(),

            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            item { }
            if (isSuccessful) {
                items(recipes, key = { it.id }) {
                    ListItem(it, navController)
                }
            } else {
                item {
                    ErrorNetworkCard {
                        viewModel.getFavouritesRecipes()
                    }
                }
            }
            item { }
        }
    }


}




