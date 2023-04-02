package taflgames.common.code;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import taflgames.common.api.FactoryMoveSet;
import taflgames.common.api.Vector;
/**
*{@inheritDoc}.
*/
public class ImplFactoryMoveset implements FactoryMoveSet {
    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<Vector> createBasicMoveSet() {
        final Set<Vector> s = new HashSet<>(); 
        s.add(new VectorImpl(1, 0, true));
        s.add(new VectorImpl(0, -1, true));
        s.add(new VectorImpl(-1, 0, true));
        s.add(new VectorImpl(0, 1, true));
        return s;
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<Vector> createSwapperMoveSet(final Set<Position> enemyPositions) {
        final var t = Objects.requireNonNull(enemyPositions);
        /**
         * creation of vectors pointing to enemy positions
         */
        final Set<Vector> s = new HashSet<>(t.stream()
                                    .map(p -> new VectorImpl(new Position(0, 0), p, false))
                                    .collect(Collectors.toSet()));
        s.addAll(this.createBasicMoveSet());
        return s;
    }

}
