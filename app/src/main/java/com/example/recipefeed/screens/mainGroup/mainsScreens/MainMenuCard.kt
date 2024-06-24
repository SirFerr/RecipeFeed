@file:OptIn(ExperimentalEncodingApi::class, ExperimentalEncodingApi::class)

package com.example.recipefeed.view.mainMenu.mainsScreens

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.screens.Destinations
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun MainScreenCard(
    navController: NavHostController,
    recipe: Recipe
) {
    Box(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.main_padding))
            .fillMaxSize(0.95f)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
            .clickable {
                navController.navigate("${Destinations.MainGroup.Recipe.route}/${recipe.id}")
            }
    ) {
        val imageBytes = Base64.decode(recipe.imageData)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)


        SubcomposeAsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = { },
        )

        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(id = R.dimen.main_padding))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(dimensionResource(id = R.dimen.main_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = recipe.recipeName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = recipe.description,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = "Likes: " + recipe.recipeLikes.toString(),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}