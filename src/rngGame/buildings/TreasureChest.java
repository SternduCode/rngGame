package rngGame.buildings;

import java.util.List;

import com.sterndu.json.JsonObject;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.shape.Ellipse;
import rngGame.main.GamePanel;


// TODO: Auto-generated Javadoc
/**
 * The Class TreasureChest.
 */
public class TreasureChest extends Building {

	/**
	 * Instantiates a new treasure chest.
	 *
	 * @param building   the building
	 * @param buildings  the buildings
	 * @param cm         the cm
	 * @param requestorB the requestor B
	 */
	public TreasureChest(Building building, List<Building> buildings, ContextMenu cm, ObjectProperty<Building> requestorB) {
		super(building, buildings, cm, requestorB);
		init();
	}

	/**
	 * Instantiates a new treasure chest.
	 *
	 * @param building   the building
	 * @param gp         the gp
	 * @param buildings  the buildings
	 * @param cm         the cm
	 * @param requestorB the requestor B
	 */
	public TreasureChest(JsonObject building, GamePanel gp, List<Building> buildings, ContextMenu cm, ObjectProperty<Building> requestorB) {
		super(building, gp, buildings, cm, requestorB);
		init();
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
	 */
	public TreasureChest(JsonObject building, GamePanel gp, String directory, List<Building> buildings, ContextMenu cm,
			ObjectProperty<Building> requestorB) {
		super(building, gp, directory, buildings, cm, requestorB);
		init();

	}

	/**
	 * Inits the.
	 */
	public void init() {
		addMiscBox("action", new Ellipse(getReqWidth() * gamepanel.getScalingFactorX() / 2, getReqHeight() * gamepanel.getScalingFactorY() / 2,
				gamepanel.BgX / 2.0, gamepanel.BgY / 2.0), (gpt, self) -> {
			gpt.getAktionbutton().setInteractionbuttonKann(true);
		});
	}

}