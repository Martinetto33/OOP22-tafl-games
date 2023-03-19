package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.GameChoiceScene;

/**
 * This class implements a scene controller for a {@link taflgames.view.scenes.RulesScene}.
 */
public class RulesDisplayControllerImpl extends AbstractBasicSceneController implements RulesDisplayController {

    public RulesDisplayControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    @Override
    public void goToNextScene() {
        /*
         * There is no scene after the rules display scene; only going back to
         * game choice scene is allowed.
         */
    }

    @Override
    public void goToPreviousScene() {
        this.getView().setScene(new GameChoiceScene(
            new GameChoiceControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public String loadClassicModeRules() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadClassicModeRules");
    }

    @Override
    public String loadVariantModeRules() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadVariantModeRules'");
    }
    
}
