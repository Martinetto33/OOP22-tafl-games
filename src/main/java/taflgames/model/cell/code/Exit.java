package taflgames.model.cell.code;

import java.util.List;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.pieces.api.Piece;

public class Exit extends AbstractCell {
    
    public Exit() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(final Position source, final Piece sender, final List<String> events, final Map<Player, Map<Position, Piece>> pieces,
                        final Map<Position, Cell> cells) {
    } 

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAccept(final Piece piece) {
        if (piece.getMyType().getTypeOfPiece().equals("KING") && super.isFree()) {
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
        return "Exit";
    }



}
