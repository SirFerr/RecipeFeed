package com.example.recipefeed.mainMenu

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import com.example.recipefeed.R

@Composable
fun TagItem(string: String){
    Card(onClick = { /*TODO*/ }, modifier = Modifier.clip(CircleShape)
        .wrapContentSize()) {
        Text(text = string, modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding)*2),maxLines = 1)
    }
}