package taflgames.controller.gameloop.api;

import java.util.Optional;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.common.code.Position;

/**
 * An interface modelling a GameLoop, responsible for the direct communication
 * with a {@link taflgames.model.Match}.
 */
public interface GameLoop {

    /**
     * If the position was already selected, this may
     * deselect it.
     * @param startPos the starting {@link taflgames.common.code.Position}.
     * @param endPos the ending {@link taflgames.common.code.Position}.
     */
    void makeMove(Position startPos, Position endPos);

    /**
     * Returns back to the game's state before the last move made.
     */
    void undo();

    /**
     * Used to signal to the GameLoop that the player has
     * passed their turn.
     * 
     */
    void passTurn();

    /**
     * Tells if the match is over.
     * @return true if the match is over.
     */
    boolean isOver();

    /**
     * Returns the result of this match, if the match has ended.
     * @return an Optional describing the result of this match if the
     * match has ended, an empty Optional otherwise.
     */
    Optional<Pair<MatchResult, MatchResult>> getMatchResult();

}
