package com.example.recipefeed.feature.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipefeed.feature.main.accountScreen.settingsScreen.SettingsScreen
import com.example.recipefeed.feature.main.scaffold.ScaffoldWithBottom
import com.example.recipefeed.feature.navigation.Destinations
import com.example.recipefeed.view.mainMenu.accountScreen.AccountScreen
import com.example.recipefeed.view.mainMenu.accountScreen.AccountScreenViewModel
import com.example.recipefeed.view.mainMenu.addedRecipesScreen.AddedRecipesScreen
import com.example.recipefeed.view.mainMenu.editRecipeScreen.EditRecipeScreen
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteRecipesViewModel
import com.example.recipefeed.view.mainMenu.favoriteScreen.FavoriteScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreen
import com.example.recipefeed.view.mainMenu.mainsScreens.MainScreenViewModel
import com.example.recipefeed.view.mainMenu.newRecipeScreen.NewRecipeScreen
import com.example.recipefeed.view.mainMenu.recipeScreen.RecipeScreen
import com.example.recipefeed.view.mainMenu.searchScreen.SearchScreen

@Composable
fun MainGroupNavigation(
    navHostController: NavController = rememberNavController(),
    navController: NavHostController = rememberNavController(),
    onThemeUpdated: () -> Unit
) {

    // Anim settings
    val duration = 700
    val enterTransition = fadeIn(animationSpec = tween(duration))
    val exitTransition = fadeOut(animationSpec = tween(duration))

    val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
    val favoriteRecipesViewModel: FavoriteRecipesViewModel = hiltViewModel()
    val accountScreenViewModel: AccountScreenViewModel = hiltViewModel()


    ScaffoldWithBottom(navController = navController, screen = {
        NavHost(navController = navController,
            startDestination = Destinations.MainGroup.Main.route,
            modifier = Modifier.padding(it),
            enterTransition = { enterTransition },
            exitTransition = { exitTransition }) {
            composable(Destinations.MainGroup.Main.route) {
                MainScreen(viewModel = mainScreenViewModel, onRecipeClick = {
                    navController.navigate("${Destinations.MainGroup.Recipe.route}/${mainScreenViewModel.nextRecipe.value.id}")
                })
            }
            composable(Destinations.MainGroup.Favorite.route) {
                FavoriteScreen(viewModel = favoriteRecipesViewModel,
                    onRecipeClick = { id -> navController.navigate("${Destinations.MainGroup.Recipe.route}/${id}") })
            }
            composable(
                Destinations.MainGroup.Search.route + "?name={name}", listOf(navArgument("name") {
                    type = NavType.StringType
                    defaultValue = ""
                })
            ) {
                val name = it.arguments?.getString("name").orEmpty()
                SearchScreen(name = name,
                    onListItemClick = { id -> navController.navigate("${Destinations.MainGroup.Recipe.route}/${id}") })
            }
            composable(Destinations.MainGroup.Account.route) {
                AccountScreen(viewModel = accountScreenViewModel,
                    onCreateRecipe = { navController.navigate(Destinations.MainGroup.NewRecipe.route) },
                    onShowCreatedRecipes = { navController.navigate(Destinations.MainGroup.AddedRecipes.route) },
                    onSettings = { navController.navigate(Destinations.MainGroup.Settings.route) },
                    onLogout = {
                        navHostController.popBackStack()
                    })
            }
            composable(
                "${Destinations.MainGroup.Recipe.route}/{id}",
                listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                if (id != null) {
                    RecipeScreen(id = id, onClickBack = { navController.popBackStack() })
                }
            }
            composable(Destinations.MainGroup.NewRecipe.route) {
                NewRecipeScreen(onClickBack = { navController.popBackStack() })
            }
            composable(Destinations.MainGroup.AddedRecipes.route) {
                AddedRecipesScreen(onClickBack = { navController.popBackStack() },
                    onRecipeClick = { id -> navController.navigate("${Destinations.MainGroup.Recipe.route}/${id}") },
                    onEditClick = { id -> navController.navigate("${Destinations.MainGroup.EditRecipe.route}/${id}") })
            }
            composable(Destinations.MainGroup.Settings.route) {
                SettingsScreen(onThemeUpdated = onThemeUpdated,
                    onClickBack = { navController.popBackStack() })
            }
            composable(
                "${Destinations.MainGroup.EditRecipe.route}/{id}",
                listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                if (id != null) {
                    EditRecipeScreen(id = id, onClickBack = { navController.popBackStack() })
                }
            }
        }
    })

}