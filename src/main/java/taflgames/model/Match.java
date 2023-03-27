package taflgames.model;

import taflgames.common.code.Position;
import taflgames.model.memento.api.MatchMemento;
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
     * @return the number of the current turn
     */
    int getTurnNumber();

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

    /**
     * Saves a snapshot of the current state of the match, in order to support the "undo" operation.
     * @return the {@link MatchMemento} that holds the snapshot
     */
    MatchMemento save();

    /**
     * Restores the state of the match saved in the {@link MatchMemento} passed as argument.
     * @param matchMemento the {@link MatchMemento} that holds the snapshot
     */
    void restore(MatchMemento matchMemento);

}
