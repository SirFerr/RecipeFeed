@file:OptIn(ExperimentalEncodingApi::class, ExperimentalEncodingApi::class)

package com.example.recipefeed.view.mainMenu.mainsScreens

import android.graphics.BitmapFactory
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.recipefeed.data.remote.recipe.Recipe
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun MainScreenCard(
    navController: NavHostController,
    recipe: Recipe
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {

                navController.navigate("recipeScreen/${recipe.id}")
            }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.main_padding))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        ) {


            val imageBytes = Base64.decode(recipe.imageData)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            SubcomposeAsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
                    .align(Alignment.CenterHorizontally),
                loading = { CircularProgressIndicator() }, contentScale = ContentScale.FillWidth
            )

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
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
            Text(
                text = "Likes: " + recipe.recipeLikes.toString(),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )

        }
    }


}