@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.newRecipeScreen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.recipefeed.feature.main.accountScreen.ImagePickerCard
import com.example.recipefeed.feature.main.accountScreen.MainInformationSection
import com.example.recipefeed.utils.uriToFile
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecipeScreen(
    viewModel: NewRecipeScreenViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
) {
    val context = LocalContext.current
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
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {}
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
                externalIngredients = viewModel.externalIngredients.value.map { it["name"] as String }, // Внешние ингредиенты
                steps = viewModel.steps.value,
                onRecipeNameChange = { viewModel.setRecipeName(it) },
                onDescriptionChange = { viewModel.setDescription(it) },
                onIngredientsChange = { index, ingredient ->
                    viewModel.changeIngredient(index, ingredient)
                },
                onIngredientDelete = { viewModel.deleteIngredient(it) },
                onStepsChange = { viewModel.setSteps(it) },
                onIngredientAdd = { viewModel.addIngredient() },
                onSearchQueryChange = { query -> viewModel.searchExternalIngredients(query) } // Поиск по внешним ингредиентам
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                onClick = { viewModel.addRecipe() },
                enabled = viewModel.recipeName.value.isNotBlank() &&
                        viewModel.steps.value.isNotBlank() &&
                        viewModel.ingredients.value.any {
                            it.name.isNotBlank() && it.amount != null && it.amount > 0 && it.unit.isNotBlank()
                        }
            ) {
                Text(
                    text = stringResource(id = R.string.complete),
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding))
                )
            }

            Spacer(modifier = Modifier)
        }
    }
}

