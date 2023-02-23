package it.unibo.api;

import java.util.Optional;

import it.unibo.code.Position;

public interface Vector {
    public Position getStartPos();

    public void setStartPos(Position startPos);

    public Position getEndPos();

    public void setEndPos(Position endPos);

    public Vector multiplyByScalar(int scalar);

    public Position applyToPosition(Position startingPoint);

    public int deltaX();

    public int deltaY();

    public Vector addVector(Vector v);

    public Optional<Vector> rotate(int angle);

    public int hashCode();

    public boolean equals(Object obj);
}
