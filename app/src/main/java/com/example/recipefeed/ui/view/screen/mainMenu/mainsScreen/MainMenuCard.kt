package com.example.recipefeed.ui.view.screen.mainMenu.mainsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipe

@Preview
@Composable
fun mainScreenCardPreview() {
    mainScreenCard()
}

@Composable
fun mainScreenCard(navController: NavHostController? = null) {
    val recipe = Recipe(id = 10)
    var imageURL =
        "https://developer.android.com/static/codelabs/jetpack-compose-animation/img/jetpack_compose_logo_with_rocket.png"


    Card(modifier = Modifier
        .clickable { navController?.navigate("recipeScreen/${recipe.id}") }) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.mainPadding)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {

            }
            AsyncImage(
                model = imageURL,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp)
                    ),
                contentScale = ContentScale.FillWidth

            )



            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))
            Column {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = recipe.recipeName,
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {

                    Text(text = recipe.description, modifier = Modifier, fontSize = 14.sp)
                    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = recipe.recipeRating.toString(), modifier = Modifier, fontSize = 14.sp
                        )
                        Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                    }


                }
            }

        }
    }

}