package com.example.weldy.screen.catDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import android.R
import com.example.weldy.data.CatResponse
import com.example.weldy.extensions.fromJson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CatDetail(navController: NavHostController, item: String) {
    val catItem = item.fromJson<CatResponse>()
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
                .data("${catItem?.url}")
                .crossfade(true)
                .build(),
            contentDescription = "",
            modifier = Modifier.size(catItem?.width?.dp ?: 0.dp, catItem?.height?.dp ?: 0.dp),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.stat_notify_error),
            placeholder = painterResource(id = R.drawable.presence_away)
        )


    }
}