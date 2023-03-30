package taflgames.model.cell.api;

import java.util.List;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.pieces.api.Piece;

/**
 * Models a component that can be attached to a Cell.
 */
public interface CellComponent {

    /**
     * Notifies the CellComponent.
     * @param source the {@link taflgames.common.code.Position} where the
     * event(s) occurred.
     * @param sender the {@link taflgames.model.pieces.api.Piece} that
     * produced the event(s).
     * @param events the List of occurred events.
     * @param pieces the Map containing the {@link taflgames.model.pieces.api.Piece}
     * elements.
     * @param cells the Map containing the {@link taflgames.model.cell.api.Cell}
     * elements.
     */
    void notifyComponent(Position source, Piece sender, List<String> events, Map<Player, Map<Position, Piece>> pieces,
                         Map<Position, Cell> cells);

    /**
     * Returns wether this CellComponent is still active.
     * @return true if this Component is still active and should not be detached.
     */
    boolean isActive();

    /**
     * Returns the type of this CellComponent.
     * @return a String representing the type of this CellComponent.
     */
    String getComponentType();
}
