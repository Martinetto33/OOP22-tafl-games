package taflgames.model.memento.api;

import java.util.List;

/**
 * An interface modelling the behaviour that the Inner Class of the Board implementation
 * should provide in order to save a snapshot of the current state of a Board. Said Inner
 * Class should implement this interface.
 * <br>This interface is part of the pattern Memento.
 */
public interface BoardMemento {
    /**
     * Returns the saved states of all the Pieces, including the dead ones.
     * The "restore()" method in the Board class should call the method
     * "restore()" on each of these PieceMemento, allowing them to go back
     * to their previous state as well.
     * @return a List of PieceMemento.
     */
    List<PieceMemento> getPiecesMemento();
    /**
     * Returns the saved states of all the Cells, including the inactive ones.
     * The "restore()" method in the Board class should call the method
     * "restore()" on each of these CellMemento, allowing them to go back
     * to their previous state as well.
     * @return a List of CellMemento.
     */
    List<CellMemento> getCellsMemento();

    /**
     * Restores the state contained in this BoardMemento.
     */
    void restore();
}
