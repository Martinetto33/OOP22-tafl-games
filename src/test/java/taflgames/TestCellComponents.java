package taflgames;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.controller.SettingsLoader;
import taflgames.controller.SettingsLoaderImpl;
import taflgames.model.Model;
import taflgames.model.Match;
import taflgames.model.board.api.Board;
import taflgames.model.board.code.BoardImpl;
import taflgames.model.builders.CellsCollectionBuilder;
import taflgames.model.builders.CellsCollectionBuilderImpl;
import taflgames.model.builders.PiecesCollectionBuilder;
import taflgames.model.builders.PiecesCollectionBuilderImpl;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.CellComponent;
import taflgames.model.cell.code.ClassicCell;
import taflgames.model.cell.code.Tomb;
import taflgames.model.pieces.api.Piece;

/**
 * Tests the CellComponents, which in our case are only the Tombs.
 * It uses an instance of a Variant mode Match to see if the application
 * behaves naturally after calls to the Match object.
 */
class TestCellComponents {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMatch.class);

    private static final String TOMB = "Tomb";

    private Model match;
    private Board board;

    /**
     * Initializes each test before its execution.
     * {credits for this test to Elena Boschetti}
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
            this.board = new BoardImpl(pieces, cells, size);
            this.match = new Match(this.board);
        } catch (final IOException ex) {
            LOGGER.error("Cannot read configuration file. {}", ex.getMessage());
            fail();
        }
    }

    /**
     * Tests if the basic operations of attaching and detaching components work.
     */
    @Test
    void testAttachAndDetachComponent() {
        final Cell basicCell = new ClassicCell();
        final CellComponent tombComponent = new Tomb();
        basicCell.attachComponent(tombComponent);
        assertEquals(Set.of(tombComponent), basicCell.getComponents());
        assertTrue(basicCell.getComponents().stream()
                .map(c -> c.getComponentType())
                .anyMatch(type -> TOMB.equals(type)));
        basicCell.detachComponent(tombComponent);
        assertTrue(basicCell.getComponents().isEmpty());
    }

    /**
     * Tests if the CellComponents are automatically attached 
     * and detached in a real case scenario.
     */
    @Test
    void testTombCreationAsComponents() {
        final Position attacker1StartingPos = new Position(7, 0);
        final Position attacker1EndPos = new Position(7, 4);

        assertTrue(this.match.selectSource(attacker1StartingPos));
        assertTrue(this.match.selectDestination(attacker1StartingPos, attacker1EndPos));
        this.match.makeMove(attacker1StartingPos, attacker1EndPos);
        this.match.setNextActivePlayer();

        final Position defenderStartingPos = new Position(6, 6);
        final Position defenderEndPos = new Position(9, 6);

        assertTrue(this.match.selectSource(defenderStartingPos));
        assertTrue(this.match.selectDestination(defenderStartingPos, defenderEndPos));
        this.match.makeMove(defenderStartingPos, defenderEndPos);
        this.match.setNextActivePlayer();

        final Position attacker2StartPos = new Position(7, 10);
        final Position attacker2EndPos = new Position(7, 6);
        final Position expectedKillPosition = new Position(7, 5);
        final Piece victim = this.board.getMapPieces().get(Player.DEFENDER).get(expectedKillPosition);

        assertTrue(this.match.selectSource(attacker2StartPos));
        assertTrue(this.match.selectDestination(attacker2StartPos, attacker2EndPos));
        this.match.makeMove(attacker2StartPos, attacker2EndPos);

        final Cell tombPositionCell = this.board.getMapCells().get(expectedKillPosition);

        assertTrue(tombPositionCell.isFree());
        assertEquals(1, tombPositionCell.getComponents().size());
        final CellComponent tomb = tombPositionCell.getComponents().stream()
                                    .findFirst().get();
        assertTrue(tomb.isActive());

        this.match.setNextActivePlayer();
        assertEquals(Player.DEFENDER, this.match.getActivePlayer());
        /* The defender Queen in (6, 5) will attempt to revive the dead piece in (7, 5) */
        final Position queenStartPos = new Position(6, 5);

        /* Moving the Queen to a Cell adjacent to the tomb cell. */
        final Position queenEndPos = new Position(8, 5);

        assertTrue(this.match.selectSource(queenStartPos));
        assertTrue(this.match.selectDestination(queenStartPos, queenEndPos));
        this.match.makeMove(queenStartPos, queenEndPos);

        assertFalse(tombPositionCell.isFree());
        assertFalse(tomb.isActive());
        assertEquals(victim, this.board.getMapPieces().get(Player.DEFENDER).get(expectedKillPosition));
    }

    /**
     * Ensures that no more than one Tomb per Cell is spawned.
     */
    @Test
    void testNoMoreThanOneTombPerCell() {
        this.init();
        final Position attacker1StartPos = new Position(3, 10);
        final Position deathPosition = new Position(3, 6);

        assertTrue(this.match.selectSource(attacker1StartPos));
        assertTrue(this.match.selectDestination(attacker1StartPos, deathPosition));
        this.match.makeMove(attacker1StartPos, deathPosition);
        this.match.setNextActivePlayer();

        final Position defender1StartPos = new Position(5, 7);
        final Position defender1EndPos = new Position(3, 7);

        assertTrue(this.match.selectSource(defender1StartPos));
        assertTrue(this.match.selectDestination(defender1StartPos, defender1EndPos));
        this.match.makeMove(defender1StartPos, defender1EndPos);
        this.match.setNextActivePlayer();

        final Position attacker2StartPos = new Position(0, 6);

        assertTrue(this.match.selectSource(attacker2StartPos));
        assertTrue(this.match.selectDestination(attacker2StartPos, deathPosition));
        this.match.makeMove(attacker2StartPos, deathPosition);
        this.match.setNextActivePlayer();

        /* Going back to start pos with the defender */
        assertTrue(this.match.selectSource(defender1EndPos));
        assertTrue(this.match.selectDestination(defender1EndPos, defender1StartPos));
        this.match.makeMove(defender1EndPos, defender1StartPos);
        this.match.setNextActivePlayer();

        final Position randomAttackerStartPos = new Position(10, 3);
        final Position randomAttackerEndPos = new Position(10, 2);

        assertTrue(this.match.selectSource(randomAttackerStartPos));
        assertTrue(this.match.selectDestination(randomAttackerStartPos, randomAttackerEndPos));
        this.match.makeMove(randomAttackerStartPos, randomAttackerEndPos);
        this.match.setNextActivePlayer();

        assertTrue(this.match.selectSource(defender1StartPos));
        assertTrue(this.match.selectDestination(defender1StartPos, defender1EndPos));
        this.match.makeMove(defender1StartPos, defender1EndPos);

        final Cell deathCell = this.board.getMapCells().get(deathPosition);
        assertEquals(1, deathCell.getComponents().size()); //No more than one Tomb!
    }

    /**
     * Ensures that only Queens of the same team of the dead piece can reanimate
     * said dead piece from the Tomb.
     */
    @Test
    void testResurrectionConsistency() {
        this.init();
        final Position attacker1StartPos = new Position(10, 4);
        final Position deathPosition = new Position(7, 4);

        assertTrue(this.match.selectSource(attacker1StartPos));
        assertTrue(this.match.selectDestination(attacker1StartPos, deathPosition));
        this.match.makeMove(attacker1StartPos, deathPosition);
        this.match.setNextActivePlayer();

        final Position defender1StartPos = new Position(5, 3);
        final Position defender1EndPos = new Position(7, 3);

        assertTrue(this.match.selectSource(defender1StartPos));
        assertTrue(this.match.selectDestination(defender1StartPos, defender1EndPos));
        this.match.makeMove(defender1StartPos, defender1EndPos);
        this.match.setNextActivePlayer();

        final Position randomAttackerStartPos = new Position(10, 7);
        final Position randomAttackerEndPos = new Position(10, 8);

        assertTrue(this.match.selectSource(randomAttackerStartPos));
        assertTrue(this.match.selectDestination(randomAttackerStartPos, randomAttackerEndPos));
        this.match.makeMove(randomAttackerStartPos, randomAttackerEndPos);
        this.match.setNextActivePlayer();

        /* Moving defender in (7, 5) out of the way of the defender Queen. */

        final Position blockingAttackerStartPos = new Position(7, 5);
        final Position blockingAttackerEndPos = new Position(7, 9);

        assertTrue(this.match.selectSource(blockingAttackerStartPos));
        assertTrue(this.match.selectDestination(blockingAttackerStartPos, blockingAttackerEndPos));
        this.match.makeMove(blockingAttackerStartPos, blockingAttackerEndPos);
        this.match.setNextActivePlayer();

        /* Random attacker move again */
        assertTrue(this.match.selectSource(randomAttackerEndPos));
        assertTrue(this.match.selectDestination(randomAttackerEndPos, randomAttackerStartPos));
        this.match.makeMove(randomAttackerEndPos, randomAttackerStartPos);
        this.match.setNextActivePlayer();

        final Position queenStartPos = new Position(6, 5);
        final Position adjacentDeathPosition = new Position(7, 5);

        assertTrue(this.match.selectSource(queenStartPos));
        assertTrue(this.match.selectDestination(queenStartPos, adjacentDeathPosition));
        this.match.makeMove(queenStartPos, adjacentDeathPosition);
        this.match.setNextActivePlayer();

        final Cell deathCell = this.board.getMapCells().get(deathPosition);
        assertTrue(deathCell.isFree()); //the piece was not resurrected
        final CellComponent tomb = deathCell.getComponents().stream().findFirst().get();
        /* Since there was no resurrection, the tomb component should still be active. */
        assertTrue(tomb.isActive());
    }
}
