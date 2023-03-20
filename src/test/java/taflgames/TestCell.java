package taflgames;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import taflgames.model.board.code.Piece;
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

    private AbstractCell classic;
    private AbstractCell exit;
    private AbstractCell slider;
    private AbstractCell throne;
    private AbstractCell tomb;

    @BeforeAll
    public void init() {
		this.classic = new ClassicCell();
        this.exit = new Exit();
        this.slider = new Slider();
        this.throne = new Throne();
        this.tomb = new Tomb();
	}
    
    @Test
    void testCanAccept() {
        //assertFalse(classic.canAccept(piece));
    } 

    
    @Test
    void testsetFree() {
        classic.setFree(true);
        assertTrue(classic.isFree());
        
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
    
    
    
}
