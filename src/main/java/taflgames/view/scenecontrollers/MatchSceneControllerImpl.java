package taflgames.view.scenecontrollers;

import java.util.List;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.controller.Controller;
import taflgames.view.View;

public final class MatchSceneControllerImpl extends AbstractBasicSceneController implements MatchSceneController {

    protected MatchSceneControllerImpl(final View view, final Controller controller) {
        super(view, controller);
    }

    @Override
    public void goToNextScene() {
        // TODO:
        // this.getView().setScene(new GameOverScene(
        //     new GameOverControllerImpl(this.getView(), this.getController())
        // ));
    }

    @Override
    public void goToPreviousScene() {
        // TODO:
        // Exiting the match brings the view back to the home scene.
        // this.getView().setScene(new HomeScene(
        //     new HomeControllerImpl(this.getView(), this.getController())
        // ));
    }

    @Override
    public Map<Position, List<String>> getCellsMapping() {
        return this.getController().getCellsDisposition();
    }

    @Override
    public Map<Player, Map<Position, String>> getPiecesMapping() {
        return this.getController().getPiecesDisposition();
    }

    @Override
    public boolean isSourceSelectionValid(Position pos) {
        return this.getController().isStartingPointValid(pos);
    }

    @Override
    public boolean moveIfLegal(Position source, Position destination) {
        return this.getController().moveIfLegal(source, destination);
    }

}
