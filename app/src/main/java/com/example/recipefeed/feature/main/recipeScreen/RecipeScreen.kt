@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.recipefeed.view.mainMenu.recipeScreen

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.example.recipefeed.feature.composable.cards.TagItem
import com.example.recipefeed.utils.base64ToBitmap
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalEncodingApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun RecipeScreen(
    id: Int = -1,
    viewModel: RecipeScreenViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onComment: (Int) -> Unit,
    onTagClick: (String) -> Unit
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
                } else {
                    IconButton(onClick = { viewModel.toggleLike() }) {
                        Icon(
                            imageVector = if (viewModel.isLiked.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (viewModel.isLiked.value) "Remove from favorites" else "Add to favorites"
                        )
                    }
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

                        if (recipe.isOnApprove)
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                            ) {
                            Text(
                                modifier = alignmentStartModifier.padding(dimensionResource(id = R.dimen.main_padding)),
                                text = "На проверке",
                                style = MaterialTheme.typography.bodyLarge
                            )}

                        if (!recipe.rejectReason.isNullOrBlank()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                            ) {
                                Text(
                                    modifier = alignmentStartModifier.padding(dimensionResource(id = R.dimen.main_padding)),
                                    text = "Reject Reason: ${recipe.rejectReason}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onError
                                )
                            }
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                        ) {
                            Column(Modifier.padding(dimensionResource(id = R.dimen.main_padding))) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = recipe.description ?: "",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }


                        // Отображение тегов
                        Text(
                            modifier = alignmentStartModifier,
                            text = "Теги:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
                        ) {
                            viewModel.tags.value.forEach { (originalName, translatedName) ->
                                TagItem(
                                    string = translatedName,
                                    onClick = { onTagClick(originalName) } // Передаём английское значение
                                )
                            }

                        }

                        Text(
                            modifier = alignmentStartModifier,
                            text = "Лайки: ${recipe.likes}",
                        )

                        // Отображение ингредиентов
                        Text(
                            modifier = alignmentStartModifier,
                            text = "Ингредиенты:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                        ) {
                            viewModel.ingredients.value.forEach { ingredient ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(dimensionResource(id = R.dimen.main_padding))
                                ) {
                                    Text(
                                        text = "${ingredient.ingredientName} - ${ingredient.amount} ${ingredient.unit}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }

                        // Отображение питательной ценности
                        viewModel.nutrition.value?.let { nutrition ->
                            Text(
                                modifier = alignmentStartModifier,
                                text = "Питательность (на 100г):",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                            ) {
                                Column(Modifier.padding(dimensionResource(id = R.dimen.main_padding))) {
                                    Text(text = "Белок: ${nutrition.protein} г")
                                    Text(text = "Углеводы: ${nutrition.carbs} г")
                                    Text(text = "Жиры: ${nutrition.fat} г")
                                    Text(text = "Калории: ${nutrition.calories} ккал")
                                }
                            }
                        }

                        // Отображение шагов в карточке
                        Text(
                            modifier = alignmentStartModifier,
                            text = "Шаги:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                        ) {
                            Column(Modifier.padding(dimensionResource(id = R.dimen.main_padding))) {
                                Text(
                                    text = recipe.steps ?: "",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                        if (viewModel.recipe.value?.isOnApprove == false) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .clickable { onComment(id) }
                            ) {
                                Text("Комментарии")
                                Spacer(Modifier.size(12.dp))
                                Icon(
                                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                else -> {
                    Text(
                        text = "Не удалось загрузить рецепт",
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
                    Text(text = "Отклонить")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.approve() }) {
                    Text(text = "Подтвердить")
                }
            },
            text = {
                Text(text = "Вы уверены, что хотите одобрить рецепт?")
            }
        )
    }

    if (viewModel.isRejectShow.value) {
        AlertDialog(
            onDismissRequest = { viewModel.setIsRejectShow(false) },
            dismissButton = {
                TextButton(onClick = { viewModel.setIsRejectShow(false) }) {
                    Text(text = "Отклонить")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.reject() }) {
                    Text(text = "Подтвердить")
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Вы уверены, что хотите отклонить этот рецепт?")
                    Spacer(Modifier.size(8.dp))
                    CustomTextField(
                        stringRes = "Причина",
                        text = viewModel.rejectReason.value,
                        onValueChange = { viewModel.setRejectReason(it) }
                    )
                }
            }
        )
    }
}