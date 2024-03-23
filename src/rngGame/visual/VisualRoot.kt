package rngGame.visual

import javafx.animation.*
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.layout.Pane
import javafx.util.Duration
import rngGame.main.*
import rngGame.main.GamePanel
import java.awt.MouseInfo

object VisualRoot: Pane() {

	val cursor_ = AnimatedImage("./res/gui/always/Cursor.png")

	var gamePanel: GamePanel? = null
		private set

	private val titleScreen = TitleScreen()

	init {
		WindowManager.startLogicThread()

		cursor = Cursor.NONE
		children.addAll(
			titleScreen,
			LoadingScreen,
			cursor_
		)
		val scale = ScaleTransition(Duration.millis(500.0), cursor_).apply {
			cycleCount = -1
			fromX = 1.0
			fromY = 1.0
			toX = 2.0
			toY = 2.0
			isAutoReverse = true
			playFromStart()
		}

		Timeline(
			scale.targetFramerate,
			KeyFrame(Duration.millis(2000.0), KeyValue(cursor_.rotateProperty(), 360.0, Interpolator.LINEAR))
		).apply {
			cycleCount = -1
			playFromStart()
		}
	}


	fun updateLogic() {
		val (x, y) = MouseInfo.getPointerInfo().location
		val scenePosition = Point2D(x, y).subtract(localToScreen(Point2D(0.0, 0.0)))
		cursor_.setPosition(scenePosition.x - cursor_.width / 2, scenePosition.y - cursor_.height / 2)
		titleScreen.updateLogic()
		gamePanel?.updateLogic()
	}

	fun scaleF11() {
		titleScreen.scaleF11()
		gamePanel?.scaleF11()
	}

	fun updateUI() {
		titleScreen.updateUI()
		gamePanel?.updateUI()
	}

}