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
import taflgames.model.pieces.code.Swapper;
import taflgames.common.Player;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.code.ClassicCell;
import taflgames.model.cell.code.Exit;
import taflgames.model.cell.code.Throne;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import taflgames.model.board.api.Eaten;

public class TestEaten {
    private static Board boardToCheckEaten;
    private static Eaten eat;
    private static Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
    private static Map<Position, Cell> cells = new HashMap<>();
    private static Player p1 = Player.ATTACKER;
    private static Player p2 = Player.DEFENDER;

    @BeforeAll
	static void init() {
        final Map<Position, Piece> piecesPlayer1 = new HashMap<>();
        final Map<Position, Piece> piecesPlayer2 = new HashMap<>();
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
		boardToCheckEaten = new BoardImpl(pieces, cells, 5);
        eat = new EatenImpl((BoardImpl) boardToCheckEaten);
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

        final Eaten eat;
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
        eat = new EatenImpl(secondBoard);

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
        final Eaten eat;
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
        eat = new EatenImpl(thirdBoard);

        Set<Position> hitbox = eat.trimHitbox(new BasicPiece(new Position(1, 1), p1), pieces, cells, 5);
        List<Piece> enemies = eat.getThreatenedPos(hitbox, pieces, new BasicPiece(new Position(1, 1), p1));
        Map<Piece, Set<Piece>> finalmap = new HashMap<>();
        Set<Piece> allies = new HashSet<>();
        allies.add(new Archer(new Position(4, 1), p1));
        allies.add(new BasicPiece(new Position(1, 1), p1));
        finalmap.put(new BasicPiece(new Position(2, 1), p2), allies);

        allies = new HashSet<>();
        allies.add(new BasicPiece(new Position(1, 1), p1));
        finalmap.put(new BasicPiece(new Position(1, 2), p2), allies);

        assertEquals(finalmap, eat.checkAllies(enemies, pieces, new BasicPiece(new Position(1, 1), p1)));

        hitbox = eat.trimHitbox(new BasicPiece(new Position(1, 2), p2), pieces, cells, 5);
        enemies = eat.getThreatenedPos(hitbox, pieces, new BasicPiece(new Position(1, 2), p2));
        allies = new HashSet<>();
        allies.add(new BasicPiece(new Position(1, 2), p2));
        finalmap = new HashMap<>();
        finalmap.put(new BasicPiece(new Position(1, 1), p1), allies);

        assertEquals(finalmap, eat.checkAllies(enemies, pieces, new BasicPiece(new Position(1, 2), p2)));
        
    }

    @Test
    void testNotifyAllThreatened() {
        final Board fourthBoard;
        final Eaten eat;
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
        eat = new EatenImpl(fourthBoard);

        Set<Position> hitbox = eat.trimHitbox(new BasicPiece(new Position(1, 1), p1), pieces, cells, 5);
        List<Piece> enemies = eat.getThreatenedPos(hitbox, pieces, new BasicPiece(new Position(1, 0), p1));
        Map<Piece, Set<Piece>> finalmap =  eat.checkAllies(enemies, pieces, new BasicPiece(new Position(1, 1), p1));
        eat.notifyAllThreatened(finalmap, new BasicPiece(new Position(1, 1), p1), cells, pieces);
        assertTrue(cells.get(new Position(2,1)).isFree());
        assertTrue(cells.get(new Position(1,2)).isFree());
        assertFalse(pieces.get(p2).containsKey(new Position(2,1)));
        assertFalse(pieces.get(p2).containsKey(new Position(1,2)));

    }

    /**
     * Tests if the hitbox of the cells is considered when checking if pieces were eaten.
     */
    @Test
    void testEatenWithCellsHitbox() {
        final Board fifthBoard;
        final Map<Player, Map<Position, Piece>> pieces = new HashMap<>();
        final Map<Position, Cell> cells = new HashMap<>();
        final Player p1 = Player.ATTACKER;
        final Player p2 = Player.DEFENDER;
        final Position thronePos = new Position(2, 2);
        final Position exitPos = new Position(4, 4);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cells.put(new Position(i, j), new ClassicCell());
            }
        }

        cells.values().forEach(e -> e.setFree(true));
        /* A Throne and an Exit */
        cells.put(thronePos, new Throne());
        cells.put(exitPos, new Exit());

        /*
         * Here follows a representation of the situation described by the test
         * (supposing that the attacker piece ATK was the last moved piece).
         *
         *      4 |_|_|_ATK__|DEF|Exit|
         *      3 |_|_|_DEF__|___|____|
         *      2 |_|_|Throne|___|____|
         *      1 |_|_|______|___|____| 
         *      0 |_|_|______|___|____|
         *         0 1    2    3    4
         */
        final Map<Position, Piece> attackerPieces = new HashMap<>();
        final Position attackerStartingPosition = new Position(1, 4);
        final Position attackerEndingPosition = new Position(2, 4);
        attackerPieces.put(attackerStartingPosition, new Swapper(attackerStartingPosition, p1));

        final Map<Position, Piece> defenderPieces = new HashMap<>();
        final Position defender1Pos = new Position(3, 4);
        final Position defender2Pos = new Position(2, 3);
        defenderPieces.put(defender1Pos, new BasicPiece(defender1Pos, p2));
        defenderPieces.put(defender2Pos, new BasicPiece(defender2Pos, p2));

        pieces.put(Player.ATTACKER, attackerPieces);
        pieces.put(Player.DEFENDER, defenderPieces);

        cells.get(attackerStartingPosition).setFree(false);
        cells.get(defender1Pos).setFree(false);
        cells.get(defender2Pos).setFree(false);

        fifthBoard = new BoardImpl(pieces, cells, 5);
        fifthBoard.updatePiecePos(attackerStartingPosition, attackerEndingPosition, Player.ATTACKER);
        fifthBoard.eat();

        assertTrue(cells.get(defender1Pos).isFree());
        assertTrue(cells.get(defender2Pos).isFree());
        assertFalse(cells.get(attackerEndingPosition).isFree());

    }
    
}
