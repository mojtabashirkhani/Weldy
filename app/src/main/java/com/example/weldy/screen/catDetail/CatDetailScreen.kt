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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weldy.data.local.model.CatEntity
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.extensions.fromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CatDetail(item: String, isVisible: Boolean) {
    val catDetailVM: CatDetailVM = hiltViewModel()
    val catItem = item.fromJson<CatResponse>()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (isVisible) {
            Button(onClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        catDetailVM.insertCatToFavourite(CatEntity(catItem?.id ?: "", catItem?.url, catItem?.width, catItem?.height))
                    }
                }
            }) {
                Text("Add to Favourite")
            }
        }

        Card() {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data("${catItem?.url}")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier.size((LocalConfiguration.current.screenWidthDp).dp, (LocalConfiguration.current.screenHeightDp).dp),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.stat_notify_error),
                placeholder = painterResource(id = R.drawable.presence_away)
            )
        }



    }
}