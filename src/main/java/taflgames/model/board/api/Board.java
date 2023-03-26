package taflgames.model.board.api;


import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.memento.api.BoardMemento;
import taflgames.model.pieces.api.Piece;

import java.util.Map;
import taflgames.common.Player;

public interface Board {

    boolean isStartingPointValid(Position start, Player player);

    boolean isDestinationValid(Position start, Position dest, Player player);

    void updatePiecePos(Position oldPos, Position newPos);

    Position getFurthestReachablePos(Position startPos, Vector direction);

    boolean isDraw(final Player playerInTurn);

    /**
     * This method must be called by Match after method {@link #updatePiecePos}
     */
    void eat();

    public Map<Position, Cell> getMapCells();

    public Map<Player, Map<Position, Piece>> getMapPieces();

    BoardMemento save();

    void restore(BoardMemento boardMemento);

}
