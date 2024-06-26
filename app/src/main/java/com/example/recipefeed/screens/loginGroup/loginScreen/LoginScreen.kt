package com.example.recipefeed.screens.loginGroup.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.screens.navigation.Destinations
import com.example.recipefeed.screens.loginGroup.CustomTextField
import com.example.recipefeed.screens.loginGroup.ErrorMessage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    navController: NavHostController,
    viewModel: LoginScreenViewModel = hiltViewModel(),
) {
    val token by viewModel.token.collectAsState()

    if (token.isNotEmpty()) {
        navController.navigate(Destinations.MainGroup.route) {
            popUpTo(Destinations.LoginGroup.route) { inclusive = true }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        )
        {

            CustomTextField(
                stringResource(id = R.string.username_field),
                textValue = viewModel.textUsername
            )
            CustomTextField(
                stringResource(id = R.string.password_field),
                textValue = viewModel.textPassword
            )

            ErrorMessage(textValue = viewModel.errorMessage)

            Button(onClick = {
                viewModel.signIn(
                    isSuccess = {
                        navController.navigate(Destinations.MainGroup.route) {
                            popUpTo(Destinations.LoginGroup.route) { inclusive = true }
                        }
                    },
                )

            }) {
                Text(text = stringResource(id = R.string.login))
            }

            TextButton(onClick = {
                navController.navigate(Destinations.LoginGroup.SignUp.route)

            }) {
                Text(text = stringResource(id = R.string.signup))
            }


        }

    }
}