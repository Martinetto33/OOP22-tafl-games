package taflgames.model.cell.api;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;

/**
 * An interface modelling a Slider Mediator. Following the pattern Mediator,
 * this object allows communication between a {@link taflgames.model.cell.api.Slider}
 * and a {@link taflgames.model.board.api.Board}.
 */
public interface SliderMediator {

    /**
     * Requests the Board linked to this Mediator to return the furthest reachable position
     * in the direction pointed by the linked Slider.
     * @param source the {@link taflgames.common.code.Position} of the Slider.
     * @param orientation a {@link taflgames.common.api.Vector} describing the
     * orientation of the Slider.
     * @return the furthest reachable Position.
     */
    Position requestMove(Position source, Vector orientation);  // NOPMD
    // The Vector class models a vector and provides features that a List does not support.

    /**
     * Moves the Piece that landed onto the linked Slider to the
     * furthest reachable position returned by the Board.
     * @param startPos the Slider position.
     * @param destPos the furthest reachable position.
     * @param currentPlayer the player in turn.
     */
    void updatePiecePos(Position startPos, Position destPos, Player currentPlayer);
}
