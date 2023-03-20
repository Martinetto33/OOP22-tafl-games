package taflgames.model.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.pieces.code.Archer;
import taflgames.model.pieces.code.BasicPiece;
import taflgames.model.pieces.code.King;
import taflgames.model.pieces.api.Piece;
import taflgames.model.pieces.code.Queen;
import taflgames.model.pieces.code.Shield;
import taflgames.model.pieces.code.Swapper;

/**
 * This class implements a builder to create a collection of pieces.
 */
public final class PiecesCollectionBuilderImpl implements PiecesCollectionBuilder {

    private final Map<Player, Map<Position, Piece>> pieces;

    /**
     * Creates a new builder for a collection of pieces.
     */
    public PiecesCollectionBuilderImpl() {
        this.pieces = new HashMap<>();
        for (final Player player : Player.values()) {
            this.pieces.put(player, new HashMap<>());
        }
    }

    @Override
    public void addKing(final Position position) {
        this.pieces.get(Player.DEFENDER).put(position, new King(position));
    }

    @Override
    public void addQueens(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Queen(pos, player));
            }
        });
    }

    @Override
    public void addArchers(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Archer(pos, player));
            }
        });
    }

    @Override
    public void addShields(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Shield(pos, player));
            }
        });
    }

    @Override
    public void addSwappers(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new Swapper(pos, player));
            }
        });
    }

    @Override
    public void addBasicPieces(final Map<Player, Set<Position>> positions) {
        positions.forEach((player, posSet) -> {
            for (final var pos : posSet) {
                this.pieces.get(player).put(pos, new BasicPiece(pos, player));
            }
        });
    }

    @Override
    public Map<Player, Map<Position, Piece>> build() {
        return this.pieces;
    }

}
