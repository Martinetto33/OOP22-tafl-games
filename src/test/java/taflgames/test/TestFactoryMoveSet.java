package taflgames.test;

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

public class TestFactoryMoveSet {

    @Test
    void testCreateBasicMoveSet(){
        FactoryMoveSet f= new ImplFactoryMoveset();
        Set<Vector> s = new HashSet<>();
        assertNotEquals(f.createBasicMoveSet(),s);

        assertNotEquals(f.createBasicMoveSet(), null);

        Set<Vector> s2 = new HashSet<>(); 
        s2.add(new VectorImpl(1, 0,true));
        s2.add(new VectorImpl(0,-1,true));
        s2.add(new VectorImpl(-1, 0,true));
        s2.add(new VectorImpl(0, 1,true));
        assertEquals(f.createBasicMoveSet(),s2);

    }

    @Test
    void testCreateSwapper() {
        FactoryMoveSet f= new ImplFactoryMoveset();
        Set<Position> enemyPositions= new HashSet<>();
        enemyPositions.add(new Position(5, 0));
        enemyPositions.add(new Position(0, 0));
        enemyPositions.add(new Position(-31, 0));
        enemyPositions.add(new Position(0, -4));

        Set<Vector> s = new HashSet<>(enemyPositions.stream()
                                    .map(p -> new VectorImpl(new Position(0, 0), p, false))
                                    .toList());
        s.addAll(f.createBasicMoveSet());

        assertEquals(f.createSwapperMoveSet(enemyPositions),s);

        assertNotEquals(f.createSwapperMoveSet(enemyPositions),new HashSet<>());
        assertNotEquals(f.createSwapperMoveSet(enemyPositions), null);


    }
    
}
