package entity;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Entity extends Pane {

	protected ImageView iv;

	public int worldX, worldY, oldWorldX, oldWorldY;
	public int speed;
	public List<Image> up, upl, down, downl, left, right, nomovel, nomover, nomoveup;
	public String direction;
	public long spriteCounter = 0;
	public int spriteNum = 0;
	protected Rectangle shape;

	public int getOldWorldX() { return oldWorldX; }
	public int getOldWorldY() { return oldWorldY; }

	public int getWorldX() { return worldX; }
	public int getWorldY() { return worldY; }

}
