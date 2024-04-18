package com.example.weldy.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weldy.screen.catBookmark.CatBookmarkList
import com.example.weldy.screen.catDetail.CatDetail
import com.example.weldy.screen.catList.CatInfoList

enum class Screen {
    HOME,
    DETAIL,
    BOOKMARK
}
sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Detail : NavigationItem(Screen.DETAIL.name)
    object Bookmark: NavigationItem(Screen.BOOKMARK.name)
}

@Composable
fun AppNavHost(modifier: Modifier = Modifier,
               navController: NavHostController,
               startDestination: String = NavigationItem.Home.route,) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(startDestination) {
            CatInfoList(
                modifier = Modifier.fillMaxWidth(),
                navController
            )
        }
        composable(
            route = "${NavigationItem.Detail.route}?catItem={catItem}&isFavouriteVisible={isFavouriteVisible}",
            arguments = listOf(
                navArgument("catItem") { type = NavType.StringType },
                navArgument("isFavouriteVisible") { type = NavType.BoolType })
        ) { backStackEntry ->
            CatDetail(
                backStackEntry.arguments?.getString("catItem") ?: "",
                backStackEntry.arguments?.getBoolean("isFavouriteVisible") ?: false
            )
        }
        composable(NavigationItem.Bookmark.route) {
            CatBookmarkList(
                modifier = Modifier.fillMaxWidth(),
                navController
            )
        }

    }
}