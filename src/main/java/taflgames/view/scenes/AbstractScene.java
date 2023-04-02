package taflgames.view.scenes;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Optional;

/**
 * This abstract class defines the abstract model of a {@link Scene}.
 */
public abstract class AbstractScene implements Scene {

    private final String sceneName;
    private final JPanel scene;
    private static final String DEFAULT_FONT_NAME = "";
    private static final int DEFAULT_FONT_SIZE = 15;
    private static final String SEP = System.getProperty("file.separator");
    private static final String ROOT = "taflgames" + SEP;
    private static final String COMPONENT_BACKGROUND = "wooden-plank.jpg";

    /**
     * Initializes the scene state.
     * @param sceneName the name of the scene
     * @param bgFileName the name of the file of the background image
     */
    protected AbstractScene(final String sceneName, final Optional<String> bgFileName) {
        this.sceneName = sceneName;
        this.scene = new JPanel() {
            @Override
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                if (bgFileName.isPresent()) {
                    final URL imgURL = ClassLoader.getSystemResource(ROOT + "images" + SEP + bgFileName.get());
                    final Image image = Toolkit.getDefaultToolkit().getImage(imgURL);
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    }

    @Override
    public final String getSceneName() {
        return this.sceneName;
    }

    @Override
    public final JPanel getScene() {
        return this.scene;
    }

    /**
     * @return the default font used for the text of the scene
     */
    public static Font getDefaultFont() {
        return new Font(DEFAULT_FONT_NAME, Font.BOLD, DEFAULT_FONT_SIZE);
    }

    /**
     * Paints the background of a given Component and adds it to the main panel.
     * @param mainPanel the Panel that will contain the painted component.
     * @param width the width of the Component.
     * @param height the height of the Component.
     * @param component the Component to paint the background of.
     */
    public void addComponentBackground(JPanel mainPanel, int width, int height, Component component) {
        final JPanel paintedPanel = new JPanel() {
            @Override
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final URL imgURL = ClassLoader.getSystemResource(ROOT + SEP + "images" + SEP + COMPONENT_BACKGROUND);
                final Image image = Toolkit.getDefaultToolkit().getImage(imgURL);
                customResize(image, width, height);
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
    public void customResize(Image image, int width, int height) {
        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
