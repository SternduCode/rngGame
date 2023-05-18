package rngGame.visual;

import javafx.animation.FadeTransition;
import javafx.util.Duration;


// TODO: Auto-generated Javadoc
/**
 * The Class LoadingScreen.
 */
public class LoadingScreen extends AnimatedImage {

	/**
	 * Instantiates a new loading screen.
	 *
	 * @param logic the logic
	 */
	public LoadingScreen(rngGame.ui.LoadingScreen logic) { super(logic); }

	/**
	 * Go into loading screen.
	 */
	public void goIntoLoadingScreen() {

		setFitWidth(getImage().getWidth() * logic.getScalingFactorHolder().scalingFactorX());
		setFitHeight(getImage().getHeight() * logic.getScalingFactorHolder().scalingFactorY());

		FadeTransition ft = new FadeTransition(Duration.millis(250), this);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

	}

	/**
	 * Go out of loading screen.
	 */
	public void goOutOfLoadingScreen() {
		setFitWidth(getImage().getWidth() * logic.getScalingFactorHolder().scalingFactorX());
		setFitHeight(getImage().getHeight() * logic.getScalingFactorHolder().scalingFactorY());

		FadeTransition ft = new FadeTransition(Duration.millis(250), this);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();

	}

	/**
	 * Checks if is in loading screen.
	 *
	 * @return true, if is in loading screen
	 */
	public boolean isInLoadingScreen() {
		return getOpacity() > .5;
	}

}
