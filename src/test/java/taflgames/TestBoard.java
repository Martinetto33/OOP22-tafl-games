package taflgames;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.time.Period;
import java.util.*;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.model.board.api.Board;
import taflgames.model.board.code.BoardImpl;
import taflgames.model.board.code.Piece;
import taflgames.model.board.code.Player;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.code.ClassicCell;

public class TestBoard {

    private static Board board;
    private static Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
    private static Map<Position, Cell> cells = new HashMap<>();
    

    @BeforeAll
	static void init() {
        Player p1;
        Player p2;
        //pieces.put(p1, null);
        //pieces.put(p2, null);
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                cells.put(new Position(i,j), new ClassicCell());
            }
        }
		board = new BoardImpl(pieces, cells, 5);
	}
    
    @Test
    void testIsStartingPointValid(){

        
    }
}
