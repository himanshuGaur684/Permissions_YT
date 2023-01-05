package gaur.himanshu.permissions_yt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.*
import gaur.himanshu.permissions_yt.ui.theme.Permissions_YTTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Permissions_YTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MultiplePermissionExample()
                }
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionExample() {

    val permissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {

        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchPermissionRequest()
                }
                else -> {

                }
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (permissionState.status.isGranted) {
            Text(text = "Permission Granted")
        } else {
            val text = if (permissionState.status.shouldShowRationale) {
                "Permission Required"
            } else {
                "Please provide Permission"
            }
            Text(text = text)
        }

    }


}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MultiplePermissionExample() {

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {

        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchMultiplePermissionRequest()
                }
                else -> {

                }
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        permissionState.permissions.forEach {

            when(it.permission){
                android.Manifest.permission.CAMERA->{
                    if (it.status.isGranted) {
                        Text(text = "Permission Granted Camera")
                    } else {
                        val text = if (it.status.shouldShowRationale) {
                            "Permission Camera Required"
                        } else {
                            "Please provide Camera Permission"
                        }
                        Text(text = text)
                    }
                }
                android.Manifest.permission.ACCESS_COARSE_LOCATION->{
                    if (it.status.isGranted) {
                        Text(text = "Permission Granted Location")
                    } else {
                        val text = if (it.status.shouldShowRationale) {
                            "Permission Location Required"
                        } else {
                            "Please provide Location Permission"
                        }
                        Text(text = text)
                    }
                }
            }

        }


    }


}





