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
        

        assertEquals(hitb, n.createQueenBehaviour().generateHitbox());
        assertEquals(movs, n.createQueenBehaviour().generateMoveSet());

        assertNotEquals(n.createQueenBehaviour().generateHitbox(), null);
        assertNotEquals(n.createQueenBehaviour().generateMoveSet(), null);

        assertNotEquals(n.createQueenBehaviour().generateHitbox(), new HashSet<>());
        assertNotEquals(n.createQueenBehaviour().generateMoveSet(), new HashSet<>());

        toTest.generate();
        assertEquals("QUEEN", toTest.getTypeOfPiece());
        assertEquals(1, toTest.getTotalNumbOfLives());

    }

    @Test
    void testCreateKingBehaviour() {
        final var toTest = n.createKingBehaviour(); 
        final var hitb = h.createBasicHitboxDistance(0);
        final var movs = m.createBasicMoveSet();

        assertEquals(hitb, toTest.getHitbox());
        assertEquals(movs, toTest.getMoveSet());

        assertNotEquals(toTest.generateHitbox(), null);
        assertNotEquals(toTest.generateMoveSet(), null);

        assertNotEquals(toTest.generateHitbox(), new HashSet<>());
        assertNotEquals(toTest.generateMoveSet(), new HashSet<>());
        

        assertEquals(hitb, n.createKingBehaviour().generateHitbox());
        assertEquals(movs, n.createKingBehaviour().generateMoveSet());

        assertNotEquals(n.createKingBehaviour().generateHitbox(), null);
        assertNotEquals(n.createKingBehaviour().generateMoveSet(), null);

        assertNotEquals(n.createKingBehaviour().generateHitbox(), new HashSet<>());
        assertNotEquals(n.createKingBehaviour().generateMoveSet(), new HashSet<>());

        toTest.generate();
        assertEquals("KING", toTest.getTypeOfPiece());
        assertEquals(1, toTest.getTotalNumbOfLives());

    }

    @Test
    void testCreateSwapperBehaviour() {
        final Set<Position> k = new HashSet<>();
        k.add(new Position(2, 1));
        k.add(new Position(0, 2));
        final var toTest = n.createSwapperBehaviour(); 
        final var hitb = h.createBasicHitbox();
        final var movs = m.createSwapperMoveSet(new HashSet<>(k));

        assertEquals(hitb, toTest.getHitbox());
        assertEquals(movs, toTest.getMoveSet());

        assertNotEquals(toTest.generateHitbox(), null);
        assertNotEquals(toTest.generateMoveSet(), null);

        assertNotEquals(toTest.generateHitbox(), new HashSet<>());
        assertNotEquals(toTest.generateMoveSet(), new HashSet<>());
        

        assertEquals(hitb, n.createSwapperBehaviour().generateHitbox());
        assertEquals(movs, n.createSwapperBehaviour().generateMoveSet());

        assertNotEquals(n.createSwapperBehaviour().generateHitbox(), null);
        assertNotEquals(n.createSwapperBehaviour().generateMoveSet(), null);

        assertNotEquals(n.createSwapperBehaviour().generateHitbox(), new HashSet<>());
        assertNotEquals(n.createSwapperBehaviour().generateMoveSet(), new HashSet<>());

        toTest.generate();
        assertEquals("SWAPPER", toTest.getTypeOfPiece());
        assertEquals(1, toTest.getTotalNumbOfLives());
    }

    @Test
    void testCreateShieldBehaviour() {
        final var toTest = n.createShieldBehaviour(); 
        final var hitb = h.createBasicHitbox();
        final var movs = m.createBasicMoveSet();

        assertEquals(hitb, toTest.getHitbox());
        assertEquals(movs, toTest.getMoveSet());

        assertNotEquals(toTest.generateHitbox(), null);
        assertNotEquals(toTest.generateMoveSet(), null);

        assertNotEquals(toTest.generateHitbox(), new HashSet<>());
        assertNotEquals(toTest.generateMoveSet(), new HashSet<>());
        

        assertEquals(hitb, n.createShieldBehaviour().generateHitbox());
        assertEquals(movs, n.createShieldBehaviour().generateMoveSet());

        assertNotEquals(n.createShieldBehaviour().generateHitbox(), null);
        assertNotEquals(n.createShieldBehaviour().generateMoveSet(), null);

        assertNotEquals(n.createShieldBehaviour().generateHitbox(), new HashSet<>());
        assertNotEquals(n.createShieldBehaviour().generateMoveSet(), new HashSet<>());

        toTest.generate();
        assertEquals("SHIELD", toTest.getTypeOfPiece());
        assertEquals(2, toTest.getTotalNumbOfLives());

    }

    @Test
    void testCreateArcherBehaviour() {
        final var toTest = n.createArcherBehaviour(); 
        final var hitb = h.createArcherHitbox(3);
        final var movs = m.createBasicMoveSet();

        assertEquals(hitb, toTest.getHitbox());
        assertEquals(movs, toTest.getMoveSet());

        assertNotEquals(toTest.generateHitbox(), null);
        assertNotEquals(toTest.generateMoveSet(), null);

        assertNotEquals(toTest.generateHitbox(), new HashSet<>());
        assertNotEquals(toTest.generateMoveSet(), new HashSet<>());
        

        assertEquals(hitb, n.createArcherBehaviour().generateHitbox());
        assertEquals(movs, n.createArcherBehaviour().generateMoveSet());

        assertNotEquals(n.createArcherBehaviour().generateHitbox(), null);
        assertNotEquals(n.createArcherBehaviour().generateMoveSet(), null);

        assertNotEquals(n.createArcherBehaviour().generateHitbox(), new HashSet<>());
        assertNotEquals(n.createArcherBehaviour().generateMoveSet(), new HashSet<>());

        toTest.generate();
        assertEquals("ARCHER", toTest.getTypeOfPiece());
        assertEquals(1, toTest.getTotalNumbOfLives());

    }

    
}
