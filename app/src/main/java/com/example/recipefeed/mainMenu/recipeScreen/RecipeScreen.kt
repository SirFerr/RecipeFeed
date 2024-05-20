package com.example.recipefeed.view.mainMenu.recipeScreen

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.view.mainMenu.CustomAsyncImage
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun RecipeScreen(
    id: Int = -1,
    viewModel: RecipeScreenViewModel = hiltViewModel()
) {
    val recipe by viewModel.idRecipe.collectAsState()
    val isLiked by viewModel.isLiked.collectAsState()
    viewModel.getById(id)
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.changeLike() }) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.FavoriteBorder else Icons.Filled.Favorite,
                    contentDescription = null
                )
            }
        }
    )
    {
        it
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.main_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            val alignmentStartModifier = Modifier.align(Alignment.Start)
            val imageBytes = Base64.decode(recipe.imageData)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            CustomAsyncImage(image)
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = recipe.recipeName,
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
            ) {
                Column(Modifier.padding(dimensionResource(id = R.dimen.main_padding))) {
                    Text(
                        modifier = alignmentStartModifier,
                        text = recipe.description,
                    )
                }

            }

            Text(
                modifier = alignmentStartModifier,
                text = "Likes: " + recipe.recipeLikes.toString(),
            )
            Text(
                modifier = alignmentStartModifier,
                text = stringResource(id = R.string.time_to_cook) + ": " + recipe.timeToCook,
            )
            Text(
                modifier = alignmentStartModifier,
                text = stringResource(id = R.string.ingridients) + ": " + recipe.ingredients,
            )
//                Text(
//                    text = "Recipe: " + recipe!!.ingredients,
//                    style = MaterialTheme.typography.bodyLarge
//                )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding) * 4))
        }

    }
}

