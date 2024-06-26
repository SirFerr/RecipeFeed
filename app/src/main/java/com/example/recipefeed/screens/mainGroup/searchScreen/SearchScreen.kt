@file:OptIn(ExperimentalLayoutApi::class)

package com.example.recipefeed.view.mainMenu.searchScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.screens.mainGroup.TagItem
import com.example.recipefeed.view.mainMenu.CircularLoad
import com.example.recipefeed.view.mainMenu.ErrorNetworkCard
import com.example.recipefeed.view.mainMenu.ListItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchRecipesViewModel = hiltViewModel(),
    name: String,
) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    val padding by animateDpAsState(
        targetValue = if (!isSearching) dimensionResource(id = R.dimen.main_padding) else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    if (name.isNotEmpty()){
        viewModel.searchText.value = name
        viewModel.search()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SearchBar(
            query = searchText,
            onQueryChange = {
                viewModel.searchText.value = it
            },
            onSearch = {
                viewModel.search()
            },
            active = isSearching,
            onActiveChange = { viewModel.isSearching.value = !viewModel.isSearching.value },
            placeholder = { Text(text = stringResource(id = R.string.search_title)) },

            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
                .fillMaxWidth()
                .padding(padding),
            leadingIcon = ({
                IconButton(
                    onClick = { viewModel.search() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            }),
            trailingIcon = {
                if (isSearching)
                    IconButton(
                        onClick = {
                            if (searchText.isNotBlank())
                                viewModel.searchText.value = ""
                            else
                                viewModel.isSearching.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
            }) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.sub_padding)),
                horizontalAlignment = Alignment.Start
            ) {
                items(searchHistory.filter { it.lowercase().startsWith(searchText) }) {
                    TextButton(modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.searchText.value = it }
                    ) {
                        Text(text = it, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
        val recipes by viewModel.recipes.collectAsState()

        if (recipes.isEmpty()) {
            TagsGrid(viewModel)
        } else {
            SearchList(navController, viewModel)
        }
    }
}

@Composable
private fun TagsGrid(viewModel: SearchRecipesViewModel) {
    val tags by viewModel.tags.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            tags.forEach {
                TagItem(string = it, onClick = {
                    viewModel.searchText.value = it
                    viewModel.search()
                })
            }
        }
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_padding)))

    }


}

@Composable
private fun SearchList(
    navController: NavHostController,
    viewModel: SearchRecipesViewModel
) {
    val isSuccessful by viewModel.isSuccessful.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isFound by viewModel.isFound.collectAsState()
    val recipes by viewModel.recipes.collectAsState()
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
    ) {
        if (!isLoading) {
            if (isSuccessful) {
                if (!isFound) {
                    item {
                        Text(
                            text = stringResource(id = R.string.nothing_found),
                            modifier = Modifier
                                .padding(
                                    dimensionResource(id = R.dimen.main_padding)
                                )
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )

                    }
                } else
                    items(recipes) {

                        ListItem(it, navController)
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
