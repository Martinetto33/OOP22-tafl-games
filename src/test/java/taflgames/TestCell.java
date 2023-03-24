package taflgames;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import taflgames.model.pieces.api.Piece;
import taflgames.model.pieces.code.BasicPiece;
import taflgames.model.pieces.code.King;
import taflgames.model.pieces.code.Queen;
import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.board.code.BoardImpl;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.code.AbstractCell;
import taflgames.model.cell.code.ClassicCell;
import taflgames.model.cell.code.Exit;
import taflgames.model.cell.code.Slider;
import taflgames.model.cell.code.Throne;
import taflgames.model.cell.code.Tomb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

public class TestCell {

    private static AbstractCell classic;
    private static AbstractCell exit;
    private static AbstractCell slider;
    private static AbstractCell throne;
    private static AbstractCell tomb;

    @BeforeAll
    static void init() {
		classic = new ClassicCell();
        exit = new Exit();
        slider = new Slider(new Position(1, 1));
        throne = new Throne();
        tomb = new Tomb();
	}
    
    @Test
    void testCanAccept() {
        Piece piece = new BasicPiece(new Position(0, 0), Player.ATTACKER);
        /*testing only ClassicCell and Throne beacuse Tomb and Slider method canAccept 
        behave like the one of ClassicCell, while Throne and Exit method canAccept behave in the same way*/
        /*expected false because when initialized the cell is set to not free */
        assertFalse(classic.canAccept(piece));
        classic.setFree(true);
        assertTrue(classic.canAccept(piece));
        Piece king = new King(new Position(2, 2));
        throne.setFree(true);
        assertTrue(throne.canAccept(king));
    } 

    
    @Test
    void testsetFree() {
        classic.setFree(true);
        assertTrue(classic.isFree());
        classic.setFree(false);
        assertFalse(classic.isFree());
        
    }

    @Test
    void testgetType() {
        assertEquals(classic.getType(), "ClassicCell");
        assertEquals(exit.getType(), "Exit");
        assertEquals(slider.getType(), "Slider");
        assertEquals(throne.getType(), "Throne");
        assertEquals(tomb.getType(), "Tomb");
    }
    
    @Test
    void testisFree() {
        assertFalse(classic.isFree());
    }

    @Test
    void testNotifyTomb() {
        Board board;
        Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        Map<Position, Cell> cells = new HashMap<>();
        Player p1 = Player.ATTACKER;
        Player p2 = Player.DEFENDER;
        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(2, 1), new Queen(new Position(2, 1), p1));
        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                if(i == 2 && j == 2) {
                    cells.put(new Position(i,j), new Tomb());
                } else {
                    cells.put(new Position(i,j), new ClassicCell());
                }
                cells.get(new Position(i,j)).setFree(true);
            }
        } 
        cells.get(new Position(2,1)).setFree(false);
        board = new BoardImpl(pieces, cells, 5);
        
        tomb.notify(new Position(2, 2), new BasicPiece(new Position(2, 2), p1), List.of("DEAD_PIECE"), pieces, cells);
        tomb.notify(new Position(2, 1), new Queen(new Position(2, 1), p1), List.of("QUEEN_MOVE"), pieces, cells);
        assertFalse(cells.get(new Position(2,2)).isFree());
        assertTrue(pieces.get(p1).keySet().contains(new Position(2,2 )));
        
    }

    @Test 
    void testNotifySlider() {
        
    }
    
    
    
}
