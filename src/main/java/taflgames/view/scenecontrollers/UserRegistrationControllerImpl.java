package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.GameChoiceScene;

/**
 * This class implements user registration scene controller.
 */
public final class UserRegistrationControllerImpl implements UserRegistrationController {

    private final View view;
    private final Controller controller;

    /**
     * Creates a new user registration scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public UserRegistrationControllerImpl(final View view, final Controller controller) {
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void goToNextScene() {
        /*
         * TO DO
         */
    }

    @Override
    public void goToPreviousScene() {
        this.view.setScene(new GameChoiceScene(new GameChoiceControllerImpl(this.view, this.controller)));
    }

}
