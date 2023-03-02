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
    private static final String PATH = "config" + SEP;

    public SettingsLoaderImpl() {
        
    }

    public void loadClassicModeConfig(final BoardBuilder boardBuilder) throws IOException {
        try {
            final InputStream configFile = Objects.requireNonNull(
                this.getClass().getResourceAsStream(PATH + "ClassicModeSettings.xml")
            );
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            final Document document = docBuilder.parse(configFile);
            document.getDocumentElement().normalize();
            
            final NodeList nodeList = document.getElementsByTagName("Settings");
            final Element settings = (Element)(nodeList.item(0));
            this.loadBoardSize(settings, boardBuilder);
            this.loadKingData(settings, boardBuilder);
            this.loadExitsData(settings, boardBuilder);
            this.loadBasicPiecesData(settings, boardBuilder);

            configFile.close();

        } catch (final ParserConfigurationException | SAXException e) {
            throw new IOException("Cannot read configuration file");
        }
    }

    public void loadVariantModeConfig(final BoardBuilder boardBuilder) throws IOException {
        /*
         * TO DO
         */
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
        final Set<Position> exitsPositions = new HashSet<>();
        final Element exitsPosElem = (Element)(settings.getElementsByTagName("ExitsPositions").item(0));
        final int length = exitsPosElem.getElementsByTagName("Position").getLength();
        for (int i = 0; i < length; i++) {
            final Element posElem = (Element)(exitsPosElem.getElementsByTagName("Position").item(i));
            exitsPositions.add(new Position(
                Integer.parseInt(posElem.getAttribute("row")),
                Integer.parseInt(posElem.getAttribute("column"))
            ));
        }
        boardBuilder.addExits(exitsPositions);
    }

    private void loadBasicPiecesData(final Element settings, final BoardBuilder boardBuilder) {
        
        final Map<Player, Set<Position>> basicPiecesPositions = new HashMap<>();
        
        final Set<Position> attackerBasicPiecesPositions = new HashSet<>();
        final Element attackerPiecesPosElem = (Element)(settings.getElementsByTagName("AttackerBasicPiecesPositions").item(0));
        int length = attackerPiecesPosElem.getElementsByTagName("Position").getLength();
        for (int i = 0; i < length; i++) {
            final Element posElem = (Element)(attackerPiecesPosElem.getElementsByTagName("Position").item(i));
            attackerBasicPiecesPositions.add(new Position(
                Integer.parseInt(posElem.getAttribute("row")),
                Integer.parseInt(posElem.getAttribute("column"))
            ));
        }
        basicPiecesPositions.put(Player.ATTACKER, attackerBasicPiecesPositions);

        final Set<Position> defenderBasicPiecesPositions = new HashSet<>();
        final Element defenderPiecesPosElem = (Element)(settings.getElementsByTagName("DefenderBasicPiecesPositions").item(0));
        length = defenderPiecesPosElem.getElementsByTagName("Position").getLength();
        for (int i = 0; i < length; i++) {
            final Element posElem = (Element)(defenderPiecesPosElem.getElementsByTagName("Position").item(i));
            defenderBasicPiecesPositions.add(new Position(
                Integer.parseInt(posElem.getAttribute("row")),
                Integer.parseInt(posElem.getAttribute("column"))
            ));
        }
        basicPiecesPositions.put(Player.DEFENDER, defenderBasicPiecesPositions);
        
        boardBuilder.addBasicPieces(basicPiecesPositions);
    }

}
