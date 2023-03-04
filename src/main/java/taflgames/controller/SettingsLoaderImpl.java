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

    private Set<Position> getPositionsByTag(final String tagName, final Element settings) {
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
        final Set<Position> exitsPositions = getPositionsByTag("ExitsPositions", settings);
        boardBuilder.addExits(exitsPositions);
    }

    private void loadBasicPiecesData(final Element settings, final BoardBuilder boardBuilder) {

        final Map<Player, Set<Position>> basicPiecesPositions = new HashMap<>();

        final Set<Position> attackerBasicPiecesPositions = getPositionsByTag("AttackerBasicPiecesPositions", settings);
        basicPiecesPositions.put(Player.ATTACKER, attackerBasicPiecesPositions);
        final Set<Position> defenderBasicPiecesPositions = getPositionsByTag("DefenderBasicPiecesPositions", settings);
        basicPiecesPositions.put(Player.DEFENDER, defenderBasicPiecesPositions);

        boardBuilder.addBasicPieces(basicPiecesPositions);
    }

}
