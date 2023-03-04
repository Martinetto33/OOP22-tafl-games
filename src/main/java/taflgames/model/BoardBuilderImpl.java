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
import taflgames.model.pieces.Shield;
import taflgames.model.pieces.Swapper;


public class BoardBuilderImpl implements BoardBuilder {
    
    private final Map<Position, Cell> cells;
    private final Map<Player, Map<Position, Piece>> pieces;
    private int boardSize;

    public BoardBuilderImpl() {
        this.cells = new HashMap<>();
        this.pieces = new HashMap<>();
        for (Player player : Player.values()) {
            this.pieces.put(player, new HashMap<>());
        }
    }

    @Override
    public void addBoardSize(final int boardSize) {
        this.boardSize = boardSize;
    }

    @Override
    public void addThroneAndKing(final Position thronePos) {
        this.cells.put(thronePos, new Throne());
        this.pieces.get(Player.DEFENDER).put(thronePos, new King());
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
    public void addQueens(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Queen());
            }
        });
    }

    @Override
    public void addArchers(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Archer());
            }
        });
    }

    @Override
    public void addShields(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Shield());
            }
        });
    }

    @Override
    public void addSwappers(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Swapper());
            }
        });
    }

    @Override
    public void addBasicPieces(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new BasicPiece());
            }
        });
    }

    @Override
    public Board build() {
        return new BoardImpl(this.cells, this.pieces);
    }

    @Override
    public Map<Position, Cell> getCells() {
        return this.cells;
    }

    @Override
    public Map<Player, Map<Position, Piece>> getPieces() {
        return this.pieces;
    }
    
}
