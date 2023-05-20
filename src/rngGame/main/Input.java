package rngGame.main;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.shape.Polygon;
import rngGame.ui.GamePanel;
import rngGame.ui.WindowDataHolder;

// TODO: Auto-generated Javadoc
/**
 * The Class Input.
 */
public class Input {

	/**
	 * The  KeyHandlerKeyCodePair.
	 */
	private record KeyHandlerKeyCodePair(Consumer<ModKeysState> handler, KeyCode keyCode, boolean up) {}

	/**
	 * The  ModKeysState.
	 */
	public record ModKeysState(boolean isControlPressed, boolean isShiftPressed, boolean isCapsPressed,
			boolean isSuperPressed, boolean isAltPressed, boolean isAltgrPressed) {}

	/** The Constant INSTANCE. */
	private static Input INSTANCE;

	/** The key up handlers. */
	private final Map<KeyCode, List<Consumer<ModKeysState>>> keyDownHandlers = new HashMap<>(),
			keyUpHandlers = new HashMap<>();

	/** The key handlers. */
	private final Map<String, KeyHandlerKeyCodePair> keyHandlers = new HashMap<>();

	/** The s. */
	private boolean n, s;

	/** The altgr state. */
	private boolean ctrlState, shiftState, altState, superState, capsState, altgrState;

	/** The resize. */
	private GameObject move, resize;

	/** The gamepanel. */
	private GamePanel gamePanel;

	/** The scaling factor holder. */
	private final WindowDataHolder windowDataHolder;

	/**
	 * Instantiates a new input.
	 *
	 * @param windowDataHolder the window data holder
	 */
	private Input(WindowDataHolder windowDataHolder) {
		this.windowDataHolder = windowDataHolder;
		setKeyHandler("ControlDown", mod -> {
			ctrlState = true;
		}, KeyCode.CONTROL, false);
		setKeyHandler("ControlUp", mod -> {
			ctrlState = false;
		}, KeyCode.CONTROL, true);
		setKeyHandler("ShiftDown", mod -> {
			shiftState = true;
		}, KeyCode.SHIFT, false);
		setKeyHandler("ShiftUp", mod -> {
			shiftState = false;
		}, KeyCode.SHIFT, true);
		setKeyHandler("AltDown", mod -> {
			altState = true;
		}, KeyCode.ALT, false);
		setKeyHandler("AltUp", mod -> {
			altState = false;
		}, KeyCode.ALT, true);
		setKeyHandler("SuperDown", mod -> {
			superState = true;
		}, KeyCode.WINDOWS, false);
		setKeyHandler("SuperUp", mod -> {
			superState = false;
		}, KeyCode.WINDOWS, true);
		setKeyHandler("CapsDown", mod -> {
			//			Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK); gget caps state swing/awt
			capsState = true;
		}, KeyCode.CAPS, false);
		setKeyHandler("CapsUp", mod -> {
			capsState = false;
		}, KeyCode.CAPS, true);
		setKeyHandler("AltgrDown", mod -> {
			altgrState = true;
		}, KeyCode.ALT_GRAPH, false);
		setKeyHandler("AltgrUp", mod -> {
			altgrState = false;
		}, KeyCode.ALT_GRAPH, true);
		setKeyHandler("Fullscreen", mod -> {
			toggleFullScreen();
		}, KeyCode.F11, false);
		setKeyHandler("ContextMenu", mod -> {
			// TODO tbd
		}, KeyCode.CONTEXT_MENU, false);
		setKeyHandler("AlternateTickUpdate", mod -> {
			if ("true".equals(System.getProperty("alternateUpdate"))) System.setProperty("alternateUpdate", "false");
			else System.setProperty("alternateUpdate", "true");
		}, KeyCode.M, false);
		setKeyHandler("Reload", mod -> {
			if (mod.isControlPressed()) if (gamePanel != null)
				gamePanel.reload();
		}, KeyCode.R, false);
		setKeyHandler("ToggleFPSLabel", mod -> {
			if (gamePanel != null)
				gamePanel.toggleFpsLabelVisible();
		}, KeyCode.F, false);
		setKeyHandler("Teleport", mod -> {
			if ("true".equals(System.getProperty("teleport"))) System.setProperty("teleport", "false");
			else System.setProperty("teleport", "true");
		}, KeyCode.T, false);
	}

