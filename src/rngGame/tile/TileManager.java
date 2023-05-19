package rngGame.tile;

import java.io.*;
import java.util.*;

import com.sterndu.json.*;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.MenuItem;
import javafx.scene.image.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.shape.Shape;
import rngGame.buildings.*;
import rngGame.entity.*;
import rngGame.main.GameObject;
import rngGame.ui.*;


// TODO: Auto-generated Javadoc
/**
 * The Class TileManager.
 */
public class TileManager {

	/**
	 * The Class FakeTextureHolder for making tiles outside of the map.
	 */
	private class FakeTextureHolder extends TextureHolder {

		/**
		 * Instantiates a new fake texture holder.
		 *
		 * @param x the x coordinate
		 * @param y the y coordinate
		 */
		public FakeTextureHolder(double x, double y) {
			super(new Tile("", "", null) {

				{
					images = new Image[]{new Image(new ByteArrayInputStream(new byte[0]))};
				}

			}, null, x, y, null, null, 0, 0);
			setWidth(windowDataHolder.blockSizeX());
			setHeight(windowDataHolder.blockSizeY());

		}

		/**
		 * Sets the tile.
		 *
		 * @param tile the new tile
		 */
		@Override
		public void setTile(Tile tile) {}

	}

	/** The path to the map file. */
	private String path;

	/** The GamePanel. */
	private final GamePanel gamePanel;

	/** The tiles. */
	private final List<Tile> tiles;

	/** The map tile numbers. */
	List<List<Integer>> mapTileNum;

	/** Is generated?. */
	private boolean generated;

	/** The map. */
	private List<List<TextureHolder>> map;

	/** The requester for the context menu containing the TextureHolder. */
	private final ObjectProperty<TextureHolder> requester = new ObjectPropertyBase<>() {

		@Override
		public Object getBean() { return this; }

		@Override
		public String getName() { return "requester"; }

	};

	/** The requester for the context menu containing the Building. */
	private final ObjectProperty<Building> requesterB = new ObjectPropertyBase<>() {

		@Override
		public Object getBean() { return this; }

		@Override
		public String getName() { return "requester"; }

	};

	/** The requester for the context menu containing the NPC. */
	private final ObjectProperty<NPC> requestorN = new ObjectPropertyBase<>() {

		@Override
		public Object getBean() { return this; }

		@Override
		public String getName() { return "requester"; }

	};

	/** The requester for the context menu containing the MobRan. */
	private final ObjectProperty<MobRan> requestorM = new ObjectPropertyBase<>() {

		@Override
		public Object getBean() { return this; }

		@Override
		public String getName() { return "requester"; }

	};

	/** The buildings. */
	private List<Building> buildings;

	/** The npcs. */
	private List<NPC> npcs;

	/** The background path, the path to the folder containing the tile textures and the path to the exit map. */
	private String exitMap, dir, backgroundPath;

	/** The exit position, the starting position and the position one starts at when using the exit. */
	private double[] startingPosition, exitStartingPosition, exitPosition, origStartingPosition;

	/** The player layer. */
	private int playerLayer;

	/** The mobs. */
	private List<MobRan> mobs;

	/** The background music. */
	private String backgroundMusic;

	/** The overlay. */
	private String overlay;

	/** The window data holder. */
	private final WindowDataHolder windowDataHolder;

	/** The context menu showing. */
	private boolean contextMenuShowing;

	/**
	 * Instantiates a new tile manager.
	 *
	 * @param gamePanel the GamePanel
	 * @param windowDataHolder the window data holder
	 */
	public TileManager(GamePanel gamePanel, WindowDataHolder windowDataHolder) {

		this.windowDataHolder = windowDataHolder;

		this.gamePanel = gamePanel;

		tiles		= new ArrayList<>();
		map			= new ArrayList<>();
		mapTileNum	= new ArrayList<>();

		npcs		= new ArrayList<>();
		buildings	= new ArrayList<>();
		mobs		= new ArrayList<>();

	}

