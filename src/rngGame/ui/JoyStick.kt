package rngGame.ui

import javafx.animation.*
import javafx.beans.property.DoublePropertyBase
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Polygon
import javafx.util.Duration
import rngGame.main.Direction
import rngGame.main.WindowManager
import rngGame.visual.AnimatedImage
import kotlin.math.*

object JoyStick: Pane() {

	private val joyStick = AnimatedImage("./res/gui/always/JoyschtickBaumwolle.png")

	private var _x = 0.0

	private var _y = 0.0

	private var _theta = 0.0

	private var lastRadiusPercentage = 0.0

	private var positionForGravity = object: DoublePropertyBase() {

		override fun getBean(): Any = Unit

		override fun getName() = "PositionForGravity"

		override fun setValue(p0: Number?) {
			super.setValue(p0)//Needs to be adjusted to keep theta while in animation
			joyStick.layoutX = background.imgRequestedWidth * .5 - joyStick.imgRequestedWidth * .5 + (p0?.toDouble() ?: 0.0) * (background.imgRequestedWidth/2) * lastRadiusPercentage * cos(theta)
			joyStick.layoutY = background.imgRequestedHeight * .5 - joyStick.imgRequestedHeight * .5 + (p0?.toDouble() ?: 0.0) * (background.imgRequestedHeight/2) * lastRadiusPercentage * sin(theta)
		}

	}

	val timeline = Timeline(KeyFrame(Duration.seconds(3.0), KeyValue(positionForGravity, 3.75, object: Interpolator() {
		override fun curve(p0: Double) = (Math.E.pow(-p0 * 3.75) * cos(Math.PI * 2 * p0 * 3.75)).also { v ->
			positionForGravity.value = v
		}
	})))

	val x
		get() = _x

	val y
		get() = _y

	val theta
		get() = _theta

	private val line = Polygon(
		0.0, 0.0,
		0.0, 0.0,
		0.0, 0.0
	)

	private val background = AnimatedImage("./res/gui/always/JoyschtickRand.png")

