package taflgames.controller.gameloop.api;

import java.util.Optional;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.common.code.Position;

public interface GameLoop {

    /**
     * Starts the GameLoop.
     */
    void startGameLoop();

    /**
     * Ends the GameLoop.
     */
    void endGameLoop();

    /**
     * Attempts to select a Position in the current match.
     * @param p the Position to be selected.
     * @return true if the specified Position can be selected.
     */
    //boolean canSelect(Position p);
    //THIS METHOD SHOULD BE IN THE CONTROLLER

    /**
     * If the position was already selected, this may
     * deselect it.
     * @param startPos the starting {@link taflgames.common.code.Position}.
     * @param endPos the ending {@link taflgames.common.code.Position}.
     * @throws IllegalAccessException if the GameLoop is not running.
     */
    void makeMove(Position startPos, Position endPos) throws IllegalAccessException;

    /**
     * Returns back to the game's state before the last move made.
     * @throws IllegalAccessException if the GameLoop is not running.
     */
    void undo() throws IllegalAccessException;

    /**
     * Used to signal to the GameLoop that the player has
     * passed their turn.
     * @throws IllegalAccessException
     */
    void passTurn() throws IllegalAccessException;

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
