package taflgames.model.cell.code;

import java.util.List;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.controller.entitystate.CellState;
import taflgames.controller.entitystate.CellStateImpl;
import taflgames.model.cell.api.Cell;
import taflgames.model.pieces.api.Piece;

/**
 * This class models a throne, a special cell
 * that accepts only the king.
 */
public final class Throne extends AbstractCell {

    private static final String THRONE_TYPE = "Throne";
    private static final String KING = "KING";

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(
        final Position source,
        final Piece sender,
        final List<String> events, 
        final Map<Player, Map<Position, Piece>> pieces,
        final Map<Position, Cell> cells
    ) {
        // Empty
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAccept(final Piece piece) {
        return KING.equals(piece.getMyType().getTypeOfPiece()) && super.isFree();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return THRONE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState getSubclassCellState() {
        return new CellStateImpl(this.getType(), new VectorImpl(0, 0), null);
    }

}
