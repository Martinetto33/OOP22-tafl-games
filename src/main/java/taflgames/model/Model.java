package taflgames.model;

import taflgames.common.code.Pair;
import taflgames.common.code.Position;
import taflgames.controller.entitystate.CellState;
import taflgames.controller.entitystate.PieceState;
import taflgames.model.memento.api.MatchMemento;

import java.util.Map;
import java.util.Optional;

import taflgames.common.code.MatchResult;
import taflgames.common.Player;

/**
 * Interface to interact with the game logic.
 */
public interface Model {

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
     * Checks if the match is over; if it is, then returns the result of the match.
     * @return if the match is over, it returns an {@link Optional} of a {@link Pair} contaning the result of the match
     * for the attacker (first element) and the defender (second element);
     * otherwise, an empty {@link Optional} is returned.
     */
    Optional<Pair<MatchResult, MatchResult>> getMatchEndStatus();

    /**
     * @return a collection that maps each position of the grid to a list of labels
     * that indicate the cell and (if present) the cells components located at that position.
     */
    Map<Position, CellState> getCellsMapping();

    /**
     * @return a collection that, for each player, contains the mapping of the player's
     * pieces positions to the type of pieces located at each position.
     */
    Map<Position, PieceState> getPiecesMapping();

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
