package rngGame.main

import javafx.geometry.Point2D
import javafx.scene.Node
import java.awt.Point

fun Node.setPosition(x: Double, y: Double) {
	this.layoutX = x
	this.layoutY = y
}

fun Node.setPositionScaling(x: Double, y: Double) {
	this.layoutX = x * WindowManager.scalingFactorX
	this.layoutY = y * WindowManager.scalingFactorY
}

operator fun Point.component1() = getX()
operator fun Point.component2() = getY()

operator fun Point2D.component1() = x
operator fun Point2D.component2() = y