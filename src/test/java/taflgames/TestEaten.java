package taflgames;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.board.code.BoardImpl;
import taflgames.model.board.code.EatenImpl;
import taflgames.model.pieces.api.Piece;
import taflgames.model.pieces.code.Archer;
import taflgames.model.pieces.code.BasicPiece;
import taflgames.model.pieces.code.King;
import taflgames.common.Player;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.code.ClassicCell;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import taflgames.model.board.api.Eaten;

public class TestEaten {
    private static Eaten eat = new EatenImpl();
    private static Board boardToCeckEaten;
    private static Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
    private static Map<Position, Cell> cells = new HashMap<>();
    private static Player p1 = Player.ATTACKER;
    private static Player p2 = Player.DEFENDER;

    @BeforeAll
	static void init() {
        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(0, 0), new BasicPiece(new Position(0, 0), p1));
        piecesPlayer1.put(new Position(1, 4), new Archer(new Position(1,4), p1));
        piecesPlayer2.put(new Position(3, 3), new BasicPiece(new Position(3, 3), p2));
        piecesPlayer2.put(new Position(3, 2), new Archer(new Position(3, 2), p2));
        piecesPlayer2.put(new Position(4, 0), new King(new Position(3, 2)));


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
        cells.get(new Position(1,4)).setFree(false);
        cells.get(new Position(3,2)).setFree(false);
        cells.get(new Position(4,0)).setFree(false);
		boardToCeckEaten = new BoardImpl(pieces, cells, 5);
	}

    @Test 
    void trimHitbox() {
        Set<Position> expectedHitbox = new HashSet<>();
        expectedHitbox.add(new Position(1, 0));
        expectedHitbox.add(new Position(0, 1));
        //pedina normale vicino al bordo
        assertEquals(expectedHitbox, eat.trimHitbox(new BasicPiece(new Position(0, 0), p1), pieces, cells, 5));

        expectedHitbox = new HashSet<>();
        expectedHitbox.add(new Position(3, 4));
        expectedHitbox.add(new Position(2, 3));
        expectedHitbox.add(new Position(4, 3));
        //pedina normale al centro con vicino una della stessa squadra
        assertEquals(expectedHitbox, eat.trimHitbox(new BasicPiece(new Position(3, 3), p2), pieces, cells, 5));

        expectedHitbox = new HashSet<>();

        expectedHitbox.add(new Position(3, 0));
        expectedHitbox.add(new Position(3, 1));
        expectedHitbox.add(new Position(0, 2));
        expectedHitbox.add(new Position(1, 2));
        expectedHitbox.add(new Position(2, 2));
        expectedHitbox.add(new Position(4, 2));
        //arciere al centro con vicino una della stessa squadra
        assertEquals(expectedHitbox, eat.trimHitbox(new Archer(new Position(3, 2), p2), pieces, cells, 5));

        expectedHitbox = new HashSet<>();
        expectedHitbox.add(new Position(0, 4));
        expectedHitbox.add(new Position(1, 3));
        expectedHitbox.add(new Position(1, 2));
        expectedHitbox.add(new Position(1, 1));
        expectedHitbox.add(new Position(2, 4));
        expectedHitbox.add(new Position(3, 4));
        expectedHitbox.add(new Position(4, 4));
        //arciere vicino al bordo
        assertEquals(expectedHitbox, eat.trimHitbox(new Archer(new Position(1,4), p1), pieces, cells, 5));
        
        //re
        expectedHitbox = new HashSet<>();
        assertEquals(expectedHitbox, eat.trimHitbox(new King(new Position(4,0)), pieces, cells, 5));

    }

    @Test
    void testGetThreatenedPos() {
        Set<Position> hitbox = eat.trimHitbox(new Archer(new Position(1,4), p1), pieces, cells, 5);
        List<Piece> enemies = new ArrayList<>();
        assertEquals(enemies, eat.getThreatenedPos(hitbox, pieces, new Archer(new Position(1,4), p1)));

        hitbox = eat.trimHitbox(new BasicPiece(new Position(3, 3), p2), pieces, cells, 5);
        enemies = new ArrayList<>();
        assertEquals(enemies, eat.getThreatenedPos(hitbox, pieces, new BasicPiece(new Position(3, 3), p2)));

        final Eaten eat = new EatenImpl();
        final Board secondBoard;
        final Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        final Map<Position, Cell> cells = new HashMap<>();
        final Player p1 = Player.ATTACKER;
        final Player p2 = Player.DEFENDER;

        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(1, 1), new BasicPiece(new Position(1, 1), p1));
        piecesPlayer1.put(new Position(4, 1), new Archer(new Position(4, 1), p1));
        piecesPlayer2.put(new Position(1, 0), new BasicPiece(new Position(1, 0), p2));
        piecesPlayer2.put(new Position(1, 2), new BasicPiece(new Position(1, 2), p2));
        piecesPlayer2.put(new Position(4, 4), new BasicPiece(new Position(4, 4), p2));
        piecesPlayer2.put(new Position(0, 1), new King(new Position(0, 1)));

        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                cells.put(new Position(i,j), new ClassicCell());
                cells.get(new Position(i,j)).setFree(true);
            }
        } 
        cells.get(new Position(1,1)).setFree(false);
        cells.get(new Position(4,1)).setFree(false);
        cells.get(new Position(1,0)).setFree(false);
        cells.get(new Position(1,2)).setFree(false);
        cells.get(new Position(4,4)).setFree(false);
        cells.get(new Position(0,1)).setFree(false);

		secondBoard = new BoardImpl(pieces, cells, 5);

        //classic piece circondato da due nemici uno sopra e uno sotto
        hitbox = eat.trimHitbox(new BasicPiece(new Position(1, 1), p1), pieces, cells, 5);
        enemies = new ArrayList<>();
        enemies.add(new BasicPiece(new Position(1, 0), p2));
        enemies.add(new BasicPiece(new Position(1, 2), p2));
        enemies.add(new King(new Position(0, 1)));
        assertEquals(enemies, eat.getThreatenedPos(hitbox, pieces, new BasicPiece(new Position(1, 1), p1)));

        //arciere che presenta un nemico nella sua hitbox
        hitbox = eat.trimHitbox(new Archer(new Position(4, 1), p1), pieces, cells, 5);
        enemies = new ArrayList<>();
        enemies.add(new BasicPiece(new Position(4, 4), p2));
        assertEquals(enemies, eat.getThreatenedPos(hitbox, pieces, new Archer(new Position(4, 1), p1)));

        //classic piece vicino al bordo e con un nemico nella hitbox
        hitbox = eat.trimHitbox(new BasicPiece(new Position(1, 0), p2), pieces, cells, 5);
        enemies = new ArrayList<>();
        enemies.add(new BasicPiece(new Position(1, 1), p1));
        assertEquals(enemies, eat.getThreatenedPos(hitbox, pieces, new BasicPiece(new Position(1, 0), p2)));

        //re con difianco un nemico
        hitbox = eat.trimHitbox(new King(new Position(0, 1)), pieces, cells, 5);
        enemies = new ArrayList<>();
        assertEquals(enemies, eat.getThreatenedPos(hitbox, pieces, new King(new Position(0, 1))));
    }

    @Test 
    void testCheckAllies() {
        final Eaten eat = new EatenImpl();
        final Board thirdBoard;
        final Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        final Map<Position, Cell> cells = new HashMap<>();
        final Player p1 = Player.ATTACKER;
        final Player p2 = Player.DEFENDER;

        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(1, 1), new BasicPiece(new Position(1, 1), p1));
        piecesPlayer1.put(new Position(4, 1), new Archer(new Position(4, 1), p1));
        piecesPlayer2.put(new Position(2, 1), new BasicPiece(new Position(2, 1), p2));
        piecesPlayer2.put(new Position(1, 2), new BasicPiece(new Position(1, 2), p2));


        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                cells.put(new Position(i,j), new ClassicCell());
                cells.get(new Position(i,j)).setFree(true);
            }
        } 
        cells.get(new Position(1,1)).setFree(false);
        cells.get(new Position(4,1)).setFree(false);
        cells.get(new Position(2,1)).setFree(false);
        cells.get(new Position(1,2)).setFree(false);

        
		thirdBoard = new BoardImpl(pieces, cells, 5);

        Set<Position> hitbox = eat.trimHitbox(new BasicPiece(new Position(1, 1), p1), pieces, cells, 5);
        List<Piece> enemies = eat.getThreatenedPos(hitbox, pieces, new BasicPiece(new Position(1, 0), p1));
        Map<Piece, Set<Piece>> finalmap = new HashMap<>();
        Set<Piece> allies = new HashSet<>();
        allies.add(new Archer(new Position(4, 1), p1));
        allies.add(new BasicPiece(new Position(1, 1), p1));
        finalmap.put(new BasicPiece(new Position(2, 1), p2), allies);

        allies = new HashSet<>();
        allies.add(new BasicPiece(new Position(1, 1), p1));
        finalmap.put(new BasicPiece(new Position(1, 2), p2), allies);

        assertEquals(finalmap, eat.checkAllies(enemies, pieces, p1));
        
    }

    @Test
    void testNotifyAllThreatened() {
        final Eaten eat = new EatenImpl();
        final Board fourthBoard;
        final Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        final Map<Position, Cell> cells = new HashMap<>();
        final Player p1 = Player.ATTACKER;
        final Player p2 = Player.DEFENDER;

        Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        Map<Position, Piece> piecesPlayer2 = new HashMap<>();
        piecesPlayer1.put(new Position(1, 1), new BasicPiece(new Position(1, 1), p1));
        piecesPlayer1.put(new Position(4, 1), new Archer(new Position(4, 1), p1));
        piecesPlayer1.put(new Position(1, 3), new BasicPiece(new Position(1, 3), p1));
        piecesPlayer2.put(new Position(2, 1), new BasicPiece(new Position(2, 1), p2));
        piecesPlayer2.put(new Position(1, 2), new BasicPiece(new Position(1, 2), p2));


        pieces.put(p1, piecesPlayer1);
        pieces.put(p2, piecesPlayer2);
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                cells.put(new Position(i,j), new ClassicCell());
                cells.get(new Position(i,j)).setFree(true);
            }
        } 
        cells.get(new Position(1,1)).setFree(false);
        cells.get(new Position(4,1)).setFree(false);
        cells.get(new Position(1,3)).setFree(false);
        cells.get(new Position(2,1)).setFree(false);
        cells.get(new Position(1,2)).setFree(false);

        
        fourthBoard = new BoardImpl(pieces, cells, 5);

        Set<Position> hitbox = eat.trimHitbox(new BasicPiece(new Position(1, 1), p1), pieces, cells, 5);
        List<Piece> enemies = eat.getThreatenedPos(hitbox, pieces, new BasicPiece(new Position(1, 0), p1));
        Map<Piece, Set<Piece>> finalmap =  eat.checkAllies(enemies, pieces, p1);
        eat.notifyAllThreatened(finalmap, new BasicPiece(new Position(1, 1), p1), cells, pieces);
        assertTrue(cells.get(new Position(2,1)).isFree());
        assertTrue(cells.get(new Position(1,2)).isFree());
        assertFalse(pieces.get(p2).containsKey(new Position(2,1)));
        assertFalse(pieces.get(p2).containsKey(new Position(1,2)));
    }
    
}
