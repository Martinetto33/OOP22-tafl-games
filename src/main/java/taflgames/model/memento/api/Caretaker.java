package taflgames.model.memento.api;

/**
 * This interface models a Caretaker, which is part of the memento pattern.
 * The Caretaker stores trace of any relevant data such as turn number, positions
 * etc. at a given turn, and is able to provide the Memento objects required
 * to restore a previous state.
 * 
 * Each saved object has a public inner class: this choice was made consciously,
 * since no operations on these Memento objects have any side-effects and since
 * the Inner Classes in Java have a reference to their Outer Classes, this
 * helps deal with the problem of associating each instance of multiple
 * objects of the same type (such as Pieces and Cells) to their specific Memento
 * from which they can restore their previous state.
 */
public interface Caretaker {

    /**
     * Registers a new MatchMemento, by pushing it onto the history stack.
     * This method should be called by a handler at the beginning of a turn,
     * in order to save the starting state of the match before any changes.
     * The call to this method causes the history to expand, and also
     * locks it, so that no caller can undo moves unless the history
     * is explicitly unlocked.
     */
    void updateHistory();

    /**
     * Returns to the previous saved state.
     */
    void undo();

    /**
     * Must be called before any {@link #undo()} operation
     * can be performed. This additional step ensures that the state
     * of the observed Match has actually changed before calling undo.
     */
    void unlockHistory();

    /**
     * Returns whether the history is locked. If yes, {@link #undo()}
     * method cannot be called until the history is unlocked.
     * @return true if the history is locked.
     */
    boolean isLocked();
}
