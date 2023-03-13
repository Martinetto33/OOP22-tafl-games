package taflgames.model.pieces.code;

import java.util.HashSet;
import java.util.Set;

import taflgames.common.api.FactoryHitbox;
import taflgames.common.code.Position;

public class ImplFactoryHitbox implements FactoryHitbox {

    @Override
    public Set<Position> createBasicHitboxDistance(int distance) throws IllegalArgumentException{
        if(distance == 0) {
            throw new IllegalArgumentException("distance was set as 0");
        }
        Set<Position> s = new HashSet<>();
        s.add(new Position(distance,0));
        s.add(new Position(0,distance));
        s.add(new Position(-distance,0));
        s.add(new Position(0,-distance));

        return s;
    }

    @Override
    public Set<Position> createArcherHitbox(final int range) {
        Set<Position> f = new HashSet<>();
        for(int i=1;i<=range;i++){
            f.addAll(this.createBasicHitboxDistance(i));
        }
        return f;
    }

    @Override
    public Set<Position> createBasicHitbox() {
        return this.createBasicHitboxDistance(1);
    }

}
