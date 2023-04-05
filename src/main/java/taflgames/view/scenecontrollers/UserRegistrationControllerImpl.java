package taflgames.view.scenecontrollers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import taflgames.common.Player;
import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.controller.Controller;
import taflgames.controller.leaderboard.api.Leaderboard;
import taflgames.controller.leaderboard.api.LeaderboardSaver;
import taflgames.controller.leaderboard.code.LeaderboardSaverImpl;
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
    private Map<Player, MatchResult> result = null;

    /**
     * Creates a new user registration scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public UserRegistrationControllerImpl(final View view, final Controller controller) {
        super(view, controller);
        this.saver = new LeaderboardSaverImpl();
        this.leaderboard = this.saver.retrieveFromSave();
        this.getEndMatchResults();
    }

    /**
     * Next scene after user registration is the main menu.
     */
    @Override
    public void goToNextScene() {
        this.getView().setScene(new HomeScene(
            new HomeSceneControllerImpl(this.getView(), this.getController())
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
     *{@inheritDoc}
     */
    @Override
    public void getEndMatchResults() {
        Optional<Pair<MatchResult, MatchResult>> matchRes = this.getController().getMatchResult();
        if (matchRes.isPresent()) {
            this.setEndMatchResults(matchRes.get().getX(), matchRes.get().getY());
            return;
        }
        throw new IllegalStateException("Match result is not present!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEndMatchResults(final MatchResult attackerResult, final MatchResult defenderResult) {
        if (this.result == null || !this.result.isEmpty()) {
            this.result = new HashMap<>();
        }
        this.result.put(Player.ATTACKER, attackerResult);
        this.result.put(Player.DEFENDER, defenderResult);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerMatchResult(final String attackerPlayer, final String defenderPlayer) {
        Objects.requireNonNull(this.result);
        if (this.result.size() != Player.values().length 
                   || !this.result.keySet().containsAll(List.of(Player.ATTACKER, Player.DEFENDER))) {
            throw new IllegalStateException("Cannot find result for both attacker and defender!");
        }
        this.leaderboard.addResult(attackerPlayer, this.result.get(Player.ATTACKER));
        this.leaderboard.addResult(defenderPlayer, this.result.get(Player.DEFENDER));
        this.saver.saveLeaderboard(this.leaderboard);
    }

}
