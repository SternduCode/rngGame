package rngGame.tile;

import java.util.*;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import rngGame.main.*;

public class TextureHolder extends Pane {

	private Tile tile;

	@SuppressWarnings("unused")
	private SpielPanel gp;

	private final ImageView iv;
	private final Polygon poly;

	private double fps = 7.5;

	public long spriteCounter = 0;
	public int spriteNum = 0;
	private double x, y;

	private final Menu menu;

	private final MenuItem position, fpsI;

	private int hc;

	public TextureHolder(Tile tile, SpielPanel gp, double layoutX, double layoutY, ContextMenu cm,
			ObjectProperty<TextureHolder> requestor, double x, double y) {
		menu = new Menu("Texture Holder");
		position = new MenuItem();
		fpsI = new MenuItem();
		fpsI.setOnAction(this::setFPS);
		menu.getItems().addAll(position,fpsI);
		setOnContextMenuRequested(e -> {
			if (System.getProperty("edit").equals("true")) {
				requestor.set(TextureHolder.this);
				Input.getInstance().comp.add(TextureHolder.this.tile.images.get(0));
				position.setText("Position: "+x+" "+y);
				fpsI.setText("FPS: "+fps);
				cm.getItems().clear();
				cm.getItems().addAll(gp.getTileM().getMenus());
				cm.getItems().add(menu);
				cm.show(TextureHolder.this, e.getScreenX(), e.getScreenY());
			}
		});
		this.tile = tile;
		fps = tile.fps;
		iv = new ImageView(tile.images.get(0));
		// tile.Image.getPixelReader();
		// new WritableImage(null, layoutX, layoutY)
		iv.setDisable(true);
		getChildren().add(iv);
		setLayoutX(layoutX);
		setLayoutY(layoutY);

		poly = new Polygon();
		poly.setDisable(true);
		poly.setFill(Color.color(1, 0, 0, 0.75));
		poly.getPoints()
		.addAll(tile.poly != null ? tile.poly : new ArrayList<>());
		if (tile.poly != null)
			hc = tile.poly.hashCode();
		getChildren().add(poly);

		// TODO lighing iv.setOpacity(0.5);
	}

	private void setFPS(ActionEvent e) {
		TextInputDialog dialog = new TextInputDialog("" + fps);
		dialog.setTitle("FPS");
		dialog.setHeaderText("");
		dialog.setContentText("Please enter the new FPS value:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) try {
			tile.fps = Double.parseDouble(result.get());
		} catch (NumberFormatException e2) {
		}
	}

	public Polygon getPoly() {
		return poly;
	}

	public Tile getTile() {
		return tile;
	}



	public double getX() { return x; }

	public double getY() { return y; }

	public void setTile(Tile tile) {
		this.tile=tile;
		poly.getPoints().clear();
		poly.getPoints()
		.addAll(tile.poly != null ? tile.poly : new ArrayList<>());
		iv.setImage(tile.images.get(0));
	}

	public void update() {

		if (tile.poly != null && tile.poly.hashCode() != hc) {
			poly.getPoints().clear();
			poly.getPoints().addAll(tile.poly);
			hc = tile.poly.hashCode();
		}
		if (tile.fps != fps) fps = tile.fps;

		if (System.currentTimeMillis() > spriteCounter + 1000 / fps) {
			spriteCounter = System.currentTimeMillis();
			spriteNum++;
		}

		if (spriteNum >= tile.images.size()) spriteNum = 0;
		iv.setImage(tile.images.get(spriteNum));

		if (System.getProperty("coll").equals("true") && poly.getPoints().size() > 0)
			poly.setVisible(true);
		else
			poly.setVisible(false);
	}

}
