package taflgames.model.memento.code;

//import java.util.ArrayList;
//import java.util.List;

/**
 * This class will model a Caretaker, a class which is part of the pattern Memento
 * and that will be in charge of managing the history of a single Match, which here
 * takes the name of "originator".
 * <br>The updateHistory() method should be called each time a turn ends, while
 * the undo() method could be called when the user presses an "undo" button.
 * 
 * 
 * This class will be implemented with a Stack, in order to allow methods 
 * "pop" and "push" on the history
 */
public class Caretaker {
    //private MatchExample originator;
    //private List<MatchMemento> history;

    /**
     * Builds a new Caretaker.
     */
    /* public Caretaker(MatchExample originator) {
        this.originator = originator;
        this.history = new ArrayList<>();
    } */

    /**
     * Registers a new MatchMemento, by pushing it onto the history stack.
     */
    public void updateHistory() {
        //this.history.add(this.originator.save());
    }

    /* public void undo(int turnNumber) throws IllegalAccessError {
        if(turnNumber >= 0 && turnNumber < this.history.size()) {
            this.originator.restore(this.history.get(turnNumber));
        }
        else {
            throw new IllegalAccessError();
        }
    } */
}
