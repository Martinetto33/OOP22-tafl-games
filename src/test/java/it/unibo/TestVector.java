package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.api.Vector;
import it.unibo.code.Position;
import it.unibo.code.VectorImpl;

public class TestVector {
    @Test
    public void testMultiplyByScalar() {
        Vector t = new VectorImpl(-1, 1);
        Vector v = new VectorImpl(new Position(2, 2), new Position(0, 0));
        Vector w = new VectorImpl(new Position(t.getEndPos()), v.getStartPos());
        assertTrue(t.multiplyByScalar(2).equals(new VectorImpl(-2, 2)));
        /* -1 * (-2, -2) = (2, 2); therefore the vector starting from (2,2) and pointing to (0,0) [deltaX = -2, deltaY = -2] 
         * should now point to (4, 4) after multiplying it by -1.
        */
        assertTrue(v.multiplyByScalar(-1).equals(new VectorImpl(new Position(2, 2), new Position(4, 4))));
        /*v should not be modified by this operation */
        assertTrue(v.equals(new VectorImpl(new Position(2, 2), new Position(0, 0))));
        var w1 = w.multiplyByScalar(100);
        assertTrue(w.multiplyByScalar(100).equals(new VectorImpl(w.getStartPos(), new Position(299, 101))));
        assertTrue(w1.deltaX() == 300);
        assertTrue(w1.deltaY() == 100);
        assertTrue(w1.getStartPos().equals(w.getStartPos()));
        assertFalse(w1.getEndPos().equals(w.getEndPos()));
        /*A vector multiplied by itself equals itself. */
        assertTrue(w.equals(w.multiplyByScalar(1)));
        /*If multiplied by 0, the vector deltas become 0, therefore starting position and ending position coincide */
        assertTrue(w.multiplyByScalar(0).equals(new VectorImpl(new Position(-1, 1), new Position(-1, 1))));
    }
}
