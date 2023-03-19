package taflgames.view.scenes;

import java.awt.Color;

import taflgames.view.fontManager.FontManager;

/**
 * A class only containing constants useful to {@link taflgames.view.scenes.Scene}
 * implementing objects. This class was created in order to avoid useless repetition
 * and creation of the same constants or multiple instances of 
 * {@link taflgames.view.fontManager.FontManager} among the scene classes.
 */
public class SceneConstants {
    public static final int BUTTON_FONT_SIZE = 18;
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color LABEL_FOREGROUND_COLOR = Color.WHITE;
    public static final FontManager FONT_MANAGER = new FontManager();
}
