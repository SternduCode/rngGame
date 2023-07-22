package com.rngGame.demonUniverse

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import java.io.InputStream
import java.nio.IntBuffer

class ViewModel : ViewModel() {

    init {
        Thread {
            while(true) {
                images.forEach { (_, image) ->
                    image.update()
                }
            }
        }.start()
    }

    private val images: MutableMap<String, Image> = HashMap()

    fun loadGif(stream: InputStream, width: Int, height: Int, identifier: String) {
        val decoder = GifFileDecoder(stream)
        decoder.start()
        val bitmaps = ArrayList<Bitmap>()
        while(decoder.hasFrame()) {
            println(bitmaps.size)
            val pixels = decoder.readFrame() ?: break
            val bitmap = Bitmap.createBitmap(
                decoder.width, decoder.height,
                Bitmap.Config.ARGB_8888
            )
            bitmap.copyPixelsFromBuffer(IntBuffer.wrap(pixels))
            //val bitmap2 = resizeImage(bitmap, bitmap.width, bitmap.height, width, height)
            bitmaps.add(bitmap)
        }
        val bitmapsArray = bitmaps.toTypedArray()
        Thread {
            for (i in bitmapsArray.indices) {
                bitmapsArray[i] = resizeImage(bitmapsArray[i], bitmapsArray[i].width, bitmapsArray[i].height, width, height)
            }
        }.start()
        images[identifier] = Image(bitmapsArray)
    }

    fun setFrameRate(frameRate: Int, identifier: String): Boolean {
        return if (images.containsKey(identifier)) {
            images[identifier]!!.frameRate = frameRate
            true
        } else false
    }

    fun getImage(identifier: String): StateFlow<Bitmap>? {
        return if (images.containsKey(identifier)) {
            images[identifier]!!.getCurrentFrame()
        } else null
    }

    fun imageIsLoaded(identifier: String) = images.containsKey(identifier)

    private var _finishedTutorial: Boolean = false

    val finishedTutorial: Boolean
        get() = _finishedTutorial

    private var _titleScreenFrame: Int = 0

    val titleScreenFrame: Int
        get() = _titleScreenFrame
}