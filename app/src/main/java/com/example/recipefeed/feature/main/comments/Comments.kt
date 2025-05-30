package com.example.recipefeed.feature.main.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.ErrorNetworkCard
import com.example.recipefeed.feature.composable.UpdateBox
import com.example.recipefeed.feature.composable.cards.CommentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Comments(
    viewModel: CommentsViewModel = hiltViewModel(),
    recipeId: Int,
    onBackPressed: () -> Unit
) {
    var commentText by remember { mutableStateOf("") }

    LaunchedEffect(recipeId) {
        viewModel.fetchComments(recipeId)
    }

    UpdateBox(isLoading = viewModel.isLoading.value, exec = { viewModel.fetchComments(recipeId) }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },
                    actions = {}
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.main_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = dimensionResource(id = R.dimen.main_padding)),
                        placeholder = { Text("Добавить комментарий...") },
                        singleLine = true
                    )
                    IconButton(
                        onClick = {
                            if (commentText.isNotBlank()) {
                                viewModel.createComment(recipeId, commentText)
                                commentText = ""
                            }
                        },
                        enabled = commentText.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Send comment"
                        )
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
            ) {
                if (viewModel.isSuccessful.value && viewModel.comments.value.isNotEmpty()) {
                    items(viewModel.comments.value) { comment ->
                        CommentCard(
                            comment = comment,
                            isModerator = viewModel.isModerator.value,
                            isAuthor = viewModel.currentUserId.value == comment.userId,
                            onDelete = { id, reason -> viewModel.deleteComment(id, reason) }
                        )
                    }
                } else if (!viewModel.isSuccessful.value) {
                    item {
                        ErrorNetworkCard {
                            viewModel.fetchComments(recipeId)
                        }
                    }
                }
            }
        }
    }
}