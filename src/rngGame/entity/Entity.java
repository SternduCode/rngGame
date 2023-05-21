package rngGame.entity;

import java.util.List;

import com.sterndu.json.JsonObject;

import javafx.beans.property.ObjectProperty;
import rngGame.main.GameObject;
import rngGame.ui.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity.
 */
public abstract class Entity extends GameObject {

	/** The speed. */
	protected double speed;

	/**
	 * Instantiates a new entity.
	 *
	 * @param en the en
	 * @param entities the entities
	 * @param requestor the requestor
	 * @param windowDataHolder the window data holder
	 */
	public Entity(Entity en, List<? extends Entity> entities,
			ObjectProperty<? extends Entity> requestor, WindowDataHolder windowDataHolder) {
		super(en, entities, requestor, windowDataHolder);
		speed = en.speed;

	}

	/**
	 * Instantiates a new entity.
	 *
	 * @param en the en
	 * @param speed the speed
	 * @param gp the gp
	 * @param directory the directory
	 * @param entities the entities
	 * @param requestor the requestor
	 * @param windowDataHolder the window data holder
	 */
	public Entity(JsonObject en, double speed, GamePanel gp, String directory, List<? extends Entity> entities,
			ObjectProperty<? extends Entity> requestor, WindowDataHolder windowDataHolder) {
		super(en, gp, directory, entities, requestor, windowDataHolder);
		this.speed = speed;

	}

	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public double getSpeed() { return speed; }

	/**
	 * Sets the speed.
	 *
	 * @param speed the new speed
	 */
	public void setSpeed(double speed) { this.speed = speed; }

}
