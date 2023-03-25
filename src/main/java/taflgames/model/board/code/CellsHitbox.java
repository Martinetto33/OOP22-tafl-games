package taflgames.model.board.code;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.pieces.code.BasicPiece;

/**
 * This class is in charge of informing an {@link taflgames.model.board.api.Eaten}
 * component of a {@link taflgames.model.board.code.BoardImpl} what and where are
 * the hitboxes of the cells that can participate in a capture, namely
 * {@link taflgames.model.cell.code.Throne} and
 * {@link taflgames.model.cell.code.Exit}.
 */
public class CellsHitbox {
    
    private final Set<Position> throneAndExitsPositions;

    public CellsHitbox(final BoardImpl inputBoard) {
        Set<Position> relevantPositions = new HashSet<>();
        inputBoard.getMapCells().entrySet().stream()
            .filter(entry -> entry.getValue().getType().equals("Throne") || entry.getValue().getType().equals("Exit"))
            .forEach(entry -> relevantPositions.add(entry.getKey()));
        this.throneAndExitsPositions = Collections.unmodifiableSet(relevantPositions);
    }

    /**
     * This method returns a Set of {@link taflgames.model.pieces.api.Piece}
     * for compatibility reasons only.
     * @return a Set of Pieces with the exact hitbox of the Exits and the Throne.
     */
    public Set<Piece> getCellsAsPiecesWithHitbox(Player playerInTurn) {
        return this.throneAndExitsPositions.stream()
                .map(pos -> (Piece) new BasicPiece(pos, playerInTurn))
                .collect(Collectors.toUnmodifiableSet());
    }

}
