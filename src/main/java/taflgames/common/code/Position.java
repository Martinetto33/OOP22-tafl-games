package taflgames.common.code;

/**
 * A class modelling a 2D point in space.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Creates a new Position.
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new Position from an existing one, with the
     * same x and y coordinates.
     * @param p the other Position.
     */
    public Position(final Position p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    /**
     * Returns the x coordinate.
     * @return the x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate.
     * @return the y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
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
        final Position other = (Position) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) { //NOPMD
            return false;
        }
        return true;
    }

    /**
     * Returns a String representation of this Position.
     * @return a String representing this Position.
     */
    @Override
    public String toString() {
        return new StringBuilder().append("[x: ")
                                  .append(this.x)
                                  .append(", y: ")
                                  .append(this.y)
                                  .append(']')
                                  .toString();
    }
}