	init {
		timeline.cycleCount = 1

		background.imgRequestedWidth = 128
		background.imgRequestedHeight = 128

		joyStick.imgRequestedWidth = 64
		joyStick.imgRequestedHeight = 64

		background.scaleF11()
		joyStick.scaleF11()

		line.strokeWidth = 5.0
		line.fill = Color(1.0,1.0,1.0,0.5)


		children.addAll(background, line, joyStick)

		layoutX = WindowManager.getInstance().gameWidth * .1
		layoutY = WindowManager.getInstance().gameHeight * .8



		// function of A & B: f1(x) = ( ( y(B) - y(A) ) / ( x(B) - x(A) ) ) * x + y(A) - ( ( y(B) - y(A) ) / ( x(B) - x(A) ) ) * x(A)

		// orthogonal function: f2(x) = -( ( y(B) - y(A) ) / ( x(B) - x(A) ) ) ^ (-1) * x

		// Zero points are of distance 1 from center: sqrt((-(((y(B)-y(A))/(x(B)-x(A))))^(-1) * x)^(2)+x^(2))-1

		// Take Zero points as X in f2(x) for Y

		// X01 = -X02

		// X01 = ( (y(B)-y(A)) / sqrt( x(B)^(2) - 2 * x(B) * x(A) + x(A)^(2) + y(B)^(2) - 2 * y(B) * y(A) + y(A)^(2) ) )

		joyStick.layoutX = background.imgRequestedWidth * .5 - joyStick.imgRequestedWidth * .5
		joyStick.layoutY = background.imgRequestedHeight * .5 - joyStick.imgRequestedHeight * .5

		line.points[0] = background.imgRequestedWidth * .5
		line.points[1] = background.imgRequestedHeight * .5

		line.points[2] = background.imgRequestedWidth * .5
		line.points[3] = background.imgRequestedHeight * .5

		line.points[4] = background.imgRequestedWidth * .5
		line.points[5] = background.imgRequestedHeight * .5

		setOnMousePressed {

			if (timeline.status == Animation.Status.RUNNING) {
				timeline.stop()
			}

			joyStick.layoutX = it.x - joyStick.imgRequestedWidth * .5
			joyStick.layoutY = it.y - joyStick.imgRequestedHeight * .5

			val x = calculateX(it.x, it.y)

			lastRadiusPercentage = sqrt((abs(it.x - joyStick.imgRequestedWidth * .5) / (background.imgRequestedWidth / 2)).pow(2) + (abs(it.y - joyStick.imgRequestedHeight * .5) / (background.imgRequestedHeight / 2)).pow(2))

			println("$lastRadiusPercentage $x ${it.x - joyStick.imgRequestedWidth * .5} ${it.y - joyStick.imgRequestedHeight * .5}")

			val y1 = calculateY(x, it.x, it.y) * joyStick.imgRequestedHeight / 2

			val y2 = calculateY(-x, it.x, it.y) * joyStick.imgRequestedHeight / 2

			line.points[2] = it.x + x * joyStick.imgRequestedWidth / 2
			line.points[3] = it.y + y1

			line.points[4] = it.x - x * joyStick.imgRequestedWidth / 2
			line.points[5] = it.y + y2

			_x = (it.x - background.imgRequestedWidth * .5) / joyStick.imgRequestedWidth
			_y = (it.y - background.imgRequestedHeight * .5) / joyStick.imgRequestedHeight
			_theta = atan2(_y, _x)

			it.consume()
		}
		setOnMouseDragged {

			val (newX, newY) = pythagorasDistanceCap(it.x - background.imgRequestedWidth * .5, it.y - background.imgRequestedHeight * .5)
				.let { (x, y) -> (x + background.imgRequestedWidth * .5) to (y + background.imgRequestedHeight * .5) }

			joyStick.layoutX = newX - joyStick.imgRequestedWidth * .5
			joyStick.layoutY = newY - joyStick.imgRequestedHeight * .5

			val x = calculateX(newX, newY)

			lastRadiusPercentage = sqrt((abs(newX - joyStick.imgRequestedWidth * .5) / background.imgRequestedWidth * 2).pow(2) + (abs(newY - joyStick.imgRequestedHeight * .5) / background.imgRequestedHeight * 2).pow(2))

			println("$lastRadiusPercentage $x ${sqrt((newX - joyStick.imgRequestedWidth * .5).pow(2) + (newY - joyStick.imgRequestedHeight * .5).pow(2))}")

			val y1 = if (x != 0.0)
				calculateY(x, newX, newY) * joyStick.imgRequestedHeight / 2
			else
				joyStick.imgRequestedHeight * .5

			val y2 = if (x != 0.0)
				calculateY(-x, newX, newY) * joyStick.imgRequestedHeight / 2
			else
				-joyStick.imgRequestedHeight * .5

			line.points[2] = newX + x * joyStick.imgRequestedWidth / 2
			line.points[3] = newY + y1

			line.points[4] = newX - x * joyStick.imgRequestedWidth / 2
			line.points[5] = newY + y2

			_x = (newX - background.imgRequestedWidth * .5) / joyStick.imgRequestedWidth
			_y = (newY - background.imgRequestedHeight * .5) / joyStick.imgRequestedHeight
			_theta = atan2(_y, _x)

			it.consume()
		}
		setOnMouseReleased {
			//Original
			//joyStick.layoutX = background.imgRequestedWidth * .5 - joyStick.imgRequestedWidth * .5
			//joyStick.layoutY = background.imgRequestedHeight * .5 - joyStick.imgRequestedHeight * .5

			line.points[2] = background.imgRequestedWidth * .5
			line.points[3] = background.imgRequestedHeight * .5

			line.points[4] = background.imgRequestedWidth * .5
			line.points[5] = background.imgRequestedHeight * .5

			_x = 0.0
			_y = 0.0

			positionForGravity.value = 0.0

			timeline.play()

			it.consume()
		}
	}

	private fun pythagorasDistanceCap(x: Double, y: Double): Pair<Double, Double> {
		val a = x.pow(2)
		val b = y.pow(2)
		val sum = a + b
		val fraction = a / sum
		val distance64pow2 = (background.imgRequestedWidth/2.0).pow(2)
		val distancePow2 = a + b
		return if (distancePow2 > distance64pow2) {
			val newA = distance64pow2 * fraction
			val newB = distance64pow2 - newA
			(sqrt(newA) * if (x < 0) -1 else 1) to (sqrt(newB) * if (y < 0) -1 else 1)
		} else x to y
	}

	private fun calculateX(joyStickX: Double, joyStickY: Double): Double {
		return (joyStickY + (background.imgRequestedHeight * -.5)) /
				sqrt( joyStickX.pow(2) - joyStickX * background.imgRequestedWidth + (background.imgRequestedWidth * .5).pow(2)
						+ joyStickY.pow(2) - joyStickY * background.imgRequestedHeight + (background.imgRequestedHeight * .5).pow(2) )
	}

	private fun calculateY(x: Double, joyStickX: Double, joyStickY: Double): Double {
		return ( joyStickX + background.imgRequestedWidth * -.5 ) / ( joyStickY + background.imgRequestedHeight * -.5 ) * -x
	}

	fun getDirection(): Direction {
		return Direction.getDirectionFromAngle(theta)
	}

}