	/**
	 * Collides?.
	 *
	 * @param collidable the collidable
	 *
	 * @return true, if collides
	 */
	public boolean collides(GameObject collidable) {

		List<TextureHolder>	ths	= new ArrayList<>();

		int x = (int) collidable.getX() / windowDataHolder.blockSizeX(), y = (int) collidable.getY() / windowDataHolder.blockSizeY();

		for (int i = -2; i < 3; i++) for (int j = -2; j < 3; j++)
			if (x + i >= 0 && y + j >= 0 && y + j < map.size() && x + i < map.get(y + j).size()) ths.add(map.get(y + j).get(x + i));

		for (TextureHolder th : ths)
			if (th != null && th.getPoly().getPoints().size() > 0) {
				Shape intersect = Shape.intersect(collidable.getCollisionBox(), th.getPoly());
				if (!intersect.getBoundsInLocal().isEmpty()) return true;
			}

		return false;

	}

	/**
	 * Gets the background music.
	 *
	 * @return the background music
	 */
	public String getBackgroundMusic() {
		return backgroundMusic;
	}

	/**
	 * Gets the background path.
	 *
	 * @return the background path
	 */
	public String getBackgroundPath() { return backgroundPath; }

	/**
	 * Gets the buildings from map.
	 *
	 * @return the buildings from map
	 */
	public List<Building> getBuildingsFromMap() { return buildings; }

	/**
	 * Gets the directory where the tile textures are stored.
	 *
	 * @return the directory where the tile textures are stored
	 */
	public String getDir() { return dir; }

	/**
	 * Gets the exit map path.
	 *
	 * @return the exit map path
	 */
	public String getExitMap() { return exitMap; }

	/**
	 * Gets the exit position.
	 *
	 * @return the exit position
	 */
	public double[] getExitPosition() { return exitPosition; }

	/**
	 * Gets position one starts at when using the exit.
	 *
	 * @return the position one starts at when using the exit
	 */
	public double[] getExitStartingPosition() { return exitStartingPosition; }

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public List<List<TextureHolder>> getMap() { return map; }

	/**
	 * Gets the mobs from map.
	 *
	 * @return the mobs from map
	 */
	public List<MobRan> getMobsFromMap() { return mobs; }

	/**
	 * Gets the NPCS from map.
	 *
	 * @return the NPCS from map
	 */
	public List<NPC> getNPCSFromMap() { return npcs; }

	/**
	 * Gets the overlay.
	 *
	 * @return the overlay
	 */
	public String getOverlay() { return overlay; }

	/**
	 * Gets a part of the map.
	 *
	 * @param x      the x position on screen
	 * @param y      the y position on screen
	 * @param width  the width
	 * @param height the height
	 *
	 * @return the part of the map
	 */
	public List<List<TextureHolder>> getPartOfMap(double x, double y, double width, double height) {
		int lx, ly, w, h;
		lx	= (int) Math.floor( (x - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX()) / windowDataHolder.blockSizeX());
		ly	= (int) Math.floor( (y - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY()) / windowDataHolder.blockSizeY());
		w	= (int) Math.floor(width / windowDataHolder.blockSizeX());
		h	= (int) Math.floor(height / windowDataHolder.blockSizeY());

		List<List<TextureHolder>> li = new ArrayList<>();

		for (int i = 0; i < h; i++) {
			li.add(new ArrayList<>());
			for (int j = 0; j < w; j++) li.get(i).add(map.get(ly + i).get(lx + j));
		}
		return li;

	}

	/**
	 * Gets the path to the map file.
	 *
	 * @return the path to the map file
	 */
	public String getPath() { return path; }

	/**
	 * Gets the player layer.
	 *
	 * @return the player layer
	 */
	public int getPlayerLayer() { return playerLayer; }

	/**
	 * Gets the requester B.
	 *
	 * @return the requester B
	 */
	public ObjectProperty<Building> getRequesterB() { return requesterB; }

	/**
	 * Gets the requestor for the context menu containing the NPC.
	 *
	 * @return the requestor for the context menu containing the NPC
	 */
	public ObjectProperty<NPC> getRequestorN() { return requestorN; }

	/**
	 * Gets the spawn point.
	 *
	 * @return the spawn point
	 */
	public Point2D getSpawnPoint() { return new Point2D(startingPosition[0], startingPosition[1]); }

