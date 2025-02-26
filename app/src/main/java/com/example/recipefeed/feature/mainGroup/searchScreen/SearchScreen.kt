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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.CircularLoad
import com.example.recipefeed.feature.composable.ErrorNetworkCard
import com.example.recipefeed.feature.composable.TagsGrid
import com.example.recipefeed.feature.composable.cards.ListItemCard
import com.example.recipefeed.feature.composable.cards.TagItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchRecipesViewModel = hiltViewModel(),
    name: String,
    onListItemClick: (Int) -> Unit
) {
    val padding by animateDpAsState(
        targetValue = if (!viewModel.isSearching.value) dimensionResource(id = R.dimen.main_padding) else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

    if (name.isNotEmpty()) {
        viewModel.setSearchText(name)
        viewModel.search()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SearchBar(
            query = viewModel.searchText.value,
            onQueryChange = {
                viewModel.setSearchText(it)
            },
            onSearch = {
                viewModel.search()
            },
            active = viewModel.isSearching.value,
            onActiveChange = { viewModel.setIsSearching() },
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
                if (viewModel.isSearching.value)
                    IconButton(
                        onClick = {
                            if (viewModel.searchText.value.isNotBlank())
                                viewModel.setSearchText("")
                            else
                                viewModel.setIsSearching(false)
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
                items(viewModel.searchHistory.value.filter {
                    it.lowercase().startsWith(viewModel.searchText.value)
                }) {
                    TextButton(modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.setSearchText(it) }
                    ) {
                        Text(text = it, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }

        if (viewModel.recipes.value.isEmpty()) {
            TagsGrid(viewModel.tags.value, onClick = { string ->
                viewModel.setSearchText(string)
                viewModel.search()
            })
        } else {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
            ) {
                if (!viewModel.isLoading.value) {
                    if (viewModel.isSuccessful.value) {
                        if (!viewModel.isFound.value) {
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
                            items(viewModel.recipes.value) {

                                ListItemCard(
                                    it,
                                    onRecipeClick = { onListItemClick(it.id) },
                                    onEditClick = {})
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
    }
}



