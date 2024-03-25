package com.example.recipefeed.ui.view.screens.logInAndSignUp

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.recipefeed.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true, showBackground = true)
fun signUpScreen(navController: NavHostController? = null) {


    var textUsername by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var textPasswordAgain by remember { mutableStateOf("") }



    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {


        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.main_padding))

        )
        {
            val context = LocalContext.current
            OutlinedTextField(
                value = textUsername,
                label = {
                    Text(
                        text = stringResource(id = R.string.username_field),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                onValueChange = { textUsername = it })
            OutlinedTextField(
                value = textPassword,
                label = {
                    Text(
                        text = stringResource(id = R.string.password_field),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                onValueChange = { textPassword = it })
            OutlinedTextField(
                value = textPasswordAgain,
                label = {
                    Text(
                        text = stringResource(id = R.string.password_again_field),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                onValueChange = { textPasswordAgain = it })


            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.main_padding)))
            Button(onClick = {
                if (textPasswordAgain != "" && textPassword != "" && textUsername != "") {
                    if (textPasswordAgain == textPassword) {
                        navController?.navigate("logInScreen"){
                            popUpTo("loginScreen") {
                                inclusive = true
                            }
                        }

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