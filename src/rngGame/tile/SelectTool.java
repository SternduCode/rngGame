package rngGame.tile;

import java.io.*;
import java.util.*;

import com.sterndu.json.*;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import rngGame.ui.*;


// TODO: Auto-generated Javadoc
/**
 * The Class SelectTool.
 */
public class SelectTool {

	/** The dragging. */
	private boolean dragging = false;

	/** The gp. */
	private final GamePanel gamePanel;

	/** The fill. */
	private Paint fill;

	/** The y. */
	private int x, y;

	/** The visible. */
	private boolean visible;

	/** The layout Y. */
	private double width, height, layoutX, layoutY;

	/** The partial fill. */
	private Menu select, fillM, partialFill;

	/** The save as map. */
	private MenuItem copy, saveAsMap;

	/** The window data holder. */
	private final WindowDataHolder windowDataHolder;

	/**
	 * Instantiates a new select tool.
	 *
	 * @param gp               the gp
	 * @param windowDataHolder the window data holder
	 */
	public SelectTool(GamePanel gp, WindowDataHolder windowDataHolder) {
		gamePanel				= gp;
		this.windowDataHolder	= windowDataHolder;
		init();

	}

	/**
	 * Handle context menu.
	 *
	 * @param e the e
	 */
	private void handleContextMenu(ActionEvent e) {
		System.out.println(e);
		if ( ((MenuItem) e.getSource()).getParentMenu() == partialFill) {// tileM.getTileAt(globalx, globaly);
			PartialFillDialog	pfd		= new PartialFillDialog(this, ((MenuItemWTile) e.getSource()).getTile());
			Optional<Boolean>	result	= pfd.showAndWait();
			setFill(null);
			System.out.println(result);
			if (result.isPresent() && result.get()) {
				Boolean[] matrix = pfd.getMatrix();
				System.out.println(Arrays.toString(matrix));
				int matrixIdx = 0;
				for (int i = 0; i < getHeight(); i += windowDataHolder.blockSizeY())
					for (int j = 0; j < getWidth(); j += windowDataHolder.blockSizeX(), matrixIdx++)
						if (matrix[matrixIdx]) gamePanel.getTileManager().getTileAt(x + j, y + i)
						.setTile( ((MenuItemWTile) e.getSource()).getTile());
			}
		} else if ( ((MenuItem) e.getSource()).getParentMenu() == fillM) for (int i = 0; i < getWidth(); i += windowDataHolder.blockSizeX())
			for (int j = 0; j < getHeight(); j += windowDataHolder.blockSizeY()) gamePanel.getTileManager().getTileAt(x + i,
					y + j)
			.setTile( ((MenuItemWTile) e.getSource()).getTile());
		else if (e.getSource() == saveAsMap) {
			List<List<TextureHolder>>	map	= gamePanel.getTileManager().getPartOfMap(getLayoutX(), getLayoutY(), getWidth(), getHeight());
			FileChooser					fc	= new FileChooser();
			fc.setInitialDirectory(new File("./res/maps"));
			fc.getExtensionFilters().add(new ExtensionFilter("A file containing a Map", "*.json"));
			File f = fc.showSaveDialog(gamePanel.getWindow());
			if (f != null) {
				JsonObject	file	= new JsonObject();
				JsonArray	npcs	= new JsonArray();
				file.put("npcs", npcs);
				JsonArray buildings = new JsonArray();
				file.put("buildings", buildings);
				JsonObject mapO = new JsonObject();
				file.put("map", mapO);
				JsonArray startingPosition = new JsonArray(Arrays.asList(48, 48));
				mapO.put("startingPosition", startingPosition);
				mapO.put("dir", gamePanel.getTileManager().getDir());
				if (gamePanel.getTileManager().getBackgroundPath() != null)
					mapO.put("background", gamePanel.getTileManager().getBackgroundPath());
				JsonArray textures = new JsonArray();
				mapO.put("textures", textures);
				JsonArray matrix = new JsonArray();
				mapO.put("matrix", matrix);
				for (List<TextureHolder> liTh : map) {
					String line = "";
					for (TextureHolder th : liTh) {
						if (!textures.contains(th.getTile().name))
							textures.add(th.getTile().name);
						line += textures.indexOf(th.getTile().name) + " ";
					}
					matrix.add(line);
				}
				try {
					String			jsonOut	= file.toJson();
					BufferedWriter	bw		= new BufferedWriter(new FileWriter(f));
					bw.write(jsonOut);
					bw.flush();
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == copy) {
			List<List<TextureHolder>> map = gamePanel.getTileManager().getPartOfMap(getLayoutX(), getLayoutY(), getWidth(), getHeight());
			gamePanel.setClipboard(map);
			System.out.println(map);
		}

	}

	/**
	 * Inits the.
	 */
	private void init() {
		setVisible(false);
		copy = new MenuItem("Copy");
		copy.setStyle("-fx-font-size: 20;");
		copy.setOnAction(this::handleContextMenu);
		saveAsMap = new MenuItem("Save as Map");
		saveAsMap.setStyle("-fx-font-size: 20;");
		saveAsMap.setOnAction(this::handleContextMenu);
		select = new Menu("Select");
		select.setStyle("-fx-font-size: 20;");
		fillM = new Menu("Fill");
		fillM.setStyle("-fx-font-size: 20;");
		partialFill = new Menu("Partial Fill");
		partialFill.setStyle("-fx-font-size: 20;");
		select.getItems().addAll(fillM, partialFill);

	}

	/**
	 * Do drag.
	 *
	 * @param me the me
	 */
	public void doDrag(MouseEvent me) {
		if (dragging) {
			Node node = gamePanel.getTileManager().getTileAt(me.getSceneX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX(),
					me.getSceneY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY());
			if (node instanceof TextureHolder t) {
				setWidth(t.getLayoutX() + t.getWidth() - getLayoutX());
				setHeight(t.getLayoutY() + t.getHeight() - getLayoutY());
			}
			System.out.println(me.getPickResult().getIntersectedNode());
		}

	}

	/**
	 * Draw outlines.
	 *
	 * @param me the me
	 */
	public void drawOutlines(MouseEvent me) {
		setWidth(windowDataHolder.blockSizeX());
		setHeight(windowDataHolder.blockSizeY());
		double xPos = (me.getSceneX() + gamePanel.getPlayer().getX() - gamePanel.getPlayer().getScreenX()) / windowDataHolder.blockSizeX(),
				yPos = (me.getSceneY() + gamePanel.getPlayer().getY() - gamePanel.getPlayer().getScreenY()) / windowDataHolder.blockSizeY();
		if (xPos < 0) xPos -= 1;
		if (yPos < 0) yPos -= 1;
		x	= (int) xPos * windowDataHolder.blockSizeX();
		y	= (int) yPos * windowDataHolder.blockSizeY();
		setVisible(true);

	}

	/**
	 * End drag.
	 */
	public void endDrag() {
		dragging = false;
		if (getWidth() <= windowDataHolder.blockSizeX() && getHeight() <= windowDataHolder.blockSizeY()) return;// Make dragging not drag if only
		// short drag
		ContextMenu cm = gamePanel.getTileManager().getCM();
		cm.getItems().clear();
		cm.getItems().add(select);
		cm.getItems().add(copy);
		cm.getItems().add(saveAsMap);
		fillM.getItems().clear();
		partialFill.getItems().clear();
		for (Tile t : gamePanel.getTileManager().getTiles()) {
			MenuItemWTile menuitem = new MenuItemWTile(t.name,
					new ImageView(ImgUtil.resizeImage(t.images[0],
							(int) t.images[0].getWidth(), (int) t.images[0].getHeight(), 48, 48)),
					t);
			menuitem.setOnAction(this::handleContextMenu);
			fillM.getItems().add(menuitem);
			menuitem = new MenuItemWTile(t.name,
					new ImageView(ImgUtil.resizeImage(t.images[0],
							(int) t.images[0].getWidth(), (int) t.images[0].getHeight(), 48, 48)),
					t);
			menuitem.setOnAction(this::handleContextMenu);
			partialFill.getItems().add(menuitem);
		}
		fillM.getItems().sort((item1, item2) -> {
			if (item1 instanceof MenuItemWTile mi1) {
				if (item2 instanceof MenuItemWTile mi2) return mi1.getText().compareTo(mi2.getText());
				return -1;
			}
			return 1;
		});
		partialFill.getItems().sort((item1, item2) -> {
			if (item1 instanceof MenuItemWTile mi1) {
				if (item2 instanceof MenuItemWTile mi2) return mi1.getText().compareTo(mi2.getText());
				return -1;
			}
			return 1;
		});
		if (!gamePanel.isBlockUserInputs())
			cm.show(this, getLayoutX() + gamePanel.getWindow().getX(),
					getLayoutY() + gamePanel.getWindow().getY());

	}

	/**
	 * Gets the fill.
	 *
	 * @return the fill
	 */
	public Paint getFill() { return fill; }

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public double getHeight() { return height; }

	/**
	 * Gets the layout X.
	 *
	 * @return the layout X
	 */
	public double getLayoutX() { return layoutX; }

	/**
	 * Gets the layout Y.
	 *
	 * @return the layout Y
	 */
	public double getLayoutY() { return layoutY; }

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public double getWidth() { return width; }

	/**
	 * Checks if is dragging.
	 *
	 * @return true, if is dragging
	 */
	public boolean isDragging() { return dragging; }

	/**
	 * Checks if is visible.
	 *
	 * @return true, if is visible
	 */
	public boolean isVisible() { return visible; }

	/**
	 * Sets the fill.
	 *
	 * @param fill the new fill
	 */
	public void setFill(Paint fill) { this.fill = fill; }

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(double height) { this.height = height; }

	/**
	 * Sets the layout X.
	 *
	 * @param layoutX the new layout X
	 */
	public void setLayoutX(double layoutX) { this.layoutX = layoutX; }

	/**
	 * Sets the layout Y.
	 *
	 * @param layoutY the new layout Y
	 */
	public void setLayoutY(double layoutY) { this.layoutY = layoutY; }

	/**
	 * Sets the visible.
	 *
	 * @param visible the new visible
	 */
	public void setVisible(boolean visible) { this.visible = visible; }

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(double width) { this.width = width; }

	/**
	 * Start drag.
	 *
	 * @param me the me
	 */
	public void startDrag(MouseEvent me) {
		dragging = true;
		drawOutlines(me);
	}

	/**
	 * Undraw outlines.
	 */
	public void undrawOutlines() {
		if (!gamePanel.getTileManager().isContextMenuShowing()
				|| gamePanel.getTileManager().getContextMenuAnchorX() != getLayoutX() + gamePanel.getWindow().getX()
				|| gamePanel.getTileManager().getContextMenuAnchorY() != getLayoutY() + gamePanel.getWindow().getY())
			setVisible(false);
	}

	/**
	 * Update.
	 */
	public void update() {
		boolean moveCm = false;
		if (gamePanel.getTileManager().isContextMenuShowing()
				&& gamePanel.getTileManager().getContextMenuAnchorX() == getLayoutX() + gamePanel.getWindow().getX()
				&& gamePanel.getTileManager().getContextMenuAnchorY() == getLayoutY() + gamePanel.getWindow().getY())
			moveCm = true;
		double screenX = x - gamePanel.getPlayer().getX() + gamePanel.getPlayer().getScreenX();
		double screenY = y - gamePanel.getPlayer().getY() + gamePanel.getPlayer().getScreenY();
		setLayoutX(screenX);
		setLayoutY(screenY);
		if (moveCm) {
			gamePanel.getTileManager().setContextMenuAnchorX(screenX + gamePanel.getWindow().getX());
			gamePanel.getTileManager().setContextMenuAnchorY(screenY + gamePanel.getWindow().getY());
		}
	}
}
