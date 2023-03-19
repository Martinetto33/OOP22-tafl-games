package taflgames.view.scenecontrollers;

import taflgames.common.code.MatchResult;
import taflgames.controller.Controller;
import taflgames.model.leaderboard.api.Leaderboard;
import taflgames.model.leaderboard.api.LeaderboardSaver;
import taflgames.model.leaderboard.code.LeaderboardSaverImpl;
import taflgames.view.View;
import taflgames.view.scenes.GameOverScene;
import taflgames.view.scenes.HomeScene;

/**
 * This class implements user registration scene controller.
 * It is responsible for modifying the leaderboard by adding new results
 * from matches. This phase is not mandatory, so players can simply decide
 * not to register their names.
 */
public final class UserRegistrationControllerImpl extends AbstractBasicSceneController implements UserRegistrationController {
    private final LeaderboardSaver saver;
    private final Leaderboard leaderboard;

    /**
     * Creates a new user registration scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public UserRegistrationControllerImpl(final View view, final Controller controller) {
        super(view, controller);
        this.saver = new LeaderboardSaverImpl();
        this.leaderboard = this.saver.retrieveFromSave();
    }

    /**
     * Next scene after user registration is the main menu.
     */
    @Override
    public void goToNextScene() {
        this.getView().setScene(new HomeScene(
            new HomeControllerImpl(this.getView(), this.getController())
        ));
    }

    /**
     * If the players decide not to register their results or to simply go back,
     * they will be shown again the game over scene.
     */
    @Override
    public void goToPreviousScene() {
        this.getView().setScene(new GameOverScene(
            new GameOverControllerImpl(this.getView(), this.getController())
        ));
    }

    /**
     * Registers the match results into a {@link taflgames.model.leaderboard.api.Leaderboard}.
     * @param player1 the name of the first player.
     * @param player2 the name of the second player.
     * @param player1Result the result obtained by the first player.
     * @param player2Result the result obtained by the second player.
     */
    public void registerMatchResult(String player1, String player2, MatchResult player1Result, MatchResult player2Result) {
        this.leaderboard.addResult(player1, player1Result);
        this.leaderboard.addResult(player2, player2Result);
        this.saver.saveLeaderboard(this.leaderboard);
    }
}
