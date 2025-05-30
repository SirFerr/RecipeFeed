
package com.example.recipefeed.view.mainMenu.favoriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.ErrorNetworkCard
import com.example.recipefeed.feature.composable.UpdateBox
import com.example.recipefeed.feature.composable.cards.ListItemCard

@Composable
fun FavoriteScreen(
    viewModel: FavoriteRecipesViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getFavouritesRecipes()
    }
    UpdateBox(isLoading = viewModel.isLoading.value, exec = { viewModel.getFavouritesRecipes() }) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            item { }
            if (viewModel.isSuccessful.value && viewModel.recipes.value.isNotEmpty()) {
                items(viewModel.recipes.value) { recipe ->
                    ListItemCard(
                        recipe = recipe,
                        onRecipeClick = { onRecipeClick(recipe.id) },
                        onEditClick = { },
                        onFavoriteClick = { viewModel.toggleFavorite(recipe.id) },
                        isFavorite = viewModel.favoriteStatus.value[recipe.id] ?: false,
                    )
                }
            } else if (!viewModel.isSuccessful.value) {
                item {
                    ErrorNetworkCard {
                        viewModel.getFavouritesRecipes()
                    }
                }
            }
            item { Spacer(modifier = Modifier.size(200.dp)) }
        }
    }
}