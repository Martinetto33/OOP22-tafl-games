package taflgames.model.cell.code;

import java.util.List;

import taflgames.common.code.Position;
import taflgames.model.board.code.Piece;
import taflgames.model.cell.api.Cell;

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
    

}
    