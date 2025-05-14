package com.example.recipefeed.feature.main.accountScreen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.feature.UiIngredient

@Composable
fun ImagePickerCard(
    image: Any?,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    onImageCleared: () -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding) * 2)
    ) {
        Text(text = "Фото", style = MaterialTheme.typography.titleLarge)
        Card(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
        ) {
            if (image != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(onClick = onImageCleared) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
            } else {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.pick_image),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainInformationSection(
    recipeName: String,
    description: String,
    ingredients: List<UiIngredient>,
    externalIngredients: List<String>,
    steps: String,
    onRecipeNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onIngredientAdd: () -> Unit,
    onIngredientsChange: (Int, UiIngredient) -> Unit,
    onIngredientDelete: (Int) -> Unit,
    onStepsChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding) * 2)
    ) {
        Text(text = "Основная информация", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = recipeName,
            onValueChange = onRecipeNameChange,
            label = { Text(text = stringResource(id = R.string.title_recipe)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(text = stringResource(id = R.string.description_recipe)) }
        )

        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleMedium
        )

        ingredients.forEachIndexed { index, ingredient ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.main_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        var expandedIngredient by remember { mutableStateOf(false) }
                        var searchQuery by remember { mutableStateOf(ingredient.name) }

                        Text(
                            text = "Ingredient",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        ExposedDropdownMenuBox(
                            expanded = expandedIngredient,
                            onExpandedChange = { expandedIngredient = !expandedIngredient },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { query ->
                                    searchQuery = query
                                    onSearchQueryChange(query)
                                    expandedIngredient = true
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedIngredient) }
                            )

                            ExposedDropdownMenu(
                                expanded = expandedIngredient,
                                onDismissRequest = { expandedIngredient = false }
                            ) {
                                externalIngredients.forEach { name ->
                                    DropdownMenuItem(
                                        text = { Text(name) },
                                        onClick = {
                                            searchQuery = name
                                            onIngredientsChange(index, ingredient.copy(name = name))
                                            expandedIngredient = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Количество",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = ingredient.amount?.toString() ?: "",
                            onValueChange = { amount ->
                                val newAmount = amount.toDoubleOrNull()
                                onIngredientsChange(index, ingredient.copy(amount = newAmount))
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        var expandedUnit by remember { mutableStateOf(false) }

                        Text(
                            text = "Unit",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        ExposedDropdownMenuBox(
                            expanded = expandedUnit,
                            onExpandedChange = { expandedUnit = !expandedUnit },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = ingredient.unit,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedUnit) }
                            )

                            ExposedDropdownMenu(
                                expanded = expandedUnit,
                                onDismissRequest = { expandedUnit = false }
                            ) {
                                ingredient.possibleUnits.forEach { unit ->
                                    DropdownMenuItem(
                                        text = { Text(unit) },
                                        onClick = {
                                            onIngredientsChange(index, ingredient.copy(unit = unit))
                                            expandedUnit = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    IconButton(onClick = { onIngredientDelete(index) }) {
                        Icon(Icons.Default.Clear, contentDescription = "Delete ingredient")
                    }
                }
            }
        }

        Text(
            text = "Добавить ингредиент",
            modifier = Modifier.clickable { onIngredientAdd() },
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            value = steps,
            onValueChange = onStepsChange,
            label = { Text(text = stringResource(id = R.string.steps)) }
        )
    }
}

@Composable
fun DeleteRecipeDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Отменить")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Подтвердить")
            }
        },
        text = {
            Text(text = "Вы уверены, что хотите удалить рецепт?")
        }
    )
}