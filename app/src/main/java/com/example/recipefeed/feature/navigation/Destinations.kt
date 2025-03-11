package com.example.recipefeed.feature.navigation

sealed class Destinations(val route: String) {
    object LoginGroup : Destinations("LOGIN_GROUP") {
        object Login : Destinations("LOGIN")
        object SignUp : Destinations("SIGNUP")
    }

    object MainGroup : Destinations("MAIN_GROUP") {
        object RecipesOnApprove : Destinations("Recipes_On_Approve")
        object Main : Destinations("MAIN")
        object Search : Destinations("SEARCH")
        object Recipe : Destinations("RECIPE")
        object Favorite : Destinations("FAVORITE")
        object Account : Destinations("ACCOUNT")
        object NewRecipe : Destinations("NEW_RECIPE")
        object AddedRecipes : Destinations("ADDED_RECIPES")
        object EditRecipe : Destinations("EDIT_RECIPE")
        object Settings : Destinations("SETTINGS")
    }

    object ServerNotAvailable : Destinations("SERVER_NOT_AVAILABLE")
}
