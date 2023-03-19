package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.UserRegistrationScene;

public final class GameOverControllerImpl extends AbstractBasicSceneController implements GameOverController {

    public GameOverControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    /**
     * After a GameOver screen, the next scene will be the home one.
     */
    @Override
    public void goToNextScene() {
        this.getView().setScene(new HomeScene(
            new HomeControllerImpl(this.getView(), this.getController())));
    }

    @Override
    public void goToPreviousScene() {
        // no previous scene to Game Over to go back to
    }
    
    public void goToRegistrationScene() {
        this.getView().setScene(new UserRegistrationScene(
            new UserRegistrationControllerImpl(this.getView(), this.getController())
        ));
    }
}
