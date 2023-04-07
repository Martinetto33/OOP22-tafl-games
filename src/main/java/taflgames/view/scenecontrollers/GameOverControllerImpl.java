package taflgames.view.scenecontrollers;

import java.util.Optional;

import taflgames.common.Player;
import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.UserRegistrationScene;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.GameOverScene}.
 */
public final class GameOverControllerImpl extends AbstractBasicSceneController implements GameOverController {

    /**
     * Builds a new GameOver scene controller.
     * @param view the main {@link taflgames.view.View} of the application.
     * @param controller the main {@link taflgames.controller.Controller} of the application.
     */
    public GameOverControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    /**
     * After a GameOver screen, the next scene will be the home one.
     */
    @Override
    public void goToNextScene() {
        this.getView().setScene(new HomeScene(
            new HomeSceneControllerImpl(this.getView(), this.getController())));
    }

    /**
     * No previous scene to Game Over to go back to.
     */
    @Override
    public void goToPreviousScene() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goToRegistrationScene() {
        this.getView().setScene(new UserRegistrationScene(
            new UserRegistrationControllerImpl(this.getView(), this.getController())
        ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> getWinner() {
        final Optional<Pair<MatchResult, MatchResult>> result = this.getController().getMatchResult();
        if (result.isEmpty()) {
            throw new IllegalStateException("No match result obtained in the game over scene.");
        }
        final MatchResult attackerResult = result.get().getX();
        final MatchResult defenderResult = result.get().getY();
        if (attackerResult.equals(MatchResult.DRAW) && defenderResult.equals(MatchResult.DRAW)) {
            return Optional.empty();
        } else if (attackerResult.equals(MatchResult.VICTORY)) {
            return Optional.of(Player.ATTACKER);
        } else if (defenderResult.equals(MatchResult.VICTORY)) {
            return Optional.of(Player.DEFENDER);
        } else {
            throw new IllegalStateException("Something wrong happened with the match result...");
        }
    }
}
