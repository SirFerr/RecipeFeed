package com.example.recipefeed.ui.view.screen.mainMenu.mainsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.recipefeed.R
import com.example.recipefeed.data.recipe.model.Recipe

@Preview
@Composable
fun mainScreenCardPreview() {
    mainScreenCard()
}

@Composable
fun mainScreenCard() {
    val recipe = Recipe()
    var imageURL by remember {
        mutableStateOf("https://developer.android.com/static/codelabs/jetpack-compose-animation/img/jetpack_compose_logo_with_rocket.png")
    }
    var recipeName by remember {
        mutableStateOf(recipe.recipeName)
    }
    var description by remember {
        mutableStateOf(recipe.description)
    }
    var recipeRating by remember {
        mutableStateOf(recipe.recipeRating.toString())
    }
    Card(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.mainPadding)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = rememberAsyncImagePainter(imageURL),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = recipeName,
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {

                Text(text = description, modifier = Modifier, fontSize = 14.sp)
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.mainPadding)))
                Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = recipeRating, modifier = Modifier, fontSize = 14.sp)
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null )
                }

            }
        }
    }

}