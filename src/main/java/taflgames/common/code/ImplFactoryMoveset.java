package taflgames.common.code;

import java.util.HashSet;
import java.util.Set;

import taflgames.common.api.FactoryMoveSet;
import taflgames.common.api.Vector;

public class ImplFactoryMoveset implements FactoryMoveSet {


    @Override
    public Set<Vector> createBasicMoveSet() {
        Set<Vector> s = new HashSet<>(); 
        s.add(new VectorImpl(1, 0));
        s.add(new VectorImpl(0,-1));
        s.add(new VectorImpl(-1, 0));
        s.add(new VectorImpl(0, -1));
        return s;
    }

    @Override
    public Set<Vector> createSwapperMoveSet(Set<Position> enemyPositions) throws IllegalArgumentException{
        if(enemyPositions.equals(null)) {
             throw new IllegalArgumentException("enemyPositions is null");
        }
        
                                /**da controllare se dà problemi */
        Set<Vector> s = new HashSet<>(enemyPositions.stream()
                                    .map(p -> new VectorImpl(new Position(0, 0), p))
                                    .toList());
        s.addAll(this.createBasicMoveSet());
        return s;
    }

}
