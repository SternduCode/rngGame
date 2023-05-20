package rngGame.visual;

import java.util.*;

import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;
import rngGame.ui.WindowDataHolder;


// TODO: Auto-generated Javadoc
/**
 * The Class TileManager.
 */
public class TileManager extends Pane {

	/**
	 * The Class FakeTextureHolder.
	 */
	public class FakeTextureHolder extends TextureHolder {

		/**
		 * Instantiates a new fake texture holder.
		 *
		 * @param logic the logic
		 * @param contextMenu the context menu
		 */
		public FakeTextureHolder(rngGame.tile.TileManager.FakeTextureHolder logic, ContextMenu contextMenu) { super(logic, contextMenu); }

	}

	/** The logic. */
	private final rngGame.tile.TileManager logic;

	/** The scaling factor holder. */
	private final WindowDataHolder windowDataHolder;

	/** The context menu. */
	private final ContextMenu contextMenu;

	/** The ui group for the map. */
	private final Group group;

	/** The game panel. */
	private final GamePanel gamePanel;

	/** The map. */
	private final List<List<TextureHolder>> map;

	/**
	 * Instantiates a new tile manager.
	 *
	 * @param logic            the logic
	 * @param windowDataHolder the window data holder
	 * @param gamePanel        the game panel
	 */
	public TileManager(rngGame.tile.TileManager logic, WindowDataHolder windowDataHolder, GamePanel gamePanel) {
		this.logic = logic;

		map = new ArrayList<>();

		this.gamePanel = gamePanel;

		this.windowDataHolder = windowDataHolder;

		group = new Group();
		getChildren().add(group);

		contextMenu = new ContextMenu();

		setOnContextMenuRequested(e -> {
			if ("true".equals(System.getProperty("edit")) && !contextMenu.isShowing()) {
				System.out.println("dg");
				System.out.println();
				logic.setOnContextMenuRequested(e);
				contextMenu.getItems().clear();
				contextMenu.getItems().addAll(logic.getMenus());
				contextMenu.show(this, e.getScreenX(), e.getScreenY());
			}
		});

	}

	/**
	 * Gets the context menu.
	 *
	 * @return the contextMenu
	 */
	public ContextMenu getContextMenu() { return contextMenu; }

	/**
	 * Gets the tile at.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the tile at
	 */
	public rngGame.visual.TextureHolder getTileAt(double x, double y) {
		int	tx	= (int) Math.floor(x / windowDataHolder.blockSizeX());
		int	ty	= (int) Math.floor(y / windowDataHolder.blockSizeY());
		if (x < 0) tx--;
		if (y < 0) ty--;
		try {
			return map.get(ty).get(tx);
		} catch (IndexOutOfBoundsException e) {
			return new FakeTextureHolder(new rngGame.tile.TileManager.FakeTextureHolder(
					tx * windowDataHolder.blockSizeX() - gamePanel.getLogic().getPlayer().getX() + gamePanel.getLogic().getPlayer().getScreenX(),
					ty * windowDataHolder.blockSizeY() - gamePanel.getLogic().getPlayer().getY() + gamePanel.getLogic().getPlayer().getScreenY(),
					windowDataHolder),
					contextMenu);
		}

	}

	/**
	 * Update.
	 */
	public void update() {
		logic.setContextMenuShowing(contextMenu.isShowing());

		int	worldCol	= 0;
		int	worldRow	= 0;

		while (worldRow < logic.getMap().size()) {

			if (map.size() == worldRow)
				map.add(new ArrayList<>());


			TextureHolder th = null;
			if (map.get(worldRow).size() > worldCol)
				th = map.get(worldRow).get(worldCol);
			if (th != null) {
				th.setVisible(false);
				th.update();
			} else {
				th = new TextureHolder(logic.getMap().get(worldRow).get(worldCol),
						gamePanel.getContextMenu());
				th.setVisible(false);
				if (worldCol < map.get(worldRow).size())
					map.get(worldRow).set(worldCol, th);
				else
					map.get(worldRow).add(th);
			}

			worldCol++;

			if (worldCol == logic.getMapTileNum().get(worldRow).size()) {
				worldCol = 0;
				worldRow++;
			}
		}
	}

}
