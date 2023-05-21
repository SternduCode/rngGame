package rngGame.buildings;

import java.util.*;

import com.sterndu.json.*;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Ellipse;
import rngGame.main.Input;
import rngGame.stats.*;
import rngGame.ui.*;


// TODO: Auto-generated Javadoc
/**
 * The Class TreasureChest.
 */
public class TreasureChest extends Building {

	/** The voidi. */
	private static int common = 0, uncommon = 0, rare = 0, veryrare = 0, epic = 0, legendary = 0, god = 0, voidi = 0;

	/** The is open. */
	private boolean isOpen = false;

	/** The if endchest. */
	private boolean ifEndchest;



	/**
	 * Instantiates a new treasure chest.
	 *
	 * @param building   the building
	 * @param buildings  the buildings
	 * @param requestorB the requestor B
	 * @param windowDataHolder the window data holder
	 */
	public TreasureChest(Building building, List<Building> buildings, ObjectProperty<Building> requestorB, WindowDataHolder windowDataHolder) {
		super(building, buildings, requestorB, windowDataHolder);
		init();
	}

	/**
	 * Instantiates a new treasure chest.
	 *
	 * @param building   the building
	 * @param gp         the gp
	 * @param buildings  the buildings
	 * @param requestorB the requestor B
	 * @param windowDataHolder the window data holder
	 */
	public TreasureChest(JsonObject building, GamePanel gp, List<Building> buildings, ObjectProperty<Building> requestorB,
			WindowDataHolder windowDataHolder) {
		super(building, gp, buildings, requestorB, windowDataHolder);
		init();
		ifEndchest = false;
		if(building.containsKey("endChest")) ifEndchest=((BoolValue) building.get("endChest")).getValue();
		Input.getInstance(null).setKeyHandler("Items", mod -> {
			TestItem();
		}, KeyCode.I, false);
	}

	/**
	 * Instantiates a new treasure chest.
	 *
	 * @param building   the building
	 * @param gp         the gp
	 * @param directory  the directory
	 * @param buildings  the buildings
	 * @param cm         the cm
	 * @param requestorB the requestor B
	 * @param windowDataHolder the window data holder
	 */
	public TreasureChest(JsonObject building, GamePanel gp, String directory, List<Building> buildings, ContextMenu cm,
			ObjectProperty<Building> requestorB, WindowDataHolder windowDataHolder) {
		super(building, gp, directory, buildings, requestorB, windowDataHolder);
		init();
		ifEndchest = false;
		if(building.containsKey("endChest")) ifEndchest=((BoolValue) building.get("endChest")).getValue();
	}

	/**
	 * Creates the item.
	 *
	 * @return the item
	 */
	public Item createItem() {
		Random gen = new Random();
		Rarity wahl = Rarity.COMMON;

		int r = gen.nextInt(100*2)+1;
		////////////
		if (r <= 80) wahl = Rarity.COMMON; // 40%
		else if (r <= 130) wahl = Rarity.UNCOMMON; // 25%
		else if (r <= 160) wahl = Rarity.RARE; // 15%
		else if (r <= 180) wahl = Rarity.VERY_RARE; // 10%
		else if (r <= 192) wahl = Rarity.EPIC; // 6%
		else if (r <= 197) wahl = Rarity.LEGENDARY; // 2,5%
		else if (r <= 199) wahl = Rarity.GOD; // 1%
		else wahl = Rarity.VOID; 	// 0,5%
		////////////
		return switch (gen.nextInt(5)+1) {
			case 1 -> new Harnish(wahl);
			case 2 -> new Helmet(wahl);
			case 3 -> new Pants(wahl);
			case 4 -> new Sword(wahl);
			case 5 -> new Potion(wahl);

			default -> new Potion(wahl);
		};
	}

	/**
	 * Give item.
	 */
	public void giveItem() {
		if(ifEndchest) {
			Item r1 = createItem();
			gamepanel.getTabMenu().getInventory().itemToInventory(r1);
			Item r2 = createItem();
			gamepanel.getTabMenu().getInventory().itemToInventory(r2);
			Item r3 = createItem();
			gamepanel.getTabMenu().getInventory().itemToInventory(r3);

			gamepanel.setMap("./res/maps/lavaMap2.json", new double[] {
					1464.0, 372.0
			});
		} else {
			Item r1 = createItem();
			gamepanel.getTabMenu().getInventory().itemToInventory(r1);
		}
	}

	/**
	 * Inits the.
	 */
	public void init() {
		if (!getMiscBoxHandler().containsKey("action")) addMiscBox("action",
				new Ellipse(getReqWidth() * gamepanel.getWindowDataHolder().scalingFactorX() / 2,
						getReqHeight() * gamepanel.getWindowDataHolder().scalingFactorY() / 2,
						gamepanel.getWindowDataHolder().blockSizeX() / 2, gamepanel.getWindowDataHolder().blockSizeY() / 2),
				(gpt, self) -> {
					if (!isOpen)
						gpt.getActionButton().setIfInteractionButtonCanBePressed(true, gp2 -> {
							isOpen = true;
							TreasureChest.this.setCurrentKey("open");
							giveItem();
						});
				});
		else getMiscBoxHandler().put("action", (gpt, self) -> {
			if (!isOpen)
				gpt.getActionButton().setIfInteractionButtonCanBePressed(true, gp2 -> {
					isOpen = true;
					TreasureChest.this.setCurrentKey("open");
					giveItem();
				});
		});
	}

	/**
	 * Test item.
	 */
	public void TestItem() {
		Item r1 = createItem();
		switch (r1.getRarity()) {
			case COMMON -> common++;
			case UNCOMMON -> uncommon++;
			case RARE -> rare++;
			case VERY_RARE -> veryrare++;
			case EPIC -> epic++;
			case LEGENDARY -> legendary++;
			case GOD -> god++;
			case VOID -> voidi++;
			default ->
			throw new IllegalArgumentException("Unexpected value: " + r1.getRarity());
		}
		gamepanel.getTabMenu().getInventory().itemToInventory(r1);
		double sum = (common + uncommon + rare + veryrare + epic + legendary + god + voidi) / 100.0;
		System.out.printf("Common %.2f%% Uncommon %.2f%% Rare %.2f%% Very Rare %.2f%% Epic %.2f%% Legendary %.2f%% God %.2f%% Void %.2f%% Items %d\n",
				common / sum, uncommon / sum, rare / sum, veryrare / sum, epic / sum, legendary / sum, god / sum, voidi / sum, (long) (sum * 100));
	}




}
