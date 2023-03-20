package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;

/**
 * This class implements the state of a generic scene controller (which consists of
 * the {@link View} and the {@link Controller} of the application) and the
 * basic functionalities which are implemented in the same way for
 * all scene controllers.
 */
public abstract class AbstractBasicSceneController implements BasicSceneController {
    
    private final View view;
    private final Controller controller;

    protected AbstractBasicSceneController(final View view, final Controller controller) {
        this.view = view;
        this.controller = controller;
    }

    public abstract void goToNextScene();

    public abstract void goToPreviousScene();

    @Override
    public int getViewHeight() {
        return this.view.getHeight();
    }

    @Override
    public int getViewWidth() {
        return this.view.getWidth();
    }

    /**
     * @return the controller of the application
     */
    protected Controller getController() {
        return this.controller;
    }

    /**
     * @return the view of the application
     */
    protected View getView() {
        return this.view;
    }

}
