package taflgames.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import taflgames.common.code.Position;
import taflgames.model.cells.BasicCell;
import taflgames.model.cells.Cell;
import taflgames.model.cells.Exit;
import taflgames.model.cells.Slider;
import taflgames.model.cells.Throne;

public class CellsCollectionBuilderImpl implements CellsCollectionBuilder {
    
    private final Map<Position, Cell> cells;
    private int boardSize;

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
        for (var pos : positions) {
            this.cells.put(pos, new Exit());
        }
    }

    @Override
    public void addSliders(final Set<Position> positions) {
        for (final var pos : positions) {
            this.cells.put(pos, new Slider());
        }
    }

    @Override
    public void addBasicCells() {
        for (int row = 0; row < this.boardSize; row++) {
            for (int col = 0; col < this.boardSize; col++) {
                final Position pos = new Position(row, col);
                if (!this.cells.containsKey(pos)) {
                    this.cells.put(pos, new BasicCell());
                }
            }
        }
    }

    @Override
    public Map<Position, Cell> build() {
        return this.cells;
    }

}
