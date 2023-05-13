package rngGame.visual;

import javafx.scene.image.*;
import rngGame.tile.ImgUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class AnimatedImage.
 */
public class AnimatedImage extends ImageView {

	/** The gamepanel. */
	protected final GamePanel gamepanel;

	/** The fps. */
	private int fps;

	/** The path. */
	private String path;

	/** The last frame time. */
	private long lastFrameTime = 0l;

	/** The frames. */
	private Image[] frames;

	/** The frame index. */
	private int frameIndex = 0;

	/**
	 * Instantiates a new animated image.
	 *
	 * @param gamepanel the gamepanel
	 */
	public AnimatedImage(GamePanel gamepanel) {
		this.gamepanel = gamepanel;
		fps = 7;
		gamepanel.addAnimatedImage(this);
	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param path      the path
	 * @param gamepanel the gamepanel
	 */
	public AnimatedImage(String path, GamePanel gamepanel) {
		this.path		= path;
		this.gamepanel	= gamepanel;
		fps				= 7;
		gamepanel.addAnimatedImage(this);
		scaleF11();
		update();
	}

	/**
	 * Instantiates a new animated image.
	 *
	 * @param path      the path
	 * @param gamepanel the gamepanel
	 * @param fps       the fps
	 */
	public AnimatedImage(String path, GamePanel gamepanel, int fps) {
		this.path		= path;
		this.gamepanel	= gamepanel;
		this.fps		= fps;
		gamepanel.addAnimatedImage(this);
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
		update();
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
		update();
	}

	/**
	 * Scale F 11.
	 */
	public void scaleF11() {
		frames = ImgUtil.getScaledImages(gamepanel, path);
		if (frameIndex >= frames.length)
			frameIndex = 0;
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
	}

	/**
	 * Update.
	 */
	public void update() {
		if (frames != null && System.currentTimeMillis() >= lastFrameTime + 1000.0 / fps) {
			frameIndex++;
			if (frameIndex >= frames.length) frameIndex = 0;
			lastFrameTime = System.currentTimeMillis();
			setImage(frames[frameIndex]);
		}
	}

}