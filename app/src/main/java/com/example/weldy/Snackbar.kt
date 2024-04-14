import androidx.compose.foundation.background
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun SnackbarWithoutScaffold(
    message: String,
    showSb: Boolean,
    openSnackbar: (Boolean) -> Unit
) {

    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    SnackbarHost(
        modifier = Modifier,
        hostState = snackState
    ){
        Snackbar(
            snackbarData = it,
            containerColor = Color.White,
            contentColor = Color.Blue
        )
    }


    if (showSb){
        LaunchedEffect(Unit) {
            snackScope.launch { snackState.showSnackbar(message) }
            openSnackbar(false)
        }

    }


}