package taflgames.model.memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private MatchExample originator;
    private List<MatchExample.MatchMemento> history;

    public CareTaker(MatchExample originator) {
        this.originator = originator;
        this.history = new ArrayList<>();
    }

    public void doStuff() {
        this.history.add(this.originator.save());
    }

    public void undo(int turnNumber) {
        if(turnNumber >= 0 && turnNumber < this.history.size()) {
            this.originator.restore(this.history.get(turnNumber));
        }
        else {
            System.out.println("Error occurred");
        }
    }
}
