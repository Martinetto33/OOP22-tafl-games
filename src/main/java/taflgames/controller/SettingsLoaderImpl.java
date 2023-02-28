package taflgames.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import taflgames.common.code.Position;
import taflgames.model.BoardBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SettingsLoaderImpl implements SettingsLoader {

    private static final String SEP = System.getProperty("file.separator");
    private static final String PATH = "config" + SEP;

    public SettingsLoaderImpl(final GameMode gamemode, final BoardBuilder boardBuilder) throws IOException {
        try {
            final InputStream configFile = Objects.requireNonNull(
                this.getClass().getResourceAsStream(PATH + gamemode.getConfigFileName())
            );
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            final Document document = docBuilder.parse(configFile);
            document.getDocumentElement().normalize();
            
            final NodeList nodeList = document.getElementsByTagName("Settings");
            final Element settings = (Element)(nodeList.item(0));
            this.readBoardSize(settings, boardBuilder);
            this.readKingPosition(settings, boardBuilder);
            /*
             * TO DO: read other settings
             */

            configFile.close();

        } catch (final ParserConfigurationException | SAXException e) {
            throw new IOException("Cannot read configuration file");
        }
    }

    private void readBoardSize(final Element settings, final BoardBuilder boardBuilder) {
        final int boardSize = Integer.parseInt(
            settings.getElementsByTagName("BoardSize").item(0).getTextContent()
        );
        boardBuilder.addBoardSize(boardSize);
    }

    private void readKingPosition(final Element settings, final BoardBuilder boardBuilder) {
        final Element kingPosElem = (Element)(settings.getElementsByTagName("KingPosition").item(0));
        final Element posElem = (Element)(kingPosElem.getElementsByTagName("Position").item(0));
        final Position kingPos = new Position(
            Integer.parseInt(posElem.getAttribute("row")),
            Integer.parseInt(posElem.getAttribute("column"))
        );
        boardBuilder.addThroneAndKing(kingPos);
    }

    /*
     * TO DO: define other read methods
     */

}
