package taflgames.view.fontmanager;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.view.scenes.Scene;

/**
 * This class can use a custom font located in the resources packages.
 * Any .ttf file can be potentially used. A rune-like font was chosen to fit
 * better the style of the game.
 */
public class FontManager {

    private static final String SEP = System.getProperty("file.separator");
    private static final String PATH = "taflgames" + SEP + "font" + SEP;
    private static final String FONT_FILE_NAME = "latin_runes_v20.ttf";
    private static final Logger LOGGER = LoggerFactory.getLogger(FontManager.class);

    private Font font;

    /**
     * Builds a new FontManager, initialising its font.
     */
    public FontManager() {
        this.font = this.initialise(FontManager.FONT_FILE_NAME);
    }

    private Font initialise(final String fileName) {
        try (InputStream fontFile = Objects.requireNonNull(
            ClassLoader.getSystemResourceAsStream(PATH + fileName)
        )) {
            this.font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return this.font;
        } catch (IOException ioex) {
            LOGGER.error("Error occurred while trying to read font file.", ioex);
        } catch (FontFormatException e) {
            LOGGER.error("Error with the font format.", e);
        }
        return null;
    }

    /**
     * A 1-sized instance of the font this class manages.
     * @return the Font
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * A custom-sized and styled font.
     * @param size the dimension of the font (normal JButtons use font size 12)
     * @param type the style of the font, which should be a constant defined in the class Font
     * (e.g. Font.PLAIN)
     * @return the modified Font
     */
    public Font getModifiedFont(final float size, final int type) {
        return this.font.deriveFont(type, size);
    }

    /**
     * A {@link java.awt.Font} adapted for the buttons.
     * @return the button Font
     */
    public Font getButtonFont() {
        return this.font.deriveFont(Font.PLAIN, Scene.BUTTON_FONT_SIZE);
    }
}
