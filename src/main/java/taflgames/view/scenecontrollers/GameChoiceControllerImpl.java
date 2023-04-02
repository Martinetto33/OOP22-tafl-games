package taflgames.view.scenecontrollers;

import java.io.IOException;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.RulesScene;
import taflgames.view.scenes.UserRegistrationScene;

/**
 * This class implements a scene controller for a {@link taflgames.view.scenes.GameChoiceScene}.
 */
public final class GameChoiceControllerImpl extends AbstractBasicSceneController implements GameChoiceController {

    /**
     * Creates a new game choice scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public GameChoiceControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    @Override
    public void goToNextScene() {
        this.getView().setScene(new UserRegistrationScene(
            new UserRegistrationControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public void goToPreviousScene() {
        this.getView().setScene(new HomeScene(
            new HomeControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public void goToRulesScene() {
        this.getView().setScene(new RulesScene(
            new RulesDisplayControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public void createClassicModeMatch() throws IOException {
        this.getController().createClassicModeMatch();
    }

    @Override
    public void createVariantModeMatch() throws IOException {
        this.getController().createVariantModeMatch();
    }

}
