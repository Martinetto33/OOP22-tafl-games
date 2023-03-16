package taflgames.view.scenecontrollers;

/**
 * This interface extends a generic {@link SceneController}
 * to add specific functions for the game choice scene.
 */
public interface GameChoiceController extends SceneController {

    /**
     * Prompts the creation of a classic mode match.
     */
    void createClassicModeMatch();

    /**
     * Prompts the creation of a variant mode match.
     */
    void createVariantModeMatch();

}
