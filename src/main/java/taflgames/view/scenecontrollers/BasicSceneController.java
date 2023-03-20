package taflgames.view.scenecontrollers;

/**
 * This interface defines the basic functionalities of a generic scene controller.
 */
public interface BasicSceneController {

    /**
     * Switches to the next scene.
     */
    void goToNextScene();

    /**
     * Switches to the previous scene.
     */
    void goToPreviousScene();

    /**
     * @return the height of the view
     */
    int getViewHeight();

    /**
     * @return the width of the view
     */
    int getViewWidth();

}
