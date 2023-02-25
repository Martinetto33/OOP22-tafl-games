package taflgames.common.api;

import java.util.Optional;

import taflgames.common.code.Position;

//TODO: javadoc

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

    public String toString();
}
