package taflgames.model.cell.code;

import taflgames.model.cell.api.Cell;
import taflgames.model.memento.api.CellMemento;

public abstract class AbstractCell implements Cell{

    private boolean cellStatus;
    
    public AbstractCell() {
        this.cellStatus = false;
    }

    @Override
    public boolean isFree() {
        return cellStatus;
    }

    @Override
    public void setFree(final boolean cellStatus) {        
        this.cellStatus = cellStatus;
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
    