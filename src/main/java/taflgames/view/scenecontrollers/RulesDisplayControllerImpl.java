package taflgames.view.scenecontrollers;

import java.io.InputStream;
import java.util.Objects;

import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.GameChoiceScene;

/**
 * This class implements a scene controller for a {@link taflgames.view.scenes.RulesScene}.
 */
public class RulesDisplayControllerImpl extends AbstractBasicSceneController implements RulesDisplayController {

    private static final String SEP = System.getProperty("file.separator");
    private static final String FILE_NAME = "Rules.html"; 
    private static final String FILE_PATH = "taflgames"
            + SEP + "rules"
            + SEP + FILE_NAME;

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
    public InputStream getRulesFileStream() {
        final InputStream stream = Objects.requireNonNull(
            ClassLoader.getSystemResourceAsStream(FILE_PATH)
        );
        return stream;
    }
    
}
