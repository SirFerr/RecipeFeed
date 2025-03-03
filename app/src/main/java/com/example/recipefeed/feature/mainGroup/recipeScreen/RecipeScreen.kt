@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.recipeScreen

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.example.recipefeed.R
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun RecipeScreen(
    id: Int = -1,
    viewModel: RecipeScreenViewModel = hiltViewModel(),

    onClickBack: () -> Unit,
) {
    viewModel.getById(id)
    Scaffold(topBar = {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(
                onClick = onClickBack, modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }, actions = {
            IconButton(onClick = {
                viewModel.changeLike()
                viewModel.addToFavourites()
            }) {
                Icon(
                    imageVector = if (viewModel.isLiked.value) Icons.Filled.FavoriteBorder else Icons.Filled.Favorite,
                    contentDescription = null
                )
            }
        })
    }) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.main_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            val alignmentStartModifier = Modifier.align(Alignment.Start)
            val imageBytes = Base64.decode(viewModel.recipe.value.imageData)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            SubcomposeAsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
                    .height(250.dp),
                loading = { CircularProgressIndicator() },
                contentScale = ContentScale.FillWidth
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = viewModel.recipe.value.recipeName,
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
            ) {
                Column(Modifier.padding(dimensionResource(id = R.dimen.main_padding))) {
                    Text(
                        modifier = alignmentStartModifier,
                        text = viewModel.recipe.value.description,
                    )
                }

            }

            Text(
                modifier = alignmentStartModifier,
                text = "Likes: " + viewModel.recipe.value.recipeLikes.toString(),
            )
            Text(
                modifier = alignmentStartModifier,
                text = stringResource(id = R.string.time_to_cook) + ": " + viewModel.recipe.value.timeToCook,
            )
            Text(
                modifier = alignmentStartModifier,
                text = stringResource(id = R.string.ingridients) + ": " + viewModel.recipe.value.ingredients,
            )
//                Text(
//                    text = "Recipe: " + recipe!!.ingredients,
//                    style = MaterialTheme.typography.bodyLarge
//                )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding) * 4))
        }

    }


}

