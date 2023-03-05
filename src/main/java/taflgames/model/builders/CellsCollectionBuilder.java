package taflgames.model.builders;

import java.util.Map;
import java.util.Set;

import taflgames.common.code.Position;
import taflgames.model.cells.Cell;

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
     * @return the collection of cells that has been set up
     */
    Map<Position, Cell> build();

}
