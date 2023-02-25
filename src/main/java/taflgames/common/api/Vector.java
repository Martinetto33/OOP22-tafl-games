package taflgames.common.api;

import java.util.Optional;

import taflgames.common.code.Position;

/**
 * This interface will model a mathematic Vector, allowing basic operations
 * such as multiplying by a scalar, rotating by an angle which is multiple
 * of 45 degrees (due to this class being used in a fixed grid board game), applying
 * its deltaX and deltaY to a {@link taflgames.common.code.Position}.
 * 
 * <br>Each Vector is a pair of a starting and ending Position.
 * Most of the methods however use the deltaX and deltaY variations described
 * by the Vector, which are independent from these Positions.
 */
@SuppressWarnings("PMD.ReplaceVectorWithList") /*suppressed as the design requires
some specific methods and Lists wouldn't come in handy in this case */
public interface Vector {
    /**
     * Returns the starting position.
     * @return the Position to which the Vector is applied.
     */
    Position getStartPos();

    /**
     * Returns the ending position.
     * @return the Position this Vector points to.
     */
    Position getEndPos();

    /**
     * Allows multiplication by a scalar, which must be an int.
     * This will influence the values of deltaX and deltaY in the
     * same way it would in normal Maths. This method does NOT change
     * the internal state of this Vector.
     * @param scalar the scalar to be multiplied to this Vector.
     * @return a new Vector, result of the multiplication.
     */
    Vector multiplyByScalar(int scalar);

    /**
     * Applies the deltaX and deltaY that describe this Vector to the 
     * coordinates of a given input starting Position. This starting Position
     * will NOT be modified by this method call.
     * @param startingPoint the Position to which this Vector will be
     * applied.
     * @return a new Position, obtained by applying this Vector to the starting
     * point.
     */
    Position applyToPosition(Position startingPoint);

    /**
     * The horizontal variation described by this Vector.
     * @return the deltaX of this Vector.
     */
    int deltaX();

    /**
     * The vertical variation described by this Vector.
     * @return the deltaY of this Vector.
     */
    int deltaY();

    /**
     * Adds this Vector to the given input Vector, without modifying any of the two.
     * It returns a new Vector with deltaX and deltaY resulting from the arithmetic
     * sum of the deltaX and deltaY of the two original Vectors. This allows
     * multiple sequential additions if needed.
     * @param v the Vector to be summed to this Vector.
     * @return the resulting sum Vector.
     */
    Vector addVector(Vector v);

    /**
     * Performs a rotation on this Vector if the angle and this Vector are suitable.
     * More specifically, rotations by multiples of 45 degrees can be performed on any Vector
     * that has exclusively a horizontal or vertical component, or if the absolute value
     * of the two components is the same, meaning it lies on a diagonal line with angular
     * coefficient of 1 or -1:
     * <br>
     * <br>v1(0, 5), v2(5, 0), v3(5, 5), v4(5, -5) are all examples of Vectors that can be
     * rotated by an angle which is a multiple of 45 degrees.
     * <br>
     * <br>If a Vector isn't inclined itself by an angle multiple of 45 degrees, it can still be
     * rotated, but only by angles which ar multiples of 90 degrees, otherwise it would land
     * on the wrong coordinates of the fixed grid and its dimensions would probably be
     * altered:
     * <br>
     * <br>v1(3,2) can be rotated by any multiple of 90 degrees, but not by 45 degrees.
     * <br>
     * <br>This is an optional operation. This Vector's internal state won't be modified
     * by this method call.
     * 
     * @param angle if positive, performs an anti-clockwise rotation, otherwise a
     * clockwise rotation.
     * @return an empty Optional if a rotation fails, otherwise an Optional containing
     * a new Vector describing the result of the rotation.
     */
    Optional<Vector> rotate(int angle);

    /**
     * Creates a String representing this Vector.
     * @return a String representing this Vector.
     */
    @Override
    String toString();
}
