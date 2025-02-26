@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalEncodingApi::class,
    ExperimentalMaterial3Api::class
)

package com.example.recipefeed.view.mainMenu.newRecipeScreen

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
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.feature.mainGroup.accountScreen.recipeEdit.ImagePickerCard
import com.example.recipefeed.feature.mainGroup.accountScreen.recipeEdit.MainInformationSection
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecipeScreen(
    viewModel: NewRecipeScreenViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    ) {

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) viewModel.setSelectImages(it)
    }
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(title = { },
            navigationIcon = {
                IconButton(
                    onClick = onClickBack,
                    modifier = Modifier
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {

            })
    }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        ) {
            Spacer(modifier = Modifier)
            ImagePickerCard(viewModel.selectImages.value, galleryLauncher) { viewModel.setSelectImages(it) }

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
                    viewModel.addRecipes()
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