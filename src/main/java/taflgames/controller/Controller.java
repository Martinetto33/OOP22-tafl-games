package taflgames.controller;

import java.io.IOException;

/**
 * This interface describes the controller of the application.
 */
public interface Controller {

    /**
     * Initializes a new classic mode match.
     * @throws IOException if an error occurs during the initialization of the match
     */
    void createClassicModeMatch() throws IOException;

    /**
     * Initializes a new variant mode match.
     * @throws IOException if an error occurs during the initialization of the match
     */
    void createVariantModeMatch() throws IOException;

}
