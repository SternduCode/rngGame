package rngGame.ui

import javafx.scene.layout.Pane
import rngGame.stats.Item
import rngGame.visual.AnimatedImage

class InventoryScrollPaneElement(item: Item): Pane() {

	var item: Item = item
		set(value) {
			field = value
			itemView.init(item.path)
		}

	private val background: AnimatedImage = AnimatedImage("./res/gui/InvMain/Item_background.png")
	private val itemView: AnimatedImage = AnimatedImage()

	init {
		children.addAll(background, itemView)
		itemView.init(item.path)
	}

	val imageHeight
		get() = background.height

	val imageWidth
		get() = background.width

}