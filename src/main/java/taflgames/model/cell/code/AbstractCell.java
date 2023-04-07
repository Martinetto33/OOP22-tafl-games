package taflgames.model.cell.code;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.controller.entitystate.CellState;
import taflgames.controller.entitystate.CellStateImpl;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.CellComponent;
import taflgames.model.memento.api.CellComponentMemento;
import taflgames.model.memento.api.CellMemento;
import taflgames.model.pieces.api.Piece;

/**
 * This class implements the state of a cell regardless of its type
 * and the basic functionalities which are implemented in the same way 
 * for all the cells.
 */
public abstract class AbstractCell implements Cell {

    private boolean cellStatus;
    private final Set<CellComponent> cellComponents;

    /**
     * Initializes the state of a generic cell.
     */
    private Set<CellComponent> justAddedComponents;

    /** 
     * Builds a new AbstractCell and sets its status to 'free' (true).
    */
    public AbstractCell() {
        this.cellStatus = true;
        this.cellComponents = new HashSet<>();
        /* If a component has just been created and anybody
         * calls "undo", there is no previous state for the
         * component to go back to; this is why components
         * require additional handling.
         */
        this.justAddedComponents = new HashSet<>();
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
    public void attachComponent(final CellComponent cellComponent) {
        this.cellComponents.add(cellComponent);
        this.justAddedComponents.add(cellComponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachComponent(final CellComponent cellComponent) {
        this.cellComponents.remove(cellComponent);
        this.justAddedComponents.remove(cellComponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<CellComponent> getComponents() {
        return Collections.unmodifiableSet(this.cellComponents);
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
    protected void updateComponents(final Position source, final Piece sender, final List<String> events, 
                                    final Map<Player, Map<Position, Piece>> pieces, final Map<Position, Cell> cells) {
        if (!this.cellComponents.isEmpty()) {
            this.cellComponents
                .forEach(component -> component.notifyComponent(source, sender, events, pieces, cells));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyCellThatTurnHasEnded() {
        if (!this.cellComponents.isEmpty()) {
            final Set<CellComponent> inactiveComponents = this.cellComponents.stream()
                    .filter(component -> !component.isActive())
                    .collect(Collectors.toSet());
            if (!inactiveComponents.isEmpty()) {
                inactiveComponents.forEach(component -> this.detachComponent(component));
            }
        }
        if (!this.justAddedComponents.isEmpty()) {
            this.justAddedComponents = new HashSet<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellMemento save() {
        if (!this.cellComponents.isEmpty()) {
            return this.new CellMementoImpl(this.cellComponents.stream()
                    .map(component -> component.saveComponentState())
                    .toList());

        }
        return this.new CellMementoImpl(Collections.emptyList());
    }

    /**
     * Only subclasses should be able to access this
     * method. Restores this Cell's status to the snapshot
     * saved in the given CellMemento parameter.
     * @param cm the CellMemento containing the status
     * to which this Cell will be reverted.
     */
    protected void restore(final CellMemento cm) {
        this.cellStatus = cm.getCellStatus();
        if (!this.justAddedComponents.isEmpty()) {
            /* The just added components need to be removed before
             * any call to their 'restore' method, because they simply
             * did not exist before the move that is being undone.
             */
            this.cellComponents.removeAll(this.justAddedComponents);
            this.justAddedComponents = new HashSet<>();
        }
        cm.getComponentMementos().forEach(component -> component.restore());
    }

    /**
     * Represents the state of this Cell at a given turn
     * in the match.
     */
    public class CellMementoImpl implements CellMemento {

        private final boolean innerCellStatus;
        private final List<CellComponentMemento> componentMementos;

        /**
         * Builds a new CellMemento representing
         * the state of this cell.
         * @param componentMementos the list of the mementos
         * of the components of this cell.
         */
        public CellMementoImpl(final List<CellComponentMemento> componentMementos) {
            this.innerCellStatus = AbstractCell.this.cellStatus;
            this.componentMementos = List.copyOf(componentMementos);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean getCellStatus() {
            return this.innerCellStatus;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<CellComponentMemento> getComponentMementos() {
            return Collections.unmodifiableList(this.componentMementos);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void restore() {
            AbstractCell.this.restore(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState getCellState() {
        if (this.getComponents().stream().anyMatch(component -> "Tomb".equals(component.getComponentType()) 
            && component.isActive())) {
            final Tomb tomb = (Tomb) this.getComponents().stream()
                            .filter(cell -> cell instanceof Tomb)
                            .findAny()
                            .get();
            return new CellStateImpl("Tomb", new VectorImpl(0, 0), tomb.peekTeamOfTheTomb());
        }
        return this.getSubclassCellState();
    }

    /**
     * Returns the {@link taflgames.controller.entitystate.CellState}
     * of the subclass.
     * @return a CellState describing the Cell of the subclass.
     */
    protected abstract CellState getSubclassCellState();

    /**
     * Returns the CellComponents of this Cell; this is required if subclasses
     * need to store additional information in their CellMementos.
     * @return the {@link taflgames.model.cell.api.CellComponent} of this Cell.
     */
    protected Set<CellComponent> getCellComponents() {
        return Collections.unmodifiableSet(this.cellComponents);
    }

    /**
     * Returns the CellComponents that have just been added to this Cell; 
     * this is required if subclasses need to store additional information in their CellMementos.
     * @return the {@link taflgames.model.cell.api.CellComponent} of this Cell.
     */
    protected Set<CellComponent> getJustAddedComponents() {
        return Collections.unmodifiableSet(this.justAddedComponents);
    }
}

