package taflgames.common.code;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import taflgames.common.api.FactoryHitbox;

 /**
 * {@inheritDoc}.
*/
public class ImplFactoryHitbox implements FactoryHitbox {
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Position> createBasicHitboxDistance(final int distance) throws IllegalArgumentException {
        final Integer t = Objects.requireNonNull(distance);
        if (t.intValue() < 0) {
            throw new IllegalArgumentException("distance was set as 0");
        }
        Set<Position> s = new HashSet<>();
        s.add(new Position(t.intValue(), 0));
        s.add(new Position(0, t.intValue()));
        s.add(new Position(-t.intValue(), 0));
        s.add(new Position(0, -t.intValue()));
        return s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Position> createArcherHitbox(final int range) throws IllegalArgumentException {
        final Integer t = Objects.requireNonNull(range);
        if (t.intValue() <= 0) {
            throw new IllegalArgumentException("range <=0");
        }
        Set<Position> f = new HashSet<>();
        for (int i = 1; i <= t.intValue(); i++) {
            f.addAll(this.createBasicHitboxDistance(i));
        }
        return f;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Position> createBasicHitbox() {
        return this.createBasicHitboxDistance(1);
    }

}
