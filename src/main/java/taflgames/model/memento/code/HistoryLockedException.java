package taflgames.model.memento.code;

/**
 * An exception thrown by a {@link taflgames.model.memento.code.CaretakerImpl}
 * if a user attempts to access the history before having it unlocked.
 */
public class HistoryLockedException extends RuntimeException {
    private static final long serialVersionUID = 391826789L;

    /**
     * Builds a new HistoryLockedException.
     */
    public HistoryLockedException() {
        super("ERROR: tried to undo before changing the state of the match (i.e. no move was made).");
    }
}
