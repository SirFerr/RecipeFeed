@file:OptIn(ExperimentalEncodingApi::class, ExperimentalEncodingApi::class)

package com.example.recipefeed.ui.view.screens.mainMenu.mainsScreens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.recipefeed.R
import com.example.recipefeed.ui.viewModel.RecipeViewModel
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Composable
fun mainScreenCard(
    navController: NavHostController? = null,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    var imageURL =
        "https://developer.android.com/static/codelabs/jetpack-compose-animation/img/jetpack_compose_logo_with_rocket.png"
    val recipe by recipeViewModel.randomRecipe.collectAsState()
    val imageBytes = Base64.decode(recipe.imageData)
    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    if (image != null)
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable { navController?.navigate("recipeScreen/${recipe.id}") }
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.mainPadding))
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(dimensionResource(id = R.dimen.roundedCorner))
                            )
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop

                    )

                    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))
                    Text(
                        text = recipe.recipeName,
                        modifier = Modifier,
                        style = MaterialTheme.typography.headlineMedium
                    )


                }





                Column {

                    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {

                        Text(
                            text = recipe.description,
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))

                        Text(
                            text = recipe.recipeRating.toString(),
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyLarge
                        )


                    }
                }

            }
        }

}