package taflgames.model.cell.code;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.CellComponent;
import taflgames.model.memento.api.CellComponentMemento;
import taflgames.model.memento.api.CellMemento;
import taflgames.model.pieces.api.Piece;

public abstract class AbstractCell implements Cell {

    private boolean cellStatus;
    private final Set<CellComponent> cellComponents;
    /** 
     * 
    */
    public AbstractCell() {
        this.cellStatus = true;
        this.cellComponents = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFree() {
        return cellStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFree(final boolean cellStatus) {        
        this.cellStatus = cellStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachComponent(CellComponent cellComponent) {
        this.cellComponents.add(cellComponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachComponent(CellComponent cellComponent) {
        this.cellComponents.remove(cellComponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<CellComponent> getComponents() {
        return Collections.unmodifiableSet(this.cellComponents);
    }

    /**
     * {@inheritDoc}
     * //TODO
     */
    @Override
    public CellComponentMemento saveComponentsState() {
        return null;
    }

    /**
     * Only subclasses of this class should call this method.
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
    protected void updateComponents(Position source, Piece sender, List<String> events, Map<Player,
                                    Map<Position, Piece>> pieces, Map<Position, Cell> cells) {
        if (!this.cellComponents.isEmpty()) {
            this.cellComponents
                .forEach(component -> component.notifyComponent(source, sender, events, pieces, cells));
        }
    }

    /**
     * At the end of each turn, inactive components are detached
     * by the Board.
     */
    @Override
    public void notifyCellThatTurnHasEnded() {
        if (!this.cellComponents.isEmpty()) {
            Set<CellComponent> inactiveComponents = this.cellComponents.stream()
                    .filter(component -> !component.isActive())
                    .collect(Collectors.toSet());
            if (!inactiveComponents.isEmpty()) {
                inactiveComponents.forEach(component -> this.detachComponent(component));
            }
        }
    }

    public CellMemento save() {
        return this.new CellMementoImpl();
    }


    public void restore(CellMemento cm) {
        this.cellStatus = cm.getCellStatus();
    }
    
    public class CellMementoImpl implements CellMemento {

        private final boolean innerCellStatus;

        public CellMementoImpl() {
            this.innerCellStatus = AbstractCell.this.cellStatus;
        }

        public boolean getCellStatus() {
            return this.innerCellStatus;
        }

        @Override
        public void restore() {
            AbstractCell.this.restore(this);
        }

    }
}
    