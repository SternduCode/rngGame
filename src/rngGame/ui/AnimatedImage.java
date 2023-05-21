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

	/** The img requested height. */
	private int imgRequestedWidth, imgRequestedHeight;

	/** The fps. */
	private int fps;

	/** The scaling factor holder. */
	private final WindowDataHolder windowDataHolder;

	/**
	 * Instantiates a new animated image.
	 *
	 * @param frames           the frames
	 * @param windowDataHolder the window data holder
	 */
	public AnimatedImage(Image[] frames, WindowDataHolder windowDataHolder) {
		path					= null;
		this.frames				= frames;
		this.windowDataHolder	= windowDataHolder;
		fps						= 7;
		imgRequestedWidth		= -1;
		imgRequestedHeight		= -1;
		update();

	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param frames the frames
	 * @param windowDataHolder the window data holder
	 * @param fps                 the fps
	 */
	public AnimatedImage(Image[] frames, WindowDataHolder windowDataHolder, int fps) {
		path					= null;
		this.frames				= frames;
		this.windowDataHolder	= windowDataHolder;
		this.fps				= fps;
		imgRequestedWidth		= -1;
		imgRequestedHeight		= -1;
		update();

	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param path             the path
	 * @param windowDataHolder the window data holder
	 */
	public AnimatedImage(String path, WindowDataHolder windowDataHolder) {
		this.path				= path;
		this.windowDataHolder	= windowDataHolder;
		fps						= 7;
		imgRequestedWidth		= -1;
		imgRequestedHeight		= -1;
		scaleF11();
		update();

	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param path                the path
	 * @param windowDataHolder the window data holder
	 * @param fps                 the fps
	 */
	public AnimatedImage(String path, WindowDataHolder windowDataHolder, int fps) {
		this.path				= path;
		this.windowDataHolder	= windowDataHolder;
		this.fps				= fps;
		imgRequestedWidth		= -1;
		imgRequestedHeight		= -1;
		scaleF11();
		update();

	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param windowDataHolder the window data holder
	 */
	public AnimatedImage(WindowDataHolder windowDataHolder) {
		this.windowDataHolder	= windowDataHolder;
		fps						= 7;
		imgRequestedWidth		= -1;
		imgRequestedHeight		= -1;
		path					= null;

	}

	/**
	 * Frame count.
	 *
	 * @return the int
	 */
	public int frameCount() { return frames.length; }

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
	 * Gets the img requested height.
	 *
	 * @return the img requested height
	 */
	public int getImgRequestedHeight() { return imgRequestedHeight; }

	/**
	 * Gets the img requested width.
	 *
	 * @return the img requested width
	 */
	public int getImgRequestedWidth() { return imgRequestedWidth; }

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
	public WindowDataHolder getWindowDataHolder() { return windowDataHolder; }

	/**
	 * Inits the.
	 *
	 * @param frames the frames
	 */
	public void init(Image[] frames) {
		path		= null;
		this.frames = frames;

	}

	/**
	 * Inits the.
	 *
	 * @param frames the frames
	 * @param fps  the fps
	 */
	public void init(Image[] frames, int fps) {
		path		= null;
		this.frames	= frames;
		this.fps	= fps;

	}

	/**
	 * Inits the.
	 *
	 * @param path the path
	 */
	public void init(String path) {
		this.path = path;
		scaleF11();

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
		if (path != null) {
			if (imgRequestedWidth != -1 && imgRequestedHeight != -1)
				frames = ImgUtil.getScaledImages(windowDataHolder, path, imgRequestedWidth, imgRequestedHeight);
			else frames = ImgUtil.getScaledImages(windowDataHolder, path);
			if (frameIndex >= frames.length)
				frameIndex = 0;
		} else for (int i = 0; i < frames.length; i++) {
			Image img = frames[i];
			if (imgRequestedWidth != -1 && imgRequestedHeight != -1)
				frames[i] = ImgUtil.resizeImage(img, (int) img.getWidth(), (int) img.getHeight(),
						(int) (imgRequestedWidth * windowDataHolder.scalingFactorX()), (int) (imgRequestedHeight * windowDataHolder.scalingFactorY()));
			else
				frames[i] = ImgUtil.resizeImage(img, (int) img.getWidth(), (int) img.getHeight(),
						(int) (img.getWidth() * windowDataHolder.scalingFactorX()), (int) (img.getHeight() * windowDataHolder.scalingFactorY()));
		}
		dirty = true;

	}

	/**
	 * Sets the fps.
	 *
	 * @param fps the new fps
	 */
	public void setFps(int fps) { this.fps = fps; }

	/**
	 * Sets the img requested height.
	 *
	 * @param imgRequestedHeight the new img requested height
	 */
	public void setImgRequestedHeight(int imgRequestedHeight) { this.imgRequestedHeight = imgRequestedHeight; }

	/**
	 * Sets the img requested width.
	 *
	 * @param imgRequestedWidth the new img requested width
	 */
	public void setImgRequestedWidth(int imgRequestedWidth) { this.imgRequestedWidth = imgRequestedWidth; }

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
