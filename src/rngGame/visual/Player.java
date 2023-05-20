package rngGame.visual;

import javafx.scene.control.ContextMenu;
import rngGame.ui.WindowDataHolder;


// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player extends GameObject {

	/**
	 * Instantiates a new player.
	 *
	 * @param logic the logic
	 * @param windowDataHolder the window data holder
	 * @param contextMenu the context menu
	 * @param gamePanel the game panel
	 */
	public Player(rngGame.entity.Player logic, WindowDataHolder windowDataHolder, ContextMenu contextMenu, GamePanel gamePanel) {
		super(logic, windowDataHolder, contextMenu, gamePanel);

	}

	/**
	 * Update.
	 */
	@Override
	public void update() {
		super.update();
		setLayoutX((int) (windowDataHolder.gameWidth() / 2 - image.getImage().getWidth() / 2));
		setLayoutY((int) (windowDataHolder.gameHeight() / 2 - image.getImage().getHeight() / 2));

	}

}
