package rngGame.ui;

import java.util.*;
import java.util.Map;

import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.shape.Circle;
import javafx.stage.Window;
import rngGame.buildings.Building;
import rngGame.entity.*;
import rngGame.main.UndoRedo;
import rngGame.tile.*;
import rngGame.visual.GameObject;


// TODO: Auto-generated Javadoc
/**
 * The Class GamePanel.
 */
public class GamePanel {

	/** The visual game panel. */
	private rngGame.visual.GamePanel visualGamePanel;

	/** The points. */
	private final Map<Point2D, Circle> points;

	/** The player. */
	private final Player player;

	/** The overlay. */
	private final AnimatedImage overlay;

	/** The action button. */
	private final ActionButton actionButton;

	/** The tile manager. */
	private final TileManager tileManager;

	/** The tab menu. */
	private final TabMenu tabMenu;

	/** The speak bubble. */
	private final AnimatedImage speakBubble;

	/** The select tool. */
	private final SelectTool selectTool;

	/** The tps. */
	private double tps;

	/** The window data holder. */
	private final WindowDataHolder windowDataHolder;

	/** The fps label visible. */
	private boolean fpsLabelVisible;

	/** The block user inputs. */
	private boolean blockUserInputs;

	/** The speak bubble elements. */
	private final List<Node> speakBubbleElements;

	/** The difficulty. */
	private Difficulty difficulty;

	/** The frame times. */
	private final List<Long> frameTimes;

	/** The last frame. */
	private long lastFrame;

	/** The buildings. */
	private final List<Building> buildings;

	/** The npcs. */
	private final List<NPC> npcs;

	private final List<MobRan> mobRans;

	/** The clipboard. */
	private List<List<TextureHolder>> clipboard;

	private String backgroundPath;

	private boolean backgroundPathDirty;

	private final Fight fight;

	/**
	 * Instantiates a new game panel.
	 *
	 * @param windowDataHolder the window data holder
	 */
	public GamePanel(WindowDataHolder windowDataHolder) {

		this.windowDataHolder = windowDataHolder;

		frameTimes = new ArrayList<>();

		npcs = new ArrayList<>();

		buildings = new ArrayList<>();

		mobRans = new ArrayList<>();

		visualGamePanel = null;

		fpsLabelVisible = false;

		blockUserInputs = false;

		backgroundPath		= null;
		backgroundPathDirty	= false;

		tps = 0;

		speakBubbleElements = new ArrayList<>();

		points = new HashMap<>();

		selectTool = new SelectTool(this, windowDataHolder);

		tileManager = new TileManager(this, windowDataHolder);

		tabMenu = new TabMenu(this);

		fight = new Fight(this);

		overlay = new AnimatedImage(windowDataHolder);

		actionButton = new ActionButton(this, windowDataHolder);

		speakBubble = new AnimatedImage(windowDataHolder);

		player = new Player(this, tileManager.getRequestorN(), windowDataHolder);

	}

	/**
	 * Convert layout point to world point.
	 *
	 * @param layoutPoint the layout point
	 *
	 * @return the world point
	 */
	public Point2D convertLayoutPointToWorldPoint(Point2D layoutPoint) {
		return layoutPoint.add(new Point2D(player.getX(), player.getY())).subtract(new Point2D(player.getScreenX(), player.getScreenY()));
	}

	/**
	 * Convert world point to layout point.
	 *
	 * @param worldPoint the world point
	 *
	 * @return the layout point
	 */
	public Point2D convertWorldPointToLayoutPoint(Point2D worldPoint) {
		return worldPoint.subtract(new Point2D(player.getX(), player.getY())).add(new Point2D(player.getScreenX(), player.getScreenY()));
	}

	/**
	 * Gets the action button.
	 *
	 * @return the action button
	 */
	public ActionButton getActionButton() { return actionButton; }

	public String getBackgroundPath() { return backgroundPath; }

	/**
	 * Gets the buildings.
	 *
	 * @return the buildings
	 */
	public List<Building> getBuildings() { return buildings; }