	/**
	 * Gets the only instance of Input.
	 *
	 * @param windowDataHolder the window data holder
	 * @return only instance of Input
	 */
	public static Input getInstance(WindowDataHolder windowDataHolder) {
		return INSTANCE != null ? INSTANCE : (INSTANCE = new Input(windowDataHolder));

	}

	/**
	 * New C.
	 *
	 * @param p the p
	 */
	private void newC(Polygon p) {
		p.getPoints().clear();
		p.setVisible(false);
		if (p.getParent() instanceof rngGame.visual.TextureHolder th) if (th.getLogic().getTile().poly != null) th.getLogic().getTile().poly.clear();
	}

	/**
	 * Save.
	 *
	 * @param t the t
	 * @param path the path
	 */
	private void save(Polygon t, String path) {
		File f = new File("./res/collisions/" + path);
		if (!f.exists()) try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			RandomAccessFile raf = new RandomAccessFile(f, "rws");
			raf.seek(0l);
			raf.writeInt(t.getPoints().size());
			boolean s = false;
			for (Double element: t.getPoints())
				raf.writeDouble((long) (element / ( (s = !s) ? windowDataHolder.scalingFactorX() : windowDataHolder.scalingFactorY())));
			raf.setLength(4l + t.getPoints().size() * 8l);
			raf.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(f);
	}


	/**
	 * Sets the game panel.
	 *
	 * @param gamePanel the new game panel
	 */
	protected void setGamePanel(GamePanel gamePanel) { this.gamePanel = gamePanel; }

	/**
	 * Drag detected.
	 *
	 * @param me the me
	 */
	public void dragDetected(MouseEvent me) {}

	/**
	 * Checks if is altgr pressed.
	 *
	 * @return true, if is altgr pressed
	 */
	public boolean isAltgrPressed() { return altgrState; }

	/**
	 * Checks if is alt pressed.
	 *
	 * @return true, if is alt pressed
	 */
	public boolean isAltPressed() { return altState; }

	/**
	 * Checks if is caps pressed.
	 *
	 * @return true, if is caps pressed
	 */
	public boolean isCapsPressed() { return capsState; }

	/**
	 * Checks if is ctrl pressed.
	 *
	 * @return true, if is ctrl pressed
	 */
	public boolean isCtrlPressed() { return ctrlState; }

	/**
	 * Checks if is shift pressed.
	 *
	 * @return true, if is shift pressed
	 */
	public boolean isShiftPressed() { return shiftState; }

	/**
	 * Checks if is super pressed.
	 *
	 * @return true, if is super pressed
	 */
	public boolean isSuperPressed() { return superState; }

	/**
	 * Key pressed.
	 *
	 * @param e the e
	 */
	public void keyPressed(KeyEvent e) {
		if (gamePanel != null && !gamePanel.isBlockUserInputs()) {
			KeyCode code = e.getCode();

			ModKeysState modKeysState = new ModKeysState(ctrlState, shiftState, capsState, superState, altState,
					altgrState);

			if (keyDownHandlers.containsKey(code)) keyDownHandlers.get(code).forEach(con -> con.accept(modKeysState));

			if (code == KeyCode.N) n = true;

			if (code == KeyCode.S) s = true;
		}

	}

