package taflgames.model.builders;

import java.util.Map;
import java.util.Set;

import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;

/**
 * This interface allows to interact with a builder to create a collection of cells.
 */
public interface CellsCollectionBuilder {

    /**
     * Registers the size of the board.
     * @param boardSize the size of the board
     */
    void addBoardSize(int boardSize);

    /**
     * Adds the throne cell to the cells collection being built.
     * @param position the position where the throne must be placed
     */
    void addThrone(Position position);

    /**
     * Adds the exits cells to the cells collection being built.
     * @param positions the positions where the exits must be placed
     */
    void addExits(Set<Position> positions);

    /**
     * Adds the slider cells to the cells collection being built.
     * @param positions the positions where the sliders must be placed
     */
    void addSliders(Set<Position> positions);

    /**
     * Adds the basic cells to the cells collection being built.
     */
    void addBasicCells();

    /**
     * Marks the cell at {@code position} as occupied by a piece.
     * @param position the position of the occupied cell
     */
    void setCellAsOccupied(Position position);

    /**
     * @return the collection of cells that has been set up
     */
    Map<Position, Cell> build();

}
