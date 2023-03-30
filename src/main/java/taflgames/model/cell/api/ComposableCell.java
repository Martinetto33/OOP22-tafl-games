package taflgames.model.cell.api;

import taflgames.model.memento.api.CellComponentMemento;

/**
 * Models a Cell to which some {@link taflgames.model.cell.api.CellComponent}(s)
 * can be attached or detached.
 */
public interface ComposableCell {

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
     * Saves the state of the CellComponent.
     * @return the {@link taflgames.model.memento.api.CellComponentMemento}
     * representing the saved state of this component.
     */
    CellComponentMemento saveComponentsState();

}
