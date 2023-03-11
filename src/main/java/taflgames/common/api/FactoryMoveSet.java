package taflgames.common.api;

import java.util.Set;

import taflgames.common.code.Position;

public interface FactoryMoveSet {
    /**
     * creates the basic moveset used by the BasicPiece and other pieces,
     * which is rappresented by four directions: (1,0)(0,1)(-1,0)(0,-1)
     * 
     * @return basic move-set
     */
    Set<Vector> createBasicMoveSet();
    /**
     * creates the move-set for the Swapper starting from the
     * enemy pieces' positions
     * @param enemyPositions
     * @return
     */
    Set<Vector> createSwapperMoveSet(Set<Position> enemyPositions);
}
