package com.rngGame.demonUniverse

import android.graphics.Bitmap
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

    fun loadGif(stream: InputStream, width: Int = -1, height: Int = -1, identifier: String) {
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
            bitmaps.add(bitmap)
        }
        val bitmapsArray = bitmaps.toTypedArray()
        images[identifier] = Image(bitmaps = bitmapsArray, _width = width, _height = height)
    }

    fun setFrameRate(frameRate: Int, identifier: String): Boolean {
        return if (images.containsKey(identifier)) {
            images[identifier]!!.frameRate = frameRate
            true
        } else false
    }

    fun setSize(width: Int = -1, height: Int = -1, identifier: String): Boolean {
        return if (images.containsKey(identifier)) {
            images[identifier]!!.setSize(
                if (width != -1) width else images[identifier]!!.width,
                if (height != -1) height else images[identifier]!!.height
            )
            true
        } else false
    }

    fun getImage(identifier: String): StateFlow<Bitmap>? {
        return if (images.containsKey(identifier)) {
            images[identifier]!!.getCurrentFrame()
        } else null
    }

    fun imageIsLoaded(identifier: String) = images.containsKey(identifier)

    fun isDone(identifier: String): Boolean? {
        return if (images.containsKey(identifier)) {
            images[identifier]!!.isDone
        } else null
    }

    private var _finishedTutorial: Boolean = false

    val finishedTutorial: Boolean
        get() = _finishedTutorial

}