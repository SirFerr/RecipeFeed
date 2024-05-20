package com.example.recipefeed.loginAndSignUp.signUpScreen

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.recipefeed.R
import com.example.recipefeed.loginAndSignUp.customTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SignUpScreen(
    navController: NavHostController? = null,
    viewModel: SignUpScreenViewModel = hiltViewModel()
) {
    val textUsername by viewModel.textUsername.collectAsState()
    val textPassword by viewModel.textPassword.collectAsState()
    val textPasswordAgain by viewModel.textPasswordAgain.collectAsState()



    Box(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.large_padding)),
        contentAlignment = Alignment.Center
    )
    {


        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))

        )
        {
            val context = LocalContext.current
            customTextField(
                stringResource(id = R.string.username_field),
                textValue = viewModel.textUsername
            )
            customTextField(
                stringResource(id = R.string.password_field),
                textValue = viewModel.textPassword
            )
            customTextField(
                stringResource(id = R.string.password_again_field),
                textValue = viewModel.textPasswordAgain
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding)))
            Button(onClick = {
                if (textPasswordAgain != "" && textPassword != "" && textUsername != "") {
                    if (textPasswordAgain == textPassword) {
                        viewModel.signUp()
                        navController?.popBackStack()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.password_not_compared),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.field_not_fill),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(text = stringResource(id = R.string.complete))
            }

        }
    }
}