package taflgames.view.scenecontrollers;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.GameChoiceScene;

/**
 * This class implements home scene controller.
 */
public final class HomeControllerImpl implements HomeController {

    private final View view;
    private final Controller controller;

    /**
     * Creates a new home scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public HomeControllerImpl(final View view, final Controller controller) {
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void goToNextScene() {
        this.view.setScene(new GameChoiceScene(new GameChoiceControllerImpl(this.view, this.controller)));
    }

    @Override
    public void goToPreviousScene() {
        // There is no scene before the home scene
    }

    @Override
    public void close() {
        this.view.close();
    }

    @Override
    public int getViewHeight() {
        return this.view.getHeight();
    }

    @Override
    public int getViewWidth() {
        return this.view.getWidth();
    }

}
