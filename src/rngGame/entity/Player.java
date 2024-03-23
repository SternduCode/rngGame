package rngGame.entity;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import rngGame.main.*;
import rngGame.tile.TileManager;
import rngGame.visual.GamePanel;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class that defines the Player.
 *
 * @author Sterndu
 * @author ICEBLUE
 * @author neo30
 */

public class Player extends Entity {

	/** The size. */
	private final int size = 128; // The value that reqHeight will be set to

	/** The p. */
	private final AtomicBoolean p = new AtomicBoolean(false);

	/** The colli box height. */
	private final double colliBoxX = 40, colliBoxY = 90, colliBoxWidth = 46.5, colliBoxHeight = 40;

	private final AtomicBoolean w = new AtomicBoolean(false);
	private final AtomicBoolean a = new AtomicBoolean(false);
	private final AtomicBoolean s = new AtomicBoolean(false);
	private final AtomicBoolean d = new AtomicBoolean(false);

	private Direction direction = Direction.E;

	/** The screen X. */
	private int screenX; // the players X position in the window

	/** The screen Y. */
	private int screenY; // the players Y position in the window

	/** The old Y. */
	private double oldX, oldY; // used for collision detection

	/**
	 * Player is not defined in map file but some attributes of it are.
	 *
	 * @param gamePanel A reference to the {@link GamePanel}
	 * @param cm        A reference to the {@link TileManager#getCM() ContextMenu} via {@link GamePanel#getTileManager()}.
	 * @param requestor Is used to know on what the {@link TileManager#getCM() ContextMenu} was triggered
	 */
	public Player(GamePanel gamePanel, ContextMenu cm, ObjectProperty<? extends Entity> requestor) {
		super(null, 6 * 60, gamePanel, "player", null, cm, requestor);
		setCurrentKey("E_idle");

		fps = 10.5;

		reqWidth = ((reqHeight = getSize())); // Set reqHeight to 64 and reqWidth to 96; Player size is
		// rectangular in this case

		gamepanel = gamePanel;

		screenX	= WindowManager.INSTANCE.getGameWidth() / 2 - getSize() / 2;	// Place the player in the middle of the screen
		screenY	= WindowManager.INSTANCE.getGameHeight() / 2 - getSize() / 2;

		setPosition(0, 0); // Put player on upper left corner of the map; can be overridden in map file

		getPlayerImage();

		textureFiles.forEach((key, file) -> {
			collisionBoxes.put(key, new Polygon()); // Add collisionBoxes for all textures
		});

		generateCollisionBox();

		/*
		 * KeyHandler P > sets the Player invisible
		 */
		Input.getInstance().setKeyHandler("p", mod -> p.set(!p.get()), KeyCode.P, false);

		Input.getInstance().setKeyHandler("w-d", mod -> w.set(true), KeyCode.W, false);
		Input.getInstance().setKeyHandler("a-d", mod -> a.set(true), KeyCode.A, false);
		Input.getInstance().setKeyHandler("s-d", mod -> s.set(true), KeyCode.S, false);
		Input.getInstance().setKeyHandler("d-d", mod -> d.set(true), KeyCode.D, false);

		Input.getInstance().setKeyHandler("w-u", mod -> w.set(false), KeyCode.W, true);
		Input.getInstance().setKeyHandler("a-u", mod -> a.set(false), KeyCode.A, true);
		Input.getInstance().setKeyHandler("s-u", mod -> s.set(false), KeyCode.S, true);
		Input.getInstance().setKeyHandler("d-u", mod -> d.set(false), KeyCode.D, true);
	}

