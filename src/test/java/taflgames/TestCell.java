package taflgames;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import taflgames.model.pieces.api.Piece;
import taflgames.model.pieces.code.AbstractPiece;
import taflgames.model.pieces.code.BasicPiece;
import taflgames.model.pieces.code.King;
import taflgames.common.Player;
import taflgames.common.code.Position;
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
    void testNotify() {
        
    }
    
    
    
}
