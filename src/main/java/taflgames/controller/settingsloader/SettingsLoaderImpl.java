package taflgames.controller.settingsloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.builders.CellsCollectionBuilder;
import taflgames.model.builders.PiecesCollectionBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class loads the configuration settings for the setup of the board
 * from configuration files.
 */
public final class SettingsLoaderImpl implements SettingsLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsLoaderImpl.class);

    private static final String SEP = System.getProperty("file.separator");
    private static final String PATH = "taflgames" + SEP + "config" + SEP;
    private static final String CLASSIC_CONFIG_FILE = "ClassicModeSettings.xml";
    private static final String VARIANT_CONFIG_FILE = "VariantModeSettings.xml";

    private Element settings;

    @Override
    public void loadClassicModeConfig(
        final CellsCollectionBuilder cellsCollBuilder,
        final PiecesCollectionBuilder piecesCollBuilder
    ) throws IOException {
        this.settings = getSettingsFromFile(CLASSIC_CONFIG_FILE);
        this.loadBoardSize(cellsCollBuilder);
        this.loadKingAndThroneData(cellsCollBuilder, piecesCollBuilder);
        this.loadExitsData(cellsCollBuilder);
        this.loadClassicCellsData(cellsCollBuilder);
        this.loadBasicPiecesData(cellsCollBuilder, piecesCollBuilder);
    }

    @Override
    public void loadVariantModeConfig(
        final CellsCollectionBuilder cellsCollBuilder,
        final PiecesCollectionBuilder piecesCollBuilder
    ) throws IOException {
        this.settings = getSettingsFromFile(VARIANT_CONFIG_FILE);
        this.loadBoardSize(cellsCollBuilder);
        this.loadKingAndThroneData(cellsCollBuilder, piecesCollBuilder);
        this.loadExitsData(cellsCollBuilder);
        this.loadSlidersData(cellsCollBuilder);
        this.loadClassicCellsData(cellsCollBuilder);
        this.loadBasicPiecesData(cellsCollBuilder, piecesCollBuilder);
        this.loadQueensData(cellsCollBuilder, piecesCollBuilder);
        this.loadArchersData(cellsCollBuilder, piecesCollBuilder);
        this.loadShieldsData(cellsCollBuilder, piecesCollBuilder);
        this.loadSwappersData(cellsCollBuilder, piecesCollBuilder);
    }

    private Element getSettingsFromFile(final String filename) throws IOException {
        try (InputStream configFile = Objects.requireNonNull(
            ClassLoader.getSystemResourceAsStream(PATH + filename)
        )) {
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            final Document document = docBuilder.parse(configFile);
            document.getDocumentElement().normalize();
            final NodeList nodeList = document.getElementsByTagName("Settings");
            return (Element) nodeList.item(0);
        } catch (final IOException | ParserConfigurationException | SAXException exception) {
            final String errorMsg = "An error occurred while trying to get or parse the configuration file.";
            LOGGER.error(errorMsg, exception);
            throw new IOException(errorMsg);    // NOPMD
            // Original stack trace is already shown using the logger.
        }
    }

    private void loadBoardSize(final CellsCollectionBuilder cellsCollBuilder) {
        Objects.requireNonNull(settings);
        final int boardSize = Integer.parseInt(
            settings.getElementsByTagName("BoardSize").item(0).getTextContent()
        );
        cellsCollBuilder.addBoardSize(boardSize);
    }

    private void loadKingAndThroneData(
        final CellsCollectionBuilder cellsCollBuilder,
        final PiecesCollectionBuilder piecesCollBuilder
    ) {
        Objects.requireNonNull(settings);
        final Element kingPosElem = (Element) settings.getElementsByTagName("KingPosition").item(0);
        final Element posElem = (Element) kingPosElem.getElementsByTagName("Position").item(0);
        final Position kingPos = new Position(
            Integer.parseInt(posElem.getAttribute("row")),
            Integer.parseInt(posElem.getAttribute("column"))
        );
        piecesCollBuilder.addKing(kingPos);
        cellsCollBuilder.addThrone(kingPos);
    }

    private void loadExitsData(final CellsCollectionBuilder cellsCollBuilder) {
        final Set<Position> exitsPositions = getPositionsByTagName("ExitsPositions");
        cellsCollBuilder.addExits(exitsPositions);
    }

    private void loadSlidersData(final CellsCollectionBuilder cellsCollBuilder) {
        cellsCollBuilder.addSliders(
            getPositionsByTagName("SlidersPositions")
        );
    }

    private void loadClassicCellsData(final CellsCollectionBuilder cellsCollBuilder) {
        cellsCollBuilder.addBasicCells();
    }

    private void loadBasicPiecesData(
        final CellsCollectionBuilder cellsCollBuilder,
        final PiecesCollectionBuilder piecesCollBuilder
    ) {
        final var positionsForEachTeam = getPiecesPositionsForEachTeam("BasicPieces");
        piecesCollBuilder.addBasicPieces(positionsForEachTeam);
        for (final var positions : positionsForEachTeam.values()) {
            setCellsStateAsOccupied(cellsCollBuilder, positions);
        }
    }

    private void loadQueensData(
        final CellsCollectionBuilder cellsCollBuilder,
        final PiecesCollectionBuilder piecesCollBuilder
    ) {
        final var positionsForEachTeam = getPiecesPositionsForEachTeam("Queens");
        piecesCollBuilder.addQueens(positionsForEachTeam);
        for (final var positions : positionsForEachTeam.values()) {
            setCellsStateAsOccupied(cellsCollBuilder, positions);
        }
    }

    private void loadArchersData(
        final CellsCollectionBuilder cellsCollBuilder,
        final PiecesCollectionBuilder piecesCollBuilder
    ) {
        final var positionsForEachTeam = getPiecesPositionsForEachTeam("Archers");
        piecesCollBuilder.addArchers(positionsForEachTeam);
        for (final var positions : positionsForEachTeam.values()) {
            setCellsStateAsOccupied(cellsCollBuilder, positions);
        }
    }

    private void loadShieldsData(
        final CellsCollectionBuilder cellsCollBuilder,
        final PiecesCollectionBuilder piecesCollBuilder
    ) {
        final var positionsForEachTeam = getPiecesPositionsForEachTeam("Shields");
        piecesCollBuilder.addShields(positionsForEachTeam);
        for (final var positions : positionsForEachTeam.values()) {
            setCellsStateAsOccupied(cellsCollBuilder, positions);
        }
    }

    private void loadSwappersData(
        final CellsCollectionBuilder cellsCollBuilder,
        final PiecesCollectionBuilder piecesCollBuilder
    ) {
        final var positionsForEachTeam = getPiecesPositionsForEachTeam("Swappers");
        piecesCollBuilder.addSwappers(positionsForEachTeam);
        for (final var positions : positionsForEachTeam.values()) {
            setCellsStateAsOccupied(cellsCollBuilder, positions);
        }
    }

    private Map<Player, Set<Position>> getPiecesPositionsForEachTeam(final String piecesName) {
        final Map<Player, Set<Position>> positions = new HashMap<>();
        positions.put(Player.ATTACKER, getPositionsByTagName("Attacker" + piecesName + "Positions"));
        positions.put(Player.DEFENDER, getPositionsByTagName("Defender" + piecesName + "Positions"));
        return positions;
    }

    private Set<Position> getPositionsByTagName(final String tagName) {
        Objects.requireNonNull(settings);
        final Set<Position> positions = new HashSet<>();
        final Element positionsElement = (Element) settings.getElementsByTagName(tagName).item(0);
        final int length = positionsElement.getElementsByTagName("Position").getLength();
        for (int i = 0; i < length; i++) {
            final Element posElem = (Element) positionsElement.getElementsByTagName("Position").item(i);
            positions.add(new Position(
                Integer.parseInt(posElem.getAttribute("row")),
                Integer.parseInt(posElem.getAttribute("column"))
            ));
        }
        return positions;
    }

    private void setCellsStateAsOccupied(final CellsCollectionBuilder cellsCollBuilder, final Set<Position> positions) {
        positions.stream().forEach(pos -> cellsCollBuilder.setCellAsOccupied(pos));
    }

}
