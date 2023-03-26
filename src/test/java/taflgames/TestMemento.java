package taflgames;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.common.code.Position;
import taflgames.controller.SettingsLoader;
import taflgames.controller.SettingsLoaderImpl;
import taflgames.model.Match;
import taflgames.model.MatchImpl;
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

    private final Position legalStartingPositionForDefenderMove = new Position(7, 5);
    private Match match;

    /**
     * Initializes each test before its execution.
     * {credits for this test to Elena Boschetti}.
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
            this.match = new MatchImpl(
                new BoardImpl(pieces, cells, size)
            );
        } catch (final IOException ex) {
            LOGGER.error("Cannot read configuration file. {}", ex.getMessage());
            fail();
        }
    }

    @Test
    void testCaretaker() {
        final Caretaker caretaker = new CaretakerImpl(this.match);
        if (this.match.selectSource(this.legalStartingPositionForDefenderMove)) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }
}
