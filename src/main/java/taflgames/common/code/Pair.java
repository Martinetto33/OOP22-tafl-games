package taflgames.common.code;

/**
 * A standard generic Pair<X,Y>, with getters, hashCode, equals, and toString well implemented.
 * The code was taken from the professor's Pair class which can be found at: 
 * https://bitbucket.org/mviroli/oop2022-esami/src/master/a01a/e1/Pair.java
 * @param <X> the type of the first element
 * @param <Y> the type of the second element
 */
public class Pair<X, Y> {
    private final X x; 
    private final Y y;
    /**
     * Creates a new Pair with given parameters.
     * @param x the first element
     * @param y the second element
     */
    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the first element of this Pair.
     * @return the first element
     */
    public X getX() {
        return x;
    }

    /**
     * Returns the second element of this Pair.
     * @return the second element
     */
    public Y getY() {
        return y;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair other = (Pair) obj;
        if (x == null) {
            if (other.x != null) {
                return false;
            }
        } else if (!x.equals(other.x)) {
            return false;
        }
        if (y == null) {
            if (other.y != null) {
                return false;
            }
        } else if (!y.equals(other.y)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a String representation of this Pair.
     * @return a String describing this Pair's content.
     */
    @Override
    public String toString() {
        return "Pair [x=" + x + ", y=" + y + "]";
    }
}
