package com.example.recipefeed.feature.main.accountScreen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.feature.UiIngredient
import com.example.recipefeed.feature.composable.cards.PickerPopup

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
        Text(text = "Photo", style = MaterialTheme.typography.titleLarge)
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


@Composable
fun MainInformationSection(
    recipeName: String,
    description: String,
    ingredients: List<UiIngredient>,
    ingredientsBase: List<String>, // Список доступных имен ингредиентов для выбора
    steps: String,
    onRecipeNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onIngredientsChange: (Int, UiIngredient) -> Unit,
    onIngredientDelete: (Int) -> Unit,
    onStepsChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding) * 2)
    ) {
        Text(text = "Main information", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = recipeName,
            onValueChange = onRecipeNameChange,
            label = {
                Text(
                    text = stringResource(id = R.string.title_recipe),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = onDescriptionChange,
            label = {
                Text(
                    text = stringResource(id = R.string.description_recipe),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )

        ingredients.forEachIndexed { index, ingredient ->
            var anchorCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
            var showPicker by remember { mutableStateOf(false) }
            val interactionSource = remember { MutableInteractionSource() }
            val isFocused by interactionSource.collectIsFocusedAsState()

            LaunchedEffect(isFocused) {
                if (isFocused) showPicker = true
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .onGloballyPositioned { coordinates ->
                            anchorCoordinates = coordinates
                        },
                    value = ingredient.name,
                    onValueChange = { name ->
                        onIngredientsChange(index, ingredient.copy(name = name))
                    },
                    interactionSource = interactionSource,
                    label = {
                        Text(
                            text = stringResource(id = R.string.ingredients),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                )

                Spacer(Modifier.size(12.dp))

                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = ingredient.amount?.toString() ?: "",
                    onValueChange = { amount ->
                        val newAmount = amount.toDoubleOrNull()
                        onIngredientsChange(index, ingredient.copy(amount = newAmount))
                    },
                    label = {
                        Text(
                            text = "Amount",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                )

                Spacer(Modifier.size(12.dp))

                IconButton(onClick = { onIngredientDelete(index) }) {
                    Icon(Icons.Default.Clear, contentDescription = "Delete ingredient")
                }

                if (showPicker && anchorCoordinates != null) {
                    PickerPopup(
                        anchorCoordinates = anchorCoordinates!!,
                        onDismiss = { showPicker = false },
                        current = ingredient.name,
                        onItemSelected = { name ->
                            onIngredientsChange(index, ingredient.copy(name = name))
                            showPicker = false
                        },
                        list = ingredientsBase
                    )
                }
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = steps,
            onValueChange = onStepsChange,
            label = {
                Text(
                    text = stringResource(id = R.string.steps),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        )
    }
}

// Заглушка для PickerPopup
@Composable
fun PickerPopup(
    anchorCoordinates: LayoutCoordinates,
    onDismiss: () -> Unit,
    current: String,
    onItemSelected: (String) -> Unit,
    list: List<String>
) {
    // Здесь должна быть реализация выпадающего списка
}

@Composable
fun DeleteRecipeDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "confirm")
            }
        },
        text = {
            Text(text = "Are you sure you want to delete the recipe?")
        }
    )
}
