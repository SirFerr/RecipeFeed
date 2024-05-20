@file:OptIn(ExperimentalEncodingApi::class, ExperimentalEncodingApi::class)

package com.example.recipefeed.view.mainMenu.mainsScreens

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.utils.Destinations
import com.example.recipefeed.view.mainMenu.CustomAsyncImage
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
                navController.navigate("${Destinations.recipe}/${recipe.id}")
            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),

        ) {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentSize()
                .padding(dimensionResource(id = R.dimen.main_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            val imageBytes = Base64.decode(recipe.imageData)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            CustomAsyncImage(image)

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