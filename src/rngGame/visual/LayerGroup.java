package rngGame.visual;

import javafx.scene.Group;


// TODO: Auto-generated Javadoc
/**
 * The Class LayerGroup.
 */
public class LayerGroup extends Group {

	/**
	 * Gets the layer.
	 *
	 * @param layer the layer
	 *
	 * @return the layer
	 */
	public Group getLayer(int layer) {
		int size = getChildren().size();
		if (layer >= size) for (int i = 0; i <= layer - size; i++) getChildren().add(new Group());
		return (Group) getChildren().get(layer);

	}

}
