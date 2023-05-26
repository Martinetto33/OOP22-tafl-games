package taflgames.model.builders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        positions.forEach(pos -> this.cells.put(pos, new Exit()));
    }

    @Override
    public void addSliders(final Set<Position> positions) {
        positions.forEach(pos -> this.cells.put(pos, new SliderImpl(pos)));
    }

    @Override
    public void addBasicCells() {
        // Generate all positions of the grid
        final Set<Position> positions = Stream.iterate(0, row -> row + 1).limit(this.boardSize)
                .map(row -> Stream.iterate(0, col -> col + 1).limit(this.boardSize)
                                .map(col -> new Position(row, col))
                                .collect(Collectors.toSet()))
                .collect(HashSet::new, HashSet::addAll, HashSet::addAll);
        // For each position where no other type of cell has been placed, place a classic cell
        positions.stream()
                .filter(pos -> !this.cells.containsKey(pos))
                .forEach(pos -> this.cells.put(pos, new ClassicCell()));
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
