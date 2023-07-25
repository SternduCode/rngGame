package com.rngGame.demonUniverse

import android.graphics.Bitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Image(val bitmaps: Array<Bitmap>, var frameRate: Int = 7,
                 private var _width: Int = -1,
                 private var _height: Int = -1
) {

    val width
        get() = _width
    val height
        get() = _height

    private var _isDone = true

    val isDone
        get() = _isDone

    fun setSize(width: Int = _width, height: Int = _height) {
        _width = width
        _height = height
        rescale()
    }

    private var index: Int = 0
    private var lastFrameTime: Long = 0
    private var state: MutableStateFlow<Bitmap> = MutableStateFlow(bitmaps[0])
    private val originalBitmaps: Array<Bitmap> = bitmaps.copyOf()

    init {
       rescale()
    }

    private fun rescale() {
        println("Start")
        _isDone = false
        if ((width != -1) || (height != -1)) {
            val jobList = ArrayList<Job>()
            for (i in originalBitmaps.indices) {
                val bitmap = originalBitmaps[i]
                val coroutineScope = CoroutineScope(Dispatchers.Default)
                val job = coroutineScope.launch {
                    bitmaps[i] = resizeImage(bitmap, bitmap.width, bitmap.height,
                        if (width != -1) width else bitmap.width,
                        if (height != -1) height else bitmap.height
                    )
                }
                jobList.add(job)
            }
            Thread {
                runBlocking { jobList.joinAll() }
                _isDone = true
            }.start()
        }
        println("End")
    }

    fun update() {
        val time = System.currentTimeMillis()
        if (time - lastFrameTime > 1000 / frameRate) {
            lastFrameTime = time
            if (index + 1 < bitmaps.size) {
                index++
            } else index = 0
            runBlocking {
                state.emit(bitmaps[index])
            }
            println(index)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (!bitmaps.contentEquals(other.bitmaps)) return false
        if (frameRate != other.frameRate) return false
        if (index != other.index) return false
        if (lastFrameTime != other.lastFrameTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bitmaps.contentHashCode()
        result = 31 * result + frameRate
        result = 31 * result + index
        result = 31 * result + lastFrameTime.hashCode()
        return result
    }

    fun getCurrentFrame() = state

}
