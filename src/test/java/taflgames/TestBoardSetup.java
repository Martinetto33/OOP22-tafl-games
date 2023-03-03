package taflgames;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import taflgames.common.code.Position;
import taflgames.controller.SettingsLoader;
import taflgames.controller.SettingsLoaderImpl;
import taflgames.model.BoardBuilder;
import taflgames.model.cells.Cell;
import taflgames.model.pieces.King;
import taflgames.model.pieces.Piece;
import taflgames.common.Player;

/**
 * JUnit test for board setup using a {@link BoardBuilder}
 * that receives the configuration data from a {@link SettingsLoader}.
 */
class TestBoardSetup {
    
    private BoardBuilder builder;

    @BeforeEach
    void init() {
        this.builder = new BoardBuilder();
    }

    /**
     * Test the board building according to the classic mode settings.
     */
    @Test
    void TestClassicBoardConfigFromFile() {
        final SettingsLoader loader = new SettingsLoaderImpl();
        try {
            final BoardBuilder builder = new BoardBuilder();
            loader.loadClassicModeConfig(this.builder);
            final Map<Position, Cell> cells = this.builder.getCells();
            final Map<Player, Map<Position, Piece>> pieces = this.builder.getPieces();
            assertEquals(
                pieces.get(Player.DEFENDER).get(new Position(5, 5)).getClass(),
                King.class
            );
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
