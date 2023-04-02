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

    /**
     * The size of the font of a Button.
     */
    int BUTTON_FONT_SIZE = 18;

    /**
     * A transparent color, useful for components backgrounds.
     */
    Color TRANSPARENT = new Color(0, 0, 0, 0);

    /**
     * A color for the text on some labels.
     */
    Color LABEL_FOREGROUND_COLOR = Color.WHITE;

    /**
     * A {@link taflgames.view.fontmanager.FontManager} used
     * to manage a custom viking font, that can provide
     * Font objects.
     */
    FontManager FONT_MANAGER = new FontManager();

    /**
     * Updates the scene.
     */
    void update();

}
