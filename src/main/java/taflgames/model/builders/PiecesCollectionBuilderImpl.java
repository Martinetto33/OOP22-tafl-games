package taflgames.model.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.pieces.Archer;
import taflgames.model.pieces.BasicPiece;
import taflgames.model.pieces.King;
import taflgames.model.pieces.Piece;
import taflgames.model.pieces.Queen;
import taflgames.model.pieces.Shield;
import taflgames.model.pieces.Swapper;

public class PiecesCollectionBuilderImpl implements PiecesCollectionBuilder {
    
    private final Map<Player, Map<Position, Piece>> pieces;

    public PiecesCollectionBuilderImpl() {
        this.pieces = new HashMap<>();
        for (Player player : Player.values()) {
            this.pieces.put(player, new HashMap<>());
        }
    }

    @Override
    public void addKing(final Position position) {
        this.pieces.get(Player.DEFENDER).put(position, new King());
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
    public Map<Player, Map<Position, Piece>> build() {
        return this.pieces;
    }

}
