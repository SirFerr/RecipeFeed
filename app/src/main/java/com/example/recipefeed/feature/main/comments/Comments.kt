package com.example.recipefeed.feature.main.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.ErrorNetworkCard
import com.example.recipefeed.feature.composable.cards.CommentCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Comments(
    viewModel: CommentsViewModel = hiltViewModel(),
    recipeId: Int,
    onBackPressed: () -> Unit
) {
    viewModel.fetchComments(recipeId)
    Scaffold(topBar = {
        TopAppBar(title = { },
            navigationIcon = {
                IconButton(
                    onClick = onBackPressed,
                    modifier = Modifier
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {

            })
    }) {

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
        ) {
            if (viewModel.isSuccessful.value)
                items(viewModel.comments.value) { comment ->
                    CommentCard(
                        comment = comment,
                        isModerator = viewModel.isModerator.value,
                        onDelete = { id, reason -> viewModel.deleteComment(id, reason) }
                    )
                }
            else
                item {
                    ErrorNetworkCard {
                        viewModel.fetchComments(recipeId)
                    }
                }

        }

    }
}


