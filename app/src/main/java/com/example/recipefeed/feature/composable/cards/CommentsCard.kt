package com.example.recipefeed.feature.composable.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.recipefeed.feature.composable.CustomTextField

@Preview(showBackground = true)
@Composable
fun PreviewCommentCard() {
    Column {
        CommentCard(
            Comment(1, "SirFerr", "jgjhgjhjhghjhjhjhjghjghj", "12.12.12"),
        )
        Spacer(Modifier.size(12.dp))
        CommentCard(
            Comment(1, "SirFerr", "jgjhgjhjhghjhjhjhjghjghj", "12.12.12"),
            isModerator = true
        )
    }


}

data class Comment(
    val id: Int,
    val name: String,
    val text: String,
    val date: String,
    val isAuthor: Boolean = false,
)

@Composable
fun CommentCard(
    comment: Comment,
    isModerator: Boolean = false,
    onDelete: (Int, String) -> Unit = { id, reason -> }
) {
    var isDeleteShow by remember { mutableStateOf(false) }
    var rejectReason by remember { mutableStateOf("") }
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.main_padding)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(comment.name, maxLines = 1)
                Spacer(Modifier.width(12.dp))
                Text(comment.date)
                if (isModerator or comment.isAuthor) {
                    val iconSize = 24.dp
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            isDeleteShow = true
                        },
                        modifier = Modifier.size(iconSize)
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = null,
                        )
                    }
                }
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.main_padding)))
            Text(comment.text)
        }
    }
    if (isDeleteShow)
        AlertDialog(
            onDismissRequest = { isDeleteShow = false },
            dismissButton = {
                TextButton(onClick = { isDeleteShow = false }) {
                    Text(text = "dismiss")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (isModerator and rejectReason.isNotEmpty()) {
                        onDelete(
                            comment.id,
                            rejectReason
                        )
                    }
                    if (comment.isAuthor) {
                        onDelete(
                            comment.id,
                            ""
                        )
                    }

                }) {
                    Text(text = "confirm")
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Are you sure you want to delete comment?")
                    Spacer(Modifier.size(8.dp))
                    if (isModerator) {
                        CustomTextField(
                            stringRes = "Reason",
                            text = rejectReason
                        ) {
                            rejectReason = it
                        }
                    }
                }
            }
        )
}