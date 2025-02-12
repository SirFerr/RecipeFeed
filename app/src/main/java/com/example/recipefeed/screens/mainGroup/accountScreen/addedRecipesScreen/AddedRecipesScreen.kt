@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.addedRecipesScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.view.mainMenu.ErrorNetworkCard
import com.example.recipefeed.view.mainMenu.ListItem
import com.example.recipefeed.view.mainMenu.UpdateBox


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddedRecipesScreen(
    navController: NavHostController,
    viewModel: AddedRecipesViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        TopAppBar(title = { },
            navigationIcon = {
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {

            })
    }) {
        UpdateBox(isLoading = viewModel.isLoading.value, exec = { viewModel.getAddedRecipes() }) {
            LazyColumn(
                state = rememberLazyListState(),

                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
            ) {
                item { }

                if (viewModel.isSuccessful.value) {
                    items(viewModel.recipes.value) {
                        ListItem(it, navController, Icons.Filled.Edit)
                    }
                } else {
                    item {
                        ErrorNetworkCard {
                            viewModel.getAddedRecipes()
                        }
                    }
                }
                item { }
            }
        }
    }
}




