@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.recipeScreen

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.recipefeed.feature.composable.CustomTextField
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun RecipeScreen(
    id: Int = -1,
    viewModel: RecipeScreenViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onComment:(Int)->Unit,
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
            if (viewModel.isModerator.value) {
                Row {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        viewModel.reject()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null
                        )
                    }
                }
            }
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
                text = stringResource(id = R.string.ingredients) + ": " + viewModel.recipe.value.ingredients,
            )
//                Text(
//                    text = "Recipe: " + recipe!!.ingredients,
//                    style = MaterialTheme.typography.bodyLarge
//                )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.End).clickable {
                    onComment(id)
                }
            ) {
                Text("Comments")
                Spacer(Modifier.size(12.dp))
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding) * 4))
        }

    }


    if (viewModel.isApproveShow.value) {
        AlertDialog(
            onDismissRequest = { viewModel.setIsApproveShow(false) },
            dismissButton = {
                TextButton(onClick = { viewModel.setIsApproveShow(false) }) {
                    Text(text = "dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.approve() }) {
                    Text(text = "confirm")
                }
            },
            text = {
                Text(text = "Are you sure you want to approve the recipe?")
            }
        )
    }

    if (viewModel.isRejectShow.value) {
        AlertDialog(
            onDismissRequest = { viewModel.setIsRejectShow(false) },
            dismissButton = {
                TextButton(onClick = { viewModel.setIsRejectShow(false) }) {
                    Text(text = "dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.reject() }) {
                    Text(text = "confirm")
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Are you sure you want to reject the recipe?")
                    Spacer(Modifier.size(8.dp))
                    CustomTextField(
                        stringRes = "Reason",
                        text = viewModel.rejectReason.value
                    ) {
                        viewModel.setRejectReason(it)
                    }
                }
            }
        )
    }


}

