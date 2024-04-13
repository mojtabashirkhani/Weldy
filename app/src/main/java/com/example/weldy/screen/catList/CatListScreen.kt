package com.example.weldy.screen.catList

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weldy.data.CatResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CatInfoList(modifier: Modifier, catList: Flow<PagingData<CatResponse>>, context: Context, navController: NavHostController) {
    val catListItems: LazyPagingItems<CatResponse> = catList.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        ) {
        items(catListItems.itemCount){ item ->
            ListViewItem(item = catListItems[item]!!, onItemClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        val encodedUrl = URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())
                        withContext(Dispatchers.Main) {
                            val itemAsJsonString = Gson().toJson(CatResponse(it.id, encodedUrl, it.width, it.height))
                            navController.navigate("details/${itemAsJsonString}")
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
                    //You can use modifier to show error message
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
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data("${item.url}")
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier.size(item.width?.dp ?: 0.dp, item.height?.dp ?: 0.dp),
            contentScale = ContentScale.Crop,
            error = painterResource(id = android.R.drawable.stat_notify_error),
            placeholder = painterResource(id = android.R.drawable.presence_away)
        )


    }
}