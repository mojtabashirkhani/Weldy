package com.example.weldy.fragment.catList

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weldy.data.CatResponse
import kotlinx.coroutines.flow.Flow

@Composable
fun CatInfoList(modifier: Modifier, catList: Flow<PagingData<CatResponse>>, context: Context) {
    val catListItems: LazyPagingItems<CatResponse> = catList.collectAsLazyPagingItems()

    LazyColumn {
        items(catListItems.itemCount){ item ->
            ListViewItem(item = catListItems[item]!!)
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
fun ListViewItem(item: CatResponse) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
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