package taflgames.model.cell.code;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.SliderMediator;

public final class SliderMediatorImpl implements SliderMediator {

    private final Board board;

    public SliderMediatorImpl(final Board board) {
        this.board = board;
    }

    @Override
    public Position requestMove(final Position source, final Vector orientation) {
        return board.getFurthestReachablePos(source, orientation);
    }

    @Override
    public void updatePiecePos(final Position startPos, final Position destPos, final Player currentPlayer) {
        board.movePlaceholder(startPos, destPos, currentPlayer);
    }

}
