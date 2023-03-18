package taflgames.view.scenecontrollers;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.GameChoiceScene}.
 */
public interface GameChoiceController extends BasicSceneController {

    /**
     * Prompts the creation of a classic mode match.
     */
    void createClassicModeMatch();

    /**
     * Prompts the creation of a variant mode match.
     */
    void createVariantModeMatch();

}
