@file:JvmName("LoadingScreen")
package rngGame.main

import javafx.animation.FadeTransition
import javafx.util.Duration
import rngGame.visual.AnimatedImage

object LoadingScreen: AnimatedImage() {

	init {
		init("./res/gui/Loadingscreen.gif")
		isDisable = true
		opacity = 0.0
		scaleF11()
	}

	override fun scaleF11() {
		imgRequestedWidth = WindowManager.gameWidth
		imgRequestedHeight = WindowManager.gameHeight
		super.scaleF11()
	}

	fun isInLoadingScreen(): Boolean {
		return opacity > .5
	}

	fun goIntoLoadingScreen() {
		if (!isInLoadingScreen()) {
			fitWidth = image.width * WindowManager.scalingFactorX
			fitHeight = image.height * WindowManager.scalingFactorY
			val ft = FadeTransition(Duration.millis(250.0), this)
			ft.fromValue = 0.0
			ft.toValue = 1.0
			ft.play()
		}
	}

	fun goOutOfLoadingScreen() {
		if (isInLoadingScreen()) {
			fitWidth = image.width * WindowManager.scalingFactorX
			fitHeight = image.height * WindowManager.scalingFactorY
			val ft = FadeTransition(Duration.millis(250.0), this)
			ft.fromValue = 1.0
			ft.toValue = 0.0
			ft.play()
		}
	}

}