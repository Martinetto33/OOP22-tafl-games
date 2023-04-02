package taflgames.view.scenecontrollers;

import java.util.Map;

import taflgames.common.code.Pair;
import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.HomeScene;

/**
 * A controller for the HighScore Scene, which displays players' results organised
 * in a Leaderboard.
 */
public class HighScoreControllerImpl extends AbstractBasicSceneController implements HighScoreController {

    /**
     * Builds a new HighScoreControllerImpl.
     * @param view the main {@link taflgames.view.View} of the application.
     * @param controller the main {@link taflgames.controller.Controller} of the application.
     */
    public HighScoreControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Pair<Integer, Integer>> requestLeaderboard() {
        return this.getController().getLeaderboard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToNextScene() {
        // no next scene after HighScore scene
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToPreviousScene() {
        this.getView().setScene(new HomeScene(
            new HomeControllerImpl(this.getView(), this.getController())));
    }
    
}
