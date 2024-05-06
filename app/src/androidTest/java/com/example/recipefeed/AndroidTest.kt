package com.example.recipefeed

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipefeed.data.remote.RecipeFeedApi
import com.example.recipefeed.utils.Destinations
import com.example.recipefeed.view.loginAndSignUp.loginScreen.LoginScreenViewModel
import com.example.recipefeed.view.loginAndSignUp.loginScreen.LogInScreen
import com.example.recipefeed.view.loginAndSignUp.signUpScreen.SignUpScreen
import com.example.recipefeed.view.loginAndSignUp.signUpScreen.SignUpScreenViewModel
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreenViewModel
import com.example.recipefeed.view.mainMenu.recipeScreen.RecipeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginSuccess() {
        // Start with the login screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Destinations.login) {
                composable(Destinations.login) {
                    LogInScreen(navController = navController, LoginScreenViewModel())
                }
                composable("main"){ Text(text = "MainScreen")}
            }
        }
        // Perform login with valid credentials
        composeTestRule.onNodeWithText("Username").performTextInput("validUsername")
        composeTestRule.onNodeWithText("Password").performTextInput("validPassword")
        composeTestRule.onNodeWithText("LogIn").performClick()

        // Validate navigation to main screen
        composeTestRule.onNodeWithText("MainScreen").assertExists()
    }

    @Test
    fun testSignUpSuccess() {
        // Start with the sign up screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen") {
                composable("signUpScreen") {
                    SignUpScreen(navController = navController, SignUpScreenViewModel())
                }
                composable("loginScreen"){ Text(text = "loginScreen")
                Button(onClick = { navController.navigate("signUpScreen") }) {
                    Text(text = "btn")
                }}
            }
        }
        composeTestRule.onNodeWithText("btn").performClick()
        // Perform sign up with valid credentials
        composeTestRule.onNodeWithText("Username").performTextInput("validUsername")
        composeTestRule.onNodeWithText("Password").performTextInput("validPassword")
        composeTestRule.onNodeWithText("Password again").performTextInput("validPassword")
        composeTestRule.onNodeWithText("Complete").performClick()

        // Validate navigation to main screen
        composeTestRule.onNodeWithText("loginScreen").assertExists()


    }


}
