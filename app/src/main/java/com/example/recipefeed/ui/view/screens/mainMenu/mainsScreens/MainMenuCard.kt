@file:OptIn(ExperimentalEncodingApi::class, ExperimentalEncodingApi::class)

package com.example.recipefeed.ui.view.screens.mainMenu.mainsScreens

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.recipe.Recipe
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun mainScreenCard(
    navController: NavHostController? = null,
    recipe : Recipe
) {
    var imageURL =
        "https://developer.android.com/static/codelabs/jetpack-compose-animation/img/jetpack_compose_logo_with_rocket.png"
    val imageBytes = Base64.decode(recipe.imageData)
    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    if (image != null)
        Card(
            modifier = Modifier
                .wrapContentSize()
                .clickable { navController?.navigate("recipeScreen/${recipe.id}") }
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.main_padding))
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                        )
                        .wrapContentSize(),
                )
                Text(
                    text = recipe.recipeName,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center
                )
                Divider(color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = recipe.description,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Likes: "+recipe.recipeLikes.toString(),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge
                )

            }
        }

}