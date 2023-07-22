package com.rngGame.demonUniverse

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

data class Image(val bitmaps: Array<Bitmap>, var frameRate: Int = 7) {

    private var index: Int = 0
    private var lastFrameTime: Long = 0
    private var state: MutableStateFlow<Bitmap> = MutableStateFlow(bitmaps[0])

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
