package rngGame.ui;

import java.util.*;
import java.util.Map;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.stage.Window;
import rngGame.entity.Player;
import rngGame.main.GameObject;
import rngGame.tile.*;


// TODO: Auto-generated Javadoc
/**
 * The Class GamePanel.
 */
public class GamePanel {

	/** The visual game panel. */
	private rngGame.visual.GamePanel visualGamePanel;

	/** The points. */
	private Map<Point2D, Circle> points;

	/** The player. */
	private Player player;

	/** The overlay. */
	private AnimatedImage overlay;

	/** The action button. */
	private ActionButton actionButton;

	/** The tile manager. */
	private final TileManager tileManager;

	/** The tab menu. */
	private TabMenu tabMenu;

	/** The speak bubble. */
	private AnimatedImage speakBubble;

	/** The select tool. */
	private final SelectTool selectTool;

	/** The tps. */
	private final int tps;

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

	/**
	 * Instantiates a new game panel.
	 *
	 * @param windowDataHolder the window data holder
	 */
	public GamePanel(WindowDataHolder windowDataHolder) {

		this.windowDataHolder = windowDataHolder;

		fpsLabelVisible = false;

		blockUserInputs = false;

		tps = 0;

		visualGamePanel = null;

		speakBubbleElements = new ArrayList<>();

		selectTool = new SelectTool(this, windowDataHolder);

		tileManager = new TileManager(this, windowDataHolder);

	}

	/**
	 * Gets the action button.
	 *
	 * @return the action button
	 */
	public ActionButton getActionButton() { return actionButton; }

	/**
	 * Gets the clipboard.
	 *
	 * @return the clipboard
	 */
	public List<List<TextureHolder>> getClipboard() { return null; }

	/**
	 * Gets the difficulty.
	 *
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() { return difficulty; }

	/**
	 * Gets the object at.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 *
	 * @return the object at x and y
	 */
	public Node getObjectAt(double x, double y) {
		List<Node> nodes = visualGamePanel.getLayerGroup().getChildren().stream()
				.filter(n -> n.contains(x - ((GameObject) n).getX(), y - ((GameObject) n).getY()) && n.isVisible())
				.toList();
		if (nodes.size() != 0) return nodes.get(nodes.size() - 1);
		if (x < 0 || y < 0) return null;
		return tileManager.getTileAt(x, y);

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
	public int getTps() { return tps; }

	/**
	 * Gets the window.
	 *
	 * @return the window
	 */
	public Window getWindow() { return visualGamePanel.getScene().getWindow(); }

	/**
	 * Go into full screen.
	 */
	public void goIntoFullScreen() { visualGamePanel.goIntoFullScreen(); }

	/**
	 * Go out of full screen.
	 */
	public void goOutOfFullScreen() { visualGamePanel.goOutOfFullScreen(); }

	/**
	 * Checks if is block user inputs.
	 *
	 * @return true, if is block user inputs
	 */
	public boolean isBlockUserInputs() { return visualGamePanel.isInLoadingScreen() || blockUserInputs; }

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

	/**
	 * Save map.
	 */
	public void saveMap() {}

	/**
	 * Sets the blcokuser inputs.
	 *
	 * @param blockUserInputs the new blcokuser inputs
	 */
	public void setBlcokuserInputs(boolean blockUserInputs) {
		this.blockUserInputs = blockUserInputs;
	}

	/**
	 * Sets the clipboard.
	 *
	 * @param map the new clipboard
	 */
	public void setClipboard(List<List<TextureHolder>> map) {}

	/**
	 * Sets the visual game panel.
	 *
	 * @param gamePanel the new visual game panel
	 */
	public void setVisualGamePanel(rngGame.visual.GamePanel gamePanel) { visualGamePanel = gamePanel; }

	/**
	 * Toggle fps label visible.
	 */
	public void toggleFpsLabelVisible() { fpsLabelVisible = !fpsLabelVisible; }

	/**
	 * Update.
	 */
	public void update() {

	}

}
