@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.ui.view.screens.mainMenu.accountScreens.addedRecipes

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipe
import com.example.recipefeed.ui.viewModel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editRecipeScreen(
    navController: NavHostController,
    id: Int = -1,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {


    var recipe by remember { mutableStateOf<Recipe?>(null) }

    LaunchedEffect(id) {
        recipe = recipeViewModel.getById(id)
    }

    if (recipe != null) {
        var title by remember {
            mutableStateOf(recipe!!.recipeName)
        }
        var description by remember {
            mutableStateOf(recipe!!.description)
        }
        var ingredients by remember {
            mutableStateOf(recipe!!.ingredients)
        }
        var timeToCook by remember {
            mutableStateOf(recipe!!.timeToCook)
        }


        var selectImages by remember { mutableStateOf<Uri?>(null) }
        val galleryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
                selectImages = it
            }

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.mainPadding))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            TextField(modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.titleRecipe),
                        style = MaterialTheme.typography.titleMedium
                    )
                })
            Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))

            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
            ) {
                Text(text = "Pick Image From Gallery", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            if (selectImages != null) {

                Image(
                    painter = rememberAsyncImagePainter(model = selectImages),
                    contentDescription = null,
                    modifier = Modifier

                        .fillMaxSize()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.roundedCorner))),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            }

            TextField(modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { description = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.descriptionRecipe),
                        style = MaterialTheme.typography.titleMedium
                    )
                })
            Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))

            TextField(modifier = Modifier.fillMaxWidth(),
                value = ingredients,
                onValueChange = { ingredients = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.ingridients),
                        style = MaterialTheme.typography.titleMedium
                    )
                })
            Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))


            TextField(modifier = Modifier.fillMaxWidth(),
                value = timeToCook,
                onValueChange = { timeToCook = it },
                label = {
                    Text(
                        text = stringResource(id = R.string.timeToCook),
                        style = MaterialTheme.typography.titleMedium
                    )
                })

            Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
            Spacer(Modifier.weight(1f))
            Button(modifier = Modifier.wrapContentSize(), onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.complete)

                )
            }
        }
    }
}