	/**
	 * Gets the starting position.
	 *
	 * @return the starting position
	 */
	public double[] getStartingPosition() { return startingPosition; }

	/**
	 * Gets the tile at x and y.
	 *
	 * @param x the x position
	 * @param y the y position
	 *
	 * @return the tile at x and y
	 */
	public TextureHolder getTileAt(double x, double y) {
		int	tx	= (int) Math.floor(x / windowDataHolder.blockSizeX());
		int	ty	= (int) Math.floor(y / windowDataHolder.blockSizeY());
		if (x < 0) tx--;
		if (y < 0) ty--;
		try {
			return map.get(ty).get(tx);
		} catch (IndexOutOfBoundsException e) {
			return new FakeTextureHolder(tx * windowDataHolder.blockSizeX() - gamePanel.getPlayer().getX() + gamePanel.getPlayer().getScreenX(),
					ty * windowDataHolder.blockSizeY() - gamePanel.getPlayer().getY() + gamePanel.getPlayer().getScreenY());
		}

	}

	/**
	 * Gets the tiles.
	 *
	 * @return the tiles
	 */
	public List<Tile> getTiles() { return tiles; }

	/**
	 * Handle save.
	 *
	 * @param ae the ae
	 */
	public void handleSave(ActionEvent ae) {
		gamePanel.saveMap();
	}

	/**
	 * Checks if is context menu showing.
	 *
	 * @return true, if is context menu showing
	 */
	public boolean isContextMenuShowing() { return contextMenuShowing; }

	/**
	 * Load map.
	 *
	 * @param data the map data/matrix
	 */
	public void loadMap(JsonArray data) {

		int	col	= 0;
		int	row	= 0;

		try {

			int idx = 0;

			while (row < data.size()) {

				String		line	= ((StringValue) data.get(idx)).getValue();
				String[]	numbers	= line.split(" ");

				if (mapTileNum.size() == row)
					mapTileNum.add(new ArrayList<>());

				while (col < numbers.length) {

					int num = Integer.parseInt(numbers[col]);

					mapTileNum.get(row).add(num);
					col++;
				}
				if (col == numbers.length) {
					col = 0;
					row++;
					idx++;
				}
			}

		} catch (Exception e) {
			new Exception(row + 1 + " " + (col + 1), e).printStackTrace();
		}

	}

	/**
	 * Paste.
	 *
	 * @param ae the ae
	 */
	public void paste(ActionEvent ae) {
		TextureHolder th = requester.getValue();
		if (gamePanel.getClipboard().size() > 0) {
			List<List<TextureHolder>> partOfMap = getPartOfMap(th.getLayoutX(), th.getLayoutY(),
					gamePanel.getClipboard().get(0).size() * windowDataHolder.blockSizeX(),
					gamePanel.getClipboard().size() * windowDataHolder.blockSizeY());
			System.out.println(partOfMap);
			for (int i = 0; i < partOfMap.size(); i++)
				for (int j = 0; j < partOfMap.get(i).size(); j++) partOfMap.get(i).get(j).setTile(gamePanel.getClipboard().get(i).get(j).getTile());
		}
	}

	/**
	 * Reload the map.
	 */
	public void reload() { setMap(path); }

