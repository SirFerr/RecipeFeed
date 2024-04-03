package com.example.recipefeed.screens.mainMenu.recipeScreen

import android.graphics.BitmapFactory
import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.recipefeed.R
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun recipeScreen(
    navController: NavHostController,
    id: Int = -1,
    viewModel: RecipeScreenViewModel = hiltViewModel()
) {

    viewModel.getById(id)

    val context = LocalContext.current

    val recipe by viewModel.idRecipe.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.main_padding)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = recipe.recipeName,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )


        val imageBytes = Base64.decode(recipe.imageData)
        Log.d("image Bytes", imageBytes.toString())
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        SubcomposeAsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))),
            loading = { CircularProgressIndicator() }, contentScale = ContentScale.FillWidth
        )

        Text(text = recipe.description, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider()

        Text(
            text = stringResource(id = R.string.time_to_cook) + ": " + recipe.timeToCook,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(id = R.string.ingridients) + ": " + recipe.ingredients,
            style = MaterialTheme.typography.bodyLarge
        )
//                Text(
//                    text = "Recipe: " + recipe!!.ingredients,
//                    style = MaterialTheme.typography.bodyLarge
//                )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding) * 4))


    }


}

