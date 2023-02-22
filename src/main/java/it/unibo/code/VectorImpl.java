package it.unibo.code;

import it.unibo.api.Vector;

/**
 * This class is fake for now and will model a mathematic vector.
 */
public class VectorImpl implements Vector {
    private Position startPos;
    private Position endPos;
    
    public VectorImpl(Position startPos, Position endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
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
}
