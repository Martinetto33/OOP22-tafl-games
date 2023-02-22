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
        Position s = new Position(this.startPos);
        Position e = new Position(this.endPos);
        s.multiplyByInteger(scalar);
        e.multiplyByInteger(scalar);
        return new VectorImpl(s, e);
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
}
