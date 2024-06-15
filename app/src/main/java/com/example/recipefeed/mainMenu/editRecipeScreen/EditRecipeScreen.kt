@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalEncodingApi::class)

package com.example.recipefeed.view.mainMenu.editRecipeScreen

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.utils.convertToMultipart
import kotlinx.coroutines.delay
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.math.roundToInt


@OptIn(ExperimentalEncodingApi::class)
@Preview(showBackground = true)
@Composable
fun EditRecipeScreen(
    navController: NavHostController = rememberNavController(),
    id: Int = 1,
    viewModel: EditRecipeScreenViewModel = hiltViewModel(),

    ) {
    val context = LocalContext.current

    val recipe by viewModel.recipe.collectAsState()
    viewModel.getById(id)

    var recipeName by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var ingredients by remember {
        mutableStateOf("")
    }
    var timeToCook by remember {
        mutableStateOf("")
    }
    var isDelete by remember {
        mutableStateOf(false)
    }


    val imageBytes = Base64.decode(recipe.imageData)
    var selectImages by remember {
        mutableStateOf<Any?>(
            ""
        )
    }

    LaunchedEffect(recipe) {
        selectImages = BitmapFactory.decodeByteArray(
            imageBytes,
            0,
            imageBytes.size
        )
        recipeName = recipe.recipeName
        description = recipe.description
        ingredients = recipe.ingredients
        timeToCook = recipe.timeToCook
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null)
                selectImages = it
        }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End, modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.main_padding))
        ) {
            IconButton(
                onClick = {
                    isDelete = true

                },
                modifier = Modifier
                    .size(30.dp)
                    .wrapContentSize()
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }
        }

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = recipeName,
            onValueChange = { recipeName = it },
            label = {
                Text(
                    text = stringResource(id = R.string.title_recipe),
                    style = MaterialTheme.typography.titleMedium
                )
            })
        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
        ) {
            Text(
                text = stringResource(id = R.string.pick_image),
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (selectImages != null)
            AsyncImage(
                model = selectImages,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))),
                contentScale = ContentScale.FillWidth
            )
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            label = {
                Text(
                    text = stringResource(id = R.string.description_recipe),
                    style = MaterialTheme.typography.titleMedium
                )
            })

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = ingredients,
            onValueChange = { ingredients = it },
            label = {
                Text(
                    text = stringResource(id = R.string.ingridients),
                    style = MaterialTheme.typography.titleMedium
                )
            })


        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = timeToCook,
            onValueChange = { timeToCook = it },
            label = {
                Text(
                    text = stringResource(id = R.string.time_to_cook),
                    style = MaterialTheme.typography.titleMedium
                )
            })
        Spacer(modifier = Modifier.weight(1f))

        Button(modifier = Modifier.wrapContentSize(), onClick = {
            viewModel.editRecipe(
                Recipe(),
                convertToMultipart(selectImages, context),
                context
            )
        }) {
            Text(
                text = stringResource(id = R.string.complete)

            )
        }
        Spacer(modifier = Modifier)

    }
    if (isDelete) {

        AlertDialog(
            onDismissRequest = {
                isDelete = false
            },
            dismissButton = {
                TextButton(onClick = {
                    isDelete = false
                }) {
                    Text(text = "dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    isDelete = false
                    viewModel.deleteRecipeById(id)
                    navController.popBackStack()
                }) {
                    Text(text = "confirm")
                }
            }, text = {
                Text(text = "Are you sure you want to delete the recipe?")
            })

    }

}

