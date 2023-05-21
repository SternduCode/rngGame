package rngGame.tile;

import java.util.*;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import rngGame.ui.GamePanel;

// TODO: Auto-generated Javadoc
/**
 * The Class TextureHolder.
 */
public class TextureHolder {

	/** The tile. */
	private Tile tile;

	/** The gp. */
	private GamePanel gp;

	/** The poly. */
	private final Polygon poly;

	/** The fps. */
	private double fps = 7.5;

	/** The y. */
	private double x, y;

	/** The menu. */
	private final Menu menu;

	/** The fps I. */
	private final MenuItem position, fpsI;

	/** The hc. */
	private int hc;

	/** The requestor. */
	private final ObjectProperty<TextureHolder> requestor;

	/** The layout X. */
	private double layoutX;

	/** The layout Y. */
	private double layoutY;

	/** The height. */
	private double width, height;

	/** The visible. */
	private boolean visible;

	/**
	 * Instantiates a new texture holder.
	 *
	 * @param tile      the tile
	 * @param gp        the gp
	 * @param layoutX   the layout X
	 * @param layoutY   the layout Y
	 * @param requestor the requestor
	 * @param x         the x
	 * @param y         the y
	 */
	public TextureHolder(Tile tile, GamePanel gp, double layoutX, double layoutY,
			ObjectProperty<TextureHolder> requestor, double x, double y) {

		menu		= new Menu("Texture Holder");
		position	= new MenuItem();
		fpsI		= new MenuItem();
		menu.setStyle("-fx-font-size: 20;");
		position.setStyle("-fx-font-size: 20;");
		fpsI.setStyle("-fx-font-size: 20;");
		fpsI.setOnAction(this::setFPS);
		menu.getItems().addAll(position, fpsI);
		this.tile	= tile;
		fps			= tile.fps;

		this.requestor = requestor;

		this.layoutX = layoutX;

		this.layoutY = layoutY;

		poly = new Polygon();
		poly.setDisable(true);
		poly.setFill(Color.color(1, 0, 0, 0.75));
		poly.getPoints()
		.addAll(tile.poly != null ? tile.poly : new ArrayList<>());
		if (tile.poly != null)
			hc = tile.poly.hashCode();

		// TODO lighing iv.setOpacity(0.5);
	}

	/**
	 * Sets the fps.
	 *
	 * @param e the new fps
	 */
	private void setFPS(ActionEvent e) {
		TextInputDialog dialog = new TextInputDialog("" + fps);
		dialog.setTitle("FPS");
		dialog.setGraphic(null);
		dialog.setHeaderText("");
		dialog.setContentText("Please enter the new FPS value:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) try {
			tile.fps = Double.parseDouble(result.get());
		} catch (NumberFormatException e2) {}

	}

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
	 * Gets the menus.
	 *
	 * @return the menus
	 */
	public Menu[] getMenus() {

		Menu[] tileManagerMenus = gp.getTileManager().getMenus();

		Menu[] menus = new Menu[tileManagerMenus.length + 1];

		System.arraycopy(tileManagerMenus, 0, menus, 0, tileManagerMenus.length);

		requestor.set(TextureHolder.this);
		position.setText("Position: " + x + " " + y);
		fpsI.setText("FPS: " + fps);

		menus[menus.length - 1] = menu;

		return menus;

	}

	/**
	 * Gets the poly.
	 *
	 * @return the poly
	 */
	public Polygon getPoly() {
		return poly;
	}

	/**
	 * Gets the tile.
	 *
	 * @return the tile
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public double getWidth() { return width; }



	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX() { return x; }

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY() { return y; }

	/**
	 * Checks if is visible.
	 *
	 * @return true, if is visible
	 */
	public boolean isVisible() { return visible; }

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
	 * Sets the tile.
	 *
	 * @param tile the new tile
	 */
	public void setTile(Tile tile) {
		this.tile=tile;
		poly.getPoints().clear();
		poly.getPoints()
		.addAll(tile.poly != null ? tile.poly : new ArrayList<>());
	}

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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() { return "TextureHolder [tile=" + tile.name + "]"; }

	/**
	 * Update.
	 */
	public void update() {

		tile.update();

		if (tile.poly != null && tile.poly.hashCode() != hc) {
			poly.getPoints().clear();
			poly.getPoints().addAll(tile.poly);
			hc = tile.poly.hashCode();
		}
		if (tile.fps != fps) fps = tile.fps;

		if ("true".equals(System.getProperty("coll")) && poly.getPoints().size() > 0)
			poly.setVisible(true);
		else
			poly.setVisible(false);

	}

}
