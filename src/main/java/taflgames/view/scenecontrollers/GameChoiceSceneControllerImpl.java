package taflgames.view.scenecontrollers;

import java.io.IOException;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.MatchScene;
import taflgames.view.scenes.RulesScene;

/**
 * This class implements a scene controller for a {@link taflgames.view.scenes.GameChoiceScene}.
 */
public final class GameChoiceSceneControllerImpl extends AbstractBasicSceneController implements GameChoiceSceneController {

    /**
     * Creates a new game choice scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public GameChoiceSceneControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    @Override
    public void goToNextScene() {
        this.getView().setScene(new MatchScene(
            new MatchSceneControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public void goToPreviousScene() {
        this.getView().setScene(new HomeScene(
            new HomeSceneControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public void goToRulesScene() {
        this.getView().setScene(new RulesScene(
            new RulesSceneControllerImpl(this.getView(), this.getController())
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
