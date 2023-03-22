package taflgames.view.scenecontrollers;

import taflgames.common.code.MatchResult;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.UserRegistrationScene}.
 */
public interface UserRegistrationController extends BasicSceneController {
    
    /**
     * This method should be called by an external controller in order to register
     * the results of a completed match. No binding between username and player role
     * exists, since the users are free to chose if they want or not to register their
     * scores. This is why these results are only associated with the roles specified
     * in the {@link taflgames.common.Player} class.
     * @param attackerResult the result of the attacker player
     * @param defenderResult the result of the defender player
     */
    void getResultsAtTheEndOfMatch(MatchResult attackerResult, MatchResult defenderResult);

    /**
     * Registers the match results into a {@link taflgames.model.leaderboard.api.Leaderboard},
     * once the players' names are received from a view element.
     * @param player1 the name of the first player.
     * @param player2 the name of the second player.
     * @param player1Result the result obtained by the first player.
     * @param player2Result the result obtained by the second player.
     */
    void registerMatchResult(String attackerPlayer, String defenderPlayer);
    
}
