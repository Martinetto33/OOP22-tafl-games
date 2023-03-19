package taflgames.view.scenecontrollers;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.RulesScene}.
 */
public interface RulesDisplayController extends BasicSceneController {
    
    String loadClassicModeRules();

    String loadVariantModeRules();

}
