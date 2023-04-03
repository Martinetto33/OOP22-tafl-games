package taflgames.model.cell.code;

import java.util.List;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.pieces.api.Piece;

/**
 * This class models a classic cell.
 */
public class ClassicCell extends AbstractCell {

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
        super.updateComponents(source, sender, events, pieces, cells);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAccept(final Piece piece) {
        return super.isFree();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "ClassicCell";
    }
}