	/**
	 * Sets all collisionBoxes to the correct size and position.
	 */
	public void generateCollisionBox() {


		collisionBoxes.forEach((key, poly) -> {
			poly.getPoints().clear();
			poly.setFill(Color.color(1, 0, 1, 0.75));
			poly.getPoints().addAll(colliBoxX * WindowManager.INSTANCE.getScalingFactorX() - 0.5,
					colliBoxY * WindowManager.INSTANCE.getScalingFactorY() - 0.5, colliBoxX * WindowManager.INSTANCE.getScalingFactorX() - 0.5,
					(colliBoxY + colliBoxHeight) * WindowManager.INSTANCE.getScalingFactorY() + 0.5,
					(colliBoxX + colliBoxWidth) * WindowManager.INSTANCE.getScalingFactorX() + 0.5,
					(colliBoxY + colliBoxHeight) * WindowManager.INSTANCE.getScalingFactorY() + 0.5,
					(colliBoxX + colliBoxWidth) * WindowManager.INSTANCE.getScalingFactorX() + 0.5,
					colliBoxY * WindowManager.INSTANCE.getScalingFactorY() - 0.5);
		});
	}

	/**
	 * Gets the colli box height.
	 *
	 * @return the colli box height
	 */
	public double getColliBoxHeight() { return colliBoxHeight * WindowManager.INSTANCE.getScalingFactorY(); }

	/**
	 * Gets the colli box width.
	 *
	 * @return the colli box width
	 */
	public double getColliBoxWidth() { return colliBoxWidth * WindowManager.INSTANCE.getScalingFactorX(); }

	/**
	 * Gets the colli box X.
	 *
	 * @return the colli box X
	 */
	public double getColliBoxX() { return colliBoxX * WindowManager.INSTANCE.getScalingFactorX(); }

	/**
	 * Gets the colli box Y.
	 *
	 * @return the colli box Y
	 */
	public double getColliBoxY() { return colliBoxY * WindowManager.INSTANCE.getScalingFactorY(); }

