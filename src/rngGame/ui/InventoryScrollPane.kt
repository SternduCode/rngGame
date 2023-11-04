package rngGame.ui

import javafx.scene.layout.Pane
import rngGame.main.WindowManager
import rngGame.stats.Item
import kotlin.math.pow

class InventoryScrollPane: Pane() {
	private val elements = Array(40) { index ->
		InventoryScrollPaneElement(Item.NOITEM).also {
			it.layoutY = (it.imageHeight + 5 * WindowManager.getInstance().scalingFactorY) * index
		}
	}

	init {
		elements.forEach(children::add)
	}

	fun parabola(y: Double) = y.pow(2) * 0.005

	fun update() {
		elements.forEach {
			it.layoutX = parabola((it.imageHeight / 2 + it.layoutY - WindowManager.getInstance().gameHeight / 2) / 3)
			println(it.layoutX)
		}
	}

}