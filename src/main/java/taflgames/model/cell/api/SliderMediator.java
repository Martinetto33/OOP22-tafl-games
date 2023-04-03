package taflgames.model.cell.api;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;

/**
 * This interface allows entities to comunicate with 
 * the Board and use a few of its methods.
 * In our version of the game Slider is the only entity 
 * that needs to use Board's methods.
 */
public interface SliderMediator {

    /**
     * Calls the method getFurthestReachablePos of Board 
     * to know the furthest Position reachable by a Piece starting from a given Position.
     * @param source the Position where the Piece is located.
     * @param orientation the direction along which to find the furthest position.
     * @return The furthest Position reachable.
     */
    Position requestMove(Position source, Vector orientation);  // NOPMD
    // The Vector class models a vector and provides features that a List does not support.

    /**
     * Calss method movePlaceholder of Board {@link taflgames.model.board.api.Board}
     * to update the position of a Piece.
     * @param startPos the starting Position.
     * @param destPos the new Position which the Piece will be updated to.
     * @param currentPlayer the Player in turn.
     */
    void updatePiecePos(Position startPos, Position destPos, Player currentPlayer);
}
