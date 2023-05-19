package rngGame.visual;

import java.util.*;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import rngGame.ui.WindowDataHolder;


// TODO: Auto-generated Javadoc
/**
 * The Class GamePanel.
 */
public class GamePanel extends Pane {

	/** The logic. */
	private final rngGame.ui.GamePanel logic;

	/** The tile manager. */
	private final TileManager tileManager;

	/** The layer group. */
	private final LayerGroup layerGroup;

	/** The loading screen. */
	private final LoadingScreen loadingScreen;

	/** The window data holder. */
	private final WindowDataHolder windowDataHolder;

	/** The action button. */
	private final ActionButton actionButton;

	/** The point group. */
	private final Group pointGroup;

	/** The overlay. */
	private final AnimatedImage overlay, speakBubble;

	/** The speak bubble text. */
	private final Pane speakBubbleText;

	/** The select tool. */
	private final SelectTool selectTool;

	/** The fps label. */
	private final Label fpsLabel;

	/** The tab menu. */
	private final TabMenu tabMenu;

	/** The game objects. */
	private final List<GameObject> gameObjects;

	/** The player. */
	private final Player player;

	/** The fps. */
	private String fps;

	/**
	 * Instantiates a new game panel.
	 *
	 * @param logic the logic
	 * @param windowDataHolder the window data holder
	 */
	public GamePanel(rngGame.ui.GamePanel logic, WindowDataHolder windowDataHolder) {

		logic.setVisualGamePanel(this);

		this.logic = logic;

		this.windowDataHolder = windowDataHolder;

		gameObjects = new ArrayList<>();

		tileManager = new TileManager(logic.getTileManager(), windowDataHolder);

		layerGroup = new LayerGroup();

		player = new Player(logic.getPlayer());

		overlay = new AnimatedImage(logic.getOverlay());

		pointGroup = new Group();

		selectTool = new SelectTool(logic.getSelectTool());

		actionButton = new ActionButton(logic.getActionButton());

		speakBubble = new AnimatedImage(logic.getSpeakBubble());

		speakBubbleText = new Pane();

		tabMenu = new TabMenu(logic.getTabMenu());

		fpsLabel = new Label();

		loadingScreen = new LoadingScreen(rngGame.ui.LoadingScreen.getDefaultLoadingScreen(windowDataHolder));

		getChildren().addAll(tileManager, layerGroup, overlay, pointGroup, selectTool, actionButton, speakBubble, speakBubbleText,
				tabMenu, fpsLabel, loadingScreen);

	}

	/**
	 * Gets the layer group.
	 *
	 * @return the layer group
	 */
	public LayerGroup getLayerGroup() { return layerGroup; }

	/**
	 * Go into full screen.
	 */
	public void goIntoFullScreen() {

		((Stage) getScene().getWindow()).setFullScreen(true);

		windowDataHolder.setGameWidth((int) getScene().getWidth());
		windowDataHolder.setGameHeight((int) getScene().getHeight());
	}

	/**
	 * Go out of full screen.
	 */
	public void goOutOfFullScreen() {

		((Stage) getScene().getWindow()).setFullScreen(false);

		windowDataHolder.setGameWidth((int) getScene().getWidth());
		windowDataHolder.setGameHeight((int) getScene().getHeight());

	}

	/**
	 * Checks if is full screen.
	 *
	 * @return true, if is full screen
	 */
	public boolean isFullScreen() { return ((Stage) getScene().getWindow()).isFullScreen(); }

	/**
	 * Checks if is in loading screen.
	 *
	 * @return true, if is in loading screen
	 */
	public boolean isInLoadingScreen() { return loadingScreen.isInLoadingScreen(); }

	/**
	 * Update.
	 */
	public void update() {
		tileManager.update();

		for (int i = 0; i < layerGroup.getChildren().size(); i++) {
			Group group = layerGroup.getLayer(i);
			group.getChildren().clear();
		}
		for (GameObject gameObject : gameObjects) {
			gameObject.update();
			layerGroup.getLayer(gameObject.getLayer()).getChildren().add(gameObject);// TODO ordering
		}

		overlay.update();

		pointGroup.getChildren().clear();
		pointGroup.getChildren().addAll(logic.getPoints().values());

		selectTool.update();

		actionButton.update();

		speakBubble.update();

		speakBubbleText.getChildren().clear();
		speakBubbleText.getChildren().addAll(logic.getSpeakBubbleTextElements());

		tabMenu.update();

		fpsLabel.setText("FPS: " + fps + "\nTPS: " + logic.getTps());
		fpsLabel.setLayoutX(windowDataHolder.gameWidth() - fpsLabel.getWidth());

		loadingScreen.update();

	}

}
