package taflgames.common.api;

import java.util.Set;

import taflgames.common.code.Position;

/**
 * this factory creates different types of move-sets
 * that are used by the pieces on the board.
 */
public interface FactoryMoveSet {
    /**
     * creates the basic moveset used by the BasicPiece and other pieces,
     * which is rappresented by four directions: (1,0)(0,1)(-1,0)(0,-1).
     * 
     * @return basic move-set
     */
    Set<Vector> createBasicMoveSet();
    /**
     * IT'S NOT USED IN THE PROGRAM BUT IT COULD BE
     * USED TO IMPROOVE EXTENSIBILITY OF THE PROGRAM WITH A MEDIATOR FOR SWAPPER...
     * creates the move-set for the Swapper starting from the
     * enemy pieces' positions.
     * @param enemyPositions
     * @return swapper move-set with enemy positions
     */
    Set<Vector> createSwapperMoveSet(Set<Position> enemyPositions);
}
