@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.ui.view.screens.mainMenu.accountScreens.addedRecipes

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import com.example.recipefeed.ui.viewModel.RecipeViewModel
import com.example.recipefeed.utils.convertToMultipart
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalEncodingApi::class)
@Composable
fun editRecipeScreen(
    navController: NavHostController,
    id: Int = 10,
    recipeViewModel: RecipeViewModel = hiltViewModel(navController.getBackStackEntry("mainScreen"))

) {

    val context = LocalContext.current

    recipeViewModel.getById(id = id)

    val recipe by recipeViewModel.idRecipe.collectAsState()


    var title by remember {
        mutableStateOf(recipe.recipeName)
    }
    var description by remember {
        mutableStateOf(recipe.description)
    }
    var ingredients by remember {
        mutableStateOf(recipe.ingredients)
    }
    var timeToCook by remember {
        mutableStateOf(recipe.timeToCook)
    }

    val imageBytes = Base64.decode(recipe.imageData)
    var selectImages by remember {
        mutableStateOf<Any?>(
            BitmapFactory.decodeByteArray(
                imageBytes,
                0,
                imageBytes.size
            )
        )
    }


    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            selectImages = it
        }


    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
    ) {
        Spacer(modifier = Modifier)
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            label = {
                Text(
                    text = stringResource(id = R.string.title_recipe),
                    style = MaterialTheme.typography.titleMedium
                )
            })
        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
        ) {
            Text(
                text = stringResource(id = R.string.pick_image),
                style = MaterialTheme.typography.titleMedium
            )
        }
        AsyncImage(
            model = selectImages, contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
        )

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            label = {
                Text(
                    text = stringResource(id = R.string.description_recipe),
                    style = MaterialTheme.typography.titleMedium
                )
            })

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = ingredients,
            onValueChange = { ingredients = it },
            label = {
                Text(
                    text = stringResource(id = R.string.ingridients),
                    style = MaterialTheme.typography.titleMedium
                )
            })


        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = timeToCook,
            onValueChange = { timeToCook = it },
            label = {
                Text(
                    text = stringResource(id = R.string.time_to_cook),
                    style = MaterialTheme.typography.titleMedium
                )
            })

        Spacer(Modifier.weight(1f))
        Button(modifier = Modifier.wrapContentSize(), onClick = {


            recipeViewModel.addRecipes(Recipe(), convertToMultipart(selectImages, context))
        }) {
            Text(
                text = stringResource(id = R.string.complete)

            )
        }
        Spacer(modifier = Modifier)

    }
}
