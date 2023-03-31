package taflgames;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.common.code.Position;
import taflgames.controller.SettingsLoader;
import taflgames.controller.SettingsLoaderImpl;
import taflgames.model.Match;
import taflgames.model.MatchImpl;
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

/**
 * Tests the CellComponents, which in our case are only the Tombs.
 * It uses an instance of a Variant mode Match to see if the application
 * behaves naturally after calls to the Match object.
 */
public class TestCellComponents {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMatch.class);

    private Match match;
    private Board board;

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
            this.board = new BoardImpl(pieces, cells, size);
            this.match = new MatchImpl(this.board);
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
                .anyMatch(type -> type.equals("Tomb")));
        basicCell.detachComponent(tombComponent);
        assertTrue(basicCell.getComponents().isEmpty());
    }

    /**
     * Tests if the CellComponents are automatically attached 
     * and detached in a real case scenario.
     */
    @Test
    void testTombCreationAsComponents() {
        final Position attacker1StartingPos = new Position(3, 0);
        final Position attacker1EndPos = new Position(3, 4);

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

        final Position attacker2StartPos = new Position(3, 10);
        final Position attacker2EndPos = new Position(3, 6);
        final Position expectedKillPosition = new Position(7, 5);

        assertTrue(this.match.selectSource(attacker2StartPos));
        assertTrue(this.match.selectDestination(attacker2StartPos, attacker2EndPos));
        this.match.makeMove(attacker2StartPos, attacker2EndPos);

        assertTrue(this.board.getMapCells().get(expectedKillPosition).isFree());
        assertTrue(this.board.getMapCells().get(expectedKillPosition).getComponents().size() == 1);
        final CellComponent tomb = this.board.getMapCells().get(expectedKillPosition).getComponents().stream()
                                    .findFirst().get();
        assertTrue(tomb.isActive());

        //TODO: if many pieces die on the same cell, there still should be only one Tomb component.
    }
}
