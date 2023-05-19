package rngGame.ui;

// TODO: Auto-generated Javadoc
/**
 * The Class ScalingFactorsHolder.
 */
public class WindowDataHolder {

	/** The scaling factor Y. */
	private double scalingFactorX, scalingFactorY;

	/** The game height. */
	private int gameWidth, gameHeight;

	/** The block size Y. */
	private final int blockSizeX, blockSizeY;

	/** The y blocks. */
	private final int blockSize = 48, xBlocks = 20, yBlocks = 11;

	/**
	 * Instantiates a new scaling factors holder.
	 *
	 * @param scalingFactorX the scaling factor X
	 * @param scalingFactorY the scaling factor Y
	 */
	public WindowDataHolder(double scalingFactorX, double scalingFactorY) {
		this.scalingFactorX	= scalingFactorX;
		this.scalingFactorY	= scalingFactorY;
		blockSizeX			= (int) (blockSize * scalingFactorX);
		blockSizeY			= (int) (blockSize * scalingFactorY);
		gameWidth			= blockSizeX * xBlocks;
		gameHeight			= blockSizeY * yBlocks;

	}

	/**
	 * Block size.
	 *
	 * @return the int
	 */
	public int blockSize() { return blockSize; }

	/**
	 * Block size X.
	 *
	 * @return the int
	 */
	public int blockSizeX() { return blockSizeX; }

	/**
	 * Block size Y.
	 *
	 * @return the int
	 */
	public int blockSizeY() { return blockSizeY; }

	/**
	 * Game height.
	 *
	 * @return the int
	 */
	public int gameHeight() { return gameHeight; }

	/**
	 * Game width.
	 *
	 * @return the int
	 */
	public int gameWidth() { return gameWidth; }

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
	 * Sets the game height.
	 *
	 * @param gameHeight the new game height
	 */
	public void setGameHeight(int gameHeight) {
		scalingFactorY	= gameHeight / (this.gameHeight / scalingFactorY);
		this.gameHeight	= gameHeight;

	}

	/**
	 * Sets the game width.
	 *
	 * @param gameWidth the new game width
	 */
	public void setGameWidth(int gameWidth) {
		scalingFactorX	= gameWidth / (this.gameWidth / scalingFactorX);
		this.gameWidth	= gameWidth;

	}

	/**
	 * X blocks.
	 *
	 * @return the int
	 */
	public int xBlocks() { return xBlocks; }

	/**
	 * Y blocks.
	 *
	 * @return the int
	 */
	public int yBlocks() { return yBlocks; }

}
