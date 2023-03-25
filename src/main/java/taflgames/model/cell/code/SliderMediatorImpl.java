package taflgames.model.cell.code;

import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.SliderMediator;

public class SliderMediatorImpl implements SliderMediator {

    private final Board board;

    public SliderMediatorImpl(final Board board) {
        this.board = board;
    }

    @Override
    public Position requestMove(Position source, Vector orientation) {
        return board.getFurthestReachablePos(source, orientation);
        
    }

    @Override
    public void updatePiecePos(Position startPos, Position destPos) {
        board.updatePiecePos(startPos, destPos);
    }
    
}
