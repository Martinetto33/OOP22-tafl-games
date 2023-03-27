package taflgames.model.memento.code;

public class HistoryLockedException extends RuntimeException {

    public HistoryLockedException() {
        super("ERROR: tried to undo before changing the state of the match (i.e. no move was made).");
    }
}
