package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.GameChoiceScene;
import taflgames.view.scenes.HighScoreScene;

/**
 * This class implements a scene controller for a {@link taflgames.view.scenes.HomeScene}.
 */
public final class HomeSceneControllerImpl extends AbstractBasicSceneController implements HomeSceneController {

    /**
     * Creates a new home scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public HomeSceneControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    @Override
    public void goToNextScene() {
        this.getView().setScene(new GameChoiceScene(
            new GameChoiceSceneControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public void goToPreviousScene() {
        // There is no scene before the home scene
    }

    @Override
    public void close() {
        this.getView().close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToHighScoreScene() {
        this.getView().setScene(new HighScoreScene(
            new HighScoreControllerImpl(this.getView(), this.getController())
        ));
    }

}
