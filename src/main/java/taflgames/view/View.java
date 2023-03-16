package taflgames.view;

import taflgames.controller.Controller;
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
     * Closes the application.
     */
    void close();

    /**
     * @return the controller of the application
     */
    Controller getController();

}
