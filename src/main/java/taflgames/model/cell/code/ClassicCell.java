package taflgames.model.cell.code;

import java.util.List;
import taflgames.common.code.Position;
import taflgames.model.pieces.api.Piece;

public class ClassicCell extends AbstractCell{

    public ClassicCell() {
        super();
    }

    @Override
    public void notify(Position source, Piece sender, List<String> events) {
    }

    @Override
    public boolean canAccept(Piece piece) {
        if(super.isFree()) {
            return true;
        } else {
            return false;
        }   
    }

    @Override
    public String getType() {
        return "ClassicCell";
    }
}
