package com.example.weldy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weldy.screen.catBookmark.CatBookmarkList
import com.example.weldy.screen.catBookmark.CatBookmarkVM
import com.example.weldy.screen.catDetail.CatDetail
import com.example.weldy.screen.catDetail.CatDetailVM
import com.example.weldy.screen.catList.CatInfoList
import com.example.weldy.screen.catList.CatListVM
import com.example.weldy.ui.theme.WeldyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeldyTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            CatInfoList(
                                modifier = Modifier.fillMaxWidth(),
                                navController
                            )
                        }
                        composable(
                            route = "details?catItem={catItem}&isFavouriteVisible={isFavouriteVisible}",
                            arguments = listOf(
                                navArgument("catItem") { type = NavType.StringType },
                                navArgument("isFavouriteVisible") {type = NavType.BoolType})
                        ) { backStackEntry ->
                            CatDetail(
                                backStackEntry.arguments?.getString("catItem") ?: "",
                                backStackEntry.arguments?.getBoolean("isFavouriteVisible") ?: false
                            )
                        }
                        composable("favourite") {
                            CatBookmarkList(
                                modifier = Modifier.fillMaxWidth(),
                                navController
                            )
                        }
                    }

                }


            }
        }
    }
}

