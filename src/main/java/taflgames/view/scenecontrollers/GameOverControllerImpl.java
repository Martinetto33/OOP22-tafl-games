package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.HomeScene;

/**
 * This class implements game over scene controller.
 */
public final class GameOverControllerImpl implements GameOverController {

    private final View view;
    private final Controller controller;

    /**
     * Creates a new game over scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public GameOverControllerImpl(final View view, final Controller controller) {
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void goToNextScene() {
        this.view.setScene(new HomeScene(new HomeControllerImpl(this.view, this.controller)));
    }

    @Override
    public void goToPreviousScene() {
        /*
         * TO DO
         */
    }

}
