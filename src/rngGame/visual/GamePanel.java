package rngGame.visual;

import java.io.*;
import java.util.*;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

	private final Fight fight;

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
	private double fps;

	/** The last frame. */
	private long lastFrame;

	/** The frame times. */
	private final List<Long> frameTimes;

	/**
	 * Instantiates a new game panel.
	 *
	 * @param logic the logic
	 * @param windowDataHolder the window data holder
	 */
	public GamePanel(rngGame.ui.GamePanel logic, WindowDataHolder windowDataHolder) {

		logic.setVisualGamePanel(this);

		frameTimes = new ArrayList<>();

		this.logic = logic;

		this.windowDataHolder = windowDataHolder;

		gameObjects = new ArrayList<>();

		fight = new Fight(logic.getFight(), this, windowDataHolder);
		fight.setVisible(false);

		loadingScreen = new LoadingScreen(rngGame.ui.LoadingScreen.getDefaultLoadingScreen(windowDataHolder));
		loadingScreen.setDisable(true);
		loadingScreen.setOpacity(0);
		loadingScreen.update();

		tileManager = new TileManager(logic.getTileManager(), windowDataHolder, this);

		layerGroup = new LayerGroup();

		player = new Player(logic.getPlayer(), windowDataHolder, tileManager.getContextMenu(), this);

		overlay = new AnimatedImage(logic.getOverlay());

		pointGroup = new Group();

		selectTool = new SelectTool(logic.getSelectTool());

		actionButton = new ActionButton(logic.getActionButton());

		speakBubble = new AnimatedImage(logic.getSpeakBubble());

		speakBubbleText = new Pane();

		tabMenu = new TabMenu(logic.getTabMenu());

		fpsLabel = new Label();
		fpsLabel.setBackground(new Background(new BackgroundFill(Color.color(.5, .5, .5, 1), null, null)));
		fpsLabel.setTextFill(Color.color(.1, .1, .1));
		fpsLabel.setOpacity(.6);
		fpsLabel.setDisable(true);
		fpsLabel.setVisible(false);

		getChildren().addAll(tileManager, layerGroup, overlay, pointGroup, selectTool, actionButton, speakBubble, speakBubbleText,
				tabMenu, fight, fpsLabel, loadingScreen);

	}

	/**
	 * Gets the context menu.
	 *
	 * @return the context menu
	 */
	public ContextMenu getContextMenu() { return tileManager.getContextMenu(); }

	/**
	 * Gets the game objects.
	 *
	 * @return the game objects
	 */
	public List<GameObject> getGameObjects() { return gameObjects; }

	/**
	 * Gets the layer group.
	 *
	 * @return the layer group
	 */
	public LayerGroup getLayerGroup() { return layerGroup; }

	/**
	 * Gets the logic.
	 *
	 * @return the logic
	 */
	public rngGame.ui.GamePanel getLogic() { return logic; }

	/**
	 * Gets the select tool.
	 *
	 * @return the select tool
	 */
	public SelectTool getSelectTool() { return selectTool; }

	/**
	 * Gets the tile at.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the tile at
	 */
	public rngGame.visual.TextureHolder getTileAt(double x, double y) { return tileManager.getTileAt(x, y); }

	/**
	 * Go into full screen.
	 */
	public void goIntoFullScreen() {

		((Stage) getScene().getWindow()).setFullScreen(true);

		windowDataHolder.setGameWidth((int) getScene().getWidth());
		windowDataHolder.setGameHeight((int) getScene().getHeight());
	}

	/**
	 * Go into loading screen.
	 */
	public void goIntoLoadingScreen() {
		loadingScreen.goIntoLoadingScreen();
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
	 * Go out of loading screen.
	 */
	public void goOutOfLoadingScreen() {
		loadingScreen.goOutOfLoadingScreen();
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

	public void showFight() { fight.setVisible(true); }

	/**
	 * Update.
	 */
	public void update() {

		long lastFrameTime = frameTimes.size() > 0 ? frameTimes.get(frameTimes.size() - 1) : 0;

		gameObjects.clear();

		gameObjects.add(player);

		logic.getNpcs().forEach(n -> {
			gameObjects.add(new GameObject(n, windowDataHolder, getContextMenu(), this));
		});
		logic.getBuildings().forEach(b -> {
			gameObjects.add(new GameObject(b, windowDataHolder, getContextMenu(), this));
		});
		logic.getMobRans().forEach(m -> {
			gameObjects.add(new GameObject(m, windowDataHolder, getContextMenu(), this));
		});

		if (logic.isBackgroundPathDirty()) if (logic.getBackgroundPath() != null) try {
			setBackground(new Background(
					new BackgroundImage(new Image(new FileInputStream("./res/" + logic.getBackgroundPath())),
							BackgroundRepeat.NO_REPEAT,
							BackgroundRepeat.NO_REPEAT, null,
							new BackgroundSize(windowDataHolder.gameWidth(), windowDataHolder.gameHeight(), false, false, false, false))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		else setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

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

		fpsLabel.setText("FPS: " + String.format("%.2f", 1000 / fps) + "\nTPS: " + String.format("%.2f", 1000 / logic.getTps()));
		fpsLabel.setLayoutX(windowDataHolder.gameWidth() - fpsLabel.getWidth());
		fpsLabel.setVisible(logic.isFpsLabelVisible());

		loadingScreen.update();

		long frameTime = System.currentTimeMillis() - lastFrame;
		lastFrame = System.currentTimeMillis();
		frameTimes.add(frameTime);
		fps = frameTimes.stream().mapToLong(l -> l).average().getAsDouble();
		while (frameTimes.size() > Math.max(Math.pow(1000 / fps * 12, 1.2), 1)) frameTimes.remove(0);

	}

}
