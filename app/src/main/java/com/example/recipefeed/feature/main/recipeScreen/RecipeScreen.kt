@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.recipeScreen

import android.graphics.Bitmap
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.CustomTextField
import com.example.recipefeed.utils.base64ToBitmap
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun RecipeScreen(
    id: Int = -1,
    viewModel: RecipeScreenViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onComment: (Int) -> Unit,
) {
    LaunchedEffect(Unit) { viewModel.getById(id) }

    Scaffold(topBar = {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = onClickBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                if (viewModel.recipe.value?.isOnApprove == true) {
                    if (viewModel.isModerator.value) {
                        Row {
                            IconButton(onClick = { viewModel.setIsApproveShow(true) }) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Approve"
                                )
                            }
                            IconButton(onClick = { viewModel.setIsRejectShow(true) }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Reject"
                                )
                            }
                        }
                    }
                } else
                    IconButton(onClick = { viewModel.toggleLike() }) {
                        Icon(
                            imageVector = if (viewModel.isLiked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (viewModel.isLiked.value) "Remove from favorites" else "Add to favorites"
                        )
                    }
            }
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.main_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        ) {
            val alignmentStartModifier = Modifier.align(Alignment.Start)

            when {
                viewModel.isLoading.value -> {
                    CircularProgressIndicator()
                }

                viewModel.isSuccessful.value && viewModel.recipe.value != null -> {
                    viewModel.recipe.value?.let { recipe ->
                        val bitmap: Bitmap? = remember(recipe.imageData) {
                            recipe.imageData?.let { base64ToBitmap(it) }
                        }

                        SubcomposeAsyncImage(
                            model = bitmap,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)))
                                .height(250.dp),
                            loading = { CircularProgressIndicator() },
                            contentScale = ContentScale.FillWidth
                        )

                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = recipe.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                        ) {
                            Column(Modifier.padding(dimensionResource(id = R.dimen.main_padding))) {
                                Text(
                                    modifier = alignmentStartModifier,
                                    text = recipe.description ?: "No description",
                                )
                            }
                        }

                        Text(
                            modifier = alignmentStartModifier,
                            text = "Likes: ${recipe.likes}",
                        )

                        Text(
                            modifier = alignmentStartModifier,
                            text = "Steps: ${recipe.steps ?: "No steps provided"}",
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable { onComment(id) }
                        ) {
                            Text("Comments")
                            Spacer(Modifier.size(12.dp))
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                }

                else -> {
                    Text(
                        text = "Failed to load recipe",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = alignmentStartModifier
                    )
                }
            }

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding) * 4))
        }
    }

    if (viewModel.isApproveShow.value) {
        AlertDialog(
            onDismissRequest = { viewModel.setIsApproveShow(false) },
            dismissButton = {
                TextButton(onClick = { viewModel.setIsApproveShow(false) }) {
                    Text(text = "Dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.approve() }) {
                    Text(text = "Confirm")
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
                    Text(text = "Dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.reject() }) {
                    Text(text = "Confirm")
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Are you sure you want to reject the recipe?")
                    Spacer(Modifier.size(8.dp))
                    CustomTextField(
                        stringRes = "Reason",
                        text = viewModel.rejectReason.value,
                        onValueChange = { viewModel.setRejectReason(it) }
                    )
                }
            }
        )
    }
}