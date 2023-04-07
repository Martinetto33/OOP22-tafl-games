package taflgames;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import taflgames.model.pieces.api.Piece;
import taflgames.model.pieces.code.BasicPiece;
import taflgames.model.pieces.code.King;
import taflgames.model.pieces.code.Queen;
import taflgames.model.pieces.code.Swapper;
import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.board.code.BoardImpl;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.code.AbstractCell;
import taflgames.model.cell.code.ClassicCell;
import taflgames.model.cell.code.Exit;
import taflgames.model.cell.code.SliderImpl;
import taflgames.model.cell.code.Throne;
import taflgames.model.cell.code.Tomb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JUnit tests for {@link Cell}.
 */
class TestCell {

    private static final int DEFAULT_BOARD_SIZE = 5;

    private static AbstractCell classic;
    private static AbstractCell exit;
    private static AbstractCell slider;
    private static AbstractCell throne;
    private static AbstractCell tomb;

    /**
     * Initializes a board before the first test.
     */
    @BeforeAll
    static void init() {
        classic = new ClassicCell();
        exit = new Exit();
        slider = new SliderImpl(new Position(1, 1));
        throne = new Throne();
        tomb = new Tomb();
    }

    /**
     * Test if a cell can accept a particular piece.
     */
    @Test
    void testCanAccept() {
        final Piece piece = new BasicPiece(new Position(0, 0), Player.ATTACKER);
        /*testing ClassicCell, Throne and Exit*/
        /*expected false because when initialized the cell is set to not free */
        classic = new ClassicCell();
        assertTrue(classic.canAccept(piece));
        classic.setFree(false);
        assertFalse(classic.canAccept(piece));
        final Piece king = new King(new Position(2, 2));
        throne = new Throne();
        throne.setFree(true);
        assertTrue(throne.canAccept(king));
        exit = new Exit();
        exit.setFree(true);
        assertTrue(exit.canAccept(king));
    } 

    /**
     * Test the setting of the fild isFree.
     */
    @Test
    void testsetFree() {
        /*setting a ClassicCell as free at first and then as occupied */
        classic.setFree(true);
        assertTrue(classic.isFree());
        classic.setFree(false);
        assertFalse(classic.isFree());
    }

    /**
     * Test the getter for the cell type.
     */
    @Test
    void testgetType() {
        assertEquals(classic.getType(), "ClassicCell");
        assertEquals(exit.getType(), "Exit");
        assertEquals(slider.getType(), "Slider");
        assertEquals(throne.getType(), "Throne");
        assertEquals(tomb.getType(), "Tomb");
    }

    /**
     * Test if a cell is free or not.
     */
    @Test
    void testisFree() {
        assertTrue(classic.isFree());
    }

