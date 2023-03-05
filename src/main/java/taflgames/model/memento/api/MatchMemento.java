package taflgames.model.memento.api;

/**
 * An interface modelling the behaviour that the Inner Class of the Match implementation
 * should provide in order to save a snapshot of the current state of a Match. Said Inner
 * Class should implement this interface.
 * <br>This interface is part of the pattern Memento.
 */
public interface MatchMemento {
    /**
     * Since the Match has a pointer to the Board, after restoring its state from
     * this Match Memento the "restore()" method of the class Match should call
     * "board.save()" by passing this BoardMemento as a parameter.
     * @return a BoardMemento from which the board can restore its previous state.
     */
    BoardMemento getBoardMemento();

}
