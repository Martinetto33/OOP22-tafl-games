package taflgames.controller;

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
import taflgames.model.BoardBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SettingsLoaderImpl implements SettingsLoader {

    private static final String SEP = System.getProperty("file.separator");
    private static final String PATH = "taflgames" + SEP + "config" + SEP;
    private static final String CLASSIC_CONFIG_FILE = "ClassicModeSettings.xml";
    private static final String VARIANT_CONFIG_FILE = "VariantModeSettings.xml";

    public SettingsLoaderImpl() {
    }
    
    public void loadClassicModeConfig(final BoardBuilder boardBuilder) throws IOException {
        try {
            final Element settings = getSettingsFromFile(CLASSIC_CONFIG_FILE);
            this.loadBoardSize(settings, boardBuilder);
            this.loadKingData(settings, boardBuilder);
            this.loadExitsData(settings, boardBuilder);
            this.loadBasicPiecesData(settings, boardBuilder);
        } catch (final IOException e) {
            throw new IOException("Cannot read configuration file");
        }
    }

    public void loadVariantModeConfig(final BoardBuilder boardBuilder) throws IOException {
        try {
            final Element settings = getSettingsFromFile(VARIANT_CONFIG_FILE);
            this.loadBoardSize(settings, boardBuilder);
            this.loadKingData(settings, boardBuilder);
            this.loadExitsData(settings, boardBuilder);
            this.loadSlidersData(settings, boardBuilder);
            this.loadBasicPiecesData(settings, boardBuilder);
            this.loadQueensData(settings, boardBuilder);
            this.loadArchersData(settings, boardBuilder);
            this.loadShieldsData(settings, boardBuilder);
            this.loadSwappersData(settings, boardBuilder);
        } catch (final IOException e) {
            throw new IOException("Cannot read configuration file");
        }
    }

    private Element getSettingsFromFile(final String filename) throws IOException {
        try (final InputStream configFile = Objects.requireNonNull(
            ClassLoader.getSystemResourceAsStream(PATH + filename)
        )) {
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            final Document document = docBuilder.parse(configFile);
            document.getDocumentElement().normalize();
            final NodeList nodeList = document.getElementsByTagName("Settings");
            final Element settings = (Element)(nodeList.item(0));
            return settings;
        } catch (final ParserConfigurationException | SAXException | IOException e) {
            throw new IOException();
        }
    }
    
    private void loadBoardSize(final Element settings, final BoardBuilder boardBuilder) {
        final int boardSize = Integer.parseInt(
            settings.getElementsByTagName("BoardSize").item(0).getTextContent()
        );
        boardBuilder.addBoardSize(boardSize);
    }

    private void loadKingData(final Element settings, final BoardBuilder boardBuilder) {
        final Element kingPosElem = (Element)(settings.getElementsByTagName("KingPosition").item(0));
        final Element posElem = (Element)(kingPosElem.getElementsByTagName("Position").item(0));
        final Position kingPos = new Position(
            Integer.parseInt(posElem.getAttribute("row")),
            Integer.parseInt(posElem.getAttribute("column"))
        );
        boardBuilder.addThroneAndKing(kingPos);
    }

    private void loadExitsData(final Element settings, final BoardBuilder boardBuilder) {
        final Set<Position> exitsPositions = getPositionsByTagName("ExitsPositions", settings);
        boardBuilder.addExits(exitsPositions);
    }

    private void loadSlidersData(final Element settings, final BoardBuilder boardBuilder) {
        boardBuilder.addSliders(
            getPositionsByTagName("SlidersPositions", settings)
        );
    }

    private void loadBasicPiecesData(final Element settings, final BoardBuilder boardBuilder) {
        boardBuilder.addBasicPieces(
            getPiecesPositionsForEachTeam("BasicPieces", settings)
        );
    }

    private void loadQueensData(final Element settings, final BoardBuilder boardBuilder) {
        boardBuilder.addQueens(
            getPiecesPositionsForEachTeam("Queens", settings)
        );
    }

    private void loadArchersData(final Element settings, final BoardBuilder boardBuilder) {
        boardBuilder.addArchers(
            getPiecesPositionsForEachTeam("Archers", settings)
        );
    }
    
    private void loadShieldsData(final Element settings, final BoardBuilder boardBuilder) {
        boardBuilder.addShields(
            getPiecesPositionsForEachTeam("Shields", settings)
        );
    }

    private void loadSwappersData(final Element settings, final BoardBuilder boardBuilder) {
        boardBuilder.addSwappers(
            getPiecesPositionsForEachTeam("Swappers", settings)
        );
    }

    private Map<Player, Set<Position>> getPiecesPositionsForEachTeam(final String piecesName, final Element settings) {
        final Map<Player, Set<Position>> positions = new HashMap<>();
        positions.put(Player.ATTACKER, getPositionsByTagName("Attacker" + piecesName + "Positions", settings));
        positions.put(Player.DEFENDER, getPositionsByTagName("Defender" + piecesName + "Positions", settings));
        return positions;
    }

    private Set<Position> getPositionsByTagName(final String tagName, final Element settings) {
        final Set<Position> positions = new HashSet<>();
        final Element positionsElement = (Element)(settings.getElementsByTagName(tagName).item(0));
        final int length = positionsElement.getElementsByTagName("Position").getLength();
        for (int i = 0; i < length; i++) {
            final Element posElem = (Element)(positionsElement.getElementsByTagName("Position").item(i));
            positions.add(new Position(
                Integer.parseInt(posElem.getAttribute("row")),
                Integer.parseInt(posElem.getAttribute("column"))
            ));
        }
        return positions;
    }

}
