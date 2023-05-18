package rngGame.ui;

// TODO: Auto-generated Javadoc
/**
 * The Class ScalingFactorsHolder.
 */
public class ScalingFactorHolder {

	/** The scaling factor Y. */
	private double scalingFactorX, scalingFactorY;

	/**
	 * Instantiates a new scaling factors holder.
	 *
	 * @param scalingFactorX the scaling factor X
	 * @param scalingFactorY the scaling factor Y
	 */
	public ScalingFactorHolder(double scalingFactorX, double scalingFactorY) {
		this.scalingFactorX	= scalingFactorX;
		this.scalingFactorY	= scalingFactorY;

	}

	/**
	 * Scaling factor X.
	 *
	 * @return the double
	 */
	public double scalingFactorX() { return scalingFactorX; }

	/**
	 * Scaling factor Y.
	 *
	 * @return the double
	 */
	public double scalingFactorY() { return scalingFactorY; }

	/**
	 * Sets the scaling factor X.
	 *
	 * @param scalingFactorX the new scaling factor X
	 */
	public void setScalingFactorX(double scalingFactorX) { this.scalingFactorX = scalingFactorX; }

	/**
	 * Sets the scaling factor Y.
	 *
	 * @param scalingFactorY the new scaling factor Y
	 */
	public void setScalingFactorY(double scalingFactorY) { this.scalingFactorY = scalingFactorY; }

}
