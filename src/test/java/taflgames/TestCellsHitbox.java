package taflgames;

import org.junit.jupiter.api.Test;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.board.code.BoardImpl;
import taflgames.model.board.code.CellsHitbox;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.code.Exit;
import taflgames.model.cell.code.Throne;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TestCellsHitbox {

    @Test
    void testCellsHitbox() {
        final Map<Position, Cell> cells = new HashMap<>();
        
        cells.put(new Position(0, 0), new Throne());
        cells.put(new Position(1, 1), new Exit());
		final BoardImpl board = new BoardImpl(null, cells, 5);
        final CellsHitbox cellsHitbox = new CellsHitbox(board);

        final Set<Position> expectedHitbox = new HashSet<>();
        /* All the cells surrounding the Throne and the Exit should be included
         * in the hitbox; the CellsHitbox treat the cells as if they were BasicPieces,
         * so it will be enough to check if the hitboxes of the two pieces
         * contain the positions that represent the hitbox of these cells.
         */
        expectedHitbox.add(new Position(1, 0));
        expectedHitbox.add(new Position(0, 1));
        expectedHitbox.add(new Position(2, 1));
        expectedHitbox.add(new Position(1, 2));

        assertTrue(cellsHitbox.getCellsAsPiecesWithHitbox(Player.ATTACKER).stream()
                .flatMap(piece -> piece.whereToHit().stream())
                .collect(Collectors.toUnmodifiableSet()).containsAll(expectedHitbox));
    }
}
