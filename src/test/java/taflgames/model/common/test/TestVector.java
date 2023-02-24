package taflgames.model.common.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import taflgames.model.common.api.Vector;
import taflgames.model.common.code.Position;
import taflgames.model.common.code.VectorImpl;

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

    @Test
    public void testApplyToPosition() {
        Position p = new Position(0, 0);
        Vector v = new VectorImpl(p, new Position(1, 2));
        Vector w = new VectorImpl(new Position(1, 2), new Position(3, 6));
        Position p1 = v.applyToPosition(p);
        assertTrue(p1.equals(new Position(1, 2)));
        assertTrue(w.applyToPosition(p).equals(new Position(2, 4)));
        /*p has not changed */
        assertTrue(p.equals(new Position(0, 0)));
        /*Two vectors applied to the same point should return the same end position,
         * no matter the order in which they are applied to the point.
         */
        assertTrue(w.applyToPosition(v.applyToPosition(p)).equals(new Position(3, 6)));
        assertTrue(v.applyToPosition(w.applyToPosition(p)).equals(new Position(3, 6)));
        /*A null vector should not return different coordinates */
        assertTrue(new VectorImpl(p, p).applyToPosition(p).equals(p));
        Vector w1 = w.multiplyByScalar(-1);
        assertTrue(p1.equals(w1.applyToPosition(w.applyToPosition(p1))));
    }

    @Test
    public void testRotation() {
        /*Rotations occur clockwise if the angle is negative, anti-clockwise otherwise. */
        Vector v = new VectorImpl(1, 1);
        Vector v2 = new VectorImpl(new Position(3, 4), new Position(4, -2));
        /*Rotations can only occur if an angle is a multiple of 45 degrees
         * on a map represented by fixed cells.
         */
        assertTrue(v.rotate(32).isEmpty());
        assertTrue(new VectorImpl(1, -1).equals(v.rotate(-90).get()));
        assertTrue(v.rotate(0).get().equals(new VectorImpl(1, 1)));
        Vector w = new VectorImpl(2,3);
        /*Can't rotate an odd vector if not by multiples of
         * 90 degrees.
         */
        assertTrue(w.rotate(45).isEmpty());
        assertTrue(w.rotate(180).get().equals(new VectorImpl(-2, -3)));
        /*The vectors resulted from rotation are applied to the origin (0, 0) */
        assertTrue(v2.rotate(90).get().equals(new VectorImpl(new Position(0, 0), new Position(6, 1))));
        /*By applying a 180°-rotated vector to its end position
         * we should get back to its start position.
         */
        Position e = v2.getEndPos();
        Vector opposite = v2.rotate(180).get();
        assertTrue(opposite.applyToPosition(e).equals(v2.getStartPos()));
    }
}
