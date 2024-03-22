package rngGame.visual

import javafx.animation.*
import javafx.geometry.Point2D
import javafx.scene.layout.Pane
import javafx.util.Duration
import rngGame.main.*
import java.awt.MouseInfo

object VisualRoot: Pane() {

	val cursor_ = AnimatedImage("./res/gui/always/Cursor.png")

	private val titleScreen = TitleScreen()

	init {
		WindowManager.getInstance().startLogicThread()

		//setCursor(Cursor.NONE);
		children.addAll(
			titleScreen,
			LoadingScreen,
			cursor_
		)
		val scale = ScaleTransition(Duration.millis(500.0), cursor_)
		scale.cycleCount = -1
		scale.fromX = 1.0
		scale.fromY = 1.0
		scale.toX = 2.0
		scale.toY = 2.0
		scale.isAutoReverse = true
		scale.playFromStart()

		val timeline = Timeline(
			scale.targetFramerate,
			KeyFrame(Duration.millis(2000.0), KeyValue(cursor_.rotateProperty(), 360.0, Interpolator.LINEAR))
		)
		timeline.cycleCount = -1
		timeline.playFromStart()
	}


	fun updateLogic() {
		val (x, y) = MouseInfo.getPointerInfo().location
		val scenePosition = Point2D(x, y).subtract(localToScreen(Point2D(0.0, 0.0)))
		cursor_.setPosition(scenePosition.x - cursor_.width / 2, scenePosition.y - cursor_.height / 2)
		titleScreen.updateLogic()
	}

	fun scaleF11() {
		titleScreen.scaleF11()
	}

	fun updateUI() {
		titleScreen.updateUI()
	}

}