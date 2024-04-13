package com.example.weldy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.weldy.fragment.catList.CatInfoList
import com.example.weldy.fragment.catList.CatListFragmentVM
import com.example.weldy.ui.theme.WeldyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
                    MaterialTheme {
                        CatInfoList(modifier = Modifier.fillMaxWidth(), mViewModel.user,context)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeldyTheme {
        Greeting("Android")
    }
}

@Composable
fun MyApp() {


}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    TestYputubeApiTheme {
        MyApp()
    }
}*/
