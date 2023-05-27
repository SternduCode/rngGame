package rngGame.ui;

import java.io.FileNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class TabMenu.
 */
public class TabMenu {

	/** The inv B. */
	private final Button inventoryButton;

	/** The gamemenu. */
	private final AnimatedImage gameMenuBackground;

	/** The inv B 1. */
	private final String inventoryButton1;

	/** The inv B 2. */
	private final String inventoryButton2;

	/** The que B. */
	private final Button questButton;

	/** The que B 1. */
	private final String questButton1;

	/** The que B 2. */
	private final String questButton2;

	/** The leav B. */
	private final Button leaveButton;

	/** The blank. */
	private final AnimatedImage blank;

	/** The leav B 1. */
	private final String leaveButton1;

	/** The leav B 2. */
	private final String leaveButton2;

	/** The surebackround. */
	private final AnimatedImage sureBackground;

	/** The sure Y. */
	private final Button sureY;

	/** The sure N. */
	private final Button sureN;

	/** The sure Y 1. */
	private final String sureY1;

	/** The sure N 1. */
	private final String sureN1;

	/** The sure Y 2. */
	private final String sureY2;

	/** The sure N 2. */
	private final String sureN2;

	/** The inventory. */
	private final Inventory inventory;

	/** The gamepanel. */
	private final GamePanel gamePanel;

	/**
	 * Instantiates a new tab menu.
	 *
	 * @param gamepanel the gamepanel
	 * @throws FileNotFoundException the file not found exception
	 */
	public TabMenu(GamePanel gamePanel) {
		gameMenuBackground = new AnimatedImage("./res/gui/gamemenubackround.png", gamePanel.getWindowDataHolder());

		blank = new AnimatedImage("./res/gui/blackTransparent.png", gamePanel.getWindowDataHolder());

		inventoryButton1	= "./res/gui/invAbutton1.png";
		inventoryButton2	= "./res/gui/invAbutton2.png";
		inventoryButton		= new Button(inventoryButton1, gamePanel.getWindowDataHolder());

		questButton1	= "./res/gui/queAbutton1.png";
		questButton2	= "./res/gui/queAbutton2.png";
		questButton		= new Button(questButton1, gamePanel.getWindowDataHolder());

		leaveButton1	= "./res/gui/LeavAbutton1.png";
		leaveButton2	= "./res/gui/LeavAbutton2.png";
		leaveButton		= new Button(leaveButton1, gamePanel.getWindowDataHolder());

		sureBackground = new AnimatedImage("./res/gui/Sure.png", gamePanel.getWindowDataHolder());

		sureY1	= "./res/gui/SureY.png";
		sureY2	= "./res/gui/SureY2.png";
		sureY	= new Button(sureY1, gamePanel.getWindowDataHolder());

		sureN1	= "./res/gui/SureN.png";
		sureN2	= "./res/gui/SureN2.png";
		sureN	= new Button(sureN1, gamePanel.getWindowDataHolder());

		this.gamePanel = gamePanel;

		inventory = new Inventory(gamePanel, this);
	}



	/**
	 * F 11 scale.
	 */
	public void f11Scale() {

		blank.scaleF11();
		inventoryButton.scaleF11();
		questButton.scaleF11();
		leaveButton.scaleF11();
		sureY.scaleF11();
		sureN.scaleF11();
		gameMenuBackground.scaleF11();
		sureBackground.scaleF11();

		inventory.scaleF11();
	}

	/**
	 * Gets the inventory.
	 *
	 * @return the inventory
	 */
	public Inventory getInventory() { return inventory; }

	public void setBlockUserInputs(boolean block) {

		gamePanel.setBlockUserInputs(block);

	}

	public void update() {
		inventoryButton.update();
		gameMenuBackground.update();
		questButton.update();
		leaveButton.update();
		blank.update();
		sureBackground.update();
		sureY.update();
		sureN.update();
		inventory.update();
	}

}
