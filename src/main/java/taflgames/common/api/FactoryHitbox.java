package taflgames.common.api;

import java.util.Set;

import taflgames.common.code.Position;
/**
 * this factory has the job to create
 * different kinds of hit-boxes that can be 
 * used by pieces and other entities
 */
public interface FactoryHitbox {
    /**
     * creates the basic hit-box used by many 
     * types of pieces and other entities:
     * 
     *          x
     *        x 0 x
     *          x
     * 
     * @return basic hit-box
     */
    Set<Position> createBasicHitbox();

    /**
     * creates the archer's hit-box
     *
     *           x
     *           x
     *           x
     *     x x x A x x x
     *           x
     *           x
     *           x
     * @return archer's hitbox
     */
    Set<Position> createArcherHitbox();

}
