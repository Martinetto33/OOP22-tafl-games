package taflgames.model;

import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.cells.Cell;
import taflgames.model.pieces.Piece;

/**
 * This class builds a {@link Board}.
 */
public interface BoardBuilder {
    
    void addBoardSize(int boardSize);

    void addThroneAndKing(Position thronePos);

    void addExits(Set<Position> positions);
    
    void addSliders(Set<Position> positions);

    void addBasicCells();

    void addQueens(Map<Player, Set<Position>> positions);

    void addArchers(Map<Player, Set<Position>> positions);

    void addShields(Map<Player, Set<Position>> positions);

    void addSwappers(Map<Player, Set<Position>> positions);

    void addBasicPieces(Map<Player, Set<Position>> positions);

    Board build();

    Map<Position, Cell> getCells();

    Map<Player, Map<Position, Piece>> getPieces();
    
}
