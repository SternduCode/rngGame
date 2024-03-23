package rngGame.main

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import rngGame.main.LoadingScreen.goIntoLoadingScreen
import rngGame.main.LoadingScreen.goOutOfLoadingScreen
import rngGame.main.WindowManager.gameHeight
import rngGame.main.WindowManager.gameWidth
import rngGame.main.WindowManager.removeAnimatedImage
import rngGame.tile.ImgUtil
import rngGame.ui.Button
import rngGame.ui.SoundHandler
import rngGame.visual.AnimatedImage
import rngGame.visual.VisualRoot
import kotlin.system.exitProcess

class TitleScreen : Pane() {

	private var destroy = false

	private val iv = ImageView().also {
		children.add(it)
	}

	private var last = 0L

	private var frames: Array<Image> = emptyArray()

	private var currFrame = 0

	private var index = 0

	private var ploy = Button("./res/backgrounds/Ploy.png").apply {
		val (origWidth, origHeight) = ImgUtil.getoriginalSize(path)
		setImgRequestedSize((origWidth * 2).toInt(), (origHeight * 2).toInt())
		scaleF11()
		setPosition(gameWidth / 2.0 - width / 2.0, gameHeight * .85)
		onMousePressed = EventHandler { _ -> init("./res/backgrounds/Ploy2.png") }
		onMouseReleased = EventHandler { _ ->
			SoundHandler.getInstance().makeSound("click.wav")
			goIntoLoadingScreen()
			VisualRoot.startGamePanel()
			children.clear()
			destroy()
			Input.getInstance().isBlockInputs = false
			Thread {
				try {
					Thread.sleep(200)
				} catch (e1: InterruptedException) {
					e1.printStackTrace()
				}
				Platform.runLater {
					init("./res/backgrounds/Ploy.png")
					isVisible = false
					iv.isVisible = false
					settins.isVisible = false
					clous.isVisible = false
				}
				try {
					Thread.sleep(2000)
					goOutOfLoadingScreen()
				} catch (e1: InterruptedException) {
					e1.printStackTrace()
				}
			}.start()
		}
	}.also {
		children.add(it)
	}
	private var settins = Button("./res/backgrounds/Settins.png").apply {
		val (origWidth, origHeight) = ImgUtil.getoriginalSize(path)
		setImgRequestedSize((origWidth * 1.5).toInt(), (origHeight * 1.5).toInt())
		scaleF11()
		setPosition(gameWidth * .01, gameHeight * .885)
	}.also {
		children.add(it)
	}
	private var clous = Button("./res/backgrounds/Clous.png").apply {
		val clousSize = ImgUtil.getoriginalSize(path)
		setImgRequestedSize((clousSize.x * 1.5).toInt(), (clousSize.y * 1.5).toInt())
		scaleF11()
		setPosition(gameWidth * .99 - width, gameHeight * .885)
		setOnPressed { _ -> init("./res/backgrounds/Clous2.png") }
		setOnReleased { _ ->
			init("./res/backgrounds/Clous.png")
			exitProcess(0)
		}
	}.also {
		children.add(it)
	}
	private var pfail = Button("./res/backgrounds/Pfail.png").apply {
		setPosition(gameWidth - width * 1.1, gameHeight * .01)
		isVisible = false
		settins.setOnPressed { _ ->
			settins.init("./res/backgrounds/Settins2.png")
		}
		settins.setOnReleased { _ ->
			settins.init("./res/backgrounds/Settins.png")
			ploy.isVisible = false
			clous.isVisible = false
			settins.isVisible = false
			isVisible = true
			setOnPressed { _ ->
				init("./res/backgrounds/Pfail2.png")
			}
			setOnReleased { _ ->
				init("./res/backgrounds/Pfail.png")
				isVisible = false
				ploy.isVisible = true
				clous.isVisible = true
				settins.isVisible = true
				settins.init("./res/backgrounds/Settins.png")
			}
		}
	}.also {
		children.add(it)
	}

	private var storyView = AnimatedImage("./res/story/Story0.gif", 7).apply {
		onMouseReleased = EventHandler { _ ->
			SoundHandler.getInstance().makeSound("click.wav")
			if (index < 6) {
				index++
				init("./res/story/Story$index.gif")
			} else {
				isVisible = false
			}
		}
	}.also {
		children.add(it)
	}

	fun updateLogic() {
		if (!destroy) {
			storyView.updateUI()
		}
	}

	fun updateUI() {
		if (!destroy && (frames.isNotEmpty())) {
			val t = System.currentTimeMillis()
			if (t - last > 1000 / 30) {
				iv.image = frames[currFrame]
				currFrame++
				if (currFrame >= frames.size) currFrame = 0
				last = t
			}
		}
	}

	private fun destroy() {
		destroy = true
		frames = emptyArray()
		iv.image = null
		removeAnimatedImage(storyView)
		storyView.uninit()
		ploy.uninit()
		settins.uninit()
		clous.uninit()
		pfail.uninit()
	}

	fun scaleF11() {
		if (!destroy) {
			frames = ImgUtil.getScaledImages("./res/backgrounds/Main BG.gif", gameWidth, gameHeight)
			iv.image = frames[0]
			Input.getInstance().isBlockInputs = true
		}
	}
}
