package taflgames;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.common.Player;
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
import taflgames.model.memento.api.Caretaker;
import taflgames.model.memento.code.CaretakerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

public class TestMemento {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestMatch.class);

    private Match match;
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
            /*
             * Variant mode configuration is loaded, so that the match is tested also when
             * using special pieces and cells.
             */ 
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

    @Test
    void testCaretaker() {
        final Caretaker caretaker = new CaretakerImpl(this.match);
        final Position startingPosition = new Position(0, 4);
        final Position endingPosition = new Position(0, 1);
        assertTrue(this.match.selectSource(startingPosition));
        assertTrue(this.match.selectDestination(startingPosition, endingPosition));
        this.match.makeMove(startingPosition, endingPosition);
        /* The piece was correctly moved */
        assertTrue(this.board.getMapPieces().get(this.match.getActivePlayer())
        .containsKey(endingPosition));
        assertFalse(this.board.getMapPieces().get(this.match.getActivePlayer())
        .containsKey(startingPosition));
        /* Saves the game state at the end of the turn */
        caretaker.updateHistory();
        this.match.setNextActivePlayer();
        assertEquals(1, this.match.getTurnNumber()); //turn has changed as expected

        /* Now that cell should be empty */
        assertTrue(this.board.getMapCells().get(startingPosition).isFree());
        assertFalse(this.board.getMapCells().get(endingPosition).isFree());

        assertEquals(this.match.getActivePlayer(), Player.DEFENDER);

        /* Going back */
        caretaker.undo();
        assertEquals(this.match.getActivePlayer(), Player.ATTACKER);
        assertEquals(0, this.match.getTurnNumber());
        assertTrue(this.match.selectSource(startingPosition));
    }
}
