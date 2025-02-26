package com.example.recipefeed.feature.composable.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipefeed.R

@Preview(showBackground = true)
@Composable
fun PreviewCommentCard() {

    CommentCard(
        Comment("1", "1", "1"),
        listOf(Comment("1", "1", "1"), Comment("1", "1", "1"), Comment("1", "1", "1"))

    )


}

data class Comment(
    val name: String,
    val text: String,
    val date: String,
)

@Composable
fun CommentCard(comment: Comment, listReply: List<Comment> = emptyList()) {
    var isReplyShow by remember { mutableStateOf(false) }
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.main_padding)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(comment.name)
                Text(comment.date)
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.main_padding)))
            Text(comment.text)
            if (listReply.isNotEmpty())
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if(isReplyShow){
                        HorizontalDivider(modifier = Modifier.weight(1f))
                    }
                    Spacer(Modifier.size(dimensionResource(id = R.dimen.main_padding)))
                    IconButton(modifier = Modifier, onClick = {
                        isReplyShow = !isReplyShow
                    }) { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null) }
                }
            if (isReplyShow and listReply.isNotEmpty())
                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.main_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding)),
                ) {
                    items(listReply) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(comment.name)
                                Text(comment.date)
                            }
                            Spacer(Modifier.size(dimensionResource(id = R.dimen.main_padding)))
                            Text(comment.text)
                            Spacer(Modifier.size(dimensionResource(id = R.dimen.main_padding)))
                            HorizontalDivider(modifier = Modifier.weight(1f))
                        }
                    }
                }
        }
    }
}