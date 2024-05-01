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
import com.example.recipefeed.view.loginAndSignUp.loginScreen.LoginScreenViewModel
import com.example.recipefeed.view.loginAndSignUp.loginScreen.LogInScreen
import com.example.recipefeed.view.loginAndSignUp.signUpScreen.SignUpScreen
import com.example.recipefeed.view.loginAndSignUp.signUpScreen.SignUpScreenViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginScreenDisplayed() {
        // Start with the login screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen") {
                composable("loginScreen") {
                    LogInScreen(navController = navController, LoginScreenViewModel())
                }
            }
        }
        // Check if login screen elements are displayed
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("LogIn").assertIsDisplayed()
        composeTestRule.onNodeWithText("SignUp").assertIsDisplayed()
    }

    @Test
    fun testLoginSuccess() {
        // Start with the login screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen") {
                composable("loginScreen") {
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
    fun testNavigationToSignUpScreen() {
        // Start with the login screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "loginScreen") {
                composable("loginScreen") {
                    LogInScreen(navController = navController, LoginScreenViewModel())
                }
                composable("signupScreen"){
                    Text(text = "signupScreen")
                }
            }
        }
        // Click on SignUp button
        composeTestRule.onNodeWithText("SignUp").performClick()

        // Validate navigation to sign up screen
        composeTestRule.onNodeWithText("signupScreen").assertExists()
    }

    @Test
    fun testSignUpScreenDisplayed() {
        // Start with the sign up screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "signUpScreen") {
                composable("signUpScreen") {
                    SignUpScreen(navController = navController, SignUpScreenViewModel())
                }
            }
        }
        // Check if sign up screen elements are displayed
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password again").assertIsDisplayed()
        composeTestRule.onNodeWithText("Complete").assertIsDisplayed()
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
