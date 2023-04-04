package taflgames.view.scenecontrollers;

import java.io.IOException;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.GameChoiceScene}.
 */
public interface GameChoiceSceneController extends BasicSceneController {

    /**
     * Switches to the scene that shows the rules of the game modes.
     */
    void goToRulesScene();

    /**
     * Prompts the creation of a classic mode match.
     * @throws IOException if an error occurs during the initialization of the match
     */
    void createClassicModeMatch() throws IOException;

    /**
     * Prompts the creation of a variant mode match.
     * @throws IOException if an error occurs during the initialization of the match
     */
    void createVariantModeMatch() throws IOException;

}