	/**
	 * Key released.
	 *
	 * @param e the e
	 */
	public void keyReleased(KeyEvent e) {
		if (gamePanel != null && !gamePanel.isBlockUserInputs()) {

			KeyCode code = e.getCode();

			ModKeysState modKeysState = new ModKeysState(ctrlState, shiftState, capsState, superState, altState,
					altgrState);

			if (keyUpHandlers.containsKey(code)) keyUpHandlers.get(code).forEach(con -> con.accept(modKeysState));

			if ("ö".equalsIgnoreCase(e.getText())) saveMap();

			if (code == KeyCode.E) if ("true".equals(System.getProperty("edit"))) System.setProperty("edit", "false");
			else System.setProperty("edit", "true");

			if (code == KeyCode.C) if ("true".equals(System.getProperty("coll"))) System.setProperty("coll", "false");
			else System.setProperty("coll", "true");

			if (code == KeyCode.N) n = false;

			if (code == KeyCode.S) s = false;
		}
	}

	/**
	 * Key typed.
	 *
	 * @param e the e
	 */
	public void keyTyped(KeyEvent e) {}

	/**
	 * Mouse dragged.
	 *
	 * @param me the me
	 */
	public void mouseDragged(MouseEvent me) {
		if (gamePanel != null && !gamePanel.isBlockUserInputs()) if (move != null) {
			move.setX((long) (me.getSceneX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX() - move.getWidth() / 2));
			move.setY((long) (me.getSceneY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY() - move.getHeight() / 2));
		} else if (resize != null) {
			resize.setReqWidth((int) (me.getSceneX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX() - resize.getX()));
			resize.setReqHeight(
					(int) (me.getSceneY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY() - resize.getY()));
			resize.reloadTextures();
		} else {
			Node target = gamePanel.getObjectAt(me.getSceneX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX(),
					me.getSceneY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY());
			if (!gamePanel.getSelectTool().isDragging() && target instanceof rngGame.visual.TextureHolder)
				gamePanel.getSelectTool().startDrag(me);
			else if (gamePanel.getSelectTool().isDragging()) gamePanel.getSelectTool().doDrag(me);
		}
	}

	/**
	 * Mouse moved.
	 *
	 * @param me the me
	 */
	public void mouseMoved(MouseEvent me) {
		if (gamePanel != null) if (!gamePanel.getSelectTool().isDragging() && !gamePanel.getTileManager().isContextMenuShowing())
			if ("true".equals(System.getProperty("edit"))) gamePanel.getSelectTool().drawOutlines(me);
			else gamePanel.getSelectTool().undrawOutlines();
	}

	/**
	 * Mouse released.
	 *
	 * @param me the me
	 */
	public void mouseReleased(MouseEvent me) {
		if ("true".equals(System.getProperty("debug")))
			System.out.println("Released " + me);
		if (gamePanel != null) {
			Node target = gamePanel.getObjectAt(me.getSceneX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX(),
					me.getSceneY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY());
			if ("true".equals(System.getProperty("teleport"))) gamePanel.getPlayer().setPosition(
					me.getSceneX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX() - gamePanel.getPlayer().getColliBoxX(),
					me.getSceneY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY() - gamePanel.getPlayer().getColliBoxY());
			else if (target instanceof rngGame.visual.TextureHolder t) {

				if (gamePanel.getSelectTool().isDragging()) gamePanel.getSelectTool().endDrag();
				else
					if ("true".equals(System.getProperty("coll"))) if (!ctrlState || !s && !n) {
						t.getLogic().getPoly().getPoints().addAll(me.getX() - t.getLayoutX(), me.getY() - t.getLayoutY());
						if (t.getLogic().getTile().poly == null) t.getLogic().getTile().poly = new ArrayList<>();
						t.getLogic().getTile().poly.add(me.getX() - t.getLayoutX());
						t.getLogic().getTile().poly.add(me.getY() - t.getLayoutY());
					} else if (ctrlState && s) {
						String[] sp = t.getLogic().getTile().name.split("[.]");
						save(t.getLogic().getPoly(),
								gamePanel.getTileManager().getDir() + "/" + String.join(".", Arrays.copyOf(sp, sp.length - 1))
								+ ".collisionbox");
					} else if (ctrlState && n) newC(t.getLogic().getPoly());
			} else
				if (target instanceof rngGame.visual.GameObject b
						&& "true".equals(System.getProperty("coll")))
					if (!ctrlState || !s && !n)
						b.getLogic().getCollisionBox().getPoints().addAll((double) Math.round(me.getX() - b.getLayoutX()),
								(double) Math.round(me.getY() - b.getLayoutY()));
					else if (ctrlState && s) {
						String[] sp = b.getLogic().textureFiles.get(b.getLogic().getCurrentKey()).split("[.]");
						save(b.getLogic().getCollisionBox(), b.getLogic().directory + "/"
								+ String.join(".", Arrays.copyOf(sp, sp.length - 1))
								+ ".collisionbox");
					} else if (ctrlState && n) newC(b.getLogic().getCollisionBox());
		}
	}

