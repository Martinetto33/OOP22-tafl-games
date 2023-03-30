package taflgames.model.cell.code;

import java.util.List;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.pieces.api.Piece;

public class ClassicCell extends AbstractCell{

    public ClassicCell() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(Position source, Piece sender, List<String> events, Map<Player, Map<Position, Piece>> pieces,
            Map<Position, Cell> cells) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAccept(Piece piece) {
        if(super.isFree()) {
            return true;
        } else {
            return false;
        }   
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "ClassicCell";
    }

    
}
