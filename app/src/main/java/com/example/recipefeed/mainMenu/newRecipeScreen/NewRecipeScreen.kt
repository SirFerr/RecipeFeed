@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalEncodingApi::class,
    ExperimentalMaterial3Api::class
)

package com.example.recipefeed.view.mainMenu.newRecipeScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.data.remote.Recipe
import com.example.recipefeed.utils.convertToMultipart
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecipeScreen(
    navController: NavHostController,
    viewModel: NewRecipeScreenViewModel = hiltViewModel()
) {
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

    var selectImages by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null)
            selectImages = it
    }
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.main_padding))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),


        ) {
        Spacer(modifier = Modifier)
        Text(text = "Photo", style = MaterialTheme.typography.titleLarge)
        Card(
            onClick = { galleryLauncher.launch("image/*") }, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
        ) {
            if (selectImages != null)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {

                    AsyncImage(
                        model = selectImages,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(onClick = { selectImages = null }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
            else {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.pick_image),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

            }
        }

        HorizontalDivider()
        Text(text = "Main information", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = recipeName,
            onValueChange = { recipeName = it },
            label = {
                Text(
                    text = stringResource(id = R.string.title_recipe),
                    style = MaterialTheme.typography.titleMedium
                )
            })




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

        Spacer(Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            onClick = {
                viewModel.addRecipes(
                    Recipe(
                        recipeName = recipeName,
                        description = description,
                        timeToCook = timeToCook,
                        ingredients = ingredients
                    ),
                    convertToMultipart(selectImages, context), context
                )
            }) {
            Text(text = stringResource(id = R.string.complete), modifier = Modifier.padding(
                dimensionResource(id = R.dimen.main_padding)))
        }
        Spacer(modifier = Modifier)
    }
}