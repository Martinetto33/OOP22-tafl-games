package taflgames.test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import taflgames.common.api.FactoryHitbox;
import taflgames.common.code.ImplFactoryHitbox;
import taflgames.common.code.Position;

public class TestFactoryHitbox {
    @Test
    void testCreateBasicHitboxDistance() {
        final FactoryHitbox f= new ImplFactoryHitbox();
        final int d = 1;
        Set<Position> out= new HashSet<>();
        out.add(new Position(d, 0));
        out.add(new Position(0, d));
        out.add(new Position(-d, 0));
        out.add(new Position(0, -d));
        assertEquals(out,f.createBasicHitboxDistance(d));
        final int d2=-5;
        Set<Position> out2= new HashSet<>();
        out2.add(new Position(d2, 0));
        out2.add(new Position(0, d2));
        out2.add(new Position(-d2, 0));
        out2.add(new Position(0, -d2));
        assertEquals(out2,f.createBasicHitboxDistance(d2));


        assertThrows(IllegalArgumentException.class,
                        ()->f.createBasicHitboxDistance(0));
    }

    @Test
    void testCreateBasicHitbox(){
        final FactoryHitbox f= new ImplFactoryHitbox();
        int d = 1;
        Set<Position> out= new HashSet<>();
        out.add(new Position(d, 0));
        out.add(new Position(0, d));
        out.add(new Position(-d, 0));
        out.add(new Position(0, -d));
        assertEquals(out,f.createBasicHitbox());

        int d2 = 2;
        Set<Position> out2= new HashSet<>();
        out2.add(new Position(d2, 0));
        out2.add(new Position(0, d2));
        out2.add(new Position(-d2, 0));
        out2.add(new Position(0, -d2));
        assertNotEquals(out2,f.createBasicHitbox());
        
    }

    @Test
    void testCreateArcherHitbox() {
        final FactoryHitbox f= new ImplFactoryHitbox();
        Set<Position> out = new HashSet<>();
        int d2=1;
        out.add(new Position(d2, 0));
        out.add(new Position(0, d2));
        out.add(new Position(-d2, 0));
        out.add(new Position(0, -d2));
        d2=2;
        out.add(new Position(d2, 0));
        out.add(new Position(0, d2));
        out.add(new Position(-d2, 0));
        out.add(new Position(0, -d2));
        d2=3;
        out.add(new Position(d2, 0));
        out.add(new Position(0, d2));
        out.add(new Position(-d2, 0));
        out.add(new Position(0, -d2));

        int range = 3;
        
        assertEquals(out,f.createArcherHitbox(range));

        assertThrows( IllegalArgumentException.class, ()->f.createArcherHitbox(0));

        assertThrows( IllegalArgumentException.class, ()->f.createArcherHitbox(-9));
    }
    
}