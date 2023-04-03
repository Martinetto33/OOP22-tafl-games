package taflgames.model.cell.code;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.SliderMediator;

/**
 * This class models a SliderMediator {@link taflgames.model.cell.api.SliderMediator}
 */
public final class SliderMediatorImpl implements SliderMediator {

    private final Board board;

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
