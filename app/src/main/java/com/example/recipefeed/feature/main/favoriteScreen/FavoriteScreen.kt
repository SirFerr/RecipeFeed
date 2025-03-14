@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.favoriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.ErrorNetworkCard
import com.example.recipefeed.feature.composable.cards.ListItemCard
import com.example.recipefeed.feature.composable.UpdateBox


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteRecipesViewModel = hiltViewModel(),
    onRecipeClick: (Int)->Unit
) {
    UpdateBox(isLoading = viewModel.isLoading.value, exec = { viewModel.getFavouritesRecipes() }) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            item { }
            if (viewModel.isSuccessful.value) {
                items(viewModel.recipes.value) {
                    ListItemCard(it, onRecipeClick = {onRecipeClick(it.id)}, onEditClick = { })
                }
            } else {
                item {
                    ErrorNetworkCard {
                        viewModel.getFavouritesRecipes()
                    }
                }
            }
            item {  Spacer(modifier = Modifier.size(200.dp)) }
        }
    }


}