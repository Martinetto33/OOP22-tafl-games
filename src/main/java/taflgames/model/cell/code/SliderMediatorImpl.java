package taflgames.model.cell.code;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.SliderMediator;

/**
 * A class made using the Mediator pattern, to allow communication
 * between a {@link taflgames.model.cell.api.Slider} and a
 * {@link taflgames.model.board.api.Board}. This is needed because Sliders
 * are a special kind of Cell that push the pieces that arrive onto them
 * to the furthest reachable position in the direction pointed by them.
 */
public final class SliderMediatorImpl implements SliderMediator {

    private final Board board;

    /**
     * Builds a new SliderMediatorImpl, and couples it with a given
     * parameter Board.
     * @param board the board which will be asked for additional
     * movements.
     */
    public SliderMediatorImpl(final Board board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position requestMove(final Position source, final Vector orientation) {  // NOPMD
        // The Vector class models a vector and provides features that a List does not support.
        return board.getFurthestReachablePos(source, orientation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePiecePos(final Position startPos, final Position destPos, final Player currentPlayer) {
        board.movePlaceholder(startPos, destPos, currentPlayer);
    }

}
