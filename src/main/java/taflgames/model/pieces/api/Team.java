package taflgames.model.pieces.api;

import java.util.Optional;

import taflgames.common.Player;


/**
 *TO DO: REMOVE FROM PROJECT.
 */
public interface Team {
    /**
     * returns the name of the pieces' player if it was entered.
     * @return name of player or null
     */
    Optional<String> getNameOfUserPlayer();
    /**
     * returns the pieces' team, which cuold be either ATTACKER.
     * or DEFENDER
     * @return ATTACKER or DEFENDER
     */
    Player getPlayerTeam();

}
