package com.example.recipefeed.feature.main.recipesOnApprove

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.ErrorNetworkCard
import com.example.recipefeed.feature.composable.UpdateBox
import com.example.recipefeed.feature.composable.cards.ListItemCard

@Composable
fun RecipesOnApprove(
    viewModel: RecipesOnApproveViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onRecipeClick: (Int) -> Unit
) {
    UpdateBox(isLoading = viewModel.isLoading.value, exec = { viewModel.getRecipesOnApprove() }) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            item { }

            if (viewModel.isSuccessful.value) {
                if (viewModel.recipes.value.isEmpty()) {
                    item {
                        // Можно добавить сообщение "Нет рецептов на одобрении"
                    }
                } else {
                    items(viewModel.recipes.value) { recipe ->
                        ListItemCard(
                            recipe = recipe,
                            icon = null,
                            onRecipeClick = { onRecipeClick(recipe.id) },
                            onEditClick = {} // Если редактирование не нужно, оставляем пустым
                        )
                    }
                }
            } else {
                item {
                    ErrorNetworkCard {
                        viewModel.getRecipesOnApprove()
                    }
                }
            }

            item { }
        }
    }
}