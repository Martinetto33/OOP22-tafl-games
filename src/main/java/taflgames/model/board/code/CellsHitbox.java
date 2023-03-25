package taflgames.model.board.code;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is in charge of informing an {@link taflgames.model.board.api.Eaten}
 * component of a {@link taflgames.model.board.code.BoardImpl} what and where are
 * the hitboxes of the cells that can participate in a capture, namely
 * {@link taflgames.model.cell.code.Throne} and
 * {@link taflgames.model.cell.code.Exit}.
 */
public class CellsHitbox {
    
    private final BoardImpl board;
    private final Set<Piece> piecePlaceholdersOfCellsWithHitbox;

    public CellsHitbox(final BoardImpl board) {
        this.board = board;
        /* The following Set contains pieces for compatibility reasons only. */
        Set<Piece> hitboxGenerators = new HashSet<>();
        this.piecePlaceholdersOfCellsWithHitbox = Collections.unmodifiableSet(hitboxGenerators);
    }

    
}
