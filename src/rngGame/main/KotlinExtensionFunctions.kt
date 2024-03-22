package rngGame.main

import javafx.scene.Node
import java.awt.Point

fun Node.setPosition(x: Double, y: Double) {
	this.layoutX = x
	this.layoutY = y
}

fun Node.setPositionScaling(x: Double, y: Double) {
	this.layoutX = x * WindowManager.getInstance().scalingFactorX
	this.layoutY = y * WindowManager.getInstance().scalingFactorY
}

operator fun Point.component1() = getX()
operator fun Point.component2() = getY()