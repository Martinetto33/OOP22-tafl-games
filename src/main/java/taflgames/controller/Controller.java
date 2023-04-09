package taflgames.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import taflgames.common.Player;
import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.common.code.Position;
import taflgames.controller.entitystate.CellState;
import taflgames.controller.entitystate.PieceState;

/**
 * This interface describes the controller of the application.
 */
public interface Controller {

    /**
     * Initializes a new classic mode match.
     * @throws IOException if an error occurs during the initialization of the match
     */
    void createClassicModeMatch() throws IOException;

    /**
     * Initializes a new variant mode match.
     * @throws IOException if an error occurs during the initialization of the match
     */
    void createVariantModeMatch() throws IOException;

    /**
     * Checks if the first selected {@link taflgames.common.code.Position} represents
     * a valid starting point for a move.
     * @param p the selected {@link taflgames.common.code.Position}
     * @return true if the selection is a valid starting point for a move for the player
     * in turn.
     */
    boolean isStartingPointValid(Position p);

    /**
     * Checks if the move is valid.
     * @param startPos the starting {@link taflgames.common.code.Position}.
     * @param endPos the ending {@link taflgames.common.code.Position}.
     * @return true if the move is valid.
     */
    boolean isDestinationValid(Position startPos, Position endPos);

    /**
     * Makes a move, only if starting point and ending point represent a legal move.
     * @param startPos the starting {@link taflgames.common.code.Position}.
     * @param endPos the ending {@link taflgames.common.code.Position}.
     * @return true if the move is legal and then performed, false otherwise
     */
    boolean moveIfLegal(Position startPos, Position endPos);

    /**
     * @return true if the match is over.
     */
    boolean isOver();

    /**
     * Undoes the last move of this turn, if any. Only the first call
     * to this method will actually have an impact on the game state.
     */
    void undo();

    /**
     * When a player is sure about his move, this method should be
     * called to advance the state of the game.
     */
    void passTurn();

    /**
     * If the match is over, returns an object that describes the result
     * of the Attacker player and the Defender player. The first element
     * of the Pair represents the Attacker's result, the second element
     * of the Pair represents the Defender's result.
     * @return an Optional of the two {@link taflgames.common.code.MatchResult}
     * obtained by the players if the Match is over, or an empty Optional
     * otherwise.
     */
    Optional<Pair<MatchResult, MatchResult>> getMatchResult();

    /**
     * Returns a map that might be useful for the purpose of the View, which
     * describes the current state of the cells on the Board.
     * @return a Map describing the current state of the Cells. The values
     * of these entries are guaranteed to have the Cell type at index 0; if
     * other components are present, their types will follow at the successive
     * indexes.
     */
    Map<Position, CellState> getCellsDisposition();

    /**
     * Returns a map that might be useful for the purpose of the View, which
     * describes the current state of the pieces on the Board.
     * @return a Map describing the current state of the Pieces.
     */
    Map<Position, PieceState> getPiecesDisposition();

    /**
     * @return the player in turn.
     */
    Player getCurrentPlayer();

    /**
     * Returns a Map view of an existing {@link taflgames.controller.leaderboard.api.Leaderboard}.
     * @return a Map containing the entries playerNickname-winsAndLosses.
     */
    Map<String, Pair<Integer, Integer>> getLeaderboard();

    /**
     * Clears any current leaderboard.
     */
    void clearLeaderboard();
}
