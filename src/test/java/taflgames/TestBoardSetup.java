package taflgames;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import taflgames.common.code.Position;
import taflgames.controller.SettingsLoader;
import taflgames.controller.SettingsLoaderImpl;
import taflgames.model.BoardBuilder;
import taflgames.model.BoardBuilderImpl;
import taflgames.model.cells.Cell;
import taflgames.model.cells.Exit;
import taflgames.model.cells.Slider;
import taflgames.model.cells.Throne;
import taflgames.model.pieces.Archer;
import taflgames.model.pieces.BasicPiece;
import taflgames.model.pieces.King;
import taflgames.model.pieces.Piece;
import taflgames.model.pieces.Queen;
import taflgames.model.pieces.Shield;
import taflgames.model.pieces.Swapper;
import taflgames.common.Player;

/**
 * JUnit test for board setup using a {@link BoardBuilder}
 * that receives the configuration data from a {@link SettingsLoader}.
 */
class TestBoardSetup {

    private BoardBuilder builder;

    @BeforeEach
    void init() {
        this.builder = new BoardBuilderImpl();
    }

    /**
     * Test the board building according to the classic mode settings.
     */
    @Test
    void testClassicBoardConfig() {
        final SettingsLoader loader = new SettingsLoaderImpl();
        try {
            // Load classic mode game settings
            loader.loadClassicModeConfig(this.builder);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        
        // Retrieve the collections of cells and pieces built according to the settings
        final Map<Position, Cell> cells = this.builder.getCells();
        final Map<Player, Map<Position, Piece>> pieces = this.builder.getPieces();

        // Check king and throne correct placement
        assertEquals(
            pieces.get(Player.DEFENDER).get(new Position(5, 5)).getClass(),
            King.class
        );
        assertEquals(
            cells.get(new Position(5, 5)).getClass(),
            Throne.class
        );

        // Check exits correct placement
        assertEquals(
            getPositions(cells, Exit.class),
            Set.of(
                new Position(0, 0),
                new Position(0, 10),
                new Position(10, 0),
                new Position(10, 10)
            )
        );

        // Check attacker basic pieces correct placement
        assertEquals(
            getPositions(pieces.get(Player.ATTACKER), BasicPiece.class),
            Set.of(
                new Position(0, 3),
                new Position(0, 4),
                new Position(0, 5),
                new Position(0, 6),
                new Position(0, 7),
                new Position(1, 5),
                new Position(3, 0),
                new Position(4, 0),
                new Position(5, 0),
                new Position(6, 0),
                new Position(7, 0),
                new Position(5, 1),
                new Position(5, 9),
                new Position(3, 10),
                new Position(4, 10),
                new Position(5, 10),
                new Position(6, 10),
                new Position(7, 10),
                new Position(9, 5),
                new Position(10, 3),
                new Position(10, 4),
                new Position(10, 5),
                new Position(10, 6),
                new Position(10, 7)
            )
        );

        // Check defender basic pieces correct placement
        assertEquals(
            getPositions(pieces.get(Player.DEFENDER), BasicPiece.class),
            Set.of(
                new Position(3, 5),
                new Position(4, 4),
                new Position(4, 5),
                new Position(4, 6),
                new Position(5, 3),
                new Position(5, 4),
                new Position(5, 6),
                new Position(5, 7),
                new Position(6, 4),
                new Position(6, 5),
                new Position(6, 6),
                new Position(7, 5)
            )
        );
    }

    /**
     * Test the board building according to the variant mode settings.
     */
    @Test
    void testVariantBoardConfig() {
        final SettingsLoader loader = new SettingsLoaderImpl();
        try {
            // Load variant mode game settings
            loader.loadVariantModeConfig(this.builder);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // Retrieve the collections of cells and pieces built according to the settings
        final Map<Position, Cell> cells = this.builder.getCells();
        final Map<Player, Map<Position, Piece>> pieces = this.builder.getPieces();

        // Check king and throne correct placement
        assertEquals(
            pieces.get(Player.DEFENDER).get(new Position(5, 5)).getClass(),
            King.class
        );
        assertEquals(
            cells.get(new Position(5, 5)).getClass(),
            Throne.class
        );

        // Check exits correct placement
        assertEquals(
            getPositions(cells, Exit.class),
            Set.of(
                new Position(0, 0),
                new Position(0, 10),
                new Position(10, 0),
                new Position(10, 10)
            )
        );

        // Check sliders correct placement
        assertEquals(
            getPositions(cells, Slider.class),
            Set.of(
                new Position(2, 2),
                new Position(2, 8),
                new Position(8, 2),
                new Position(8, 8)
            )
        );

        // Check queens correct placement
        assertEquals(
            getPositions(pieces.get(Player.ATTACKER), Queen.class),
            Set.of(new Position(0, 5))
        );
        assertEquals(
            getPositions(pieces.get(Player.DEFENDER), Queen.class),
            Set.of(new Position(6, 5))
        );

        // Check archers correct placement
        assertEquals(
            getPositions(pieces.get(Player.ATTACKER), Archer.class),
            Set.of(new Position(5, 0), new Position(5, 10))
        );
        assertEquals(
            getPositions(pieces.get(Player.DEFENDER), Archer.class),
            Set.of(new Position(5, 4), new Position(5, 6))
        );

        // Check shields correct placement
        assertEquals(
            getPositions(pieces.get(Player.ATTACKER), Shield.class),
            Set.of(new Position(1, 5), new Position(9, 5))
        );
        assertEquals(
            getPositions(pieces.get(Player.DEFENDER), Shield.class),
            Set.of(new Position(5, 3), new Position(5, 7))
        );

        // Check swappers correct placement
        assertEquals(
            getPositions(pieces.get(Player.ATTACKER), Swapper.class),
            Set.of(new Position(10, 5))
        );
        assertEquals(
            getPositions(pieces.get(Player.DEFENDER), Swapper.class),
            Set.of(new Position(4, 5))
        );

        // Check attacker basic pieces correct placement
        assertEquals(
            getPositions(pieces.get(Player.ATTACKER), BasicPiece.class),
            Set.of(
                new Position(0, 3),
                new Position(0, 4),
                new Position(0, 6),
                new Position(0, 7),
                new Position(3, 0),
                new Position(4, 0),
                new Position(6, 0),
                new Position(7, 0),
                new Position(5, 1),
                new Position(5, 9),
                new Position(3, 10),
                new Position(4, 10),
                new Position(6, 10),
                new Position(7, 10),
                new Position(10, 3),
                new Position(10, 4),
                new Position(10, 6),
                new Position(10, 7)
            )
        );

        // Check defender basic pieces correct placement
        assertEquals(
            getPositions(pieces.get(Player.DEFENDER), BasicPiece.class),
            Set.of(
                new Position(3, 5),
                new Position(4, 4),
                new Position(4, 6),
                new Position(6, 4),
                new Position(6, 6),
                new Position(7, 5)
            )
        );
    }

    private <C> Set<Position> getPositions(final Map<Position, C> map, final Class<? extends C> targetClass) {
        return map.entrySet().stream()
                // filter out the objects that are not instances of the target class
                .filter(entry -> (entry.getValue().getClass() == targetClass))
                .map(entry -> entry.getKey())   // get the positions of objects that are instances of the target class
                .collect(Collectors.toSet());
    }

}
