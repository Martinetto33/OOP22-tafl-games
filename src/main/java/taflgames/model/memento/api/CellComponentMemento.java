package taflgames.model.memento.api;

/**
 * An interface modelling the behaviour that the Inner Class of the CellComponent implementation
 * should provide in order to save a snapshot of the current state of a CellComponent. Said Inner
 * Class should implement this interface.
 * <br>This interface is part of the pattern Memento.
 */
public interface CellComponentMemento {

    /**
     * This method should allow each instance of the Inner Classes reference their Outer
     * classes and call their "restore()" method, by passing themselves as a parameter [i.e. by passing "this"].
     * <br>This way, the Inner Classes themselves will be in charge of finding the CellComponent instance
     * that had generated them, simplifying the duty of the Caretaker and the other external
     * classes which shouldn't bother with these associations.
     */
    void restore();
}
