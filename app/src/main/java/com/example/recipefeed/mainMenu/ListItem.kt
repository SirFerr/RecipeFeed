package com.example.recipefeed.view.mainMenu

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.utils.Destinations
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
@Composable
fun ListItem(
    recipe: Recipe,
    navController: NavController? = null,
    icon: ImageVector = Icons.Filled.Favorite
) {
    var showShimmer by remember { mutableStateOf(true) }
    Card(
        Modifier
            .fillMaxWidth()
            .size(1f.dp, 70.dp)
            .clickable { navController?.navigate("${Destinations.RECIPE}/${recipe.id}") },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
    ) {
        Row(
            Modifier
                .padding(dimensionResource(id = R.dimen.sub_padding))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.sub_padding))
        ) {

            val imageBytes = Base64.decode(recipe.imageData)
            val image by remember {
                mutableStateOf(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size))
            }
            SubcomposeAsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .background(
                        ShimmerEffect(showShimmer)
                    )
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))),
                contentScale = ContentScale.Crop,
                onLoading = { showShimmer = true }, onSuccess = { showShimmer = false }
            )
            Column(
                Modifier
                    .weight(2.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = recipe.recipeName, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Likes: " + recipe.recipeLikes.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(modifier = Modifier
                .weight(1f)
                .aspectRatio(1f), onClick = {
                if (icon == Icons.Filled.Edit) {
                    navController?.navigate("${Destinations.EDIT_RECIPE}/${recipe.id}")
                }
            }) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    }

}
