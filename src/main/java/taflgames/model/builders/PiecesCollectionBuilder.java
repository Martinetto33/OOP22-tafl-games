package taflgames.model.builders;

import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.pieces.Piece;

public interface PiecesCollectionBuilder {
    
    /**
     * Adds the king piece to the pieces collection being built.
     * @param positions the position where the king must be placed
     */
    void addKing(Position position);

    /**
     * Adds the queen pieces for each team to the pieces collection being built.
     * @param positions the positions where the queens must be placed
     */
    void addQueens(Map<Player, Set<Position>> positions);

    /**
     * Adds the archer pieces for each team to the pieces collection being built.
     * @param positions the positions where the archers must be placed
     */
    void addArchers(Map<Player, Set<Position>> positions);

    /**
     * Adds the shield pieces for each team to the pieces collection being built.
     * @param positions the positions where the shields must be placed
     */
    void addShields(Map<Player, Set<Position>> positions);

    /**
     * Adds the swapper pieces for each team to the pieces collection being built.
     * @param positions the positions where the swappers must be placed
     */
    void addSwappers(Map<Player, Set<Position>> positions);

    /**
     * Adds the basic pieces for each team to the pieces collection being built.
     * @param positions the positions where the basic pieces must be placed
     */
    void addBasicPieces(Map<Player, Set<Position>> positions);

    /**
     * @return the collection of pieces that has been set up
     */
    Map<Player, Map<Position, Piece>> build();
    
}
