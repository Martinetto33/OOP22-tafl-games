package taflgames;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.model.board.api.Board;
import taflgames.model.board.code.BoardImpl;
import taflgames.model.pieces.api.Piece;
import taflgames.model.pieces.code.BasicPiece;
import taflgames.model.pieces.code.King;
import taflgames.model.pieces.code.Swapper;
import taflgames.common.Player;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.code.ClassicCell;
import taflgames.model.cell.code.Throne;

public class TestBoard {

    private static Board board;
    private static Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
    private static Map<Position, Cell> cells = new HashMap<>();
    private static Player p1 = Player.ATTACKER;
    private static Player p2 = Player.DEFENDER;
    
    @BeforeAll
	static void init() {
        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(0, 0), new BasicPiece(new Position(0, 0), p1));
        piecesPlayer2.put(new Position(3, 3), new BasicPiece(new Position(3, 3), p2));
        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                cells.put(new Position(i,j), new ClassicCell());
                cells.get(new Position(i,j)).setFree(true);
            }
        } 
        cells.get(new Position(0,0)).setFree(false);
        cells.get(new Position(3,3)).setFree(false);
		board = new BoardImpl(pieces, cells, 5);
	}
    
    @Test
    void testIsStartingPointValid() {
        assertTrue(board.isStartingPointValid(new Position(3, 3), p2));
        assertTrue(board.isStartingPointValid(new Position(0, 0), p1)); 
        assertFalse(board.isStartingPointValid(new Position(3, 3), p1));
        assertFalse(board.isStartingPointValid(new Position(2, 2), p1));
    }

    @Test
    void testIsDestinationValid() {
        Board board2;
        Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        Map<Position, Cell> cells = new HashMap<>();
        Player p1 = Player.ATTACKER;
        Player p2 = Player.DEFENDER;
        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(3, 4), new BasicPiece(new Position(3, 4), p1));
        piecesPlayer1.put(new Position(0, 0), new BasicPiece(new Position(0, 0), p1));
        piecesPlayer1.put(new Position(1, 2), new Swapper(new Position(1, 2), p1));

        piecesPlayer2.put(new Position(3, 1), new BasicPiece(new Position(3, 1), p2));
        piecesPlayer2.put(new Position(3, 3), new Swapper(new Position(3, 3), p2));
        piecesPlayer2.put(new Position(4, 2), new King(new Position(4, 2)));

        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                if(i == 3 && j == 0) {
                    cells.put(new Position(i,j), new Throne());
                } else {
                    cells.put(new Position(i,j), new ClassicCell());
                }
                cells.get(new Position(i,j)).setFree(true);
            }
        } 
        cells.get(new Position(3,4)).setFree(false);
        cells.get(new Position(3,3)).setFree(false);
        cells.get(new Position(0,0)).setFree(false);
        cells.get(new Position(3,1)).setFree(false);
        cells.get(new Position(1,2)).setFree(false);
        cells.get(new Position(4,2)).setFree(false);

		board2 = new BoardImpl(pieces, cells, 5);

        assertFalse(board2.isDestinationValid(new Position(0,0), new Position(3,0), p1));
        assertTrue(board2.isDestinationValid(new Position(0,0), new Position(0,3), p1));
        assertFalse(board2.isDestinationValid(new Position(0,0), new Position(2,2), p1));
        assertTrue(board2.isDestinationValid(new Position(3,4), new Position(0,4), p1));

        assertTrue(board2.isDestinationValid(new Position(3,3), new Position(3,4), p2));
        assertTrue(board2.isDestinationValid(new Position(3,3), new Position(0,3), p2));
        assertFalse(board2.isDestinationValid(new Position(3,3), new Position(2,4), p2));
        assertFalse(board2.isDestinationValid(new Position(3,3), new Position(3,0), p2));
        assertFalse(board2.isDestinationValid(new Position(3,3), new Position(3,1), p2));

        assertFalse(board2.isDestinationValid(new Position(1,2), new Position(4,2), p1));
    }

    @Test
    void testUpdatePiecePos() {
        cells.get(new Position(0, 0)).setFree(false);
        cells.get(new Position(1, 1)).setFree(true);
        //test the position update of a normal piece
        board.updatePiecePos(new Position(0, 0), new Position(1, 1));
        assertTrue(cells.get(new Position(0, 0)).isFree());
        assertFalse(cells.get(new Position(1, 1)).isFree());
        assertTrue(pieces.get(p1).keySet().contains(new Position(1, 1)));
        assertFalse(pieces.get(p1).keySet().contains(new Position(0, 0)));

        
        //test the position update of a swapper
        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        piecesPlayer1.put(new Position(0, 3), new Swapper(new Position(0, 3), p1));
        pieces.put(p1, piecesPlayer1);
        cells.get(new Position(0, 3)).setFree(false);
        cells.get(new Position(3, 3)).setFree(false);
        board.updatePiecePos(new Position(0, 3), new Position(3, 3));
        assertTrue(pieces.get(p1).keySet().contains(new Position(3, 3)));
        assertTrue(pieces.get(p2).keySet().contains(new Position(0, 3)));
        assertFalse(pieces.get(p2).keySet().contains(new Position(3, 3)));
        assertFalse(pieces.get(p1).keySet().contains(new Position(0, 3)));
    }

    @Test
    void testgetFurthestReachablePos() {
        Board board1;
        Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        Map<Position, Cell> cells = new HashMap<>();
        Player p1 = Player.ATTACKER;
        Player p2 = Player.DEFENDER;
        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(3, 4), new BasicPiece(new Position(3, 4), p1));
        piecesPlayer1.put(new Position(0, 0), new BasicPiece(new Position(0, 0), p1));
        piecesPlayer2.put(new Position(3, 3), new King(new Position(3, 3)));
        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                if(i == 3 && j == 0) {
                    cells.put(new Position(i,j), new Throne());
                } else {
                    cells.put(new Position(i,j), new ClassicCell());
                }
                cells.get(new Position(i,j)).setFree(true);
            }
        } 
        cells.get(new Position(3,4)).setFree(false);
        cells.get(new Position(3,3)).setFree(false);
        cells.get(new Position(0,0)).setFree(false);
		board1 = new BoardImpl(pieces, cells, 5);

        assertEquals(new Position(3, 3), board1.getFurthestReachablePos(new Position(3, 3), new VectorImpl(0, 1))); 
        assertEquals(new Position(3, 0), board1.getFurthestReachablePos(new Position(3, 3), new VectorImpl(0, -1))); 
        assertEquals(new Position(4, 3), board1.getFurthestReachablePos(new Position(3, 3), new VectorImpl(1, 0)));
        assertEquals(new Position(0, 3), board1.getFurthestReachablePos(new Position(3, 3), new VectorImpl(-1, 0)));  
        assertEquals(new Position(2, 0), board1.getFurthestReachablePos(new Position(0, 0), new VectorImpl(1, 0))); 
    }
    
}
