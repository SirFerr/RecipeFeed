@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.screens.mainMenu.favoriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.screens.mainMenu.CardItem
import com.example.recipefeed.screens.mainMenu.listItem


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun favoriteScreen(
    navController: NavHostController,
    viewModel: FavoriteRecipesViewModel
) {

    val recipes by viewModel.recipes.collectAsState()
    val isSuccessful by viewModel.isSuccessful.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val refreshState =
        rememberPullRefreshState(refreshing = isLoading, onRefresh = { viewModel.getAllRecipes() })

    Box(
        modifier = Modifier.pullRefresh(state = refreshState),
        contentAlignment = Alignment.TopCenter
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            item { }
            if (isSuccessful) {
                items(recipes, key = { it.id }) {
                    listItem(it, navController)
                }
            } else {
                item {
                    CardItem {
                        viewModel.getAllRecipes()
                    }
                }
            }
            item { }
        }
        PullRefreshIndicator(refreshing = isLoading, state = refreshState)
    }
}




