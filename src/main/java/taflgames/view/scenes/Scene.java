package taflgames.view.scenes;

import javax.swing.JPanel;
import java.awt.Color;

import taflgames.view.fontmanager.FontManager;

/**
 * This interface describes a scene to display.
 */
public interface Scene {

    /**
     * @return the name of the scene
     */
    String getSceneName();

    /**
     * @return the scene to display
     */
    JPanel getScene();

    static final int BUTTON_FONT_SIZE = 18;
    static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    static final Color LABEL_FOREGROUND_COLOR = Color.WHITE;
    static final FontManager FONT_MANAGER = new FontManager();
}
