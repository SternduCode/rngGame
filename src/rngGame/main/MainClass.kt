package rngGame.main

import javafx.application.Application
import javafx.collections.ListChangeListener
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.input.*
import javafx.stage.Stage
import javafx.stage.Window
import rngGame.visual.VisualRoot
import java.io.FileInputStream
import kotlin.system.exitProcess

class MainClass: Application() {

	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Throws(Exception::class)
	override fun start(primaryStage: Stage) {
		primaryStage.isFullScreen = false
		primaryStage.isResizable = false
		primaryStage.title = "Demon Universe"

		primaryStage.fullScreenExitHint = ""
		primaryStage.fullScreenExitKeyCombination = KeyCombination.NO_MATCH
		primaryStage.icons.add(Image(FileInputStream("./res/gui/GameIschcon.png")))
		val input = Input.getInstance()
		input.isBlockInputs = true

		// set eventHandlers to detect Mouse and Key Events on the whole Window
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED) { e: KeyEvent? -> input.keyPressed(e) }
		primaryStage.addEventHandler(KeyEvent.KEY_RELEASED) { e: KeyEvent? -> input.keyReleased(e) }
		primaryStage.addEventHandler(KeyEvent.KEY_TYPED) { e: KeyEvent? -> input.keyTyped(e) }
		primaryStage.addEventHandler(MouseEvent.MOUSE_RELEASED) { me: MouseEvent? -> input.mouseReleased(me) }
		primaryStage.addEventHandler(MouseEvent.DRAG_DETECTED) { me: MouseEvent? -> input.dragDetected(me) }
		primaryStage.addEventHandler(MouseEvent.MOUSE_MOVED) { me: MouseEvent? -> input.mouseMoved(me) }
		primaryStage.addEventHandler(MouseEvent.MOUSE_DRAGGED) { me: MouseEvent? -> input.mouseDragged(me) }

		val gameScene = Scene(VisualRoot)
		primaryStage.scene = gameScene

		VisualRoot.scaleF11()

		primaryStage.show()

		Window.getWindows().addListener { c: ListChangeListener.Change<out Window?> ->
			if (c.list.isEmpty()) isStopping = true
		}
	}

	companion object {

		/**
		 * The main method.
		 *
		 * @param args the arguments
		 */
		@JvmStatic
		fun main(args: Array<String>) {
			System.setProperty("debug", "false") // more debug messages

			System.setProperty("edit", "false") // set edit mode to disabled
			System.setProperty("coll", "false") // set collisions mode to disabled
			System.setProperty("alternateUpdate", "false") // reverse Vsync more or less
			System.setProperty("teleport", "false")

			launch(MainClass::class.java, *args)

			exitProcess(0)
		}

		var isStopping: Boolean = false
			private set
	}

}
