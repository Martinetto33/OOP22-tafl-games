package taflgames.model.memento.code;

import java.util.Stack;

import taflgames.model.Match;
import taflgames.model.memento.api.Caretaker;
import taflgames.model.memento.api.MatchMemento;

/**
 * This class will model a Caretaker, a class which is part of the pattern Memento
 * and that will be in charge of managing the history of a single Match, which here
 * takes the name of "originator".
 * <br>The updateHistory() method should be called each time a turn ends, while
 * the undo() method could be called at any given moment in a turn.
 * 
 * This class will be implemented with a Stack, in order to allow methods 
 * "pop" and "push" on the history
 * 
 * In this version of the implementation, the Caretaker will dump
 * any existing state each time a new state is saved.
 */
public class CaretakerImpl implements Caretaker {
    private final Match originator;
    private final Stack<MatchMemento> history;

    /**
     * Builds a new Caretaker.
     */
    public CaretakerImpl(Match originator) {
        this.originator = originator;
        this.history = new Stack<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateHistory() {
        if (!this.history.isEmpty()) {
            this.history.clear();
        }
        this.history.push(this.originator.save());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        if (this.history.isEmpty()) {
            return;
        }
        this.originator.restore(this.history.pop());
    }
}
