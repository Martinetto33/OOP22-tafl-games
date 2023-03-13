package taflgames.model.memento.api;

/**
 * This interface models a Caretaker, which is part of the memento pattern.
 * The Caretaker stores trace of any relevant data such as turn number, positions
 * etc. at a given turn, and is able to provide the Memento objects required
 * to restore a previous state.
 */
public interface Caretaker {
    /**
     * Adds a new snapshot in the history, from the originator class.
     */
    void updateHistory();
}
