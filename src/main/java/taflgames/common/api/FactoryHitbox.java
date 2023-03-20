package taflgames.common.api;

import java.util.Set;

import taflgames.common.code.Position;
/**
 * this factory has the job to create
 * different kinds of hit-boxes that can be 
 * used by pieces and other entities alike. A hitbox is 
 * composed by multiple cells called "cell-hitbox": they
 * rappresent cells on the board where enemy entities can be killed.
 */
public interface FactoryHitbox {
    /**
     * this method's very similar to {@link createBasicHitbox} in this factory; the only difference is that
     * you can set the distance of a singular "cell-hitbox" from its entity. It should be used when the 
     * distance is grater than 1.
     * @param distance distance of a singular "cell-hitbox" from its entity
     * @return basic hit-box with distance 
     */
    Set<Position> createBasicHitboxDistance(int distance);
    /**
     * creates the basic hit-box shaped as a cross with only 
     * one "cell-hitbox" in each 'arm' of the cross right 
     * next to the entity.
     * @return basic cross shaped hit-box
     */
    Set<Position> createBasicHitbox();

    /**
     * creates the archer's hit-box based on an initial range.
     *
     *           x
     *           x
     *           x
     *     x x x A x x x
     *           x
     *           x
     *           x
     * @param range 
     * @return archer's hitbox
     */
    Set<Position> createArcherHitbox(int range);

}
