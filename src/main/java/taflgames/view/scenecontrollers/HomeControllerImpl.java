package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.GameChoiceScene;

/**
 * This class implements a scene controller for a {@link taflgames.view.scenes.HomeScene}.
 */
public final class HomeControllerImpl extends AbstractBasicSceneController implements HomeController {

    /**
     * Creates a new home scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public HomeControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    @Override
    public void goToNextScene() {
        this.getView().setScene(new GameChoiceScene(
            new GameChoiceControllerImpl(this.getView(), this.getController())
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

}
