package taflgames.model.cell.api;

public interface TimedEntity {

    /**
     * Notify that the turn has ended.
     * @param turn the turn of the game that has just eneded.
     */
    void notifyTurnHasEnded(int turn);
}
