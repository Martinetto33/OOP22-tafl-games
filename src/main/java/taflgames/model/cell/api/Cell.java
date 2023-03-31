package taflgames.model.cell.api;
import java.util.*;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.memento.api.CellComponentMemento;
import taflgames.model.memento.api.CellMemento;
import taflgames.model.pieces.api.Piece;

public interface Cell {
    
    public void notify(Position source, Piece sender, List<String> events, Map<Player, Map<Position, Piece>> pieces, Map<Position, Cell> cells);

    public boolean canAccept(Piece piece);

    public void setFree(boolean isFree);

    public String getType();

    public boolean isFree();

    public CellMemento save();

    public void notifyCellThatTurnHasEnded();

    /**
     * Attaches the given {@link taflgames.model.cell.api.CellComponent}
     * to the ComposableCell.
     * @param cellComponent the component to be attached.
     */
    void attachComponent(CellComponent cellComponent);

    /**
     * Searches through all the {@link taflgames.model.cell.api.CellComponent}
     * and tries to detach the first occurrency of the Cell
     * @param cellComponent the component to be detached.
     */
    void detachComponent(CellComponent cellComponent);

    /**
     * Returns the Set of CellComponents attached to this Cell.
     * @return the Set of CellComponents.
     */
    Set<CellComponent> getComponents();

    /**
     * Saves the state of the CellComponent.
     * @return the {@link taflgames.model.memento.api.CellComponentMemento}
     * representing the saved state of this component.
     */
    CellComponentMemento saveComponentsState();

}
