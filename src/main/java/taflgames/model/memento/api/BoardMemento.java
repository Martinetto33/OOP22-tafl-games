package taflgames.model.memento.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.Slider;
import taflgames.model.pieces.api.Piece;

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
     * Returns the saved state of the cells.
     * @return a Map of Positions and Cells.
     */
    Map<Position, Cell> getInnerCells();

    /**
     * Returns the saved state of the attacker's pieces.
     * @return a Map of Positions and Pieces.
     */
    Map<Position, Piece> getInnerAttackerPieces();

    /**
     * Returns the saved state of the defender's pieces.
     * @return a Map of Positions and Pieces.
     */
    Map<Position, Piece> getInnerDefenderPieces();

    /**
     * Returns the last saved current position.
     * @return the Position.
     */
    Position getInnerCurrentPos();

    /**
     * Returns the saved state of the slider cells.
     * @return a Set of Sliders.
     */
    Set<Slider> getInnerSlidersEntities();

    /**
     * Restores the state contained in this BoardMemento.
     */
    void restore();
}
