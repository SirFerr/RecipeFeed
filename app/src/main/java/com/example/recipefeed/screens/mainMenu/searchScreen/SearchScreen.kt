package com.example.recipefeed.screens.mainMenu.searchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.screens.mainMenu.ErrorNetworkCard
import com.example.recipefeed.screens.mainMenu.CircularLoad
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
    val isFound by viewModel.isFound.collectAsState()
    val isVisible by remember {
        derivedStateOf {
            textSearch.isNotBlank()
        }
    }


    LazyColumn(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
            .fillMaxSize(),
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
                                onClick = { viewModel.getByName() }
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
                if (!isFound) {
                    item {
                            Text(
                                text = stringResource(id = R.string.nothing_found), modifier = Modifier
                                    .padding(
                                        dimensionResource(id = R.dimen.main_padding)
                                    )
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge
                            )

                    }
                } else
                    items(recipes, key = { it.id }) {
                        listItem(it, navController)
                    }
            } else {
                item {
                    ErrorNetworkCard {
                        viewModel.getByName()
                    }
                }
            }
        } else {
            item {
                CircularLoad()
            }
        }
        item { }
    }
}
