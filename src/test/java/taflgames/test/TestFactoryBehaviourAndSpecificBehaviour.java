package taflgames.test;

/**
 * per finirla defo controllare i test del costruttore 
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import taflgames.common.api.FactoryHitbox;
import taflgames.common.api.FactoryMoveSet;
import taflgames.common.code.ImplFactoryHitbox;
import taflgames.common.code.ImplFactoryMoveset;
import taflgames.common.code.Position;
import taflgames.model.pieces.api.FactoryBehaviourTypeOfPiece;
import taflgames.model.pieces.code.ImplFactoryBehaviourTypeOfPiece;

public class TestFactoryBehaviourAndSpecificBehaviour {

    FactoryBehaviourTypeOfPiece n = 
                new ImplFactoryBehaviourTypeOfPiece();
    FactoryHitbox h = new ImplFactoryHitbox();
    FactoryMoveSet m = new ImplFactoryMoveset();
    


    @Test
    void testBasicWasHit() {
        ImplFactoryBehaviourTypeOfPiece special = 
                        new ImplFactoryBehaviourTypeOfPiece();

        Position lastMoved = new Position(5, 6);
        /**
         * da finire quando controllo 
         * il corretto funzionamento
         *  di creazione di abstract piece
         */

    }

    @Test
    void testCreateBasicPiecBehaviour() {

        final var toTest = n.createBasicPieceBehaviour(); 
        final var hitb = h.createBasicHitbox();
        final var movs = m.createBasicMoveSet();

        assertEquals(hitb, toTest.getHitbox());
        assertEquals(movs, toTest.getMoveSet());

        assertNotEquals(toTest.generateHitbox(), null);
        assertNotEquals(toTest.generateMoveSet(), null);

        assertNotEquals(toTest.generateHitbox(), new HashSet<>());
        assertNotEquals(toTest.generateMoveSet(), new HashSet<>());
        

        assertEquals(hitb, n.createBasicPieceBehaviour().generateHitbox());
        assertEquals(movs, n.createBasicPieceBehaviour().generateMoveSet());

        assertNotEquals(n.createBasicPieceBehaviour().generateHitbox(), null);
        assertNotEquals(n.createBasicPieceBehaviour().generateMoveSet(), null);

        assertNotEquals(n.createBasicPieceBehaviour().generateHitbox(), new HashSet<>());
        assertNotEquals(n.createBasicPieceBehaviour().generateMoveSet(), new HashSet<>());

        toTest.generate();
        assertEquals("BASIC_PIECE", toTest.getTypeOfPiece());
        assertEquals(1, toTest.getTotalNumbOfLives());

    }

    @Test
    void testCreateQueenBehaviour() {

        final var toTest = n.createQueenBehaviour(); 
        final var hitb = h.createBasicHitbox();
        final var movs = m.createBasicMoveSet();

        assertEquals(hitb, toTest.getHitbox());
        assertEquals(movs, toTest.getMoveSet());

        assertNotEquals(toTest.generateHitbox(), null);
        assertNotEquals(toTest.generateMoveSet(), null);

        assertNotEquals(toTest.generateHitbox(), new HashSet<>());
        assertNotEquals(toTest.generateMoveSet(), new HashSet<>());
        

        assertEquals(hitb, n.createBasicPieceBehaviour().generateHitbox());
        assertEquals(movs, n.createBasicPieceBehaviour().generateMoveSet());

        assertNotEquals(n.createBasicPieceBehaviour().generateHitbox(), null);
        assertNotEquals(n.createBasicPieceBehaviour().generateMoveSet(), null);

        assertNotEquals(n.createBasicPieceBehaviour().generateHitbox(), new HashSet<>());
        assertNotEquals(n.createBasicPieceBehaviour().generateMoveSet(), new HashSet<>());

        toTest.generate();
        assertEquals("QUEEN", toTest.getTypeOfPiece());
        assertEquals(1, toTest.getTotalNumbOfLives());

    }
    
}
