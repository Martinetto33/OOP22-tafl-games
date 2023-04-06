package taflgames.model.cell.api;

/**
 * An interface modelling an entity that needs to know about
 * turns passing.
 */
public interface TimedEntity {

    /**
     * Notify this entity that the current turn has ended.
     * @param turn the turn that has just ended.
     */
    void notifyTurnHasEnded(int turn);

}
