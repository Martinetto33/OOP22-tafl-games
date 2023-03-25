package taflgames.model;

import java.util.Map;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.cells.Cell;
import taflgames.model.memento.api.BoardMemento;
import taflgames.model.pieces.api.Piece;

/**
 * This class implements a {@link Board}.
 */
public class BoardImpl implements Board {

    private final Map<Position, Cell> cells;
    private final Map<Player, Map<Position, Piece>> pieces;

    public BoardImpl(
        final Map<Position, Cell> cells,
        final Map<Player, Map<Position, Piece>> pieces
    ) {
        this.cells = cells;
        this.pieces = pieces;
    }

    @Override
    public boolean isStartingPointValid(Position start, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isStartingPointValid'");
    }

    @Override
    public boolean isDestinationValid(Position start, Position dest, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDestinationValid'");
    }

    @Override
    public void makeMove(Position oldPos, Position newPos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeMove'");
    }

    @Override
    public Position getFurthestReachablePos(Vector direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFurthestReachablePos'");
    }

    @Override
    public void moveByVector(Vector direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'moveByVector'");
    }

    @Override
    public void restore(BoardMemento boardMemento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restore'");
    }

    @Override
    public BoardMemento save() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

}
