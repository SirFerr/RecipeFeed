package com.example.recipefeed.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefeed.R
import com.example.recipefeed.data.remote.RecipeFeedApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkAccessViewModel @Inject constructor(private val recipeFeedApi: RecipeFeedApi) :
    ViewModel() {
    var isNetwork = MutableStateFlow(true)
    val isLoading = MutableStateFlow(false)


    init {
        checkNetwork()
    }

    fun checkNetwork() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                isNetwork.value = recipeFeedApi.getById(1).isSuccessful
            } catch (e: Exception) {
                isNetwork.value = false
                Log.e("err", e.toString())
            } finally {
                isLoading.value = false
            }
        }
    }

    @Composable
    fun NetworkErrorCard(exec: @Composable () -> Unit) {

        val _isNetwork by isNetwork.collectAsState()
        val _isLoading by isLoading.collectAsState()

        if (!_isLoading)
            if (!_isNetwork)
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.main_padding)),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .wrapContentSize(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Column(
                            Modifier
                                .padding(dimensionResource(id = R.dimen.main_padding)),
                            verticalArrangement = Arrangement.spacedBy(
                                dimensionResource(id = R.dimen.main_padding)
                            ), horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.network_error),
                                modifier = Modifier.wrapContentSize(), textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Button(
                                onClick = { checkNetwork() },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onErrorContainer),
                                modifier = Modifier.wrapContentSize()
                            ) {
                                Text(text = stringResource(id = R.string.refresh), color = MaterialTheme.colorScheme.onError)
                            }
                        }
                    }
                }
            else
                exec()
        else
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.main_padding)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
    }


}