	/**
	 * Save the map.
	 */
	public void save() {
		if (!generated) try {
			File out = new File(path).getAbsoluteFile();
			System.out.println(out);
			if (out.exists()) {
				JsonObject	jo			= (JsonObject) JsonParser.parse(out);
				JsonArray	buildings	= (JsonArray) jo.get("buildings");
				buildings.clear();
				buildings.addAll(this.buildings.stream().filter(Building::isMaster).toList());
				JsonArray npcs = (JsonArray) jo.get("npcs");
				npcs.clear();
				npcs.addAll(this.npcs.stream().filter(Entity::isMaster).toList());
				npcs.addAll(mobs.stream().filter(Entity::isMaster).toList());
				JsonObject map = (JsonObject) jo.get("map");
				for (int i = 0; i < gamePanel.getViewGroups().size(); i++) {
					Group v = gamePanel.getViewGroups().get(i);
					if (v.getChildren().contains(gamePanel.getPlayer()))
						map.put("playerLayer", i);
				}
				if (backgroundPath != null)
					map.put("background", backgroundPath);
				JsonArray textures = (JsonArray) map.get("textures");
				textures.clear();
				JsonArray startingPosition = (JsonArray) map.get("startingPosition");
				startingPosition.clear();
				startingPosition.add(this.startingPosition[0]);
				startingPosition.add(this.startingPosition[1]);
				map.put("dir", getDir());
				JsonArray matrix = (JsonArray) map.get("matrix");
				matrix.clear();
				for (List<TextureHolder> mapi : this.map) {
					StringBuilder sb = new StringBuilder();
					for (TextureHolder th : mapi) if (textures.contains(th.getTile().name))
						sb.append(textures.indexOf(th.getTile().name) + " ");
					else {
						sb.append(textures.size() + " ");
						textures.add(th.getTile().name);
					}
					matrix.add(sb.toString().substring(0, sb.toString().length() - 1));
				}
				String			jsonOut	= jo.toJson();
				BufferedWriter	bw		= new BufferedWriter(new FileWriter(out));
				bw.write(jsonOut);
				bw.flush();
				bw.close();

				double[] offset = { this.startingPosition[0] - origStartingPosition[0], this.startingPosition[1] - origStartingPosition[1] };
				for (Building b : this.buildings) if (b instanceof House h) {
					JsonObject joH = (JsonObject) JsonParser
							.parse(new FileInputStream("./res/maps/" + h.getMap()));
					JsonArray	exitSpawn	= (JsonArray) ((JsonObject) joH.get("exit")).get("spawnPosition");
					exitSpawn.set(0, ((NumberValue) exitSpawn.get(0)).getValue().doubleValue() + offset[0]);
					exitSpawn.set(1, ((NumberValue) exitSpawn.get(1)).getValue().doubleValue() + offset[1]);
					bw = new BufferedWriter(new FileWriter(new File("./res/maps/" + h.getMap())));
					bw.write(joH.toJson());
					bw.flush();
					bw.close();
				}
			}
		} catch (JsonParseException | IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sets the context menu showing.
	 *
	 * @param showing the new context menu showing
	 */
	public void setContextMenuShowing(boolean showing) { contextMenuShowing = showing; }

	/**
	 * Sets the map.
	 *
	 * @param path the path to the new map
	 */
	public void setMap(String path) {
		try {

			this.path = path;

			exitPosition			= null;
			exitStartingPosition	= null;
			exitMap					= null;
			startingPosition		= new double[] {
					0d, 0d
			};
			origStartingPosition	= new double[] {
					0d, 0d
			};

			mapTileNum.clear();
			map.clear();

			group.getChildren().clear();
			tiles.clear();
			npcs.clear();
			mobs.clear();
			buildings.clear();
			playerLayer = 0;
			JsonObject jo = (JsonObject) JsonParser
					.parse(new FileInputStream(path));

			//overlay

			if (jo.containsKey("generated") && jo.get("generated") instanceof BoolValue bv && bv.getValue()) {

				if (jo.containsKey("BackgroundMusic")) backgroundMusic = ((StringValue) jo.get("BackgroundMusic")).getValue();
				else backgroundMusic = "";

				if (jo.containsKey("overlay")) overlay = ((StringValue) jo.get("overlay")).getValue();
				else overlay = "";

				generated = true;

				if (jo.containsKey("background")) backgroundPath = ((StringValue) jo.get("background")).getValue();
				else backgroundPath = null;

				JsonObject mainmap = (JsonObject) JsonParser
						.parse(new FileInputStream("./res/maps/" + ((StringValue) jo.get("mainmap")).getValue()));

				JsonObject endmap = (JsonObject) JsonParser
						.parse(new FileInputStream("./res/maps/" + ((StringValue) jo.get("endmap")).getValue()));

				JsonArray ja_maps = (JsonArray) jo.get("maps");

				String voidImg = ((StringValue) jo.get("void")).getValue();

				DungeonGen d = new DungeonGen(gamePanel, voidImg, mainmap, ja_maps,endmap,
						((JsonArray) jo.get("connectors")).stream().map(jOb -> (JsonObject) jOb).toList(),
						((JsonArray) jo.get("connections")).stream().map(jOb -> (JsonObject) jOb).toList(),
						((JsonArray) jo.get("replacments")).stream().map(jOb -> (JsonObject) jOb).toList(), (JsonObject) jo.get("additionalData"),
						gamePanel.getDifficulty());

				d.findFreeConnectors();

				d.stitchMaps();

				return;
			}

			generated = false;

			JsonObject	map					= (JsonObject) jo.get("map");
			JsonArray	textures			= (JsonArray) map.get("textures");
			JsonArray	npcs				= (JsonArray) jo.get("npcs");
			JsonArray	buildings			= (JsonArray) jo.get("buildings");
			JsonArray	startingPosition	= (JsonArray) map.get("startingPosition");

			dir = ((StringValue) map.get("dir")).getValue();

			if (map.containsKey("BackgroundMusic")) backgroundMusic = ((StringValue) map.get("BackgroundMusic")).getValue();
			else backgroundMusic = "";

			if (map.containsKey("overlay")) overlay = ((StringValue) map.get("BackgroundMusic")).getValue();
			else overlay = "";

			if (map.containsKey("background")) backgroundPath = ((StringValue) map.get("background")).getValue();
			else backgroundPath = null;

			if (jo.containsKey("exit")) {
				JsonObject exit = (JsonObject) jo.get("exit");
				exitMap = ((StringValue) exit.get("map")).getValue();
				JsonArray	spawnPosition	= (JsonArray) exit.get("spawnPosition");
				JsonArray	position		= (JsonArray) exit.get("position");
				exitStartingPosition	= new double[] {
						((NumberValue) spawnPosition.get(0)).getValue().doubleValue(), ((NumberValue) spawnPosition.get(1)).getValue().doubleValue()
				};
				exitPosition			= new double[] {
						((NumberValue) position.get(0)).getValue().doubleValue(), ((NumberValue) position.get(1)).getValue().doubleValue()
				};
			}
			for (Object texture : textures) try {
				Tile t = new Tile( ((StringValue) texture).getValue(),
						"./res/" + getDir() + "/" + ((StringValue) texture).getValue(),
						gamePanel);
				tiles.add(t);
				mtiles.getItems()
				.add(new MenuItemWTile( ((StringValue) texture).getValue(),
						new ImageView(ImgUtil.resizeImage(t.images[0],
								(int) t.images[0].getWidth(), (int) t.images[0].getHeight(), 48, 48)),
						t));
				String[] sp = ((StringValue) texture).getValue().split("[.]");
				if (new File("./res/collisions/" + getDir() + "/" + String.join(".", Arrays.copyOf(sp, sp.length - 1))
				+ ".collisionbox").exists())
					try {
						RandomAccessFile raf = new RandomAccessFile(new File("./res/collisions/" + getDir() + "/"
								+ String.join(".", Arrays.copyOf(sp, sp.length - 1))
								+ ".collisionbox"), "rws");
						raf.seek(0l);
						int length = raf.readInt();
						t.poly = new ArrayList<>();
						boolean s = false;
						for (int i = 0; i < length; i++)
							t.poly.add(raf.readDouble() * ( (s = !s) ? windowDataHolder.scalingFactorX() : windowDataHolder.scalingFactorY()));
					} catch (IOException e) {
						e.printStackTrace();
					}
			} catch (NullPointerException e) {
				String[] sp = ((StringValue) texture).getValue().split("[.]");
				new IOException(getDir() + "/" + String.join(".", Arrays.copyOf(sp, sp.length - 1)), e)
				.printStackTrace();
			}
			this.startingPosition = new double[] {
					((NumberValue) startingPosition.get(0)).getValue().doubleValue(), ((NumberValue) startingPosition.get(1)).getValue().doubleValue()
			};
			origStartingPosition	= new double[] {
					((NumberValue) startingPosition.get(0)).getValue().doubleValue(), ((NumberValue) startingPosition.get(1)).getValue().doubleValue()
			};
			if (map.containsKey("playerLayer"))
				playerLayer = ((NumberValue) map.get("playerLayer")).getValue().intValue();
			else playerLayer = 0;
			mapTileNum	= new ArrayList<>();
			this.map	= new ArrayList<>();
			loadMap((JsonArray) map.get("matrix"));
			this.buildings	= new ArrayList<>();
			this.npcs		= new ArrayList<>();
			mobs			= new ArrayList<>();
			for (Object building : buildings) {
				Building b = switch ( ((StringValue) ((JsonObject) building).get("type")).getValue()) {
					case "House" -> new House((JsonObject) building, gamePanel, this.buildings, cm, requesterB);
					case "ContractsTable" -> new ContractsTable((JsonObject) building, gamePanel, this.buildings, cm,
							requesterB);
					case "TreasureChest" -> new TreasureChest((JsonObject) building, gamePanel, this.buildings, cm, requesterB);
					default -> new Building((JsonObject) building, gamePanel, this.buildings, cm, requesterB);
				};
				ImageView	lIV;
				if (b.isGif(b.getCurrentKey())) {
					lIV = new ImageView(b.getImages().get(b.getCurrentKey()).get(0));
					lIV.setFitWidth(16);
					lIV.setFitHeight(16);
				} else lIV = new ImageView(ImgUtil.resizeImage(b.getImages().get(b.getCurrentKey()).get(0),
						(int) b.getImages().get(b.getCurrentKey()).get(0).getWidth(),
						(int) b.getImages().get(b.getCurrentKey()).get(0).getHeight(), 48, 48));
				getMbuildings().getItems().add(new MenuItemWBuilding(
						((StringValue) ((JsonObject) ((JsonObject) building).get("textures")).values().stream()
								.findFirst().get()).getValue(),
						lIV,
						b));
			}
			for (Object npc : npcs) {
				Entity n = switch ( ((StringValue) ((JsonObject) npc).get("type")).getValue()) {
					case "MonsterNPC", "monsternpc", "Demon", "demon" -> new MonsterNPC((JsonObject) npc, gamePanel, this.npcs, cm,
							requestorN);
					case "MobRan", "mobran" -> new MobRan((JsonObject) npc, gamePanel, mobs, cm, requestorM);
					default -> new NPC((JsonObject) npc, gamePanel, this.npcs, cm, requestorN);
				};
				ImageView	lIV;
				if (n.isGif(n.getCurrentKey())) {
					lIV = new ImageView(n.getImages().get(n.getCurrentKey()).get(0));
					lIV.setFitWidth(16);
					lIV.setFitHeight(16);
				} else lIV = new ImageView(ImgUtil.resizeImage(n.getImages().get(n.getCurrentKey()).get(0),
						(int) n.getImages().get(n.getCurrentKey()).get(0).getWidth(),
						(int) n.getImages().get(n.getCurrentKey()).get(0).getHeight(), 48, 48));
				if (n instanceof MobRan mr)
					mmobs.getItems()
					.add(new MenuItemWMOB(
							((StringValue) ((JsonObject) ((JsonObject) npc).get("textures")).values().stream()
									.findFirst().get()).getValue(),
							lIV,
							mr));
				else if (n instanceof NPC np)
					mnpcs.getItems()
					.add(new MenuItemWNPC(
							((StringValue) ((JsonObject) ((JsonObject) npc).get("textures")).values().stream()
									.findFirst().get()).getValue(),
							lIV,
							np));

			}
			mtiles.getItems().add(new MenuItem("add Texture"));
			mnpcs.getItems().add(new MenuItem("add Texture"));
			mmobs.getItems().add(new MenuItem("add Texture"));
			mtiles.getItems().get(mtiles.getItems().size() - 1).setStyle("-fx-font-size: 20;");
			mnpcs.getItems().get(mnpcs.getItems().size() - 1).setStyle("-fx-font-size: 20;");
			mmobs.getItems().get(mmobs.getItems().size() - 1).setStyle("-fx-font-size: 20;");
			getMbuildings().getItems().add(new MenuItem("add Texture"));
			getMbuildings().getItems().get(getMbuildings().getItems().size() - 1).setStyle("-fx-font-size: 20;");
			for (MenuItem mi : mtiles.getItems()) mi.setOnAction(this::contextMenu);
			for (MenuItem mi : mnpcs.getItems()) mi.setOnAction(this::contextMenu);
			for (MenuItem mi : getMbuildings().getItems()) mi.setOnAction(this::contextMenu);
			for (MenuItem mi : mmobs.getItems()) mi.setOnAction(this::contextMenu);

		} catch (JsonParseException | FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sets the on context menu requested.
	 *
	 * @param e the new on context menu requested
	 */
	public void setOnContextMenuRequested(ContextMenuEvent e) {
		requester.set(new FakeTextureHolder(e.getSceneX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX(),
				e.getSceneY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY()));
	}

	/**
	 * Sets the starting position.
	 *
	 * @param startingPosition the new starting position
	 */
	public void setStartingPosition(double[] startingPosition) { this.startingPosition = startingPosition; }

	/**
	 * Update.
	 */
	public void update() {

		Player p = gamePanel.getPlayer();

		if (exitMap != null) {
			int	worldX	= (int) (exitPosition[0] * windowDataHolder.scalingFactorX());
			int	worldY	= (int) (exitPosition[1] * windowDataHolder.scalingFactorY());

			if (worldX + windowDataHolder.blockSizeX() / 2 - p.getX() < 105 * windowDataHolder.scalingFactorX()
					&& worldX + windowDataHolder.blockSizeX() / 2 - p.getX() > -45 * windowDataHolder.scalingFactorX() &&
					worldY + windowDataHolder.blockSizeY() / 2 - p.getY() < 25 * windowDataHolder.scalingFactorY()
					&& worldY + windowDataHolder.blockSizeY() / 2 - p.getY() > 0)
				gamePanel.setMap("./res/maps/" + exitMap, exitStartingPosition);
		}

		int	worldCol	= 0;
		int	worldRow	= 0;

		while (worldRow < mapTileNum.size()) {
			int tileNum = mapTileNum.get(worldRow).get(worldCol);

			int		worldX	= worldCol * windowDataHolder.blockSizeX();
			int		worldY	= worldRow * windowDataHolder.blockSizeY();
			double	screenX	= worldX - p.getX() + p.getScreenX();
			double	screenY	= worldY - p.getY() + p.getScreenY();

			if (map.size() == worldRow)
				map.add(new ArrayList<>());

			if (worldX + p.getSize() * 1.5 > p.getX() - p.getScreenX() && worldX - windowDataHolder.blockSizeX() -
					p.getSize() * 1.5 < p.getX() + p.getScreenX()
					&& worldY + windowDataHolder.blockSizeY() + p.getSize() > p.getY() - p.getScreenY()
					&& worldY - windowDataHolder.blockSizeY() - p.getSize() < p.getY() + p.getScreenY()) {
				TextureHolder th = null;
				if (map.get(worldRow).size() > worldCol)
					th = map.get(worldRow).get(worldCol);
				if (th == null) {
					th = new TextureHolder(tiles.get(tileNum < tiles.size() ? tileNum : 0), gamePanel, screenX, screenY,
							cm, requester, worldX, worldY);
					group.getChildren().add(th);
					if (worldCol < map.get(worldRow).size())
						map.get(worldRow).set(worldCol, th);
					else
						map.get(worldRow).add(th);
				} else {
					th.setLayoutX(screenX);
					th.setLayoutY(screenY);
					th.update();
				}
				th.setVisible(true);
			} else {
				TextureHolder th = null;
				if (map.get(worldRow).size() > worldCol)
					th = map.get(worldRow).get(worldCol);
				if (th != null) {
					th.setVisible(false);
					th.update();
				} else {
					th = new TextureHolder(tiles.get(tileNum < tiles.size() ? tileNum : 0), gamePanel, screenX, screenY, cm,
							requester, worldX, worldY);
					th.setVisible(false);
					group.getChildren().add(th);
					if (worldCol < map.get(worldRow).size())
						map.get(worldRow).set(worldCol, th);
					else
						map.get(worldRow).add(th);
				}
			}

			worldCol++;

			if (worldCol == mapTileNum.get(worldRow).size()) {
				worldCol = 0;
				worldRow++;
			}
		}

	}

}
