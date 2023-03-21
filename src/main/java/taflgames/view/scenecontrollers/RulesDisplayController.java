package taflgames.view.scenecontrollers;

import java.io.InputStream;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.RulesScene}.
 */
public interface RulesDisplayController extends BasicSceneController {
    
    InputStream getRulesFileStream();

}
