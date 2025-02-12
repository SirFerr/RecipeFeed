@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalEncodingApi::class,
    ExperimentalMaterial3Api::class
)

package com.example.recipefeed.view.mainMenu.editRecipeScreen

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipefeed.R
import com.example.recipefeed.screens.mainGroup.accountScreen.recipeEdit.DeleteRecipeDialog
import com.example.recipefeed.screens.mainGroup.accountScreen.recipeEdit.ImagePickerCard
import com.example.recipefeed.screens.mainGroup.accountScreen.recipeEdit.MainInformationSection
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    navController: NavHostController = rememberNavController(),
    id: Int = 1,
    viewModel: EditRecipeScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    viewModel.getById(id)
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) viewModel.setSelectImages(it)
    }

    Scaffold(topBar = {
        TopAppBar(title = { },
            navigationIcon = {
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { viewModel.changeIsDelete() },
                    modifier = Modifier

                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            })
    }) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {

            ImagePickerCard(
                viewModel.selectImages.value,
                galleryLauncher
            ) { viewModel.setSelectImages(null) }

            HorizontalDivider()

            MainInformationSection(
                viewModel.recipeName.value,
                viewModel.description.value,
                viewModel.ingredients.value,
                viewModel.timeToCook.value,
                onRecipeNameChange = { viewModel.setRecipeName(it) },
                onDescriptionChange = { viewModel.setDescription(it) },
                onIngredientsChange = { viewModel.setIngredients(it) },
                onTimeToCookChange = { viewModel.setTimeToCook(it) }
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.editRecipe()
                }
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
                    navController.popBackStack()
                }
            )
        }
    }

}
