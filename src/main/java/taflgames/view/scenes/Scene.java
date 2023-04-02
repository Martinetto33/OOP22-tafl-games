package taflgames.view.scenes;

import javax.swing.JPanel;

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
     * Updates the scene.
     */
    void update();

}
