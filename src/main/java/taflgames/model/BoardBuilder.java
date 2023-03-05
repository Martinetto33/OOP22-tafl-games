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
    
    /**
     * Registers the size of the board.
     * @param boardSize the size of the board
     */
    void addBoardSize(int boardSize);

    /**
     * Adds the throne and the king to the board being built.
     * @param position the position where the throne and the king must be placed
     */
    void addThroneAndKing(Position position);

    /**
     * Adds the exits cells to the board being built.
     * @param positions the positions where the exits must be placed
     */
    void addExits(Set<Position> positions);
    
    /**
     * Adds the slider cells to the board being built.
     * @param positions the positions where the sliders must be placed
     */
    void addSliders(Set<Position> positions);

    /**
     * Adds the basic cells to the board being built.
     */
    void addBasicCells();

    /**
     * Adds the queen pieces for each team to the board being built.
     * @param positions the positions where the queens must be placed for each team
     */
    void addQueens(Map<Player, Set<Position>> positions);

    /**
     * Adds the archer pieces for each team to the board being built.
     * @param positions the positions where the archers must be placed for each team
     */
    void addArchers(Map<Player, Set<Position>> positions);

    /**
     * Adds the shield pieces for each team to the board being built.
     * @param positions the positions where the shields must be placed for each team
     */
    void addShields(Map<Player, Set<Position>> positions);

    /**
     * Adds the swapper pieces for each team to the board being built.
     * @param positions the positions where the swappers must be placed for each team
     */
    void addSwappers(Map<Player, Set<Position>> positions);

    /**
     * Adds the basic pieces for each team to the board being built.
     * @param positions the positions where the basic pieces must be placed for each team
     */
    void addBasicPieces(Map<Player, Set<Position>> positions);

    /**
     * @return the board set up for the match
     */
    Board build();

    Map<Position, Cell> getCells();

    Map<Player, Map<Position, Piece>> getPieces();

}
