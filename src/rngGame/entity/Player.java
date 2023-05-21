package rngGame.entity;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.beans.property.ObjectProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import rngGame.main.Input;
import rngGame.tile.TileManager;
import rngGame.ui.*;

// TODO: Auto-generated Javadoc
/**
 * A class that defines the Player.
 *
 * @author Sterndu
 * @author ICEBLUE
 * @author neo30
 */

public class Player extends Entity {

	/** The size. */
	private final int size = 64; // The value that reqHeight will be set to

	/** The p. */
	private final AtomicBoolean p = new AtomicBoolean(false);

	/** The w. */
	private final AtomicBoolean w = new AtomicBoolean(false);

	/** The a. */
	private final AtomicBoolean a = new AtomicBoolean(false);

	/** The s. */
	private final AtomicBoolean s = new AtomicBoolean(false);

	/** The d. */
	private final AtomicBoolean d = new AtomicBoolean(false);

	/** The colli box height. */
	private final double colliBoxX = 33, colliBoxY = 45, colliBoxWidth = 31, colliBoxHeight = 20;

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
	 * @param requestor Is used to know on what the {@link TileManager#getContextMenu() ContextMenu} was triggered
	 * @param windowDataHolder the window data holder
	 */
	public Player(GamePanel gamePanel, ObjectProperty<? extends Entity> requestor, WindowDataHolder windowDataHolder) {
		super(null, 3 * 60, gamePanel, "player", null, requestor, windowDataHolder);
		setCurrentKey("down");

		fps = 10.5;

		reqWidth = (int) ((reqHeight = getSize()) * 1.5); // Set reqHeight to 64 and reqWidth to 96; Player size is
		// rectangular in this case

		gamepanel = gamePanel;

		setPosition(0, 0); // Put player on upper left corner of the map; can be overridden in map file

		getPlayerImage();

		textureFiles.forEach((key, file) -> {
			collisionBoxes.put(key, new Polygon()); // Add collisionBoxes for all textures
		});

		generateCollisionBox();

		/*
		 * KeyHandlers for: P > sets the Player invisible W > move forward A > move left
		 * S > move down D > move right
		 */

		Input.getInstance(null).setKeyHandler("p", mod -> {
			p.set(!p.get());
		}, KeyCode.P, false);
		Input.getInstance(null).setKeyHandler("wDOWN", mod -> {
			w.set(true);
		}, KeyCode.W, false);
		Input.getInstance(null).setKeyHandler("aDOWN", mod -> {
			a.set(true);
		}, KeyCode.A, false);
		Input.getInstance(null).setKeyHandler("sDOWN", mod -> {
			if (!mod.isControlPressed()) s.set(true);
		}, KeyCode.S, false);
		Input.getInstance(null).setKeyHandler("dDOWN", mod -> {
			d.set(true);
		}, KeyCode.D, false);
		Input.getInstance(null).setKeyHandler("wUP", mod -> {
			w.set(false);
		}, KeyCode.W, true);
		Input.getInstance(null).setKeyHandler("aUP", mod -> {
			a.set(false);
		}, KeyCode.A, true);
		Input.getInstance(null).setKeyHandler("sUP", mod -> {
			s.set(false);
		}, KeyCode.S, true);
		Input.getInstance(null).setKeyHandler("dUP", mod -> {
			d.set(false);
		}, KeyCode.D, true);
	}

	/**
	 * Sets all collisionBoxes to the correct size and position.
	 */
	public void generateCollisionBox() {


		collisionBoxes.forEach((key, poly) -> {
			poly.getPoints().clear();
			poly.setFill(Color.color(1, 0, 1, 0.75));
			poly.getPoints().addAll(colliBoxX * gamepanel.getWindowDataHolder().scalingFactorX() - 0.5,
					colliBoxY * gamepanel.getWindowDataHolder().scalingFactorY() - 0.5,
					colliBoxX * gamepanel.getWindowDataHolder().scalingFactorX() - 0.5,
					(colliBoxY + colliBoxHeight) * gamepanel.getWindowDataHolder().scalingFactorY() + 0.5,
					(colliBoxX + colliBoxWidth) * gamepanel.getWindowDataHolder().scalingFactorX() + 0.5,
					(colliBoxY + colliBoxHeight) * gamepanel.getWindowDataHolder().scalingFactorY() + 0.5,
					(colliBoxX + colliBoxWidth) * gamepanel.getWindowDataHolder().scalingFactorX() + 0.5,
					colliBoxY * gamepanel.getWindowDataHolder().scalingFactorY() - 0.5);
		});
	}

	/**
	 * Gets the colli box height.
	 *
	 * @return the colli box height
	 */
	public double getColliBoxHeight() { return colliBoxHeight * gamepanel.getWindowDataHolder().scalingFactorY(); }

	/**
	 * Gets the colli box width.
	 *
	 * @return the colli box width
	 */
	public double getColliBoxWidth() { return colliBoxWidth * gamepanel.getWindowDataHolder().scalingFactorX(); }

	/**
	 * Gets the colli box X.
	 *
	 * @return the colli box X
	 */
	public double getColliBoxX() { return colliBoxX * gamepanel.getWindowDataHolder().scalingFactorX(); }

	/**
	 * Gets the colli box Y.
	 *
	 * @return the colli box Y
	 */
	public double getColliBoxY() { return colliBoxY * gamepanel.getWindowDataHolder().scalingFactorY(); }

	/**
	 * Gets the player image.
	 *
	 * @return the player image
	 */
	public void getPlayerImage() {

		try {
			origHeight = reqHeight;
			origWidth = reqWidth;

			/*
			 * Load the textures for all states of the player
			 */

			getAnimatedImages("idledown", "Stehen.png");
			getAnimatedImages("idledownL", "Stehen2.png");
			getAnimatedImages("right", "LaufenRechts.png");
			getAnimatedImages("left", "LaufenLinks.png");
			getAnimatedImages("down", "LaufenRunter.png");
			getAnimatedImages("downL", "LaufenRunterL.png");
			getAnimatedImages("up", "LaufenHochL.png");
			getAnimatedImages("upL", "LaufenHochL.png");
			getAnimatedImages("idleup", "IdleUp.png");
			getAnimatedImages("idleupL", "IdleUp.png");
			getAnimatedImages("idle", "IdleRight.png");
			getAnimatedImages("idleL", "IdleLeft.png");

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
		return "Player [size=" + getSize() + ", p=" + p + ", w=" + w + ", a=" + a + ", s=" + s
				+ ", d=" + d + ", screenX=" + getScreenX() + ", screenY=" + getScreenY() + ", oldX=" + oldX
				+ ", oldY=" + oldY + ", speed=" + getSpeed() + ", x=" + getX() + ", y=" + getY() + ", fps="
				+ fps + ", collisionBoxes=" + collisionBoxes + ", directory="
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

		if (gamepanel.isBlockUserInputs()) {
			w.set(false);
			a.set(false);
			s.set(false);
			d.set(false);
		}

		if (w.get()) { // Hoch
			if ("left".equals(getCurrentKey()) || getCurrentKey().endsWith("L")) setCurrentKey("upL");
			else setCurrentKey("up");
			y -= updateSpeed * gamepanel.getWindowDataHolder().scalingFactorY();
		} else if (s.get()) { // Runter
			if ("left".equals(getCurrentKey()) || getCurrentKey().endsWith("L")) setCurrentKey("downL");
			else setCurrentKey("down");
			y += updateSpeed * gamepanel.getWindowDataHolder().scalingFactorY();
		} else if (a.get()) { // Links
			setCurrentKey("left");
			x -= updateSpeed * gamepanel.getWindowDataHolder().scalingFactorX();
		} else if (d.get()) { // Rechts
			setCurrentKey("right");
			x += updateSpeed * gamepanel.getWindowDataHolder().scalingFactorX();
		} else if (lastKey.contains("down")) { // Idle Runter
			if (lastKey.endsWith("L") || lastKey.contains("left")) setCurrentKey("idledownL");
			else setCurrentKey("idledown");
		} else if (lastKey.contains("up")) { // Idle Hoch
			if (lastKey.endsWith("L") || lastKey.contains("left")) setCurrentKey("idleupL");
			else setCurrentKey("idleup");
		} else if (lastKey.endsWith("L") || lastKey.contains("left")) setCurrentKey("idleL");
		else setCurrentKey("idle");

		super.update(milis);

		setLayoutX(getScreenX());
		setLayoutY(getScreenY());

		getCollisionBox().setTranslateX( (x - oldX) * 3);
		getCollisionBox().setTranslateY( (y - oldY) * 3);

		if (isVisible() && p.get()) setVisible(false);

		gamepanel.getBuildings().forEach(b -> {
			if (b.collides(this)) {
				x = oldX;
				y = oldY;
			}
		});

		gamepanel.getNpcs().forEach(b -> {
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

		int frameIndex = getImage().getFrameIndex();

		if (frameIndex < getImage().frameCount()) {
			screenX	= (int) (gamepanel.getWindowDataHolder().gameWidth() / 2 - getImage().getFrameAt(frameIndex).getWidth() / 2);
			screenY	= (int) (gamepanel.getWindowDataHolder().gameHeight() / 2 - getImage().getFrameAt(frameIndex).getHeight() / 2);
		}

	}
}
