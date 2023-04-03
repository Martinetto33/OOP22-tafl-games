package taflgames.view.scenecontrollers;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.HomeScene}.
 */
public interface HomeController extends BasicSceneController {

    /**
     * Closes the application.
     */
    void close();

    /**
     * Moves to the High Score scene.
     */
    void goToHighScoreScene();

}
