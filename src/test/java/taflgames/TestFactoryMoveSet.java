package taflgames;
//CHECKSTYLE: MagicNumber OFF
/*Magic numbers checks disabled in order to allow quicker writing of the tests; the
 * numbers used in the creation of Positions and Vectors are not intended to be 
 * constants, but only results to verify computations by need.
 */
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import taflgames.common.api.FactoryMoveSet;
import taflgames.common.api.Vector;
import taflgames.common.code.ImplFactoryMoveset;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
/**
 * testing factoryMoveSet.
 */
class TestFactoryMoveSet {
    @Test
    void testCreateBasicMoveSet() {
        final FactoryMoveSet f = new ImplFactoryMoveset();
        final Set<Vector> s = new HashSet<>();
        assertNotEquals(f.createBasicMoveSet(), s);
        assertNotEquals(f.createBasicMoveSet(), null);
        final Set<Vector> s2 = new HashSet<>(); 
        s2.add(new VectorImpl(1, 0, true));
        s2.add(new VectorImpl(0, -1, true));
        s2.add(new VectorImpl(-1, 0, true));
        s2.add(new VectorImpl(0, 1, true));
        assertEquals(f.createBasicMoveSet(), s2);
    }
    @Test
    void testCreateSwapper() {
        final FactoryMoveSet f = new ImplFactoryMoveset();
        final Set<Position> enemyPositions = new HashSet<>();
        enemyPositions.add(new Position(5, 0));
        enemyPositions.add(new Position(0, 0));
        enemyPositions.add(new Position(-31, 0));
        enemyPositions.add(new Position(0, -4));
        final Set<Vector> s = new HashSet<>(enemyPositions.stream()
                                    .map(p -> new VectorImpl(new Position(0, 0), p, false))
                                    .toList());
        s.addAll(f.createBasicMoveSet());
        assertEquals(f.createSwapperMoveSet(enemyPositions), s);
        assertNotEquals(f.createSwapperMoveSet(enemyPositions), new HashSet<>());
        assertNotEquals(f.createSwapperMoveSet(enemyPositions), null);
    }
}
