package rngGame.visual;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import rngGame.main.Input;
import rngGame.ui.WindowDataHolder;


// TODO: Auto-generated Javadoc
/**
 * The Class TabMenu.
 */
public class TabMenu extends Pane {

	/** The gamemenu. */
	private final AnimatedImage gameMenuBackground;

	/** The buttongroup. */
	private final Group buttonGroup;

	/** The Y nbuttongroup. */
	private final Group confirmButtonGroup;

	/** The inv B. */
	private final Button inventoryButton;

	/** The que B. */
	private final Button questButton;

	/** The leav B. */
	private final Button leaveButton;

	/** The blank. */
	private final AnimatedImage blank;

	/** The surebackround. */
	private final AnimatedImage sureBackground;

	/** The sure Y. */
	private final Button sureY;

	/** The sure N. */
	private final Button sureN;

	/** The inventory. */
	private final Inventory inventory;

	/** The logic. */
	private final rngGame.ui.TabMenu logic;

	/** The no input. */
	private final AtomicBoolean noInput = new AtomicBoolean(false);

	/** The ab. */
	private final AtomicBoolean ab = new AtomicBoolean(false);

	/**
	 * Instantiates a new tab menu.
	 *
	 * @param logic the logic
	 */
	public TabMenu(rngGame.ui.TabMenu logic, GamePanel gamePanel, WindowDataHolder windowDataHolder) {
		this.logic = logic;

		buttonGroup = new Group();
		buttonGroup.setVisible(false);

		confirmButtonGroup = new Group();
		confirmButtonGroup.setVisible(false);

		gameMenuBackground = new AnimatedImage(logic.getGameMenuBackground());
		gameMenuBackground.setVisible(false);

		blank = new AnimatedImage(logic.getBlank());
		blank.setVisible(false);

		sureBackground = new AnimatedImage(logic.getSureBackground());
		sureBackground.setVisible(false);

		inventoryButton = new Button(logic.getInventoryButton());

		questButton = new Button(logic.getQuestButton());

		leaveButton = new Button(logic.getLeaveButton());

		sureY = new Button(logic.getSureY());

		sureN = new Button(logic.getSureN());

		buttonGroup.getChildren().addAll(inventoryButton, questButton, leaveButton);
		confirmButtonGroup.getChildren().addAll(sureY, sureN);

		inventory = new Inventory(null, logic);

		getChildren().add(gameMenuBackground);
		getChildren().add(buttonGroup);
		getChildren().add(blank);
		getChildren().add(sureBackground);
		getChildren().add(confirmButtonGroup);
		getChildren().add(inventory);

		setDisable(true);
		inventory.setDisable(true);

		Input.getInstance(null).setKeyHandler("inv", mod -> {
			if (!noInput.get()) {
				TranslateTransition	ft	= new TranslateTransition(Duration.millis(150), gameMenuBackground);
				TranslateTransition	ib1	= new TranslateTransition(Duration.millis(150), buttonGroup);

				if (!ab.getAndSet(!ab.get())) {
					logic.show();

					gameMenuBackground.setVisible(true);
					buttonGroup.setVisible(true);

					ft.setFromY(windowDataHolder.gameHeight() / 2);
					ib1.setFromY(windowDataHolder.gameHeight() / 2);
					ft.setToY(0);
					ib1.setToY(0);
					ft.play();
					ib1.play();

				} else {
					ft.setToY(windowDataHolder.gameHeight() / 2);
					ib1.setToY(windowDataHolder.gameHeight() / 2);
					ft.play();
					ib1.play();
					closeTabm(false);
					inventory.endShow();
				}
			}
		}, KeyCode.TAB, false);

	}

	/**
	 * Close tabmenu.
	 *
	 * @param toggleState the toggle state
	 */
	public void closeTabMenu(boolean toggleState) {
		if (toggleState) ab.getAndSet(!ab.get());

		logic.setBlockUserInputs(false);

		inventory.setDisable(true);

		new Thread(() -> {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameMenuBackground.setVisible(false);
			blank.setVisible(false);
			buttonGroup.setVisible(false);
			setDisable(true);

		}).start();

	}

	public void show() {
		logic.setBlockUserInputs(true);
		setDisable(false);

		inventoryButton.setOnPressed(me -> {
			inventoryButton.setImage(invB2);
		});
		inventoryButton.setOnReleased(me -> {
			inventoryButton.setImage(invB1);
			inventory.show();
		});

		questButton.setOnPressed(me -> {
			questButton.setImage(queB2);
		});
		questButton.setOnReleased(me -> {
			questButton.setImage(queB1);
		});

		leaveButton.setOnPressed(me -> {
			leaveButton.setImage(leavB2);
		});
		leaveButton.setOnReleased(me -> {
			blank.setVisible(true);
			leaveButton.setImage(leavB1);
			noInput.set(true);
			sureBackground.setVisible(true);
			confirmButtonGroup.setVisible(true);
		});

		sureN.setOnPressed(me -> {
			sureN.setImage(sureN2);
		});
		sureN.setOnReleased(me -> {
			sureN.setImage(sureN1);
			blank.setVisible(false);
			noInput.set(false);
			sureBackground.setVisible(false);
			confirmButtonGroup.setVisible(false);
		});

		sureY.setOnPressed(me -> {
			sureY.setImage(sureY2);
		});
		sureY.setOnReleased(me -> {
			sureY.setImage(sureY1);
			System.exit(0);
		});

	}

	/**
	 * Update.
	 */
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
