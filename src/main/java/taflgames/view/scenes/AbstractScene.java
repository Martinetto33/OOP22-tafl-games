package taflgames.view.scenes;

import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import taflgames.view.fontmanager.FontManager;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.net.URL;

/**
 * This abstract class defines the abstract model of a {@link Scene}.
 */
public abstract class AbstractScene implements Scene {

    private final String sceneName;
    private final JPanel scene;
    private static final String SEP = "/";
    private static final String ROOT = "taflgames" + SEP;
    private static final String BG_FILE_NAME = "background.jpeg";
    private static final String COMPONENT_BACKGROUND = "wooden-plank.jpg";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final Color LABEL_TEXT_COLOR = Color.WHITE;
    private static final FontManager FONT_MANAGER = new FontManager();
    private static final Insets DEFAULT_INSETS = new Insets(20, 20, 20, 20);

    /**
     * Initializes the scene state.
     * @param sceneName the name of the scene
     */
    protected AbstractScene(final String sceneName) {
        this.sceneName = sceneName;
        this.scene = new JPanel() {
            @Override
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final URL imgURL = ClassLoader.getSystemResource(ROOT + "images" + SEP + BG_FILE_NAME);
                final Image image = Toolkit.getDefaultToolkit().getImage(imgURL);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    @Override
    public void update() {
        /*
         * By default, the scene has no particular behavior after user input (apert from scene switching).
         * If the scene does, instead, this method has to be overridden in its implementation.
         */
    }

    @Override
    public final String getSceneName() {
        return this.sceneName;
    }

    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Providing a base scene with some common parameters already set"
                + "is one of the purposes of this class."
    )
    @Override
    public final JPanel getScene() {
        return this.scene;
    }

    /**
     * @return the default font manager for the scene.
     */
    protected static FontManager getFontManager() {
        return FONT_MANAGER;
    }

    /**
     * @return the transparency setting for the components.
     */
    protected static Color getTransparency() {
        return TRANSPARENT;
    }

    /**
     * @return the default color for the labels text.
     */
    protected static Color getLabelTextColor() {
        return LABEL_TEXT_COLOR;
    }

    /**
     * @return the default insets for the container border
     */
    protected static Insets getDefaultInsets() {
        return DEFAULT_INSETS;
    }

    /**
     * Paints the background of a given Component and adds it to the main panel.
     * @param mainPanel the Panel that will contain the painted component.
     * @param width the width of the Component.
     * @param height the height of the Component.
     * @param component the Component to paint the background of.
     */
    public void addComponentBackground(final JPanel mainPanel, final int width, final int height, final Component component) {
        final JPanel paintedPanel = new JPanel() {
            @Override
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final URL imgURL = ClassLoader.getSystemResource(ROOT + "images" + SEP + COMPONENT_BACKGROUND);
                final Image image = Toolkit.getDefaultToolkit().getImage(imgURL);
                image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        paintedPanel.add(component);
        mainPanel.add(paintedPanel);
    }

    /**
     * Resizes an Image object to specified dimensions.
     * @param image the Image to be resized.
     * @param width the new width of the Image.
     * @param height the new height of the Image.
     */
    public void customResize(final Image image, final int width, final int height) {
        image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

}
