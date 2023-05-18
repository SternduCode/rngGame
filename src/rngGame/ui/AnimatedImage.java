package rngGame.ui;

import javafx.scene.image.Image;


// TODO: Auto-generated Javadoc
/**
 * The Class AnimatedImage.
 */
public class AnimatedImage {

	/** The frames. */
	private Image[] frames;

	/** The dirty. */
	private boolean dirty;

	/** The frame index. */
	private int frameIndex = 0;

	/** The last frame time. */
	private long lastFrameTime = 0l;

	/** The path. */
	private String path;

	/** The fps. */
	private int fps;

	/** The scaling factor holder. */
	private final ScalingFactorHolder scalingFactorHolder;

	/**
	 * Instantiates a new animated image.
	 *
	 * @param scalingFactorHolder the scaling factor holder
	 */
	public AnimatedImage(ScalingFactorHolder scalingFactorHolder) {
		this.scalingFactorHolder	= scalingFactorHolder;
		fps							= 7;

	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param path                the path
	 * @param scalingFactorHolder the scaling factor holder
	 */
	public AnimatedImage(String path, ScalingFactorHolder scalingFactorHolder) {
		this.path					= path;
		this.scalingFactorHolder	= scalingFactorHolder;
		fps							= 7;
		scaleF11();
		update();

	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param path                the path
	 * @param scalingFactorHolder the scaling factor holder
	 * @param fps                 the fps
	 */
	public AnimatedImage(String path, ScalingFactorHolder scalingFactorHolder, int fps) {
		this.path					= path;
		this.scalingFactorHolder	= scalingFactorHolder;
		this.fps					= fps;
		scaleF11();
		update();

	}

	/**
	 * Gets the fps.
	 *
	 * @return the fps
	 */
	public int getFps() { return fps; }

	/**
	 * Gets the frame at.
	 *
	 * @param index the index
	 *
	 * @return the frame at
	 */
	public Image getFrameAt(int index) { return frames[index]; }

	/**
	 * Gets the frame index.
	 *
	 * @return the frame index
	 */
	public int getFrameIndex() { return frameIndex; }

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() { return path; }

	/**
	 * Gets the scaling factor holder.
	 *
	 * @return the scaling factor holder
	 */
	public ScalingFactorHolder getScalingFactorHolder() { return scalingFactorHolder; }

	/**
	 * Inits the.
	 *
	 * @param path the path
	 */
	public void init(String path) {
		this.path = path;
		scaleF11();
		update();

	}

	/**
	 * Inits the.
	 *
	 * @param path the path
	 * @param fps  the fps
	 */
	public void init(String path, int fps) {
		this.path	= path;
		this.fps	= fps;
		scaleF11();
		update();

	}

	/**
	 * Checks if is dirty.
	 *
	 * @return true, if is dirty
	 */
	public boolean isDirty() { return dirty; }

	/**
	 * Checks if is initiated.
	 *
	 * @return true, if is initiated
	 */
	public boolean isInitiated() { return frames != null; }

	/**
	 * Reset dirty.
	 */
	public void resetDirty() {
		dirty = false;
	}

	/**
	 * Scale F 11.
	 */
	public void scaleF11() {
		frames = ImgUtil.getScaledImages(scalingFactorHolder, path);
		if (frameIndex >= frames.length)
			frameIndex = 0;
		dirty = true;

	}

	/**
	 * Sets the fps.
	 *
	 * @param fps the new fps
	 */
	public void setFps(int fps) { this.fps = fps; }

	/**
	 * Uninit.
	 */
	public void uninit() {
		frames		= null;
		frameIndex	= 0;

	}

	/**
	 * Update.
	 */
	public void update() {
		if (frames != null && System.currentTimeMillis() >= lastFrameTime + 1000.0 / fps) {
			frameIndex++;
			if (frameIndex >= frames.length) frameIndex = 0;
			lastFrameTime = System.currentTimeMillis();
		}

	}

}
