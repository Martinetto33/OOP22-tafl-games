package taflgames.view.fontManager;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FontManager {

    private static final String SEP = System.getProperty("file.separator");
    private static final String PATH = "taflgames" + SEP + "font" + SEP;
    private static final String FONT_FILE_NAME = "latin_runes_v20.ttf";
    private Font font;

    public FontManager(){
        this.font = this.initialise(FontManager.FONT_FILE_NAME);
    }

    private Font initialise(String fileName) {
        try (InputStream fontFile = Objects.requireNonNull(
            ClassLoader.getSystemResourceAsStream(PATH + fileName)
        )) {
            this.font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return this.font;
        } catch (IOException ioex) {
            System.out.println("Error occurred while trying to read font file.");
            ioex.printStackTrace();
        } catch (FontFormatException e) {
            System.out.println("Error with the font format.");
            e.printStackTrace();
        }
        return null;
    }

    public Font getFont() {
        return this.font;
    }

    public Font getModifiedFont(float size, int type) {
        Font result = this.font.deriveFont(type, size);
        return result;
    }
}
