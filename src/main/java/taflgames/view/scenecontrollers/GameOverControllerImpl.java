package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.UserRegistrationScene;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.GameOverScene}.
 */
public final class GameOverControllerImpl extends AbstractBasicSceneController implements GameOverController {

    /**
     * Builds a new GameOver scene controller.
     * @param view the main {@link taflgames.view.View} of the application.
     * @param controller the main {@link taflgames.controller.Controller} of the application.
     */
    public GameOverControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    /**
     * After a GameOver screen, the next scene will be the home one.
     */
    @Override
    public void goToNextScene() {
        this.getView().setScene(new HomeScene(
            new HomeSceneControllerImpl(this.getView(), this.getController())));
    }

    /**
     * No previous scene to Game Over to go back to.
     */
    @Override
    public void goToPreviousScene() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToRegistrationScene() {
        this.getView().setScene(new UserRegistrationScene(
            new UserRegistrationControllerImpl(this.getView(), this.getController())
        ));
    }
}