    /**
     * Test the notification of a tomb that a queen is 
     * adjacent to it, then test the resurrections of the 
     * last piece dead on the tomb of the player in turn.
     */
    @Test
    void testNotifyTomb() {
        final Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        final Map<Position, Cell> cells = new HashMap<>();
        final Player p1 = Player.ATTACKER;
        final Player p2 = Player.DEFENDER;
        final Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        final Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(2, 1), new Queen(new Position(2, 1), p1));
        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);

        for (int i = 0; i < DEFAULT_BOARD_SIZE; i++) {
            for (int j = 0; j < DEFAULT_BOARD_SIZE; j++) {
                if (i == 2 && j == 2) {
                    cells.put(new Position(i, j), new Tomb());
                } else {
                    cells.put(new Position(i, j), new ClassicCell());
                }
                cells.get(new Position(i, j)).setFree(true);
            }
        } 
        cells.get(new Position(2, 1)).setFree(false);
        tomb.notify(new Position(2, 2), new BasicPiece(new Position(2, 2), p1), List.of("DEAD_PIECE"), pieces, cells);
        tomb.notify(new Position(2, 1), new Queen(new Position(2, 1), p1), List.of("QUEEN_MOVE"), pieces, cells);
        assertFalse(cells.get(new Position(2, 2)).isFree());
        assertTrue(pieces.get(p1).keySet().contains(new Position(2, 2)));
    }

    /**
     * Test that a slider is notified when a piece end up on it
     * and then test the piece shifting.
     */
    @Test 
    void testNotifySlider() {
        final Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        final Map<Position, Cell> cells = new HashMap<>();
        final Player p1 = Player.ATTACKER;
        final Player p2 = Player.DEFENDER;
        final Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        final Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(4, 1), new BasicPiece(new Position(4, 1), p1));
        piecesPlayer1.put(new Position(1, 1), new BasicPiece(new Position(1, 1), p1));
        piecesPlayer2.put(new Position(1, 4), new BasicPiece(new Position(1, 4), p2));
        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);

        for (int i = 0; i < DEFAULT_BOARD_SIZE; i++) {
            for (int j = 0; j < DEFAULT_BOARD_SIZE; j++) {
                if (i == 1 && j == 1) {
                    cells.put(new Position(i, j), new SliderImpl(new Position(1, 1)));
                } else {
                    cells.put(new Position(i, j), new ClassicCell());
                }
                cells.get(new Position(i, j)).setFree(true);
            }
        } 
        piecesPlayer1.entrySet().stream().forEach(piece -> cells.get(piece.getKey()).setFree(false));
        piecesPlayer2.entrySet().stream().forEach(piece -> cells.get(piece.getKey()).setFree(false));
        new BoardImpl(pieces, cells, DEFAULT_BOARD_SIZE);

        //Basic piece on a slider
        cells.get(new Position(1, 1)).notify(new Position(1, 1), new BasicPiece(new Position(1, 1), p1), null, pieces, cells);
        assertTrue(cells.get(new Position(1, 1)).isFree());
        assertFalse(cells.get(new Position(1, 3)).isFree());

        //Swapper on a slider
        cells.get(new Position(1, 1)).notify(new Position(1, 1), new Swapper(new Position(1, 1), p1), null, pieces, cells);
        assertTrue(cells.get(new Position(1, 1)).isFree());
        assertFalse(cells.get(new Position(1, 3)).isFree());
    }

    /**
     * Test the end of turn notification.
     */
    @Test 
    void testNotifyTurnHasEnded() {
        final Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        final Map<Position, Cell> cells = new HashMap<>();
        final Player p1 = Player.ATTACKER;
        final Player p2 = Player.DEFENDER;
        final Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        final Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(4, 1), new BasicPiece(new Position(4, 1), p1));
        piecesPlayer1.put(new Position(1, 1), new BasicPiece(new Position(1, 1), p1));
        piecesPlayer2.put(new Position(1, 4), new BasicPiece(new Position(1, 4), p2));
        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);

        for (int i = 0; i < DEFAULT_BOARD_SIZE; i++) {
            for (int j = 0; j < DEFAULT_BOARD_SIZE; j++) {
                if (i == 1 && j == 1) {
                    cells.put(new Position(i, j), new SliderImpl(new Position(1, 1)));
                } else {
                    cells.put(new Position(i, j), new ClassicCell());
                }
                cells.get(new Position(i, j)).setFree(true);
            }
        } 
        piecesPlayer1.entrySet().stream().forEach(piece -> cells.get(piece.getKey()).setFree(false));
        piecesPlayer2.entrySet().stream().forEach(piece -> cells.get(piece.getKey()).setFree(false));
        new BoardImpl(pieces, cells, DEFAULT_BOARD_SIZE);

        /*notify the slider that a pice ended up on it */
        cells.get(new Position(1, 1)).notify(new Position(1, 1), new BasicPiece(new Position(1, 1), p1), null, pieces, cells);
        assertTrue(cells.get(new Position(1, 1)).isFree());
        assertFalse(cells.get(new Position(1, 3)).isFree());
        assertFalse(pieces.get(p1).keySet().contains(new Position(1, 1)));
        assertTrue(pieces.get(p1).keySet().contains(new Position(1, 3)));
        final SliderImpl sl = (SliderImpl) cells.get(new Position(1, 1));
        /*notify the end of turn and reset the resettable enties of the game */
        sl.notifyTurnHasEnded(1);
        sl.reset();

        /*now the slider is disabled */
        piecesPlayer2.put(new Position(1, 1), new BasicPiece(new Position(1, 1), p2));
        pieces.put(p2, piecesPlayer2);
        cells.get(new Position(1, 1)).setFree(false);
        cells.get(new Position(1, 1)).notify(new Position(1, 1), new BasicPiece(new Position(1, 1), p2), null, pieces, cells);
        assertFalse(cells.get(new Position(1, 1)).isFree());
        /*notify the end of turn and reset the resettable enties of the game */
        sl.notifyTurnHasEnded(2);
        sl.reset();

        /*the slider is active */
        cells.get(new Position(1, 1)).notify(new Position(1, 1), new BasicPiece(new Position(1, 1), p2), null, pieces, cells);
        assertTrue(cells.get(new Position(1, 1)).isFree());
        assertTrue(cells.get(new Position(1, 0)).isFree());
        assertTrue(cells.get(new Position(3, 1)).isFree());
        assertFalse(cells.get(new Position(0, 1)).isFree());
    }
}
