package rngGame.visual;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;
import rngGame.ui.WindowDataHolder;

// TODO: Auto-generated Javadoc
/**
 * The Class GameObject.
 */
public class GameObject extends Pane {

	/** The logic. */
	protected rngGame.main.GameObject logic;

	/** The window data holder. */
	protected WindowDataHolder windowDataHolder;

	/** The image. */
	protected AnimatedImage image;

	/** The collision box view group. */
	protected Group collisionBoxViewGroup;

	/** The game panel. */
	private final GamePanel gamePanel;

	/**
	 * Instantiates a new game object.
	 *
	 * @param logic the logic
	 * @param windowDataHolder the window data holder
	 * @param contextMenu the context menu
	 * @param gamePanel the game panel
	 */
	public GameObject(rngGame.main.GameObject logic, WindowDataHolder windowDataHolder, ContextMenu contextMenu, GamePanel gamePanel) {
		this.logic = logic;
		this.windowDataHolder	= windowDataHolder;
		this.gamePanel			= gamePanel;

		collisionBoxViewGroup = new Group();
		collisionBoxViewGroup.setVisible(false);
		collisionBoxViewGroup.setDisable(true);

		image = new AnimatedImage(logic.getImage());
		image.setDisable(true);

		getChildren().addAll(image, collisionBoxViewGroup);

		setOnContextMenuRequested(e -> {
			if ("true".equals(System.getProperty("edit"))) {
				logic.getRequestor().set(this);
				contextMenu.getItems().clear();
				contextMenu.getItems().addAll(logic.getMenus());
				contextMenu.show(gamePanel.getLayerGroup().getLayer(getLayer()), e.getScreenX(), e.getScreenY());
			}
		});

		addToView();

	}

	/**
	 * Adds the to view.
	 */
	protected void addToView() {
		gamePanel.getGameObjects().add(this);
	}

	/**
	 * Gets the layer.
	 *
	 * @return the layer
	 */
	public int getLayer() { return logic.getLayer(); }

	/**
	 * Gets the logic.
	 *
	 * @return the logic
	 */
	public rngGame.main.GameObject getLogic() { return logic; }

	/**
	 * Removes the.
	 */
	public void remove() {
		if (gamePanel.getGameObjects().contains(this))
			gamePanel.getGameObjects().remove(this);
		logic.resetRemove();
	}

	/**
	 * Update.
	 */
	public void update() {
		image.update();

		if (logic.isRemove()) remove();

	}

}
