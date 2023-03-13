package taflgames.common.code;

import java.util.Optional;

import taflgames.common.api.Vector;

/**
 * This class models a mathematic {@link taflgames.common.api.Vector}.
 */
@SuppressWarnings("PMD.ReplaceVectorWithList") /*suppressed as the design requires
some specific methods and Lists wouldn't come in handy in this case */
public final class VectorImpl implements Vector {
    private static final int UNIT_ANGLE = 45;
    private static final int RIGHT_ANGLE = 90;
    private final Position startPos;
    private final Position endPos;
    private final boolean isUnitVector;
    /**
     * Creates a new VectorImpl based on the Positions given. Sets the isUnitVector
     * value based on the parameter given.
     * @param startPos the starting Position
     * @param endPos the ending Position
     * @param isUnitVector states wether this VectorImpl is a unit vector.
     */
    public VectorImpl(final Position startPos, final Position endPos, final boolean isUnitVector) {
        this.startPos = startPos;
        this.endPos = endPos;
        if (this.canBeVersor() && isUnitVector) {
            this.isUnitVector = true;
        } else {
            this.isUnitVector = false;
        }
    }

    /**
     * Creates a new VectorImpl based on the Positions given. This
     * method doesn't create a versor.
     * @param startPos the starting Position
     * @param endPos the ending Position
     */
    public VectorImpl(final Position startPos, final Position endPos) {
        this(startPos, endPos, false);
    }

    private boolean canBeVersor() {
        return Math.sqrt(Math.pow(this.deltaX(), 2) + Math.pow(this.deltaY(), 2)) == 1.0;
    }

    /**
     * Creates a new VectorImpl based on the deltas given. The starting
     * Position will be considered (0,0) and the ending Postion will have
     * coordinates (deltaX, deltaY).
     * @param deltaX the horizontal variation
     * @param deltaY the vertical variation
     * @param isUnitVector states wether this VectorImpl is a unit vector.
     */
    public VectorImpl(final int deltaX, final int deltaY, final boolean isUnitVector) {
        this.startPos = new Position(0, 0);
        this.endPos = new Position(deltaX, deltaY);
        if (this.canBeVersor() && isUnitVector) {
            this.isUnitVector = true;
        } else {
            this.isUnitVector = false;
        }
    }

    /**
     * Creates a new VectorImpl based on the deltas given. The starting
     * Position will be considered (0,0) and the ending Postion will have
     * coordinates (deltaX, deltaY). This method doesn't create a unit Vector.
     * @param deltaX the horizontal variation
     * @param deltaY the vertical variation
     */
    public VectorImpl(final int deltaX, final int deltaY) {
        this(deltaX, deltaY, false);
    }

    /*Getters and setters */
    /**
     * {@inheritDoc}
     */
    @Override
    public Position getStartPos() {
        return this.startPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getEndPos() {
        return this.endPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUnitVector() {
        return this.isUnitVector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector multiplyByScalar(final int scalar) {
        final Position e = new Position(this.startPos.getX() + scalar * this.deltaX(),
                                        this.startPos.getY() + scalar * this.deltaY());
        return new VectorImpl(this.startPos, e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position applyToPosition(final Position startingPoint) {
        return new Position(startingPoint.getX() + this.deltaX(), startingPoint.getY() + this.deltaY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deltaX() {
        return this.endPos.getX() - this.startPos.getX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deltaY() {
        return this.endPos.getY() - this.startPos.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector addVector(final Vector v) {
        return new VectorImpl(v.deltaX() + this.deltaX(), v.deltaY() + this.deltaY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Vector> rotate(final int angle) {
        if (angle % VectorImpl.UNIT_ANGLE != 0) {
            return Optional.empty();
        }
        /* If a vector isn't inclined by an angle which is multiple of 45 degrees, only rotations
         * by multiples of 90 degrees would return a vector that hasn't changed in dimensions and would
         * make sense in a cells grid.
         */
        if (Math.abs(this.deltaX()) != Math.abs(this.deltaY()) && angle % VectorImpl.RIGHT_ANGLE != 0) {
            return Optional.empty();
        }
        final double radAngle = Math.toRadians(angle);
        return Optional.of(new VectorImpl((int) Math.round(this.deltaX() * Math.cos(radAngle) 
                                                - this.deltaY() * Math.sin(radAngle)), 
                                          (int) Math.round(this.deltaX() * Math.sin(radAngle) 
                                                + this.deltaY() * Math.cos(radAngle))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((startPos == null) ? 0 : startPos.hashCode());
        result = prime * result + ((endPos == null) ? 0 : endPos.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VectorImpl other = (VectorImpl) obj;
        if (startPos == null) {
            if (other.startPos != null) {
                return false;
            }
        } else if (!startPos.equals(other.startPos)) {
            return false;
        }
        if (endPos == null) {
            if (other.endPos != null) {
                return false;
            }
        } else if (!endPos.equals(other.endPos)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder().append("Vector: ")
                                  .append(this.startPos.toString())
                                  .append(" -> ")
                                  .append(this.endPos.toString())
                                  .append("; deltaX = ")
                                  .append(this.deltaX())
                                  .append(", deltaY = ")
                                  .append(this.deltaY())
                                  .toString();
    }
}
