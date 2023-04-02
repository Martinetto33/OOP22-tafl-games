package taflgames.view.scenecontrollers;

import taflgames.controller.leaderboard.api.Leaderboard;

/**
 * An interface to model a controller for the HighScore scene.
 * This controller must be able to communicate with the principal
 * {@link taflgames.controller.Controller} in order to recieve
 * a Leaderboard to display.
 */
public interface HighScoreController extends BasicSceneController {

    /**
     * Requests a {@link taflgames.controller.leaderboard.api.Leaderboard}
     * from the main {@link taflgames.controller.Controller}.
     * @return the Leaderboard to be displayed.
     */
    Leaderboard requestLeaderboard();

}
