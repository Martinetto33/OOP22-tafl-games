package taflgames.model.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import taflgames.common.code.Position;
import taflgames.model.cell.code.ClassicCell;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.code.Exit;
import taflgames.model.cell.code.SliderImpl;
import taflgames.model.cell.code.Throne;

/**
 * This class implements a builder to create a collection of cells.
 */
public final class CellsCollectionBuilderImpl implements CellsCollectionBuilder {

    private final Map<Position, Cell> cells;
    private int boardSize;

    /**
     * Creates a new builder for a collection of cells.
     */
    public CellsCollectionBuilderImpl() {
        this.cells = new HashMap<>();
    }

    @Override
    public void addBoardSize(final int boardSize) {
        this.boardSize = boardSize;
    }

    @Override
    public void addThrone(final Position thronePos) {
        this.cells.put(thronePos, new Throne());
    }

    @Override
    public void addExits(final Set<Position> positions) {
        for (final var pos : positions) {
            this.cells.put(pos, new Exit());
        }
    }

    @Override
    public void addSliders(final Set<Position> positions) {
        for (final var pos : positions) {
            this.cells.put(pos, new SliderImpl(pos));
        }
    }

    @Override
    public void addBasicCells() {
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                final Position pos = new Position(row, col);
                if (!this.cells.containsKey(pos)) {
                    this.cells.put(pos, new ClassicCell());
                }
            }
        }
    }

    @Override
    public void setCellAsOccupied(final Position position) {
        this.cells.get(position).setFree(false);
    }

    @Override
    public Map<Position, Cell> build() {
        return new HashMap<>(this.cells);
    }

}
