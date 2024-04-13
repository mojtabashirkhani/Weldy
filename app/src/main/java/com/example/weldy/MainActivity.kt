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
import com.example.weldy.data.CatResponse
import com.example.weldy.screen.catDetail.CatDetail
import com.example.weldy.screen.catList.CatInfoList
import com.example.weldy.screen.catList.CatListFragmentVM
import com.example.weldy.ui.theme.WeldyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mViewModel by lazy {
        ViewModelProvider(this)[CatListFragmentVM::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeldyTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            CatInfoList(
                                modifier = Modifier.fillMaxWidth(),
                                mViewModel.user,
                                context,
                                navController
                            )
                        }
                        composable(
                            route = "details/{catItem}",
                            arguments = listOf(navArgument("catItem") { type = NavType.StringType })
                        ) { backStackEntry ->
                            CatDetail(
                                navController,
                                backStackEntry.arguments?.getString("catItem") ?: ""
                            )
                        }
                    }

                }


            }
        }
    }
}

