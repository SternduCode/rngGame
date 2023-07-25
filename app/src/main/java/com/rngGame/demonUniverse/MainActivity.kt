package com.rngGame.demonUniverse

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rngGame.demonUniverse.ui.theme.DemonUniverseTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import java.nio.IntBuffer
import kotlin.math.floor


class MainActivity : ComponentActivity() {

    val widthFlow = MutableStateFlow(0)
    val heightFlow = MutableStateFlow(0)

    var width = 0
    var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        actionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (SDK_INT < Build.VERSION_CODES.R) {
            window.decorView.apply {
                // Hide both the navigation bar and the status bar.
                // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
                // a general rule, you should design your app to hide the status bar whenever you
                // hide the navigation bar.
                systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.systemBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
        val context: Context = this

        setContent {
            val appViewModel: ViewModel = viewModel()
            DemonUniverseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val config = LocalConfiguration.current
                    width = with(LocalDensity.current) { config.screenWidthDp.dp.roundToPx() }
                    height = with(LocalDensity.current) { config.screenHeightDp.dp.roundToPx() }
                    val widthFromFlow by widthFlow.collectAsState()
                    val heightFromFlow by heightFlow.collectAsState()
                    if (widthFromFlow != width) runBlocking { widthFlow.emit(width) }
                    else if (heightFromFlow != height) runBlocking { heightFlow.emit(height) }
                    else {
                        if (!appViewModel.finishedTutorial) {

                        }
                        TitleScreen(appViewModel)
                        GamePanel(appViewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun GamePanel(appViewModel: ViewModel) {

    }

    @Composable
    fun TitleScreen(appViewModel: ViewModel) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(19,16,43)
        ) {
            println("TitleScreen")
            if (!appViewModel.imageIsLoaded("titleScreen")) {
                appViewModel.loadGif(
                    resources.openRawResource(R.drawable.backgrounds_main_bg),
                    width,
                    height,
                    "titleScreen"
                )
                appViewModel.setFrameRate(30, "titleScreen")
            }
            val bitmap by appViewModel.getImage("titleScreen")!!.collectAsState()
            if ((bitmap.width != width || bitmap.height != height) && appViewModel.isDone("titleScreen") == true) {
                appViewModel.setSize(width, height, "titleScreen")
            }
            if (appViewModel.isDone("titleScreen") == true) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                val decoder = GifFileDecoder(resources.openRawResource(R.drawable.backgrounds_main_bg))
                decoder.start()
                val pixels = decoder.readFrame()
                val firstFrame = Bitmap.createBitmap(
                    decoder.width, decoder.height,
                    Bitmap.Config.ARGB_8888
                )
                firstFrame.copyPixelsFromBuffer(IntBuffer.wrap(pixels))
                Image(
                    bitmap = firstFrame.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

//@Composable
//fun GifImage(
//    id: Int,
//    modifier: Modifier = Modifier,
//) {
//    val context = LocalContext.current
//    val imageLoader = ImageLoader.Builder(context)
//        .components {
//            if (SDK_INT >= 28) {
//                add(ImageDecoderDecoder.Factory())
//            } else {
//                add(GifDecoder.Factory())
//            }
//        }
//        .build()
//    Image(
//        painter = rememberAsyncImagePainter(
//            ImageRequest.Builder(context).data(data = id).allowConversionToBitmap(true).build(), imageLoader = imageLoader
//        ),
//
//        contentScale = ContentScale.FillBounds,
//        contentDescription = null,
//        alignment = Alignment.CenterStart,
//        modifier = modifier.fillMaxSize(),
//    )
//}

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
    DemonUniverseTheme {
        Greeting("Android")
    }
}

fun resizeImage(input: Bitmap, w1: Int, h1: Int, w2: Int, h2: Int, flip: Boolean = false): Bitmap {
    val wi = Bitmap.createBitmap(
        w2, h2,
        Bitmap.Config.ARGB_8888
    )
    val xRatio = w1 / w2.toDouble()
    val yRatio = h1 / h2.toDouble()
    var px: Int
    var py: Int
    for (i in 0 until h2) for (j in 0 until w2) {
        px = floor(j * xRatio).toInt()
        py = floor(i * yRatio).toInt()
        if (flip) wi[w2 - 1 - j, i] = input[px, py] else wi[j, i] = input[px, py]
    }
    return wi
}
