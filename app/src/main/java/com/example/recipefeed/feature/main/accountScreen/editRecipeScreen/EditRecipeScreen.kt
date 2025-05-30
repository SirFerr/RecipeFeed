@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.editRecipeScreen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.cards.TagItem
import com.example.recipefeed.feature.main.accountScreen.DeleteRecipeDialog
import com.example.recipefeed.feature.main.accountScreen.ImagePickerCard
import com.example.recipefeed.feature.main.accountScreen.MainInformationSection
import com.example.recipefeed.utils.uriToFile

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditRecipeScreen(
    id: Int = 1,
    viewModel: EditRecipeScreenViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
) {
    val context = LocalContext.current
    viewModel.getById(id)
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.setSelectedImageFile(uriToFile(it, context)) }
    }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = onClickBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = { viewModel.changeIsDelete() }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            }
        )
    }) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
            ) {
                item {
                    Spacer(modifier = Modifier)
                    ImagePickerCard(
                        image = viewModel.selectedImageFile.value,
                        galleryLauncher = galleryLauncher,
                        onImageCleared = { viewModel.setSelectedImageFile(null) }
                    )
                    HorizontalDivider()
                }

                item {
                    MainInformationSection(
                        recipeName = viewModel.recipeName.value,
                        description = viewModel.description.value,
                        ingredients = viewModel.ingredients.value,
                        externalIngredients = viewModel.externalIngredients.value.map { it["name"] as String },
                        steps = viewModel.steps.value,
                        onRecipeNameChange = { viewModel.setRecipeName(it) },
                        onDescriptionChange = { viewModel.setDescription(it) },
                        onIngredientsChange = { index, ingredient -> viewModel.changeIngredient(index, ingredient) },
                        onIngredientDelete = { viewModel.deleteIngredient(it) },
                        onStepsChange = { viewModel.setSteps(it) },
                        onIngredientAdd = { viewModel.addIngredient() },
                        onSearchQueryChange = { viewModel.searchExternalIngredients(it) }
                    )
                }

                item {
                    HorizontalDivider()
                    Text(stringResource(R.string.tags), style = MaterialTheme.typography.titleMedium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = viewModel.tagText.value,
                                onValueChange = {
                                    viewModel.setTagText(it)
                                    viewModel.loadAvailableTags(it)
                                    expanded = true
                                },
                                label = { Text(stringResource(R.string.search_or_create_tag)) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                viewModel.availableTags.value.forEach { tag ->
                                    DropdownMenuItem(
                                        text = { Text(tag) },
                                        onClick = {
                                            if (!viewModel.tags.value.contains(tag)) {
                                                viewModel.addTag(tag)
                                            }
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                        IconButton(
                            onClick = {
                                if (viewModel.tagText.value.isNotBlank()) {
                                    viewModel.addTag(viewModel.tagText.value)
                                    viewModel.setTagText("") // Очищаем поле после добавления
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add tag"
                            )
                        }
                    }
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
                    ) {
                        viewModel.tags.value.forEach { tag ->
                            TagItem(string = tag) { viewModel.removeTag(tag) }
                        }
                    }
                }

                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.editRecipe() },
                        enabled = viewModel.recipeName.value.isNotBlank() &&
                                viewModel.steps.value.isNotBlank() &&
                                viewModel.ingredients.value.any { it.name.isNotBlank() && it.amount != null && it.amount > 0 && it.unit.isNotBlank() }
                    ) {
                        Text(text = stringResource(id = R.string.complete))
                    }
                    Spacer(modifier = Modifier)
                }
            }

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (viewModel.isDelete.value) {
                DeleteRecipeDialog(
                    onDismiss = { viewModel.changeIsDelete() },
                    onConfirm = {
                        viewModel.changeIsDelete()
                        viewModel.deleteRecipeById(id)
                        onClickBack()
                    }
                )
            }
        }
    }
}