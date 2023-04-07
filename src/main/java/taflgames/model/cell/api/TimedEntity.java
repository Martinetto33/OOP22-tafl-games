package taflgames.model.cell.api;
/**
 * This interface allows entities whose state change at each turn
 * to know that the current turn has ended.
 */
public interface TimedEntity {

    /**
     * Notify that the turn has ended.
     * @param turn the turn of the game that has just eneded.
     */
    void notifyTurnHasEnded(int turn);

}
