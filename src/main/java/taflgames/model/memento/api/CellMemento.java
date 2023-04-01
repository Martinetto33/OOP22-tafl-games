package taflgames.model.memento.api;

import java.util.List;

/**
 * An interface modelling the behaviour that the Inner Class of the Cell implementation
 * should provide in order to save a snapshot of the current state of a Cell. Said Inner
 * Class should implement this interface.
 * <br>This interface is part of the pattern Memento.
 */
public interface CellMemento {
    /**
     * This method should allow each instance of the Inner Classes reference their Outer
     * classes and call their "restore()" method, by passing themselves as a parameter [i.e. by passing "this"].
     * <br>This way, the Inner Classes themselves will be in charge of finding the Cell instance
     * that had generated them, simplifying the duty of the Caretaker and the other external
     * classes which shouldn't bother with these associations.
     */
    void restore();

    /**
     * Returns the cell status.
     * @return the cell status.
     */
    boolean getCellStatus(); //NOPMD
    /* PMD error regarding this method's name suppressed
     * because 'isCellStatus' makes no sense.
     */

    List<CellComponentMemento> getComponentMementos();

}
