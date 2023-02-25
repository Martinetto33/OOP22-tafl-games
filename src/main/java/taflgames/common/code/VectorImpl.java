package taflgames.common.code;

import java.util.Optional;

import taflgames.common.api.Vector;

/**
 * This class models a mathematic vector.
 * TODO: add javadoc
 */
public class VectorImpl implements Vector {
    private static final int UNIT_ANGLE = 45;
    private static final int RIGHT_ANGLE = 90;
    private Position startPos;
    private Position endPos;
    
    public VectorImpl(Position startPos, Position endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public VectorImpl(int deltaX, int deltaY) {
        this.startPos = new Position(0, 0);
        this.endPos = new Position(deltaX, deltaY);
    }
    /*Getters and setters */
    public Position getStartPos() {
        return this.startPos;
    }

    public void setStartPos(Position startPos) {
        this.startPos = startPos;
    }

    public Position getEndPos() {
        return this.endPos;
    }

    public void setEndPos(Position endPos) {
        this.endPos = endPos;
    }

    public Vector multiplyByScalar(int scalar) {
        Position e = new Position(this.startPos);
        e.setX(e.getX() + scalar * this.deltaX());
        e.setY(e.getY() + scalar * this.deltaY());
        return new VectorImpl(this.startPos, e);
    }

    public Position applyToPosition(Position startingPoint) {
        return new Position(startingPoint.getX() + this.deltaX(), startingPoint.getY() + this.deltaY());
    }

    public int deltaX() {
        return this.endPos.getX() - this.startPos.getX();
    }

    public int deltaY() {
        return this.endPos.getY() - this.startPos.getY();
    }

    public Vector addVector(Vector v) {
        return new VectorImpl(v.deltaX() + this.deltaX(), v.deltaY() + this.deltaY());
    }

    public Optional<Vector> rotate(int angle) {
        if (angle % VectorImpl.UNIT_ANGLE != 0) {
            return Optional.empty();
        }
        /*If a vector isn't inclined by an angle which is multiple of 45°, only rotations
         * by multiples of 90° would return a vector that hasn't changed in dimensions and would
         * make sense in a cells grid.
         */
        if (this.deltaX() != this.deltaY() && angle % VectorImpl.RIGHT_ANGLE != 0) {
            return Optional.empty();
        }
        double radAngle = Math.toRadians(angle);
        return Optional.of(new VectorImpl((int) Math.round(this.deltaX() * Math.cos(radAngle) - this.deltaY() * Math.sin(radAngle)), 
                                          (int) Math.round(this.deltaX() * Math.sin(radAngle) + this.deltaY() * Math.cos(radAngle))));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((startPos == null) ? 0 : startPos.hashCode());
        result = prime * result + ((endPos == null) ? 0 : endPos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VectorImpl other = (VectorImpl) obj;
        if (startPos == null) {
            if (other.startPos != null)
                return false;
        } else if (!startPos.equals(other.startPos))
            return false;
        if (endPos == null) {
            if (other.endPos != null)
                return false;
        } else if (!endPos.equals(other.endPos))
            return false;
        return true;
    }

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
