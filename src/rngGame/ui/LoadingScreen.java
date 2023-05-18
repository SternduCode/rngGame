package rngGame.ui;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadingScreen.
 */
public class LoadingScreen extends AnimatedImage {

	/** The default loading screen. */
	private static LoadingScreen defaultLoadingScreen;

	/**
	 * Instantiates a new loading screen.
	 *
	 * @param path the path
	 * @param scalingFactorHolder the scaling factor holder
	 */
	public LoadingScreen(String path, ScalingFactorHolder scalingFactorHolder) { super(path, scalingFactorHolder); }

	/**
	 * Instantiates a new loading screen.
	 *
	 * @param path                the path
	 * @param scalingFactorHolder the scaling factor holder
	 * @param fps                 the fps
	 */
	public LoadingScreen(String path, ScalingFactorHolder scalingFactorHolder, int fps) { super(path, scalingFactorHolder, fps); }

	/**
	 * Gets the default loading screen.
	 *
	 * @param scalingFactorHolder the scaling factor holder
	 * @return the default loading screen
	 */
	public static LoadingScreen getDefaultLoadingScreen(ScalingFactorHolder scalingFactorHolder) {
		return defaultLoadingScreen != null ? defaultLoadingScreen
				: (defaultLoadingScreen = new LoadingScreen("./res/gui/Loadingscreen.gif", scalingFactorHolder, 10));
	}


}
