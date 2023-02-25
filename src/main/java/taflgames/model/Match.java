package taflgames.model;

import taflgames.common.api.Position;
import taflgames.common.Player;

/**
 * Interface to interact with the game logic.
 */
public interface Match {

    /**
     * @return the active player
     */
    Player getActivePlayer();

    /**
     * Updates the active player.
     */
    void setNextActivePlayer();

    /**
     * Checks if the selected starting position is valid.
     * @param start the selected starting position
     * @return true if the position is valid, false otherwise
     */
    boolean selectSource(Position start);

    /**
     * Given the starting position {@code start}, checks if the selected destination
     * is valid.
     * @param start the starting position
     * @param destination the destination
     * @return true if the destination is valid, false otherwise
     */
    boolean selectDestination(Position start, Position destination);

    /**
     * Prompts the execution of the move from the starting position {@code start}
     * to the destination {@code destination}.
     * @param start the starting position
     * @param destination the destination
     */
    void makeMove(Position start, Position destination);

    /**
     * Checks if the match is over.
     * @return true if the match is over, false otherwise
     */
    boolean isOver();

}
