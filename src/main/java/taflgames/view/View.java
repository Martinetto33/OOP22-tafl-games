package taflgames.view;

import taflgames.view.scenes.Scene;

/**
 * The view of the application.
 */
public interface View {

    /**
     * Sets the scene to show.
     * @param scene the scene to show
     */
    void setScene(Scene scene);

    /**
     * @return the height of the view
     */
    int getHeight();

    /**
     * @return the width of the view
     */
    int getWidth();

    /**
     * Updates the view.
     */
    void update();

    /**
     * Closes the application.
     */
    void close();

}
