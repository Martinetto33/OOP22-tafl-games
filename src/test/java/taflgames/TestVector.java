package taflgames;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;

//CHECKSTYLE: MagicNumber OFF
/*Magic numbers checks disabled in order to allow quicker writing of the tests; the
 * numbers used in the creation of Positions and Vectors are not intended to be 
 * constants, but only results to verify computations by need.
 */
@SuppressWarnings("PMD.ReplaceVectorWithList") /*suppressed as the design requires
some specific methods and Lists wouldn't come in handy in this case */
class TestVector {
    /**
     * Tests multiplication by a scalar.
     */
    @Test
    void testMultiplyByScalar() {
        final Vector t = new VectorImpl(-1, 1);
        final Vector v = new VectorImpl(new Position(2, 2), new Position(0, 0));
        final Vector w = new VectorImpl(new Position(t.getEndPos()), v.getStartPos());
        assertEquals(t.multiplyByScalar(2), new VectorImpl(-2, 2));
        /* -1 * (-2, -2) = (2, 2); therefore the vector starting from (2,2) and pointing to (0,0) [deltaX = -2, deltaY = -2] 
         * should now point to (4, 4) after multiplying it by -1.
        */
        assertEquals(v.multiplyByScalar(-1), new VectorImpl(new Position(2, 2), new Position(4, 4)));
        /*v should not be modified by this operation */
        assertEquals(v, new VectorImpl(new Position(2, 2), new Position(0, 0)));
        final var w1 = w.multiplyByScalar(100);
        assertEquals(w.multiplyByScalar(100), new VectorImpl(w.getStartPos(), new Position(299, 101)));
        assertEquals(w1.deltaX(), 300);
        assertEquals(w1.deltaY(), 100);
        assertEquals(w1.getStartPos(), w.getStartPos());
        assertNotEquals(w1.getEndPos(), w.getEndPos());
        /*A vector multiplied by itself equals itself. */
        assertEquals(w, w.multiplyByScalar(1));
        /*If multiplied by 0, the vector deltas become 0, therefore starting position and ending position coincide */
        assertEquals(w.multiplyByScalar(0), new VectorImpl(new Position(-1, 1), new Position(-1, 1)));
    }

    /**
     * Tests the application of a Vector to a certain {@link taflgames.common.code.Position}.
     */
    @Test
    void testApplyToPosition() {
        final Position p = new Position(0, 0);
        final Vector v = new VectorImpl(p, new Position(1, 2));
        final Vector w = new VectorImpl(new Position(1, 2), new Position(3, 6));
        final Position p1 = v.applyToPosition(p);
        assertEquals(p1, new Position(1, 2));
        assertEquals(w.applyToPosition(p), new Position(2, 4));
        /*p has not changed */
        assertEquals(p, new Position(0, 0));
        /*Two vectors applied to the same point should return the same end position,
         * no matter the order in which they are applied to the point.
         */
        assertEquals(w.applyToPosition(v.applyToPosition(p)), new Position(3, 6));
        assertEquals(v.applyToPosition(w.applyToPosition(p)), new Position(3, 6));
        /*A null vector should not return different coordinates */
        assertEquals(new VectorImpl(p, p).applyToPosition(p), p);
        final Vector w1 = w.multiplyByScalar(-1);
        assertEquals(p1, w1.applyToPosition(w.applyToPosition(p1)));
    }

    /**
     * Tests the rotation of a Vector by an integer angle.
     */
    @Test
    void testRotation() {
        /*Rotations occur clockwise if the angle is negative, anti-clockwise otherwise. */
        final Vector v = new VectorImpl(1, 1);
        final Vector v2 = new VectorImpl(new Position(3, 4), new Position(4, -2));
        /*Rotations can only occur if an angle is a multiple of 45 degrees
         * on a map represented by fixed cells.
         */
        assertTrue(v.rotate(32).isEmpty());
        assertEquals(new VectorImpl(1, -1), v.rotate(-90).get());
        assertEquals(v.rotate(0).get(), new VectorImpl(1, 1));
        final Vector w = new VectorImpl(2, 3);
        /*Can't rotate an odd vector if not by multiples of
         * 90 degrees.
         */
        assertTrue(w.rotate(45).isEmpty());
        assertEquals(w.rotate(180).get(), new VectorImpl(-2, -3));
        /*The vectors resulted from rotation are applied to the origin (0, 0) */
        assertEquals(v2.rotate(90).get(), new VectorImpl(new Position(0, 0), new Position(6, 1)));
        /*By applying a 180 degrees-rotated vector to its end position
         * we should get back to its start position.
         */
        final Position e = v2.getEndPos();
        final Vector opposite = v2.rotate(180).get();
        assertEquals(opposite.applyToPosition(e), v2.getStartPos());
    }

    /**
     * Tests the creation of unit vectors.
     */
    @Test
    void testUnitVector() {
        final Vector v = new VectorImpl(1, 1);
        assertFalse(v.isUnitVector());
        final Vector v1 = new VectorImpl(1, 0, true);
        assertTrue(v1.isUnitVector());
    }

    /**
     * Tests the addition of Vectors.
     */
    @Test
    void testAdd() {
        final Vector v1 = new VectorImpl(new Position(3, 5), new Position(2, 1));
        final Vector v2 = new VectorImpl(new Position(2, 1), new Position(3, 5));
        assertEquals(new VectorImpl(0, 0), v1.addVector(v2));
        /* The addition of the vectors occurs by summing the deltas; the result
         * is always a Vector starting from (0, 0) and ending to (sum of deltaX, sum of deltaY)
         * The deltaX of v1 is 2 - 3 = -1
         * The deltaY of v1 is 1 - 5 = -4
         * So to get the Vector (0, 0) -> (5, 5) a Vector with delta (+6, + 9)
         * should be added.
         */
        assertEquals(new VectorImpl(new Position(0, 0), new Position(5, 5)),
        v1.addVector(new VectorImpl(6, 9)));
    }
}
