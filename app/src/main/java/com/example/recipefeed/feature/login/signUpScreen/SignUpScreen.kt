package com.example.recipefeed.feature.login.signUpScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefeed.R
import com.example.recipefeed.feature.composable.CustomTextField
import com.example.recipefeed.feature.composable.ErrorMessage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    onBack:()->Unit,
    onSuccess:()->Unit,
) {


    Scaffold(
        topBar = {
            TopAppBar(title = { },

                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBack()
                                  },
                        modifier = Modifier
                            .size(30.dp)
                            .wrapContentSize()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        })
    {
        it
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))

            )
            {
                CustomTextField(
                    stringResource(id = R.string.username_field),
                    viewModel.textUsername.value,
                    onChange = { viewModel.setTextUsername(it) }

                )
                CustomTextField(
                    stringResource(id = R.string.password_field),
                    viewModel.textPassword.value,
                    onChange = { viewModel.setTextPassword(it) }

                )
                CustomTextField(
                    stringResource(id = R.string.password_again_field),
                    viewModel.textPasswordAgain.value,
                    onChange = { viewModel.setTextPasswordAgain(it) }
                )

                ErrorMessage(viewModel.errorMessage.value)


                Button(onClick = {
                    viewModel.signUp(isSuccess = {
                        onSuccess()
                    })
                }) {
                    Text(text = stringResource(id = R.string.complete))
                }

            }
        }
    }
}