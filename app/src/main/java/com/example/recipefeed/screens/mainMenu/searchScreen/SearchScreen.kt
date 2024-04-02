package com.example.recipefeed.screens.mainMenu.searchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.screens.mainMenu.listItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchScreen(
    navController: NavHostController,
    viewModel: SearchRecipesViewModel,
) {
    val recipes by viewModel.recipes.collectAsState()
    val textSearch by viewModel.textSearch.collectAsState()
    val isSuccessful by viewModel.isSuccessful.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isVisible by remember {
        derivedStateOf {
            textSearch.isNotBlank()
        }
    }
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
                    singleLine = true,
                    onValueChange = { viewModel.textSearch.value = it },
                    label = {
                        Text(
                            stringResource(id = R.string.search_title),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    trailingIcon = {
                        if (isVisible) {
                            Row {
                                IconButton(
                                    onClick = { viewModel.textSearch.value = "" }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear"
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.getAllRecipes() }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Done,
                                        contentDescription = "search"
                                    )
                                }
                            }

                        }
                    }
                )
            }
            if (!isLoading) {
                if (isSuccessful) {
                    items(recipes, key = { it.id }) {
                        listItem(it, navController)
                    }
                } else {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(id = R.dimen.main_padding)),
                                verticalArrangement = Arrangement.spacedBy(
                                    dimensionResource(id = R.dimen.main_padding)
                                )
                            ) {
                                Text(text = "Network error")
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = { viewModel.getAllRecipes() },
                                        modifier = Modifier.wrapContentSize()
                                    ) {
                                        Text(text = "Refresh")
                                    }
                                }
                            }
                        }
                    }
                }
            } else  {
                item {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(id = R.dimen.main_padding)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier)
                    }
                }
            }
            item { }
        }
    }
}