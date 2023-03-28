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
import taflgames.model.pieces.api.Piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;

public class TestMemento {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestMatch.class);

    private Match classicMatch;
    private Board classicBoard;
    private Match variantMatch;
    private Board variantBoard;

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
            loader.loadClassicModeConfig(cellsCollBuilder, piecesCollBuilder);
            final var pieces = piecesCollBuilder.build();
            final var cells = cellsCollBuilder.build();
            final int size = (int) Math.sqrt(cells.size());
            this.classicBoard = new BoardImpl(pieces, cells, size);
            this.classicMatch = new MatchImpl(this.classicBoard);
        } catch (final IOException ex) {
            LOGGER.error("Cannot read configuration file. {}", ex.getMessage());
            fail();
        }
        final SettingsLoader loader2 = new SettingsLoaderImpl();
        final CellsCollectionBuilder cellsCollBuilder2 = new CellsCollectionBuilderImpl();
        final PiecesCollectionBuilder piecesCollBuilder2 = new PiecesCollectionBuilderImpl();
        try {
            loader2.loadVariantModeConfig(cellsCollBuilder2, piecesCollBuilder2);
            final var pieces = piecesCollBuilder2.build();
            final var cells = cellsCollBuilder2.build();
            final int size = (int) Math.sqrt(cells.size());
            this.variantBoard = new BoardImpl(pieces, cells, size);
            this.variantMatch = new MatchImpl(this.variantBoard);
        } catch (final IOException ex) {
            LOGGER.error("Cannot read configuration file. {}", ex.getMessage());
            fail();
        }
    }

    @Test
    void testCaretakerClassic() {
        final Caretaker caretaker = new CaretakerImpl(this.classicMatch);
        final Position startingPosition = new Position(0, 3);
        final Position endingPosition = new Position(0, 1);
        
        /* Saves the game state at the beginning of the turn */
        caretaker.updateHistory();

        assertTrue(this.classicMatch.selectSource(startingPosition));
        assertTrue(this.classicMatch.selectDestination(startingPosition, endingPosition));
        this.classicMatch.makeMove(startingPosition, endingPosition);
        /* The piece was correctly moved */
        assertTrue(this.classicBoard.getMapPieces().get(this.classicMatch.getActivePlayer())
        .containsKey(endingPosition));
        assertFalse(this.classicBoard.getMapPieces().get(this.classicMatch.getActivePlayer())
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
        assertEquals(this.classicMatch.getActivePlayer(), Player.ATTACKER); //still attacker's turn
        assertEquals(0, this.classicMatch.getTurnNumber());
        assertFalse(this.classicBoard.getMapCells().get(startingPosition).isFree());
        assertTrue(this.classicMatch.selectSource(startingPosition));
        assertTrue(this.classicBoard.getMapCells().get(endingPosition).isFree());

        /* Subsequent calls to undo should do no harm, provided that
         * they are preceeded by an "unlockHistory()" call.
        */
        caretaker.unlockHistory();
        caretaker.undo();
        caretaker.unlockHistory();
        caretaker.undo();
        assertEquals(this.classicMatch.getActivePlayer(), Player.ATTACKER);
        assertEquals(0, this.classicMatch.getTurnNumber());
        assertFalse(this.classicBoard.getMapCells().get(startingPosition).isFree());
        assertTrue(this.classicMatch.selectSource(startingPosition));

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
        assertTrue(this.classicMatch.selectSource(firstAttackerStartingPos));
        assertTrue(this.classicMatch.selectDestination(firstAttackerStartingPos, firstAttackerEndingPos));
        this.classicMatch.makeMove(firstAttackerStartingPos, firstAttackerEndingPos);
        this.classicMatch.setNextActivePlayer();

        caretaker.updateHistory();
        assertTrue(this.classicMatch.selectSource(defenderStartingPos));
        assertTrue(this.classicMatch.selectDestination(defenderStartingPos, defenderEndingPos));
        this.classicMatch.makeMove(defenderStartingPos, defenderEndingPos);
        this.classicMatch.setNextActivePlayer();

        caretaker.updateHistory();
        assertTrue(this.classicMatch.selectSource(secondAttackerStartingPos));
        assertTrue(this.classicMatch.selectDestination(secondAttackerStartingPos, secondAttackerEndingPos));
        this.classicMatch.makeMove(secondAttackerStartingPos, secondAttackerEndingPos);
        
        assertTrue(this.classicBoard.getMapCells().get(targetPiecePosition).isFree()); //the cell should be free
        /* This piece should be dead */
        assertFalse(this.classicBoard.getMapPieces().get(Player.DEFENDER).containsKey(targetPiecePosition));

        caretaker.unlockHistory();
        caretaker.undo();

        assertFalse(this.classicBoard.getMapCells().get(targetPiecePosition).isFree());
        assertTrue(this.classicBoard.getMapPieces().get(Player.DEFENDER).containsKey(targetPiecePosition));
        assertTrue(this.classicBoard.getMapPieces().get(Player.DEFENDER).get(targetPiecePosition).isAlive());
        /* Pieces are correctly respawned! */
    }

    @Test
    void testCaretakerVariant() {
        final Caretaker caretaker = new CaretakerImpl(this.variantMatch);
        /* Swapping attacker swapper with defender swapper */
        final Position attackerSwapperStartPos = new Position(10, 5);
        final Position attackerSwapperEndPos = new Position(4, 5);

        final Position defenderBasicStartPos = new Position(6, 4);
        final Position defenderBasicEndPos = new Position(6, 2);
        /* The second attacker will try to eat in combination with the swapper */
        final Position attacker2StartPos = new Position(4, 10);
        final Position attacker2EndPos = new Position(4, 7);

        /* The defender moved before will move to a Slider */
        final Position sliderPosition = new Position(8, 2);
        final Map<Position, Piece> attackerPiecesMapBefore = this.variantBoard.getMapPieces().get(Player.ATTACKER)
                .entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
        final Map<Position, Piece> defenderPiecesMapBefore = this.variantBoard.getMapPieces().get(Player.DEFENDER)
                .entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
        
        caretaker.updateHistory();
        assertTrue(this.variantMatch.selectSource(attackerSwapperStartPos));
        assertTrue(this.variantMatch.selectDestination(attackerSwapperStartPos, attackerSwapperEndPos));
        this.variantMatch.makeMove(attackerSwapperStartPos, attackerSwapperEndPos);

        assertEquals("SWAPPER", this.variantBoard.getMapPieces().get(Player.ATTACKER)
            .get(attackerSwapperEndPos).getMyType().getTypeOfPiece());
        assertTrue(this.variantBoard.getMapPieces().get(Player.DEFENDER).containsKey(attackerSwapperStartPos));

        caretaker.unlockHistory();
        caretaker.undo();
        assertEquals(attackerPiecesMapBefore, this.variantBoard.getMapPieces().get(Player.ATTACKER));
        assertEquals(defenderPiecesMapBefore, this.variantBoard.getMapPieces().get(Player.DEFENDER));

        /* Repeating the move */
        assertTrue(this.variantMatch.selectSource(attackerSwapperStartPos));
        assertTrue(this.variantMatch.selectDestination(attackerSwapperStartPos, attackerSwapperEndPos));
        this.variantMatch.makeMove(attackerSwapperStartPos, attackerSwapperEndPos);
        this.variantMatch.setNextActivePlayer();

        /* Defender's turn */
        caretaker.updateHistory();
        assertTrue(this.variantMatch.selectSource(defenderBasicStartPos));
        assertTrue(this.variantMatch.selectDestination(defenderBasicStartPos, defenderBasicEndPos));
        this.variantMatch.makeMove(defenderBasicStartPos, defenderBasicEndPos);
        this.variantMatch.setNextActivePlayer();

        /* Attacker's turn, capture attempt on defender piece in (4, 6) */
        final Position capturePosition = new Position(4, 6);
        caretaker.updateHistory();
        assertTrue(this.variantMatch.selectSource(attacker2StartPos));
        assertTrue(this.variantMatch.selectDestination(attacker2StartPos, attacker2EndPos));
        this.variantMatch.makeMove(attacker2StartPos, attacker2EndPos);

        assertTrue(this.variantBoard.getMapCells().get(capturePosition).isFree());
        /* The piece was successfully captured. */

        caretaker.unlockHistory();
        caretaker.undo();
        assertFalse(this.variantBoard.getMapCells().get(capturePosition).isFree());

        /* Doing the move again */
        assertTrue(this.variantMatch.selectSource(attacker2StartPos));
        assertTrue(this.variantMatch.selectDestination(attacker2StartPos, attacker2EndPos));
        this.variantMatch.makeMove(attacker2StartPos, attacker2EndPos);
        this.variantMatch.setNextActivePlayer();

        /* Defender's turn, going to slider.
         * TODO: this part of the test should be updated when the
         * status of the sliders will change dinamically through
         * the method 'notifyTurnHasEnded'.
         * Sliders point to the right (orientation = 0,1) at the moment
         * of their creation.
         */
        final Position expectedEndPositionAfterSlider = new Position(8, 10); //TODO: change, sliders might rotate
        caretaker.updateHistory();

        final Piece defenderPiece = this.variantBoard.getMapPieces().get(Player.DEFENDER).get(defenderBasicEndPos);
        /* The defender moves from (6, 2) to (8, 2), where the slider is. */
        assertEquals(defenderPiece.getCurrentPosition(), defenderBasicEndPos);

        assertTrue(this.variantMatch.selectSource(defenderBasicEndPos));
        assertTrue(this.variantMatch.selectDestination(defenderBasicEndPos, sliderPosition));
        this.variantMatch.makeMove(defenderBasicEndPos, sliderPosition);

        /* The following code notifies the Slider that a piece landed onto it. 
         * TODO: ideally, this should happen automatically.
        */
        this.variantBoard.getMapCells().get(sliderPosition).notify(sliderPosition, defenderPiece, 
        List.of(defenderPiece.sendSignalMove()), this.variantBoard.getMapPieces(), this.variantBoard.getMapCells());

        assertEquals(defenderPiece.getCurrentPosition(), expectedEndPositionAfterSlider);
        assertFalse(this.variantBoard.getMapCells().get(expectedEndPositionAfterSlider).isFree());
        assertEquals("Slider", this.variantBoard.getMapCells().get(sliderPosition).getType());
        assertTrue(this.variantBoard.getMapCells().get(sliderPosition).isFree());

        caretaker.unlockHistory();
        caretaker.undo();

        /* The defender piece returns correctly to its starting position (6, 2),
         * which corresponds to 'defenderBasicEndPos'.
         */
        assertEquals(defenderPiece.getCurrentPosition(), defenderBasicEndPos);
    }
}

