package rngGame.visual;

import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


// TODO: Auto-generated Javadoc
/**
 * The Class TextureHolder.
 */
public class TextureHolder extends Pane {


	/** The image. */
	private final ImageView image;

	/** The logic. */
	private final rngGame.tile.TextureHolder logic;

	/**
	 * Instantiates a new texture holder.
	 *
	 * @param logic the logic
	 * @param contextMenu the context menu
	 */
	public TextureHolder(rngGame.tile.TextureHolder logic, ContextMenu contextMenu) {

		this.logic = logic;

		image = new ImageView(logic.getTile().images[0]);
		// tile.Image.getPixelReader();
		// new WritableImage(null, layoutX, layoutY)
		image.setDisable(true);
		getChildren().add(image);
		getChildren().add(logic.getPoly());

		setOnContextMenuRequested(e -> {
			if ("true".equals(System.getProperty("edit"))) {
				contextMenu.getItems().clear();
				contextMenu.getItems().addAll(logic.getMenus());
				contextMenu.show(TextureHolder.this, e.getScreenX(), e.getScreenY());
			}
		});

	}

	/**
	 * Gets the logic.
	 *
	 * @return the logic
	 */
	public rngGame.tile.TextureHolder getLogic() { return logic; }

	/**
	 * Update.
	 */
	public void update() {
		image.setImage(logic.getTile().images[logic.getTile().spriteNum]);
		getChildren().clear();
		getChildren().addAll(image, logic.getPoly());

	}

}
