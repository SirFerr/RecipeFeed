@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.editRecipeScreen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.main.accountScreen.DeleteRecipeDialog
import com.example.recipefeed.feature.main.accountScreen.ImagePickerCard
import com.example.recipefeed.feature.main.accountScreen.MainInformationSection
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    id: Int = 1,
    viewModel: EditRecipeScreenViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
) {
    val context = LocalContext.current
    viewModel.getById(id)
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.setSelectedImageFile(uriToFile(it, context))
            }
        }

    Scaffold(topBar = {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = onClickBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = { viewModel.changeIsDelete() }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            Spacer(modifier = Modifier)

            ImagePickerCard(
                image = viewModel.selectedImageFile.value,
                galleryLauncher = galleryLauncher,
                onImageCleared = { viewModel.setSelectedImageFile(null) }
            )

            HorizontalDivider()

            MainInformationSection(
                recipeName = viewModel.recipeName.value,
                description = viewModel.description.value,
                ingredients = viewModel.ingredients.value,
                ingredientsBase = emptyList(), // Здесь можно передать список имен ингредиентов
                steps = viewModel.steps.value,
                onRecipeNameChange = { viewModel.setRecipeName(it) },
                onDescriptionChange = { viewModel.setDescription(it) },
                onIngredientsChange = { index, ingredient ->
                    viewModel.changeIngredient(index, ingredient)
                },
                onIngredientDelete = { viewModel.deleteIngredient(it) },
                onStepsChange = { viewModel.setSteps(it) },
                onIngredientAdd = { viewModel.addIngredient() }

            )

            Text(
                text = "Add ingredient",
                modifier = Modifier.clickable { viewModel.addIngredient() }
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                onClick = { viewModel.editRecipe() }
            ) {
                Text(
                    text = stringResource(id = R.string.complete),
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding))
                )
            }

            Spacer(modifier = Modifier)
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

private fun uriToFile(uri: Uri, context: Context): File {
    val file = File(context.cacheDir, "recipe_image_${System.currentTimeMillis()}.jpg")
    context.contentResolver.openInputStream(uri)?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }
    return file
}