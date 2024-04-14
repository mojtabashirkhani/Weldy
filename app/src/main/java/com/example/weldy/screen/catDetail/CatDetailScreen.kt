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
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import coil.size.Scale
import com.example.weldy.data.local.model.WeldyEntity
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.extensions.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CatDetail(navController: NavHostController, item: String) {
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
        Button(onClick = {
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    catDetailVM.insertCatToFavourite(WeldyEntity(catItem?.id ?: "", catItem?.url, catItem?.width, catItem?.height))
                }
            }
        }) {
            Text("Add to Favourite")
        }

        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data("${catItem?.url}")
                .crossfade(true)
                .scale(Scale.FILL)
                .build(),
            contentDescription = "",
            modifier = Modifier.size(catItem?.width?.dp ?: 0.dp, catItem?.height?.dp ?: 0.dp),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.stat_notify_error),
            placeholder = painterResource(id = R.drawable.presence_away)
        )


    }
}