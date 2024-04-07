package com.example.recipefeed.view.loginAndSignUp.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.view.loginAndSignUp.customTextField


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun logInScreen(
    navController: NavHostController? = null,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {

    val textUsername by viewModel.textUsername.collectAsState()
    val textPassword by viewModel.textPassword.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.large_padding)), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        )
        {

            customTextField(
                stringResource(id = R.string.username_field),
                textValue = viewModel.textUsername
            )
            customTextField(
                stringResource(id = R.string.password_field),
                textValue = viewModel.textPassword
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding)))
            Button(onClick = {
                navController?.navigate("main") {
                    popUpTo("loginScreen") {
                        inclusive = true
                    }
                }

            }) {
                Text(text = stringResource(id = R.string.login))
            }
            Button(onClick = {
                navController?.navigate("signupScreen")

            }) {
                Text(text = stringResource(id = R.string.signup))

            }


        }

    }
}