	/**
	 * Gets the clipboard.
	 *
	 * @return the clipboard
	 */
	public List<List<TextureHolder>> getClipboard() { return clipboard; }

	/**
	 * Gets the context menu.
	 *
	 * @return the context menu
	 */
	public ContextMenu getContextMenu() { return visualGamePanel.getContextMenu(); }

	/**
	 * Gets the difficulty.
	 *
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() { return difficulty; }

	public Fight getFight() { return fight; }

	public int getLayerCount() { return visualGamePanel.getLayerGroup().getChildren().size(); }

	public List<MobRan> getMobRans() { return mobRans; }

	/**
	 * Gets the npcs.
	 *
	 * @return the npcs
	 */
	public List<NPC> getNpcs() { return npcs; }

	/**
	 * Gets the object at.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 * @return the object at x and y
	 */
	public Node getObjectAt(double x, double y) {
		List<Node> nodes = visualGamePanel.getLayerGroup().getChildren().stream().map(l -> ((Group) l)).flatMap(l -> l.getChildren().parallelStream()
				.filter(n -> n.contains(x - ((GameObject) n).getLogic().getX(), y - ((GameObject) n).getLogic().getY()) && n.isVisible())).toList();
		if (nodes.size() != 0) return nodes.get(nodes.size() - 1);
		if (x < 0 || y < 0) return null;
		return visualGamePanel.getTileAt(x, y);

	}

	/**
	 * Gets the overlay.
	 *
	 * @return the overlay
	 */
	public AnimatedImage getOverlay() { return overlay; }

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() { return player; }

	/**
	 * Gets the points.
	 *
	 * @return the points
	 */
	public Map<Point2D, Circle> getPoints() { return points; }

	/**
	 * Gets the select tool.
	 *
	 * @return the select tool
	 */
	public SelectTool getSelectTool() { return selectTool; }

	/**
	 * Gets the speak bubble.
	 *
	 * @return the speak bubble
	 */
	public AnimatedImage getSpeakBubble() { return speakBubble; }

	/**
	 * Gets the speak bubble text elements.
	 *
	 * @return the speak bubble text elements
	 */
	public List<Node> getSpeakBubbleTextElements() { return speakBubbleElements; }

	/**
	 * Gets the tab menu.
	 *
	 * @return the tab menu
	 */
	public TabMenu getTabMenu() { return tabMenu; }

	/**
	 * Gets the tile manager.
	 *
	 * @return the tile manager
	 */
	public TileManager getTileManager() { return tileManager; }

	/**
	 * Gets the tps.
	 *
	 * @return the tps
	 */
	public double getTps() { return tps; }

	/**
	 * Gets the visual select tool.
	 *
	 * @return the visual select tool
	 */
	public rngGame.visual.SelectTool getVisualSelectTool() { return visualGamePanel.getSelectTool(); }

	/**
	 * Gets the window.
	 *
	 * @return the window
	 */
	public Window getWindow() { return visualGamePanel.getScene().getWindow(); }

	/**
	 * Gets the window data holder.
	 *
	 * @return the window data holder
	 */
	public WindowDataHolder getWindowDataHolder() { return windowDataHolder; }

	/**
	 * Go into full screen.
	 */
	public void goIntoFullScreen() { visualGamePanel.goIntoFullScreen(); }

	public void goIntoLoadingScreen() {
		visualGamePanel.goIntoLoadingScreen();
	}

	/**
	 * Go out of full screen.
	 */
	public void goOutOfFullScreen() { visualGamePanel.goOutOfFullScreen(); }

	public void goOutOfLoadingScreen() {
		visualGamePanel.goOutOfLoadingScreen();
	}

	public boolean isBackgroundPathDirty() {
		return backgroundPathDirty;
	}

	/**
	 * Checks if is block user inputs.
	 *
	 * @return true, if is block user inputs
	 */
	public boolean isBlockUserInputs() { return visualGamePanel == null || visualGamePanel.isInLoadingScreen() || blockUserInputs; }

	/**
	 * Checks if is fps label visible.
	 *
	 * @return true, if is fps label visible
	 */
	public boolean isFpsLabelVisible() { return fpsLabelVisible; }

