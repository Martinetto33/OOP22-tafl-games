package taflgames.view.scenes;

import javax.swing.JPanel;

/**
 * This abstract class defines the abstract model of a {@link Scene}.
 */
public abstract class AbstractScene implements Scene {

    private final String sceneName;
    private final JPanel scenePanel;
    private static final String FONT_NAME = "Serif";

    /**
     * Initializes the scene state.
     * @param sceneName the name of the scene
     */
    protected AbstractScene(final String sceneName) {
        this.sceneName = sceneName;
        this.scenePanel = new JPanel();
    }

    @Override
    public final String getSceneName() {
        return this.sceneName;
    }

    @Override
    public final JPanel getScene() {
        return this.scenePanel;
    }

    public static String getFont() {
        return AbstractScene.FONT_NAME;
    }

}
