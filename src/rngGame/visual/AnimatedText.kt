package rngGame.visual

import javafx.scene.image.*
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import rngGame.main.Text
import rngGame.main.WindowManager

class AnimatedText(val text: String, val fontHeight: Int, val showOneByOne: Boolean = false, textColor: Color = Color.WHITE): Pane() {

	private val textColor: Color = textColor

	private lateinit var img: WritableImage

	private var iv: ImageView? = null

	init {
		WindowManager.addAnimatedText(this)
		scaleF11()
		updateUI()
	}

	fun scaleF11() {
		img = Text.getInstance().convertText(text, fontHeight) as WritableImage
		if (textColor !== Color.WHITE) {
			println(textColor.blue.toString() + " " + textColor.red + " " + textColor.green)
			val pr = img.pixelReader
			var `val` = 0
			var ii = 0
			var jj = 0
			while (`val` == 0 || `val` == -16777216) {
				`val` = pr.getArgb(ii, jj)
				ii++
				if (ii >= img.width) {
					jj++
					ii = 0
				}
			}
			println((`val` shr 16).toByte())
			println((`val` shr 8).toByte())
			println(`val`.toByte())
			println(`val`)
			val pw = img.pixelWriter
			var i = 0
			while (i < img.width) {
				var j = 0
				while (j < img.height) {
					`val` = pr.getArgb(i, j)
					`val` = ((Math.round(java.lang.Byte.toUnsignedInt((`val` shr 24).toByte()) * textColor.opacity) shl 24)
							+ (Math.round(java.lang.Byte.toUnsignedInt((`val` shr 16).toByte()) * textColor.red) shl 16)
							+ (Math.round(java.lang.Byte.toUnsignedInt((`val` shr 8).toByte()) * textColor.green) shl 8)
							+ Math.round(java.lang.Byte.toUnsignedInt(`val`.toByte()) * textColor.blue)).toInt()
					pw.setArgb(i, j, `val`)
					j++
				}
				i++
			}
		}
	}

	fun getImgHeight(): Double {
		return img.height
	}

	fun getImgWidth(): Double {
		return img.width
	}

	/**
	 * Methode gibt dir die RGB werte von deinem HEX code wieder um den dann als Farbe (für Schrift) benutzen zu können
	 * wennde aufrufst einfach ein hex code übergeben, ganzer hex auch mit dem # | BSP: #4f9abd.
	 *
	 * @param hexc  the given hexcode
	 * @return the RGB from hex
	 */
	fun getRGBFromHex(hexc: String): Int {
		val rr: String = hexc.substring(1, 3)
		val gg: String = hexc.substring(3, 5)
		val bb: String = hexc.substring(5, 7)
		val valr = rr.toInt(16)
		val valg = gg.toInt(16)
		val valb = bb.toInt(16)
		return valr + valg + valb
	}

	fun updateUI() {
		if (iv == null) {
			iv = ImageView()
			children.add(iv)
		}
		iv!!.image = img
	}
}