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
import taflgames.model.memento.code.HistoryLockedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
            loader.loadClassicModeConfig(cellsCollBuilder, piecesCollBuilder);
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
        
        /* Saves the game state at the beginning of the turn */
        caretaker.updateHistory();

        assertTrue(this.match.selectSource(startingPosition));
        assertFalse(this.match.selectDestination(startingPosition, endingPosition));
        this.match.makeMove(startingPosition, endingPosition);
        /* The piece was correctly moved */
        assertTrue(this.board.getMapPieces().get(this.match.getActivePlayer())
        .containsKey(endingPosition));
        assertFalse(this.board.getMapPieces().get(this.match.getActivePlayer())
        .containsKey(startingPosition));

        /* The Caretaker should throw an Exception if the method "undo" is called
         * before "unlockHistory".
         */
        assertThrows(HistoryLockedException.class, () -> caretaker.undo());
        
        /* The following "unlockHistory" call will be performed automatically 
         * by a call to the method of the Match "makeMove()".
         */
        caretaker.unlockHistory();

        /* Going back to previous state, which is saved again in case of multiple
         * undo operations in the same turn; the undo method must be called 
         * before the end of this turn
         * to work.
         */
        caretaker.undo();
        assertEquals(this.match.getActivePlayer(), Player.ATTACKER); //still attacker's turn
        assertEquals(0, this.match.getTurnNumber());
        assertFalse(this.board.getMapCells().get(startingPosition).isFree());
        assertTrue(this.match.selectSource(startingPosition));
        assertTrue(this.board.getMapCells().get(endingPosition).isFree());

        /* Subsequent calls to undo should do no harm, provided that
         * they are preceeded by an "unlockHistory()" call.
        */
        caretaker.unlockHistory();
        caretaker.undo();
        caretaker.unlockHistory();
        caretaker.undo();
        assertEquals(this.match.getActivePlayer(), Player.ATTACKER);
        assertEquals(0, this.match.getTurnNumber());
        assertFalse(this.board.getMapCells().get(startingPosition).isFree());
        assertTrue(this.match.selectSource(startingPosition));

        /* Now there will be an attempt to eat one of the pieces in order
         * to see if the undo() call correctly reanimates it.
         * The defender piece in (5,7) will be eaten.
         */
        final Position firstAttackerStartingPos = new Position(10, 7);
        final Position firstAttackerEndingPos = new Position(6, 7);
        final Position defenderStartingPos = new Position(6, 4);
        final Position defenderEndingPos = new Position(8, 4);
        final Position secondAttackerStartingPos = new Position(4, 10);
        final Position secondAttackerEndingPos = new Position(4, 7);

        final Position targetPiecePosition = new Position(5, 7);

        caretaker.updateHistory();
        assertTrue(this.match.selectSource(firstAttackerStartingPos));
        assertTrue(this.match.selectDestination(firstAttackerStartingPos, firstAttackerEndingPos));
        this.match.makeMove(firstAttackerStartingPos, firstAttackerEndingPos);
        this.match.setNextActivePlayer();

        caretaker.updateHistory();
        assertTrue(this.match.selectSource(defenderStartingPos));
        assertTrue(this.match.selectDestination(defenderStartingPos, defenderEndingPos));
        this.match.makeMove(defenderStartingPos, defenderEndingPos);
        this.match.setNextActivePlayer();

        caretaker.updateHistory();
        assertTrue(this.match.selectSource(secondAttackerStartingPos));
        assertTrue(this.match.selectDestination(secondAttackerStartingPos, secondAttackerEndingPos));
        this.match.makeMove(secondAttackerStartingPos, secondAttackerEndingPos);
        
        assertTrue(this.board.getMapCells().get(targetPiecePosition).isFree()); //the cell should be free
        /* This piece should be dead */
        assertFalse(this.board.getMapPieces().get(Player.DEFENDER).containsKey(targetPiecePosition));

        caretaker.unlockHistory();
        caretaker.undo();

        assertFalse(this.board.getMapCells().get(targetPiecePosition).isFree());
        assertTrue(this.board.getMapPieces().get(Player.DEFENDER).containsKey(targetPiecePosition));
        assertTrue(this.board.getMapPieces().get(Player.DEFENDER).get(targetPiecePosition).isAlive());
        /* Pieces are correctly respawned! */
    }
}

//TODO: test for the variant mode.
