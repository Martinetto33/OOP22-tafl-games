package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.UserRegistrationScene;

/**
 * This class implements game choice scene controller.
 */
public final class GameChoiceControllerImpl implements GameChoiceController {

    private final View view;
    private final Controller controller;

    /**
     * Creates a new game choice scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public GameChoiceControllerImpl(final View view, final Controller controller) {
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void goToNextScene() {
        this.view.setScene(new UserRegistrationScene(new UserRegistrationControllerImpl(this.view, this.controller)));
    }

    @Override
    public void goToPreviousScene() {
        this.view.setScene(new HomeScene(new HomeControllerImpl(this.view, this.controller)));
    }

    @Override
    public void createClassicModeMatch() {
        this.controller.createClassicModeMatch();
    }

    @Override
    public void createVariantModeMatch() {
        this.controller.createVariantModeMatch();
    }

}
