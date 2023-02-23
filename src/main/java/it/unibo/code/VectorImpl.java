package it.unibo.code;

import java.util.Optional;

import it.unibo.api.Vector;

/**
 * This class is fake for now and will model a mathematic vector.
 */
public class VectorImpl implements Vector {
    private static final int UNIT_ANGLE = 45;
    private static final int ROUND_ANGLE = 360;
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
        double radAngle = this.toRadians(angle);
        return Optional.of(new VectorImpl((int) (this.deltaX() * Math.cos(radAngle) - this.deltaY() * Math.sin(radAngle)), 
                                          (int) (this.deltaX() * Math.sin(radAngle) + this.deltaY() * Math.cos(radAngle))));
    }

    private double toRadians(int angle) {
        return angle * 2 * Math.PI / VectorImpl.ROUND_ANGLE;
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

}
