@file:OptIn(ExperimentalLayoutApi::class)

package com.example.recipefeed.view.mainMenu.searchScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchRecipesViewModel = hiltViewModel(),
    tagName: String = "", // Новый параметр
    onListItemClick: (Int) -> Unit
) {
    val padding by animateDpAsState(
        targetValue = if (!viewModel.isSearching.value) dimensionResource(id = R.dimen.main_padding) else 0.dp,
        animationSpec = tween(durationMillis = 300)
    )

   if (tagName.isNotEmpty()) {
        viewModel.addSelectedTag(tagName)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            query = viewModel.searchText.value,
            onQueryChange = { viewModel.setSearchText(it) },
            onSearch = { viewModel.search() },
            active = viewModel.isSearching.value,
            onActiveChange = { viewModel.setIsSearching() },
            placeholder = { Text(text = stringResource(id = R.string.search_title)) },
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
                .fillMaxWidth()
                .padding(padding),
            leadingIcon = {
                IconButton(onClick = { viewModel.search() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            },
            trailingIcon = {
                if (viewModel.isSearching.value) {
                    IconButton(
                        onClick = {
                            if (viewModel.searchText.value.isNotBlank()) {
                                viewModel.setSearchText("")
                            } else {
                                viewModel.setIsSearching(false)
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.sub_padding)),
                horizontalAlignment = Alignment.Start
            ) {
                items(viewModel.searchHistory.value.filter {
                    it.lowercase().startsWith(viewModel.searchText.value.lowercase())
                }) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.setSearchText(it) }
                    ) {
                        Text(text = it, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }

        // Отображение выбранных тегов с возможностью удаления
        if (viewModel.selectedTags.value.isNotEmpty()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.sub_padding)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
            ) {
                viewModel.selectedTags.value.forEach { tag ->
                    TagItem(string = viewModel.translatedTags.value[tag] ?: tag) {
                        viewModel.removeSelectedTag(tag)
                    }
                }
            }
        }

        if (viewModel.recipes.value.isEmpty() && viewModel.selectedTags.value.isEmpty()) {
            TagsGrid(
                list = viewModel.tags.value.map { viewModel.translatedTags.value[it.name] ?: it.name },
                onClick = { translatedTagName ->
                    val originalName = viewModel.translatedTags.value.entries
                        .find { it.value == translatedTagName }?.key ?: translatedTagName
                    viewModel.addSelectedTag(originalName)
                }
            )

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
                                        .padding(dimensionResource(id = R.dimen.main_padding))
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        } else {
                            items(viewModel.recipes.value) { recipe ->
                                ListItemCard(
                                    recipe = recipe,
                                    onRecipeClick = { onListItemClick(recipe.id) },
                                    onEditClick = {},
                                    onFavoriteClick = { viewModel.toggleFavorite(recipe.id) },
                                    isFavorite = viewModel.favoriteStatus.value[recipe.id] ?: false,
                                )
                            }
                        }
                    } else {
                        item {
                            ErrorNetworkCard {
                                if (viewModel.selectedTags.value.isNotEmpty()) {
                                    viewModel.getByTags()
                                } else {
                                    viewModel.getByName()
                                }
                            }
                        }
                    }
                } else {
                    item { CircularLoad() }
                }
                item { Spacer(modifier = Modifier.height(200.dp)) }
            }
        }
    }
}