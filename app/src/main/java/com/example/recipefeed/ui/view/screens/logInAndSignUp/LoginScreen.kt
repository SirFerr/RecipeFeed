package com.example.recipefeed.ui.view.screens.logInAndSignUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.recipefeed.ui.viewModel.TextFieldViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun logInScreen(
    navController: NavHostController? = null,
    textFieldViewModel: TextFieldViewModel = hiltViewModel()
) {

    val textUsername by textFieldViewModel.textUsername.collectAsState()
    val textPassword by textFieldViewModel.textPassword.collectAsState()




    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))
        )
        {

            OutlinedTextField(
                value = textUsername,
                label = {
                    Text(
                        text = stringResource(id = R.string.username_field),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                onValueChange = { textFieldViewModel.textUsername.value = it })
            OutlinedTextField(
                value = textPassword,
                label = {
                    Text(
                        text = stringResource(id = R.string.password_field),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                onValueChange = { textFieldViewModel.textPassword.value = it })
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