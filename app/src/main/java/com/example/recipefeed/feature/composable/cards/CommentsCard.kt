package com.example.recipefeed.feature.composable.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.example.recipefeed.R
import com.example.recipefeed.data.models.Comment
import com.example.recipefeed.feature.composable.CustomTextField
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentCard(
    comment: Comment,
    isModerator: Boolean = false,
    isAuthor: Boolean = false, // Добавляем параметр для проверки авторства
    onDelete: (Int, String) -> Unit = { _, _ -> }
) {
    var isDeleteShow by remember { mutableStateOf(false) }
    var rejectReason by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
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
                // Предполагаем, что имя пользователя нужно получать отдельно, здесь заглушка
                Text("User ${comment.userId}", maxLines = 1)
                Spacer(Modifier.width(12.dp))
                // Форматируем дату
                val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                Text(dateFormat.format(comment.addDate))
                if (isModerator || isAuthor) {
                    val iconSize = 24.dp
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { isDeleteShow = true },
                        modifier = Modifier.size(iconSize)
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete comment"
                        )
                    }
                }
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.main_padding)))
            Text(comment.commentText)
        }
    }

    if (isDeleteShow) {
        AlertDialog(
            onDismissRequest = { isDeleteShow = false },
            dismissButton = {
                TextButton(onClick = { isDeleteShow = false }) {
                    Text(text = "Dismiss")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (isModerator && rejectReason.isNotEmpty()) {
                            onDelete(comment.id, rejectReason)
                            isDeleteShow = false
                        } else if (isAuthor) {
                            onDelete(comment.id, "") // Автору не нужен reason
                            isDeleteShow = false
                        }
                    },
                    enabled = (isModerator && rejectReason.isNotEmpty()) || isAuthor
                ) {
                    Text(text = "Confirm")
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Are you sure you want to delete this comment?")
                    Spacer(Modifier.size(8.dp))
                    if (isModerator) {
                        CustomTextField(
                            stringRes = "Reason",
                            text = rejectReason,
                            onValueChange = { rejectReason = it }
                        )
                    }
                }
            }
        )
    }
}