	/**
	 * Gets the player image.
	 *
	 * @return the player image
	 */
	public void getPlayerImage() {

		try {
			origHeight = 64;//reqHeight;
			origWidth = 96;//reqWidth;

			/*
			 * Load the textures for all states of the player
			 */



//			//Norden
			getAnimatedImages("N_run", "N_idle.gif");
			getAnimatedImages("N_idle", "N_idle.gif");

			getAnimatedImages("NE_run", "NE_idle.gif");
			getAnimatedImages("NE_idle", "NE_idle.gif");

			getAnimatedImages("NW_run", "NW_idle.gif");
			getAnimatedImages("NW_idle", "NW_idle.gif");

			//Seiten
			getAnimatedImages("E_run", "E_idle.gif");
			getAnimatedImages("E_idle", "E_idle.gif");

			getAnimatedImages("W_run", "W_idle.gif");
			getAnimatedImages("W_idle", "W_idle.gif");

			//Sueden
			getAnimatedImages("S_run", "S_idle.gif");
			getAnimatedImages("S_idle", "S_idle.gif");

			getAnimatedImages("SE_run", "SE_idle.gif");
			getAnimatedImages("SE_idle", "SE_idle.gif");

			getAnimatedImages("SW_run", "SW_idle.gif");
			getAnimatedImages("SW_idle", "SW_idle.gif");





		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the screen X.
	 *
	 * @return the players X position in the window
	 */
	public int getScreenX() {
		return screenX;
	}

	/**
	 * Gets the screen Y.
	 *
	 * @return the players Y position in the window
	 */
	public int getScreenY() {
		return screenY;
	}

	/**
	 * Gets the size.
	 *
	 * @return the height of the Player texture
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets the x.
	 *
	 * @return the players X position in the map
	 */
	@Override
	public double getX() { return (long) x; }

	/**
	 * Gets the y.
	 *
	 * @return the players Y position in the map
	 */
	@Override
	public double getY() { return (long) y; }

	/**
	 * Sets the players map position to {@code x} and {@code y}.
	 *
	 * @param x the players X position in the map
	 * @param y the players Y position in the map
	 */
	@Override
	public void setPosition(double x, double y) {
		setPosition(new double[] {x, y});
	}

	/**
	 * Sets the players map position to {@code x} and {@code y}.
	 *
	 * @param position an array containing the players x ({@code position[0]}) and y
	 *                 ({@code position[1]}) positions
	 */
	public void setPosition(double[] position) {
		x = (long) position[0];
		y = (long) position[1];
		oldX = x;
		oldY = y;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Player [size=" + getSize() + ", p=" + p + ", screenX=" + getScreenX() + ", screenY=" + getScreenY() + ", oldX=" + oldX
				+ ", oldY=" + oldY + ", speed=" + getSpeed() + ", x=" + getX() + ", y=" + getY() + ", fps="
				+ fps + ", images=" + getImages() + ", collisionBoxes=" + collisionBoxes + ", directory="
				+ directory + ", layer=" + getLayer() + ", extraData=" + extraData + ", slave=" + isSlave()
				+ ", textureFiles=" + textureFiles + ", reqWidth=" + getReqWidth() + ", reqHeight="
				+ getReqHeight() + ", origWidth=" + getOrigWidth() + ", origHeight=" + getOrigHeight()
				+ ", animationCounter=" + animationCounter + ", animationNum=" + animationNum
				+ ", background=" + isBackground() + "]";
	}


	/**
	 * Update.
	 *
	 * @param milis the milis
	 */
	@Override
	public void update(long milis) {

		double updateSpeed = speed / 1000 * milis;

		String lastKey = getCurrentKey();

		if (w.get() || a.get() || s.get() || d.get()) {
			int xDir = 0;
			int yDir = 0;
			if (w.get()) {
				yDir -= 1;
			}
			if (s.get()) {
				yDir += 1;
			}
			if (d.get()) {
				xDir += 1;
			}
			if (a.get()) {
				xDir -= 1;
			}
			direction = Direction.Companion.getDirectionFromAngle(Math.atan2(yDir, xDir));
			x += updateSpeed * xDir * WindowManager.INSTANCE.getScalingFactorX();
			y += updateSpeed * yDir * WindowManager.INSTANCE.getScalingFactorY();
			switch (direction){
				case N -> setCurrentKey("N_run");
				case NE -> setCurrentKey("NE_run");
				case NW -> setCurrentKey("NW_run");
				case E -> setCurrentKey("E_run");
				case W -> setCurrentKey("W_run");
				case S -> setCurrentKey("S_run");
				case SE -> setCurrentKey("SE_run");
				case SW -> setCurrentKey("SW_run");
			}
		} else {
			switch (direction){
				case N -> setCurrentKey("N_idle");
				case NE -> setCurrentKey("NE_idle");
				case NW -> setCurrentKey("NW_idle");
				case E -> setCurrentKey("E_idle");
				case W -> setCurrentKey("W_idle");
				case S -> setCurrentKey("S_idle");
				case SE -> setCurrentKey("SE_idle");
				case SW -> setCurrentKey("SW_idle");
			}
		}

		

		super.update(milis);

		setLayoutX(getScreenX());
		setLayoutY(getScreenY());

		getCollisionBox().setTranslateX( (x - oldX) * 3);
		getCollisionBox().setTranslateY( (y - oldY) * 3);

		if (isVisible() && p.get()) setVisible(false);

		gamepanel.getLgp().getBuildings().forEach(b -> {
			if (b.collides(this)) {
				x = oldX;
				y = oldY;
			}
		});

		gamepanel.getLgp().getNpcs().forEach(b -> {
			if (b.collides(this)) {
				x = oldX;
				y = oldY;
			}
		});

		if (gamepanel.getTileManager().collides(this)) {
			x = oldX;
			y = oldY;
		}

		oldX = x;
		oldY = y;

		screenX	= (int) (WindowManager.INSTANCE.getGameWidth() / 2 - iv.getImage().getWidth() / 2);
		screenY	= (int) (WindowManager.INSTANCE.getGameHeight() / 2 - iv.getImage().getHeight() / 2);

	}
}
