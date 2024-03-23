package rngGame.visual;

import javafx.geometry.Point2D;
import javafx.scene.image.*;
import rngGame.main.WindowManager;
import rngGame.tile.ImgUtil;


/**
 * The Class AnimatedImage.
 */
public class AnimatedImage extends ImageView {

	/** The fps. */
	private int fps;

	/** The path. */
	private String path;

	/** The last frame time. */
	private long lastFrameTime = 0L;

	/** The frames. */
	private Image[] frames;

	/** The frame index. */
	private int frameIndex = 0;

	/**
	 * Instantiates a new animated image.
	 */
	public AnimatedImage() {
		imgRequestedWidth        = -1;
        imgRequestedHeight        = -1;
		fps = 7;
		WindowManager.INSTANCE.addAnimatedImage(this);
	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param path      the path
	 */
	public AnimatedImage(String path) {
		imgRequestedWidth        = -1;
        imgRequestedHeight        = -1;
		this.path		= path;
		fps				= 7;
		WindowManager.INSTANCE.addAnimatedImage(this);
		scaleF11();
		updateUI();
	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param path      the path
	 * @param fps       the fps
	 */
	public AnimatedImage(String path, int fps) {
		imgRequestedWidth        = -1;
        imgRequestedHeight        = -1;
		this.path		= path;
		this.fps		= fps;
		WindowManager.INSTANCE.addAnimatedImage(this);
		scaleF11();
		updateUI();
	}

	/**
	 * Gets the fps.
	 *
	 * @return the fps
	 */
	public int getFps() { return fps; }

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() { return path; }

	/**
	 * Inits the.
	 *
	 * @param path the path
	 */
	public void init(String path) {
		this.path		= path;
		scaleF11();
		updateUI();
	}

	/**
	 * Inits the.
	 *
	 * @param path the path
	 * @param fps the fps
	 */
	public void init(String path, int fps) {
		this.path		= path;
		this.fps		= fps;
		scaleF11();
		updateUI();
	}

	/**
	 * Scale F 11.
	 */
	public void scaleF11() {
		if (path != null) {
            if (imgRequestedWidth != -1 && imgRequestedHeight != -1)
                frames = ImgUtil.getScaledImages(path, imgRequestedWidth, imgRequestedHeight);
            else frames = ImgUtil.getScaledImages(path);
            if (frameIndex >= frames.length)
                frameIndex = 0;
        } else for (int i = 0; i < frames.length; i++) {
            Image img = frames[i];
            if (imgRequestedWidth != -1 && imgRequestedHeight != -1)
                frames[i] = ImgUtil.resizeImage(img, (int) img.getWidth(), (int) img.getHeight(),
                        (int) (imgRequestedWidth * WindowManager.INSTANCE.getScalingFactorX()), (int) (imgRequestedHeight * WindowManager.INSTANCE.getScalingFactorY()));
            else
                frames[i] = ImgUtil.resizeImage(img, (int) img.getWidth(), (int) img.getHeight(),
                        (int) (img.getWidth() * WindowManager.INSTANCE.getScalingFactorX()), (int) (img.getHeight() * WindowManager.INSTANCE.getScalingFactorY()));
        }
		setImage(frames[frameIndex]);
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
		frames = null;
		frameIndex	= 0;
		WindowManager.INSTANCE.removeAnimatedImage(this);
	}

	/**
	 * Update.
	 */
	public void updateUI() {
		if (frames != null && System.currentTimeMillis() >= lastFrameTime + 1000.0 / fps) {
			frameIndex++;
			if (frameIndex >= frames.length) frameIndex = 0;
			lastFrameTime = System.currentTimeMillis();
			setImage(frames[frameIndex]);
		}
	}

	public double getWidth() { return getImage().getWidth(); }

	public double getHeight() { return getImage().getHeight(); }

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

    /** The img requested height. */
    private int imgRequestedWidth, imgRequestedHeight;

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

	public void setImgRequestedSize(int width, int height) {
		setImgRequestedWidth(width);
		setImgRequestedHeight(height);
	}

	public void setImgRequestedSize(Point2D size) {
		setImgRequestedSize((int) size.getX(), (int) size.getY());
	}
}

