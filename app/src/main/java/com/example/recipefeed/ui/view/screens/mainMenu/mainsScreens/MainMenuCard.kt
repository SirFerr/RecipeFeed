@file:OptIn(ExperimentalEncodingApi::class, ExperimentalEncodingApi::class)

package com.example.recipefeed.ui.view.screens.mainMenu.mainsScreens

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.ui.viewModel.RecipeViewModel
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
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
                .wrapContentSize()
                .clickable { navController?.navigate("recipeScreen/${recipe.id}") }
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.main_padding))
                    .wrapContentSize(),
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
                                RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                            )
                            .wrapContentSize(),

                        )

                    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_padding)))
                    Text(
                        text = recipe.recipeName,
                        modifier = Modifier,
                        style = MaterialTheme.typography.headlineMedium
                    )


                }





                Column {

                    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_padding)))
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {

                        Text(
                            text = recipe.description,
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.main_padding)))

                        Text(
                            text = recipe.recipeLikes.toString(),
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyLarge
                        )


                    }
                }

            }
        }

}