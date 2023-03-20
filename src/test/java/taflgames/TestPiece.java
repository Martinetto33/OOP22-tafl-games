package taflgames;
import static org.junit.jupiter.api.Assertions.assertTrue;
//CHECKSTYLE: MagicNumber OFF
/*Magic numbers checks disabled in order to allow quicker writing of the tests; the
 * numbers used in the creation of Positions and Vectors are not intended to be 
 * constants, but only results to verify computations by need.
 */
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.model.pieces.api.Piece;
import taflgames.model.pieces.code.Archer;
import taflgames.model.pieces.code.BasicPiece;
import taflgames.model.pieces.code.King;
import taflgames.model.pieces.code.Queen;
import taflgames.model.pieces.code.Shield;
import taflgames.model.pieces.code.Swapper;
/**
 * testing piece.
 */
public class TestPiece {
    /*private final FactoryHitbox facHit = new ImplFactoryHitbox();
    private final FactoryMoveSet facMov = new ImplFactoryMoveset();
    private final FactoryBehaviourTypeOfPiece facBe = 
                            new ImplFactoryBehaviourTypeOfPiece();*/
    @Test
    void testWhereToHitArcher() {
        final var p = new Position(4, 6);
        final var t = Player.ATTACKER;
        final Piece np = new Archer(p, t);
        Set<Position> hit = new HashSet<>();
        hit.add(new Position(5, 6));
        hit.add(new Position(4, 7));
        hit.add(new Position(3, 6));
        hit.add(new Position(4, 5));
        hit.add(new Position(6, 6));
        hit.add(new Position(4, 8));
        hit.add(new Position(2, 6));
        hit.add(new Position(4, 4));
        hit.add(new Position(7, 6));
        hit.add(new Position(4, 9));
        hit.add(new Position(1, 6));
        hit.add(new Position(4, 3));
        assertNotEquals(np.whereToHit(), null);
        assertNotEquals(np.whereToHit(), new HashSet<>());
        assertEquals(hit, np.whereToHit());
    }
    @Test
    void testWhereToHitBasic() {
        final var p = new Position(4, 6);
        final var t = Player.ATTACKER;
        final Piece np = new BasicPiece(p, t);
        Set<Position> hit = new HashSet<>();
        hit.add(new Position(5, 6));
        hit.add(new Position(4, 7));
        hit.add(new Position(3, 6));
        hit.add(new Position(4, 5));
        assertNotEquals(np.whereToHit(), null);
        assertNotEquals(np.whereToHit(), new HashSet<>());
        assertEquals(hit, np.whereToHit());
    }
    @Test
    void testWhereToMoveBasic() {
        final var p = new Position(4, 6);
        final var t = Player.ATTACKER;
        final Piece np = new BasicPiece(p, t);
        Set<Vector> mov = new HashSet<>();
        mov.add(new VectorImpl(p, new Position(5, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 7), true));
        mov.add(new VectorImpl(p, new Position(3, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 5), true));
        assertNotEquals(np.whereToMove(), null);
        assertNotEquals(np.whereToMove(), new HashSet<>());
        assertEquals(mov, np.whereToMove());
    }
    @Test 
    void testBasicPiece() {
        final var p = new Position(4, 6);
        final var p2 = new Position(p.getX() + 7, p.getY() - 4);
        final var t = Player.ATTACKER;
        final Piece np = new BasicPiece(p, t);
        final int numbLives = 1;
        assertEquals(p, np.getCurrentPosition());
        assertEquals(t, np.getPlayer());
        assertEquals(numbLives, np.getCurrNumbOfLives());
        Set<Position> hit = new HashSet<>();
        hit.add(new Position(5, 6));
        hit.add(new Position(4, 7));
        hit.add(new Position(3, 6));
        hit.add(new Position(4,  5));
        assertNotEquals(np.whereToHit(), null);
        assertNotEquals(np.whereToHit(), new HashSet<>());
        assertEquals(hit, np.whereToHit());
        Set<Vector> mov = new HashSet<>();
        mov.add(new VectorImpl(p, new Position(5, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 7), true));
        mov.add(new VectorImpl(p, new Position(3, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 5), true));
        assertNotEquals(np.whereToMove(), null);
        assertNotEquals(np.whereToMove(), new HashSet<>());
        assertEquals(mov, np.whereToMove());
        assertFalse(() -> np.canSwap());
        np.decrementCurrNumbOfLives();
        assertEquals(numbLives - 1, np.getCurrNumbOfLives());
        np.decrementCurrNumbOfLives();
        assertTrue(() -> !np.isAlive());
        assertFalse(() -> np.getCurrNumbOfLives() < 0);
        np.setCurrNumbOfLivesLimited(numbLives + 5);
        assertEquals(np.getCurrNumbOfLives(), np.getMyType().getTotalNumbOfLives());
        np.setCurrentPosition(p2);
        assertEquals(p2, np.getCurrentPosition());
    }
    @Test
    void testQueen() {
        final var p = new Position(4, 6);
        final var p2 = new Position(p.getX() + 7, p.getY() - 4);
        final var t = Player.ATTACKER;
        final Piece np = new Queen(p, t);
        final int numbLives = 1;
        assertEquals(p, np.getCurrentPosition());
        assertEquals(t, np.getPlayer());
        assertEquals(numbLives, np.getCurrNumbOfLives());
        Set<Position> hit = new HashSet<>();
        hit.add(new Position(5, 6));
        hit.add(new Position(4, 7));
        hit.add(new Position(3, 6));
        hit.add(new Position(4, 5));
        assertNotEquals(np.whereToHit(), null);
        assertNotEquals(np.whereToHit(), new HashSet<>());
        assertEquals(hit, np.whereToHit());
        Set<Vector> mov = new HashSet<>();
        mov.add(new VectorImpl(p, new Position(5, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 7), true));
        mov.add(new VectorImpl(p, new Position(3, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 5), true));
        assertNotEquals(np.whereToMove(), null);
        assertNotEquals(np.whereToMove(), new HashSet<>());
        assertEquals(mov, np.whereToMove());
        assertFalse(() -> np.canSwap());
        np.decrementCurrNumbOfLives();
        assertEquals(numbLives - 1, np.getCurrNumbOfLives());
        np.decrementCurrNumbOfLives();
        assertTrue(() -> !np.isAlive());
        assertFalse(() -> np.getCurrNumbOfLives() < 0);
        np.setCurrNumbOfLivesLimited(numbLives + 5);
        assertEquals(np.getCurrNumbOfLives(), np.getMyType().getTotalNumbOfLives());
        np.setCurrentPosition(p2);
        assertEquals(p2, np.getCurrentPosition());
    }
    @Test 
    void testShield() {
        final var p = new Position(4, 6);
        final var p2 = new Position(p.getX() + 7, p.getY() - 4);
        final var t = Player.ATTACKER;
        final Piece np = new Shield(p, t);
        final int numbLives = 2;
        assertEquals(p, np.getCurrentPosition());
        assertEquals(t, np.getPlayer());
        assertEquals(numbLives, np.getCurrNumbOfLives());
        Set<Position> hit = new HashSet<>();
        hit.add(new Position(5, 6));
        hit.add(new Position(4, 7));
        hit.add(new Position(3, 6));
        hit.add(new Position(4, 5));
        assertNotEquals(np.whereToHit(), null);
        assertNotEquals(np.whereToHit(), new HashSet<>());
        assertEquals(hit, np.whereToHit());
        Set<Vector> mov = new HashSet<>();
        mov.add(new VectorImpl(p, new Position(5, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 7), true));
        mov.add(new VectorImpl(p, new Position(3, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 5), true));
        assertNotEquals(np.whereToMove(), null);
        assertNotEquals(np.whereToMove(), new HashSet<>());
        assertEquals(mov, np.whereToMove());
        assertFalse(() -> np.canSwap());
        np.decrementCurrNumbOfLives();
        assertEquals(numbLives - 1, np.getCurrNumbOfLives());
        assertTrue(() -> np.isAlive());
        np.decrementCurrNumbOfLives();
        assertTrue(() -> !np.isAlive());
        np.setCurrNumbOfLivesLimited(numbLives + 5);
        assertEquals(np.getCurrNumbOfLives(), np.getMyType().getTotalNumbOfLives());
        np.setCurrentPosition(p2);
        assertEquals(p2, np.getCurrentPosition());
    }
    @Test
    void testArcher() {
        final var p = new Position(4, 6);
        final var p2 = new Position(p.getX() + 7, p.getY() - 4);
        final var t = Player.ATTACKER;
        final Piece np = new Archer(p, t);
        final int numbLives = 1;
        assertEquals(p, np.getCurrentPosition());
        assertEquals(t, np.getPlayer());
        assertEquals(numbLives, np.getCurrNumbOfLives());
        Set<Position> hit = new HashSet<>();
        hit.add(new Position(5, 6));
        hit.add(new Position(4, 7));
        hit.add(new Position(3, 6));
        hit.add(new Position(4, 5));
        hit.add(new Position(6, 6));
        hit.add(new Position(4, 8));
        hit.add(new Position(2, 6));
        hit.add(new Position(4, 4));
        hit.add(new Position(7, 6));
        hit.add(new Position(4, 9));
        hit.add(new Position(1, 6));
        hit.add(new Position(4, 3));
        assertNotEquals(np.whereToHit(), null);
        assertNotEquals(np.whereToHit(), new HashSet<>());
        assertEquals(hit, np.whereToHit());
        Set<Vector> mov = new HashSet<>();
        mov.add(new VectorImpl(p, new Position(5, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 7), true));
        mov.add(new VectorImpl(p, new Position(3, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 5), true));
        assertNotEquals(np.whereToMove(), null);
        assertNotEquals(np.whereToMove(), new HashSet<>());
        assertEquals(mov, np.whereToMove());
        assertFalse(() -> np.canSwap());
        np.decrementCurrNumbOfLives();
        assertEquals(numbLives - 1, np.getCurrNumbOfLives());
        assertTrue(() -> !np.isAlive());
        np.decrementCurrNumbOfLives();
        np.decrementCurrNumbOfLives();
        assertTrue(() -> np.getCurrNumbOfLives() == 0);
        np.setCurrNumbOfLivesLimited(numbLives + 5);
        assertEquals(np.getCurrNumbOfLives(), np.getMyType().getTotalNumbOfLives());
        np.setCurrentPosition(p2);
        assertEquals(p2, np.getCurrentPosition());
    }
    @Test
    void testKing() {
        final var p = new Position(4, 6);
        final var p2 = new Position(p.getX() + 7, p.getY() - 4);
        final var t = Player.DEFENDER; 
        final Piece np = new King(p);
        final int numbLives = 1;
        assertEquals(p, np.getCurrentPosition());
        assertEquals(t, np.getPlayer());
        assertEquals(numbLives, np.getCurrNumbOfLives());
        Set<Position> hit = new HashSet<>();
        hit.add(p);
        assertEquals(hit, np.whereToHit());
        Set<Vector> mov = new HashSet<>();
        mov.add(new VectorImpl(p, new Position(5, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 7), true));
        mov.add(new VectorImpl(p, new Position(3, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 5), true));
        assertNotEquals(np.whereToMove(), null);
        assertNotEquals(np.whereToMove(), new HashSet<>());
        assertEquals(mov, np.whereToMove());
        assertFalse(() -> np.canSwap());
        np.decrementCurrNumbOfLives();
        assertEquals(numbLives - 1, np.getCurrNumbOfLives());
        assertTrue(() -> !np.isAlive());
        np.decrementCurrNumbOfLives();
        np.decrementCurrNumbOfLives();
        assertTrue(() -> np.getCurrNumbOfLives() == 0);
        np.setCurrNumbOfLivesLimited(numbLives + 5);
        assertEquals(np.getCurrNumbOfLives(), np.getMyType().getTotalNumbOfLives());
        np.setCurrentPosition(p2);
        assertEquals(p2, np.getCurrentPosition());
    }
    @Test
    void testSwapper() {
        final var p = new Position(4, 6);
        final var p2 = new Position(p.getX() + 7, p.getY() - 4);
        final var t = Player.DEFENDER;
        final Piece np = new Swapper(p, t);
        final int numbLives = 1;
        assertEquals(p, np.getCurrentPosition());
        assertEquals(t, np.getPlayer());
        assertEquals(numbLives, np.getCurrNumbOfLives());
        assertTrue(() -> np.canSwap());
        Set<Position> hit = new HashSet<>();
        hit.add(new Position(5, 6));
        hit.add(new Position(4, 7));
        hit.add(new Position(3, 6));
        hit.add(new Position(4, 5));
        assertNotEquals(np.whereToHit(), null);
        assertNotEquals(np.whereToHit(), new HashSet<>());
        assertEquals(hit, np.whereToHit());
        Set<Vector> mov = new HashSet<>();
        mov.add(new VectorImpl(p, new Position(5, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 7), true));
        mov.add(new VectorImpl(p, new Position(3, 6), true));
        mov.add(new VectorImpl(p, new Position(4, 5), true));
        mov.add(new VectorImpl(p, new Position(0, 2), false));
        mov.add(new VectorImpl(p, new Position(2, 1), false));
        assertNotEquals(np.whereToMove(), null);
        assertNotEquals(np.whereToMove(), new HashSet<>());
        assertEquals(mov, np.whereToMove());
        np.decrementCurrNumbOfLives();
        assertEquals(numbLives - 1, np.getCurrNumbOfLives());
        assertTrue(() -> !np.isAlive());
        np.decrementCurrNumbOfLives();
        np.decrementCurrNumbOfLives();
        assertTrue(() -> np.getCurrNumbOfLives() == 0);
        np.setCurrNumbOfLivesLimited(numbLives + 5);
        assertEquals(np.getCurrNumbOfLives(), np.getMyType().getTotalNumbOfLives());
        np.setCurrentPosition(p2);
        assertEquals(p2, np.getCurrentPosition());
    }
}
