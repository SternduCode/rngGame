package rngGame.main

import javafx.animation.*
import javafx.application.Platform
import javafx.geometry.Point2D
import javafx.util.Duration
import rngGame.main.MainClass.Companion.isStopping
import rngGame.visual.*
import rngGame.visual.GamePanel
import rngGame.visual.VisualRoot.updateLogic
import rngGame.visual.VisualRoot.updateUI
import java.util.concurrent.atomic.AtomicReference

object WindowManager {

	private val animatedImages = ArrayList<AnimatedImage>()
	private val animatedTexts = ArrayList<AnimatedText>()

	private var gamePanel: GamePanel? = null

	val targetFPS: Int = 120

	var scalingFactorX: Double = 1.0
		private set

	var scalingFactorY: Double = 1.0
		private set

	const val blockSize: Int = 128

	const val xBlocks = 20

	const val yBlocks = 11

	var blockSizeX: Int = blockSize
		private set

	var blockSizeY: Int = blockSize
		private set

	var gameWidth: Int = 1920 //blockSizeX * xBlocks;
		private set

	var gameHeight: Int = 1080 //blockSizeY * yBlocks;
		private set

	var screenCenter = Point2D(gameWidth/2.0, gameHeight/2.0)
		private set

	fun setGamePanel(gamePanel: GamePanel) {
		this.gamePanel = gamePanel
	}

	fun addAnimatedImage(animatedImage: AnimatedImage) {
		animatedImages.add(animatedImage)
	}

	fun changeScalingFactor(scaleFactorX: Double, scaleFactorY: Double) {
		scalingFactorX = scaleFactorX
		scalingFactorY = scaleFactorY
		blockSizeX = (blockSize * scaleFactorX).toInt()
		blockSizeY = (blockSize * scaleFactorY).toInt()
		gameWidth = blockSizeX * xBlocks
		gameHeight = blockSizeY * yBlocks

		screenCenter = Point2D(gameWidth/2.0, gameHeight/2.0)

		animatedImages.forEach(AnimatedImage::scaleF11)
		animatedTexts.forEach(AnimatedText::scaleF11)
	}

	fun startLogicThread() {
		val runnable = AtomicReference<Runnable>()
		val arTl = AtomicReference<Timeline>()
		val tl = Timeline(
			KeyFrame(
				Duration.millis((1000 / targetFPS).toDouble()),
				{ _ ->
					update()
					if ("true" == System.getProperty("alternateUpdate")) {
						arTl.get().stop()
						Platform.runLater(runnable.get())
					}
				}
			)
		).apply {
			arTl.set(this)
			cycleCount = Animation.INDEFINITE
		}
		val r = Runnable {
			update()
			if (!isStopping && "true" == System.getProperty("alternateUpdate")) Platform.runLater(runnable.get())
			else arTl.get().play()
		}.apply {
			runnable.set(this)
		}

		if ("false" == System.getProperty("alternateUpdate")) tl.play()
		else Platform.runLater(r)
	}

	fun update() {
		animatedImages.forEach(AnimatedImage::updateUI)
		animatedTexts.forEach(AnimatedText::updateUI)

		gamePanel?.update()

		updateUI()

		updateLogic()
	}

	fun removeAnimatedImage(animatedImage: AnimatedImage) {
		animatedImages.remove(animatedImage)
	}

	fun addAnimatedText(animatedText: AnimatedText) {
		animatedTexts.add(animatedText)
	}

	fun removeAnimatedText(animatedText: AnimatedText) {
		animatedTexts.remove(animatedText)
	}
}
