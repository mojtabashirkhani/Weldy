package com.example.weldy.screen.catList

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.navigation.NavigationItem
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CatInfoList(modifier: Modifier, navController: NavHostController) {
    val catListVM: CatListVM = hiltViewModel()
    val catList = catListVM.cats
    val catListItems: LazyPagingItems<CatResponse> = catList.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Spotlight", modifier = Modifier
                .padding(8.dp), style = MaterialTheme.typography.titleLarge
        )

        Button(onClick = {
            navController.navigate(NavigationItem.Bookmark.route)
        }) {
            Text("Bookmark")

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier.padding(horizontal = 4.dp),
        ) {
            items(catListItems.itemCount) { item ->
                ListViewItem(item = catListItems[item]!!, onItemClick = {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            val encodedUrl =
                                URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())
                            withContext(Dispatchers.Main) {

                                val itemAsJsonString = Gson().toJson(
                                    CatResponse(
                                        it.id,
                                        encodedUrl,
                                        it.width,
                                        it.height
                                    )
                                )
                                navController.navigate("${NavigationItem.Detail.route}?catItem=${itemAsJsonString}&isFavouriteVisible=${true}")
                            }
                        }
                    }

                })
            }
            catListItems.apply {

                when {
                    loadState.refresh is LoadState.Loading -> {
                        //You can add modifier to manage load state when first time response page is loading
                    }

                    loadState.append is LoadState.Loading -> {
                        //You can add modifier to manage load state when next response page is loading

                    }

                    loadState.append is LoadState.Error -> {
                        Toast.makeText(
                            context,
                            (loadState.refresh as LoadState.Error).error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    loadState.refresh is LoadState.Error -> {
                        Toast.makeText(
                            context,
                            (loadState.refresh as LoadState.Error).error.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                }
            }
        }

    }

}


@Composable
fun ListViewItem(item: CatResponse, onItemClick: (CatResponse) -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Card() {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data("${item.url}")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier.size(
                    (LocalConfiguration.current.screenWidthDp * 0.3f).dp,
                    (LocalConfiguration.current.screenWidthDp * 0.4f).dp
                ),
                contentScale = ContentScale.Crop,
                error = painterResource(id = android.R.drawable.stat_notify_error),
                placeholder = painterResource(id = android.R.drawable.presence_away)
            )
        }

    }
}

