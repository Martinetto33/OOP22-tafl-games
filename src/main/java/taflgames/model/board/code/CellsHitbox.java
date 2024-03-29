package taflgames.model.board.code;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.pieces.code.BasicPiece;
import taflgames.model.board.api.Board;
import taflgames.model.pieces.api.Piece;

/**
 * This class is in charge of informing an {@link taflgames.model.board.api.Eaten}
 * component of a {@link taflgames.model.board.code.BoardImpl} what and where are
 * the hitboxes of the cells that can participate in a capture, namely
 * {@link taflgames.model.cell.code.Throne} and
 * {@link taflgames.model.cell.code.Exit}. This class was created for compatibility
 * reasons only.
 */
public class CellsHitbox {

    private static final String THRONE = "Throne";
    private static final String EXIT = "Exit";

    private final Set<Position> throneAndExitsPositions;

    /**
     * Builds a new class able to return cells with hitboxes as if they were pieces.
     * @param inputBoard the {@link taflgames.model.board.code.BoardImpl} from which
     * to take the positions of special cells.
     */
    public CellsHitbox(final Board inputBoard) {
        final Set<Position> relevantPositions = new HashSet<>();
        inputBoard.getMapCells().entrySet().stream()
            .filter(entry -> THRONE.equals(entry.getValue().getType())
                    || EXIT.equals(entry.getValue().getType()))
            .forEach(entry -> relevantPositions.add(entry.getKey()));
        this.throneAndExitsPositions = Collections.unmodifiableSet(relevantPositions);
    }

    /**
     * This method returns a Set of {@link taflgames.model.pieces.api.Piece}
     * for compatibility reasons only.
     * @param playerInTurn the player in turn.
     * @return a Set of Pieces with the exact hitbox of the Exits and the Throne.
     */
    public Set<Piece> getCellsAsPiecesWithHitbox(final Player playerInTurn) {
        return this.throneAndExitsPositions.stream()
                .map(pos -> (Piece) new BasicPiece(pos, playerInTurn))
                .collect(Collectors.toUnmodifiableSet());
    }

}
