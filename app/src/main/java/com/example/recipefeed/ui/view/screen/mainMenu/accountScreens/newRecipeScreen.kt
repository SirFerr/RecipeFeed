@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalEncodingApi::class
)

package com.example.recipefeed.ui.view.screen.mainMenu.accountScreens

import android.net.Uri
import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.core.net.toFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipe
import com.example.recipefeed.ui.viewModel.RecipeViewModel
import kotlin.io.encoding.ExperimentalEncodingApi


@Composable
fun newRecipeScreen(
    navController: NavHostController, recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var ingredients by remember {
        mutableStateOf("")
    }
    var timeToCook by remember {
        mutableStateOf("")
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
            label = { Text(text = stringResource(id = R.string.titleRecipe)) })
        Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))

        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
        ) {
            Text(text = "Pick Image From Gallery")
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
            label = { Text(text = stringResource(id = R.string.descriptionRecipe)) })
        Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))

        TextField(modifier = Modifier.fillMaxWidth(),
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text(text = stringResource(id = R.string.ingridients)) })
        Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))


        TextField(modifier = Modifier.fillMaxWidth(),
            value = timeToCook,
            onValueChange = { timeToCook = it },
            label = { Text(text = stringResource(id = R.string.timeToCook)) })

        Spacer(Modifier.padding(dimensionResource(id = R.dimen.subPadding)))
        Spacer(Modifier.weight(1f))
        Button(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                Log.d(
                    "encode", Recipe(
                        id = 1001,
                        recipeName = title,
                        ingredients = ingredients,
                        timeToCook = timeToCook,
                        description = description,
                        imageData = java.util.Base64.getEncoder()
                            .encodeToString(selectImages?.toFile()?.readBytes())
                    ).toString()
                )
            }) {
            Text(text = stringResource(id = R.string.complete))
        }
    }
}