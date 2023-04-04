package taflgames.view.scenecontrollers;

import java.io.InputStream;
import java.util.Objects;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.GameChoiceScene;

/**
 * This class implements a scene controller for a {@link taflgames.view.scenes.RulesScene}.
 */
public final class RulesSceneControllerImpl extends AbstractBasicSceneController implements RulesSceneController {

    private static final String SEP = System.getProperty("file.separator");
    private static final String FILE_NAME = "Rules.html"; 
    private static final String FILE_PATH = "taflgames"
            + SEP + "rules"
            + SEP + FILE_NAME;

    /**
     * Creates a new rules scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public RulesSceneControllerImpl(final View view, final Controller controller) {
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
            new GameChoiceSceneControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public InputStream getRulesFileStream() {
        return Objects.requireNonNull(
            ClassLoader.getSystemResourceAsStream(FILE_PATH)
        );
    }

}