	/**
	 * Checks if is full screen.
	 *
	 * @return true, if is full screen
	 */
	public boolean isFullScreen() { return visualGamePanel.isFullScreen(); }

	/**
	 * Reload.
	 */
	public void reload() {}

	public void resetBackgroundPathDirty() {
		backgroundPathDirty = false;
	}

	/**
	 * Save map.
	 */
	public void saveMap() {}

	/**
	 * Sets the blcokuser inputs.
	 *
	 * @param blockUserInputs the new blcokuser inputs
	 */
	public void setBlockUserInputs(boolean blockUserInputs) {
		this.blockUserInputs = blockUserInputs;
	}

	/**
	 * Sets the clipboard.
	 *
	 * @param clipboard the new clipboard
	 */
	public void setClipboard(List<List<TextureHolder>> clipboard) { this.clipboard = clipboard; }

	/**
	 * Sets the map.
	 *
	 * @param path the new map
	 */
	public void setMap(String path) { setMap(path, null); }

	/**
	 * Sets the map.
	 *
	 * @param path the path
	 * @param exitStartingPosition the exit starting position
	 */
	public void setMap(String path, double[] exitStartingPosition) {

		visualGamePanel.goIntoLoadingScreen();

		UndoRedo.getInstance().clearActions();

		SoundHandler.getInstance().endBackgroundMusic();

		getPoints().clear();

		tileManager.setMap(path);

		npcs.addAll(tileManager.getNPCSFromMap());

		buildings.addAll(tileManager.getBuildingsFromMap());

		mobRans.addAll(tileManager.getMobsFromMap());

		backgroundPath		= tileManager.getBackgroundPath();
		backgroundPathDirty	= true;

		if (!"".equals(tileManager.getOverlay())) overlay.init(tileManager.getOverlay());
		else overlay.uninit();

		if (!"".equals(getTileManager().getBackgroundMusic()))
			SoundHandler.getInstance().setBackgroundMusic(getTileManager().getBackgroundMusic());
		else SoundHandler.getInstance().endBackgroundMusic();

		visualGamePanel.goOutOfLoadingScreen();
	}

	/**
	 * Sets the visual game panel.
	 *
	 * @param gamePanel the new visual game panel
	 */
	public void setVisualGamePanel(rngGame.visual.GamePanel gamePanel) { visualGamePanel = gamePanel; }

	public void showFightingScreen(MobRan mobRan) {

		mobRans.remove(mobRan);

		getActionButton().setVisible(false);
		LoadingScreen.getDefaultLoadingScreen(windowDataHolder).uninit();
		LoadingScreen.getDefaultLoadingScreen(windowDataHolder).init("./res/gui/BattleIntro.gif");
		SoundHandler.getInstance().makeSound("battleintro.wav");
		goIntoLoadingScreen();
		try {
			Thread.sleep(2800);
			visualGamePanel.showFight();// TODO add visual Fight
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		goOutOfLoadingScreen();

	}

	/**
	 * Toggle fps label visible.
	 */
	public void toggleFpsLabelVisible() { fpsLabelVisible = !fpsLabelVisible; }

	/**
	 * Update.
	 */
	public void update() {

		long lastFrameTime = frameTimes.size() > 0 ? frameTimes.get(frameTimes.size() - 1) : 0;

		player.update(lastFrameTime);

		selectTool.update();

		tileManager.update();

		overlay.update();

		actionButton.update();

		speakBubble.update();

		getBuildings().forEach(b -> {
			b.update(lastFrameTime);
		});

		getNpcs().forEach(n -> {
			n.update(lastFrameTime);
		});

		getMobRans().forEach(m -> {
			m.update(lastFrameTime);
		});

		long frameTime = System.currentTimeMillis() - lastFrame;
		lastFrame = System.currentTimeMillis();
		frameTimes.add(frameTime);
		tps = frameTimes.stream().mapToLong(l -> l).average().getAsDouble();
		while (frameTimes.size() > Math.max(Math.pow(1000 / tps * 12, 1.2), 1)) frameTimes.remove(0);

	}

}
