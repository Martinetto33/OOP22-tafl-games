package taflgames;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.common.Player;
import taflgames.common.code.MatchResult;
import taflgames.common.code.Position;
import taflgames.controller.SettingsLoader;
import taflgames.controller.SettingsLoaderImpl;
import taflgames.model.board.code.BoardImpl;
import taflgames.model.Model;
import taflgames.model.Match;
import taflgames.model.builders.CellsCollectionBuilder;
import taflgames.model.builders.CellsCollectionBuilderImpl;
import taflgames.model.builders.PiecesCollectionBuilder;
import taflgames.model.builders.PiecesCollectionBuilderImpl;

/**
 * JUnit tests for {@link Model}.
 */
class TestMatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMatch.class);

    private Model match;

    /**
     * Initializes each test before its execution.
     */
    @BeforeEach
    void init() {
        final SettingsLoader loader = new SettingsLoaderImpl();
        final CellsCollectionBuilder cellsCollBuilder = new CellsCollectionBuilderImpl();
        final PiecesCollectionBuilder piecesCollBuilder = new PiecesCollectionBuilderImpl();
        try {
            loader.loadVariantModeConfig(cellsCollBuilder, piecesCollBuilder);
            final var pieces = piecesCollBuilder.build();
            final var cells = cellsCollBuilder.build();
            final int size = (int) Math.sqrt(cells.size());
            this.match = new Match(
                new BoardImpl(pieces, cells, size)
            );
        } catch (final IOException ex) {
            LOGGER.error("Cannot read configuration file. {}", ex.getMessage());
            fail();
        }
    }

    /**
     * Test the player turn queue.
     */
    @Test
    void testQueue() {
        final int turns = 4;
        final List<Player> resultingQueue = new LinkedList<>();
        for (int i = 0; i < turns; i++) {
            resultingQueue.add(match.getActivePlayer());
            match.setNextActivePlayer();
        }
        assertEquals(
            List.of(Player.ATTACKER, Player.DEFENDER, Player.ATTACKER, Player.DEFENDER), 
            resultingQueue
        );
    }

    // CHECKSTYLE: MagicNumber OFF
    // MagicNumber rule disabled because the numbers in the following code represent coordinates

    /**
     * Test the selection of the piece to move.
     */
    @Test
    void testPieceSelection() {
        /*
         * Case: the current player does a valid selection.
         * For example, the attacker must be able to select piece initially at position (row=3, col=0).
         */
        Position source = new Position(3, 0);
        assertTrue(match.selectSource(source));
        /*
         * Case: the player does an invalid selection because the chosen piece that does not
         * belong to its team. For example, the attacker cannot chose the piece initially at position
         * (row=3, col=5).
         */
        source = new Position(3, 5);
        assertFalse(match.selectSource(source));
        /*
         * Case: the player does an invalid selection as the chosen cell does not contain a piece.
         * For example, the player cannot choose position (row=1, col=1) for the first move.
         */
        source = new Position(1, 1);
        assertFalse(match.selectSource(source));

        /*
         * Perform the same tests for the defender.
         */
        match.setNextActivePlayer();
        /*
         * For example, the defender can choose the piece initially at position (row=5, col=3).
         */
        source = new Position(5, 3);
        assertTrue(match.selectSource(source));
        /*
         * For example, the defender cannot choose the piece initially at position (row=1, col=5)
         */
        source = new Position(1, 5);
        assertFalse(match.selectSource(source));
        /*
         * For example, the defender cannot choose the initially empty cell at position (row=1, col=1).
         */
        source = new Position(1, 1);
        assertFalse(match.selectSource(source));
    }

    /**
     * Test the selection of the destination cell of the move.
     */
    @Test
    void testDestinationSelection() {
        /*
         * Case: the selected destination is valid because the selected destination cell
         * is empty (assuming that the selected piece is a classic piece).
         * For example, the piece at position (row=3, col=0) can move to position (row=2, col=0).
         */
        Position source = new Position(3, 0);
        Position dest = new Position(2, 0);
        assertTrue(match.selectDestination(source, dest));
        /*
         * Case: the selected destination is invalid because the selected destination cell 
         * is not empty and the selected piece is not a swapper.
         * For example, basic piece at position (row=3, col=0) cannot move to position (row=3, col=5)
         */
        source = new Position(3, 0);
        dest = new Position(3, 5);
        assertFalse(match.selectDestination(source, dest));
        /*
         * Case: the selected destination is invalid because there is a piece in the middle 
         * of the path to the destination and the moved piece is not an opponent's swapper.
         * For example, piece at (row=0, col=4) cannot move to (row=0, col=1).
         */
        source = new Position(0, 4);
        dest = new Position(0, 1);
        assertFalse(match.selectDestination(source, dest));
        /*
         * Case: the selected destination is invalid because going from source to destination
         * would require a move that is not vertical nor horizontal.
         * For example, piece at (row=3, col=0) cannot move to (row=2, col=1).
         */
        source = new Position(3, 0);
        dest = new Position(2, 1);
        assertFalse(match.selectDestination(source, dest));
        /*
         * Case: the selected destination is invalid because the selected destination is an Exit.
         * For example, piece at position (row=3, col=0) cannot move to position (row=0, col=0).
         */
        source = new Position(3, 0);
        dest = new Position(0, 0);
        assertFalse(match.selectDestination(source, dest));
    }

    /**
     * Test the movement of a piece on the board.
     */
    @Test
    void testClassicMove() {
        /*
         * Case: move of a piece which is not a swapper.
         */
        // The attacker moves the piece at (row=3, col=0) to position (row=3, col=4)
        final Position source = new Position(3, 0);
        final Position dest = new Position(3, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        // Now the position (row=3, col=4) should be a valid source
        assertTrue(match.selectSource(dest));
    }

    /**
     * Test the classic killing of a piece, which is performed by
     * placing two pieces at two opposite adjacent sides of
     * an opponent's piece (note: the target piece of a classic killing
     * cannot be the king).
     */
    @Test
    void testClassicKilling() {
        // Attacker moves piece at (row=3, col=0) to (row=3, col=4)
        Position source = new Position(3, 0);
        Position dest = new Position(3, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        /*
         * Now the defender moves piece at (row=5, col=3) to (row=3, col=3);
         * the attacker's piece at (row=3, col=4) should be killed,
         * because there is another defender's piece at (row=3, col=5).
         */
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(5, 3);
        dest = new Position(3, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        /*
         * The piece at (row=3, col=4) should have been killed and then
         * it should not be selectable.
         */
        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());
        source = new Position(3, 4);
        assertFalse(match.selectSource(source));
        /*
         * The defender should also be able to move a piece to the position where the attacker's piece
         * was killed, at (row=3, col=4).
         */
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(3, 3);
        dest = new Position(3, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));
    }

    /**
     * Test the effect of the archer.
     * To take part to the killing of an opponent's piece, differently from the other pieces,
     * an archer does not have to be on an adjacent cell of the target piece:
     * it can be positioned within three cells far from the target piece
     * on the same row or column of the target piece, but the path from the archer
     * to the opponent's piece has to be free.
     */
    @Test
    void testArcherEffect() {

        /*
         * Case: the archer moves to a position in the same column of the target piece
         * and the path from the archer to the target piece is free.
         */

        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move defender's piece (basic) from (3, 5) to (3, 1)
        Position source = new Position(3, 5);
        Position dest = new Position(3, 1);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move attacker's piece (basic) from (0, 3) to (0, 1)
        source = new Position(0, 3);
        dest = new Position(0, 1);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move attacker's piece (basic) from (0, 1) to (2, 1)
        source = new Position(0, 1);
        dest = new Position(2, 1);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move attacker's piece (basic) from (5, 1) to (5, 2)
        source = new Position(5, 1);
        dest = new Position(5, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move attacker's archer from (5, 0) to (5, 1)
        source = new Position(5, 0);
        dest = new Position(5, 1);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Now the piece at (3, 1) should have been killed; check it is no more selectable
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(3, 1);
        assertFalse(match.selectSource(source));

        /*
         * Case: the archer moves to a position in the same row of the target piece
         * and the path from the archer to the target piece is free.
         */

        // Move defender's piece (basic) from (7, 5) to (7, 4)
        source = new Position(7, 5);
        dest = new Position(7, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move attacker's piece (basic) from (7, 10) to (7, 5)
        source = new Position(7, 10);
        dest = new Position(7, 5);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move attacker's archer from (5, 1) to (7, 1)
        source = new Position(5, 1);
        dest = new Position(7, 1);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Now the defender's piece at (7, 5) should have been killed; check it is no more selectable
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(7, 5);
        assertFalse(match.selectSource(source));

        /*
         * Case: the archer moves to a position in the same row or column of the target piece,
         * but the path from the archer to the target piece is not free, so the archer cannot take
         * part to the killing.
         */

        // Move defender's piece (basic) from (6, 4) to (9, 4)
        source = new Position(6, 4);
        dest = new Position(9, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move attacker's piece (basic) from (5, 2) to (9, 2)
        source = new Position(5, 2);
        dest = new Position(9, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move attacker's archer from (7, 1) to (9, 1)
        source = new Position(7, 1);
        dest = new Position(9, 1);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Check that there is an attacker's piece (shield) at (9, 5), according to the initial configuration
        source = new Position(9, 5);
        assertTrue(match.selectSource(source));

        // Now, there is a defender's piece at (9, 4), an attacker's piece at (9, 5) and
        // an attacker's archer at (9, 1), but there is another piece at (9, 2),
        // so the defender's piece must not get killed. Check that it is still selectable.
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(9, 4);
        assertTrue(match.selectSource(source));
    }

    /**
     * Test the effect of the queen and the tomb.
     * In the variant mode, when a piece is killed, a tomb spawns at the position
     * where the piece has been killed.
     * If a queen moves to a cell adjacent to a tomb and the piece killed there
     * belongs to the same team as the queen, then the queen brings the killed piece
     * back to life.
     */
    @Test
    void testQueenAndTombEffect() {

        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move defender's piece (basic) from (7, 5) to (7, 9)
        Position source = new Position(7, 5);
        Position dest = new Position(7, 9);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move attacker's piece (basic) from (0, 7) to (0, 8)
        source = new Position(0, 7);
        dest = new Position(0, 8);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move attacker's piece (basic) from (0, 8) to (7, 8)
        source = new Position(0, 8);
        dest = new Position(7, 8);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Now the piece at (7, 9) should have been killed, because it is between
        // two attacker's piece at (7, 8) and (7, 10). Also, a tomb should spawn at (7, 9).
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(7, 9);
        assertFalse(match.selectSource(source));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move attacker's piece (basic) from (7, 8) to (6, 8)
        source = new Position(7, 8);
        dest = new Position(6, 8);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move defender's queen from (6, 5) to (7, 5)
        source = new Position(6, 5);
        dest = new Position(7, 5);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move defender's queen from (7, 5) to (7, 8)
        source = new Position(7, 5);
        dest = new Position(7, 8);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Now the queen is at (7, 8), in a cell adjacent to the tomb at (7, 9),
        // so the defender's piece killed at (7, 9) should respawn.
        source = new Position(7, 9);
        assertTrue(match.selectSource(source));
    }

    /**
     * Test the effect of the shield.
     * The shield can survive to a killing once.
     */
    @Test
    void testShieldEffect() {

        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move defender's shield from (5, 3) to (5, 2)
        Position source = new Position(5, 3);
        Position dest = new Position(5, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move attacker's piece from (0, 3) to (5, 3)
        source = new Position(0, 3);
        dest = new Position(5, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Now the shield at (5, 2) is between two attacker's pieces at (5, 1) and (5, 3)
        // but it shouldn't have been killed since it survives to the first killing attempt.
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(5, 2);
        assertTrue(match.selectSource(source));

        // Move attacker's piece from (5, 3) to (4, 3)
        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());
        source = new Position(5, 3);
        dest = new Position(4, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move attacker's piece from (4, 3) back to (5, 3)
        source = new Position(4, 3);
        dest = new Position(5, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Now the shield should have been killed, since it survives to a killing only once
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(5, 2);
        assertFalse(match.selectSource(source));
    }

    /**
     * Test the effect of the swapper.
     * The swapper can not only move horizontally and vertically like all the other pieces;
     * it can also swap with an opponent's piece (apart from the king).
     */
    @Test
    void testSwapperEffect() {

        /*
         * Case: legal move of the swapper.
         */

        // Move piece at (9, 5) to be able to move the swapper at (10, 5)
        Position source = new Position(9, 5);
        Position dest = new Position(9, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);

        // Now the swapper should be allowed to move to (7,5), even if a defender's piece is present.
        source = new Position(10, 5);
        dest = new Position(7, 5);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);

        // Now the attacker should be able to select the swapper at (7, 5)
        assertTrue(match.selectSource(dest));

        // Now the defender should be able to select the swapped piece at (10, 5)
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(10, 5);
        assertTrue(match.selectSource(source));

        /*
         * Case: the swapper cannot swap position with the king.
         */

        // Move defender's piece from (6, 6) to (6, 7)
        source = new Position(6, 6);
        dest = new Position(6, 7);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // The piece at (6, 5) has been killed because it is between an attacker's piece
        // and the throne (which acts like a sort of wall).
        source = new Position(6, 5);
        assertFalse(match.selectSource(source));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // The swapper at (7, 5) must not allowed to move to the king in (5, 5)
        // also because the cell at (5, 5) is the throne, which can be accessed only by the king.
        source = new Position(7, 5);
        dest = new Position(5, 5);
        assertTrue(match.selectSource(source));
        assertFalse(match.selectDestination(source, dest));

        // The swapper must not be able to swap with the king even if the king moves out of the throne

        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move king from (5, 5) to (6, 5)
        source = new Position(5, 5);
        dest = new Position(6, 5);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // The swapper cannot swap with the king even if the king is at (6, 5)
        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());
        source = new Position(7, 5);
        dest = new Position(6, 5);
        assertTrue(match.selectSource(source));
        assertFalse(match.selectDestination(source, dest));
    }

    /**
     * Test the effect of the slider cell.
     * The slider makes the piece that lands on it slide to the furthest reachable position
     * in north/east/south/west direction.
     */
    @Test
    void testSliderEffect() {

        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move piece from (5, 1) to (5,2)
        Position source = new Position(5, 1);
        Position dest = new Position(5, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece from (5, 2) to (2, 2); at that position, there is a slider
        source = new Position(5, 2);
        dest = new Position(2, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);

        // The default direction for slider effect is east, so the piece is moved to (2, 10)
        source = new Position(2, 10);
        assertTrue(match.selectSource(source));

        // At the second turn of the match, the slider disappears, and it appears again two turns later.
        // The sliding direction should be north.
        match.setNextActivePlayer();
        match.setNextActivePlayer();
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move piece from (3, 5) to (3, 2)
        source = new Position(3, 5);
        dest = new Position(3, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);

        // Move piece from (3, 2) to (2, 2), on the slider
        source = new Position(3, 2);
        dest = new Position(2, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);

        // The sliding direction should be north now, so the piece should be moved to (0, 2)
        source = new Position(0, 2);
        assertTrue(match.selectSource(source));
    }

    /**
     * Test that the match ends and that the correct result is returned when the conditions for
     * the conditions for the attacker victory are verified.
     */
    @Test
    void testAttackerWin() {

        /*
         * Move the king in a vulnerable position. Before doing so, it is necessary to move
         * some other pieces to make the path free for the king.
         */

        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move piece (shield) from (5, 3) to (5, 2)
        Position source = new Position(5, 3);
        Position dest = new Position(5, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece (archer) from (5, 4) to (5, 3)
        source = new Position(5, 4);
        dest = new Position(5, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece (basic) from (4, 4) to (4, 3)
        source = new Position(4, 4);
        dest = new Position(4, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (5, 5) to (5, 4)
        source = new Position(5, 5);
        dest = new Position(5, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (5, 4) to (1, 4)
        source = new Position(5, 4);
        dest = new Position(1, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        /*
         * Now the king is at (row=1, col=4). It is already sorrounded by two attacker pieces at 
         * (row=0, col=4) and (row=1, col=5). In order to win, the attacker can place two pieces at 
         * (row=1, col=3) and (row=2, col=4).
         */

        // Check that the match is not over yet, since the king is sorrounded only at two sides
        assertTrue(match.getMatchEndStatus().isEmpty());

        // Check that king position is still selectable at (1, 4)
        source = new Position(1, 4);
        assertTrue(match.selectSource(source));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move piece (basic) from (3, 0) to (1, 0)
        source = new Position(3, 0);
        dest = new Position(1, 0);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece (basic) from (1, 0) to (1, 3)
        source = new Position(1, 0);
        dest = new Position(1, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Check that the match is not over yet, since the king is sorrounded only at three sides
        // (and not along the border; in that case, the match would end with draw)
        assertTrue(match.getMatchEndStatus().isEmpty());

        // Check that king position is still selectable at (1, 4)
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(1, 4);
        assertTrue(match.selectSource(source));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move piece (basic) from (3, 10) to (2, 10)
        source = new Position(3, 10);
        dest = new Position(2, 10);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece (basic) from (2, 10) to (2, 4)
        source = new Position(2, 10);
        dest = new Position(2, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Double check the presence of all the four attacker's pieces on the four sides of the king
        source = new Position(0, 4);
        assertTrue(match.selectSource(source));
        source = new Position(1, 3);
        assertTrue(match.selectSource(source));
        source = new Position(1, 5);
        assertTrue(match.selectSource(source));
        source = new Position(2, 4);
        assertTrue(match.selectSource(source));

        // Then the king should have been killed; check that the king is no more selectable
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(1, 4);
        assertFalse(match.selectSource(source));

        // Now the match must end with the victory of the attacker
        assertTrue(match.getMatchEndStatus().isPresent());
        // Check attacker victory
        assertEquals(
            MatchResult.VICTORY,
            match.getMatchEndStatus().get().getX()
        );
        // Check defender defeat
        assertEquals(
            MatchResult.DEFEAT,
            match.getMatchEndStatus().get().getY()
        );
    }

    /**
     * Test that the match ends and that the correct result is returned when the conditions for
     * the conditions for the defender victory are verified.
     */
    @Test
    void testDefenderWin() {

        /*
         * Move the king to the exit in the uppper-left corner of the board. Before doing so,
         * it is necessary to move some other pieces to make the path free for the king.
         */

        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move piece (shield) from (5, 3) to (5, 2)
        Position source = new Position(5, 3);
        Position dest = new Position(5, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece (archer) from (5, 4) to (5, 3)
        source = new Position(5, 4);
        dest = new Position(5, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece (basic) from (4, 4) to (4, 3)
        source = new Position(4, 4);
        dest = new Position(4, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (5, 5) to (5, 4)
        source = new Position(5, 5);
        dest = new Position(5, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (5, 4) to (1, 4)
        source = new Position(5, 4);
        dest = new Position(1, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (1, 4) to (1, 0)
        source = new Position(1, 4);
        dest = new Position(1, 0);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (1, 0) to the exit (0, 0)
        source = new Position(1, 0);
        dest = new Position(0, 0);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Now the match must end with the victory of the defender
        assertTrue(match.getMatchEndStatus().isPresent());
        // Check attacker defeat
        assertEquals(
            MatchResult.DEFEAT,
            match.getMatchEndStatus().get().getX()
        );
        // Check defender victory
        assertEquals(
            MatchResult.VICTORY,
            match.getMatchEndStatus().get().getY()
        );
    }

    /**
     * Test that the match ends and that the correct result is returned when the conditions for
     * the conditions for draw are verified.
     */
    @Test
    void testDraw() {

        /* 
         * The draw happens when the king is trapped on a border of the grid by 3 attackers on the three adjacent cells.
         * In this test, the king will be moved to (0, 2), which is a position along the border,
         * and then it will be trapped on the three adjacent sides. 
         */

        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());

        // Move piece (shield) from (5, 3) to (5, 2)
        Position source = new Position(5, 3);
        Position dest = new Position(5, 2);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece (archer) from (5, 4) to (5, 3)
        source = new Position(5, 4);
        dest = new Position(5, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move piece (basic) from (4, 4) to (4, 3)
        source = new Position(4, 4);
        dest = new Position(4, 3);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (5, 5) to (5, 4)
        source = new Position(5, 5);
        dest = new Position(5, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (5, 4) to (2, 4)
        source = new Position(5, 4);
        dest = new Position(2, 4);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Move king from (2, 4) to (2, 0)
        source = new Position(2, 4);
        dest = new Position(2, 0);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        /*
         * There is already an attacker's piece at (3, 0). Two other attacker's pieces
         * will be moved to (1, 0) and (2, 1).
         */

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move piece (shield) from (1, 5) to (1, 0)
        source = new Position(1, 5);
        dest = new Position(1, 0);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Check that the match is not over yet, since the king is sorrounded only at two sides
        assertTrue(match.getMatchEndStatus().isEmpty());

        // Check that king position is still selectable at (2, 0)
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(2, 0);
        assertTrue(match.selectSource(source));

        match.setNextActivePlayer();
        assertEquals(Player.ATTACKER, match.getActivePlayer());

        // Move piece (basic) from (3, 10) to (2, 10)
        source = new Position(3, 10);
        dest = new Position(2, 10);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Check that king position is still selectable at (2, 0)
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(2, 0);
        assertTrue(match.selectSource(source));
        match.setNextActivePlayer();

        // Move piece (basic) from (2, 10) to (2, 1)
        source = new Position(2, 10);
        dest = new Position(2, 1);
        assertTrue(match.selectSource(source));
        assertTrue(match.selectDestination(source, dest));
        match.makeMove(source, dest);
        assertTrue(match.selectSource(dest));

        // Double check the presence of all the three attacker's pieces on the three sides of the king
        source = new Position(1, 0);
        assertTrue(match.selectSource(source));
        source = new Position(2, 1);
        assertTrue(match.selectSource(source));
        source = new Position(3, 0);
        assertTrue(match.selectSource(source));

        // Check that king position is still selectable at (2, 0)
        match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, match.getActivePlayer());
        source = new Position(2, 0);
        assertTrue(match.selectSource(source));

        // Now the match must end with a draw
        assertTrue(match.getMatchEndStatus().isPresent());
        // Check attacker draw result
        assertEquals(
            MatchResult.DRAW,
            match.getMatchEndStatus().get().getX()
        );
        // Check defender draw result
        assertEquals(
            MatchResult.DRAW,
            match.getMatchEndStatus().get().getY()
        );
    }

    // CHECKSTYLE: MagicNumber ON

}
