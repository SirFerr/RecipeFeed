@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalEncodingApi::class,
    ExperimentalMaterial3Api::class
)

package com.example.recipefeed.view.mainMenu.editRecipeScreen

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.mainMenu.recipeEdit.DeleteRecipeDialog
import com.example.recipefeed.mainMenu.recipeEdit.ImagePickerCard
import com.example.recipefeed.mainMenu.recipeEdit.MainInformationSection
import com.example.recipefeed.utils.convertToMultipart
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
@Composable
fun EditRecipeScreen(
    navController: NavHostController = rememberNavController(),
    id: Int = 1,
    viewModel: EditRecipeScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    viewModel.getById(id)

    val recipeName by viewModel.recipeName.collectAsState()
    val description by viewModel.description.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()
    val timeToCook by viewModel.timeToCook.collectAsState()
    val selectImages by viewModel.selectImages.collectAsState()

    val isDelete by viewModel.isDelete.collectAsState()


    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) viewModel.setSelectImages(it)
    }

    Scaffold(topBar = {
        TopAppBar(title = { },
            navigationIcon = {
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .size(30.dp)
                        .wrapContentSize()
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {
            IconButton(
                onClick = { viewModel.changeIsDelete() },
                modifier = Modifier
                    .size(30.dp)
                    .wrapContentSize()
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

            ImagePickerCard(selectImages, galleryLauncher) { viewModel.setSelectImages(null) }

            HorizontalDivider()

            MainInformationSection(
                recipeName, description, ingredients, timeToCook,
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
                    viewModel.editRecipe(context)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.complete),
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding))
                )
            }

            Spacer(modifier = Modifier)
        }

        if (isDelete) {
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
