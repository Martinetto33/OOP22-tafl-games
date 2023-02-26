package taflgames.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.cells.BasicCell;
import taflgames.model.cells.Cell;
import taflgames.model.cells.Exit;
import taflgames.model.cells.Slider;
import taflgames.model.cells.Throne;
import taflgames.model.pieces.Archer;
import taflgames.model.pieces.BasicPiece;
import taflgames.model.pieces.King;
import taflgames.model.pieces.Piece;
import taflgames.model.pieces.Queen;
import taflgames.model.pieces.Shielder;
import taflgames.model.pieces.Swapper;

public class BoardBuilder {
    
    private final Map<Position, Cell> cells;
    private final Map<Player, Map<Position, Piece>> pieces;
    private final int size;

    public BoardBuilder(final int size) {
        this.cells = new HashMap<>();
        this.pieces = new HashMap<>();
        for (Player player : Player.values()) {
            this.pieces.put(player, new HashMap<>());
        }
        this.size = size;
    }

    public void addThroneAndKing(final Position thronePos) {
        this.cells.put(thronePos, new Throne());
        this.pieces.get(Player.DEFENDER).put(thronePos, new King());
    }

    public void addExits(final Set<Position> positions) {
        for (var pos : positions) {
            this.cells.put(pos, new Exit());
        }
    }

    public void addSliders(final int n, final Set<Position> positions) {
        for (final var pos : positions) {
            this.cells.put(pos, new Slider());
        }
    }

    public void addBasicCells() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                final Position pos = new Position(row, col);
                if (!this.cells.containsKey(pos)) {
                    this.cells.put(pos, new BasicCell());
                }
            }
        }
    }

    public void addQueens(final int n, final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Queen());
            }
        });
    }

    public void addArchers(final int n, final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Archer());
            }
        });
    }

    public void addShielders(final int n, final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Shielder());
            }
        });
    }

    public void addSwappers(final int n, final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Swapper());
            }
        });
    }

    public void addBasicPieces(final int n, final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new BasicPiece());
            }
        });
    }

    public Board build() {
        return new BoardImpl(this.cells, this.pieces);
    }

}