	/**
	 * Move game object.
	 *
	 * @param go the go
	 */
	public void moveGameObject(GameObject go) {
		move = go;
	}

	/**
	 * Removes the key handler.
	 *
	 * @param name the name
	 */
	public void removeKeyHandler(String name) {
		try {
			String className = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
			KeyHandlerKeyCodePair khkcp = keyHandlers.remove(className + "|" + name);
			if (khkcp.up()) keyUpHandlers.get(khkcp.keyCode()).remove(khkcp.handler());
			else keyDownHandlers.get(khkcp.keyCode()).remove(khkcp.handler());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resize game object.
	 *
	 * @param go the go
	 */
	public void resizeGameObject(GameObject go) {
		resize = go;
	}

	/**
	 * Save map.
	 */
	public void saveMap() {
		if (gamePanel != null)
			gamePanel.saveMap();
	}

	/**
	 * Sets the key handler.
	 *
	 * @param name the name
	 * @param handler the handler
	 * @param keyCode the key code
	 * @param keyUp the key up
	 */
	public void setKeyHandler(String name, Consumer<ModKeysState> handler, KeyCode keyCode, boolean keyUp) {
		try {
			String className = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).getSimpleName();
			if (keyHandlers.containsKey(className + "|" + name)) {
				KeyHandlerKeyCodePair khkcp = keyHandlers.remove(className + "|" + name);
				if (khkcp.up()) keyUpHandlers.get(khkcp.keyCode()).remove(khkcp.handler());
				else keyDownHandlers.get(khkcp.keyCode()).remove(khkcp.handler());
			}
			keyHandlers.put(className + "|" + name, new KeyHandlerKeyCodePair(handler, keyCode, keyUp));
			if (keyUp) {
				if (keyUpHandlers.containsKey(keyCode)) keyUpHandlers.get(keyCode).add(handler);
				else {
					List<Consumer<ModKeysState>> li = new ArrayList<>();
					li.add(handler);
					keyUpHandlers.put(keyCode, li);
				}
			} else if (keyDownHandlers.containsKey(keyCode)) keyDownHandlers.get(keyCode).add(handler);
			else {
				List<Consumer<ModKeysState>> li = new ArrayList<>();
				li.add(handler);
				keyDownHandlers.put(keyCode, li);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop moveing game object.
	 *
	 * @param go the go
	 */
	public void stopMoveingGameObject(GameObject go) {
		move = null;
	}

	/**
	 * Stop resizeing game object.
	 *
	 * @param go the go
	 */
	public void stopResizeingGameObject(GameObject go) {
		resize = null;
	}

	/**
	 * Toggle full screen.
	 */
	public void toggleFullScreen() {
		if (gamePanel.isFullScreen())
			gamePanel.goIntoFullScreen();
		else gamePanel.goOutOfFullScreen();

		System.out.println(windowDataHolder.scalingFactorX() + " " + windowDataHolder.scalingFactorY());
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Input [s=" + s + ", ctrlState=" + ctrlState + ", n=" + n + "]";
	}

}
