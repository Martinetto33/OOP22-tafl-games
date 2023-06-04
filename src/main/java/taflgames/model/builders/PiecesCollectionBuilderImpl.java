package taflgames.model.builders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

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
        addPieces(positions, Queen::new);
    }

    @Override
    public void addArchers(final Map<Player, Set<Position>> positions) {
        addPieces(positions, Archer::new);
    }

    @Override
    public void addShields(final Map<Player, Set<Position>> positions) {
        addPieces(positions, Shield::new);
    }

    @Override
    public void addSwappers(final Map<Player, Set<Position>> positions) {
        addPieces(positions, Swapper::new);
    }

    @Override
    public void addBasicPieces(final Map<Player, Set<Position>> positions) {
        addPieces(positions, BasicPiece::new);
    }

    @Override
    public Map<Player, Map<Position, Piece>> build() {
        return new HashMap<>(this.pieces);
    }

    private void addPieces(
        final Map<Player, Set<Position>> positions,
        final BiFunction<Position, Player, ? extends Piece> creator
    ) {
        positions.forEach((player, playerPosSet) -> {
            playerPosSet.forEach(pos -> this.pieces.get(player).put(pos, creator.apply(pos, player)));
        });
    }

}
