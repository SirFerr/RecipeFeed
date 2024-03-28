package com.example.recipefeed.ui.view.screens.mainMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.ui.view.screens.mainMenu.list.listItem
import com.example.recipefeed.ui.viewModel.RecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchScreen(
    navController: NavHostController,
    recipeViewModel: RecipeViewModel = hiltViewModel(

    ),
) {
    val recipes by recipeViewModel.recipes.collectAsState()
    val textSearch by recipeViewModel.textSearch.collectAsState()
    Column(
        Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
            .fillMaxSize()

    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        ) {
            item { }
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = textSearch,
                    onValueChange = { recipeViewModel.textSearch.value = it },
                    label = {
                        Text(
                            stringResource(id = R.string.search_title),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                )
            }
            items(recipes, key = { it.idRandom }) {
                listItem(it, navController)
            }
            item { }
        }
    }
}