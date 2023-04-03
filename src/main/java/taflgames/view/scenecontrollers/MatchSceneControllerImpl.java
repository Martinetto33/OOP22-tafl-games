package taflgames.view.scenecontrollers;

import java.util.Map;

import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.controller.Controller;
import taflgames.view.View;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.GameOverScene;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.MatchScene;
import taflgames.view.scenes.PieceImageInfo;

public final class MatchSceneControllerImpl extends AbstractBasicSceneController implements MatchSceneController {

    /* The controller has a reference to a Match Scene in order to update it when
     * the game state is updated.
    */
    private final MatchScene matchScene;
    
    protected MatchSceneControllerImpl(final View view, final Controller controller) {
        super(view, controller);
        this.matchScene = new MatchScene(this);
    }

    @Override
    public void goToNextScene() {
        this.getView().setScene(new GameOverScene(
            new GameOverControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public void goToPreviousScene() {
        // Exiting the match brings the view back to the home scene.
        this.getView().setScene(new HomeScene(
            new HomeControllerImpl(this.getView(), this.getController())
        ));
    }

    @Override
    public Map<Position, CellImageInfo> getCellsMapping() {
        // this.getController().getCellsDisposition().entrySet().stream()
        //         .map(entry -> {
        //             if (entry.getValue().contains("Tomb")) {
        //                 /* Devo trovare un modo di ottenere il colore dell'ultimo pezzo morto. */
        //                 return Map.entry(entry.getKey(), new CellImageInfo("CELL_TOMB", null, UP_VECTOR));
        //             }
        //             if (entry.getValue().contains("Slider")) {
        //                 return Map.entry(entry.getKey(), new CellImageInfo("CELL_SLIDER", null, null));
        //             }
        //             return null;
        //         });
        return null;
    }

    @Override
    public Map<Position, PieceImageInfo> getPiecesMapping() {
        //return this.getController().getPiecesDisposition();
        return null;
    }

    @Override
    public boolean isSourceSelectionValid(Position pos) {
        return this.getController().isStartingPointValid(pos);
    }

    @Override
    public boolean moveIfLegal(Position source, Position destination) {
        return this.getController().moveIfLegal(source, destination);
    }

    @Override
    public void updateView() {
        this.matchScene.updateBoardInstance(this.getPiecesMapping(), this.getCellsMapping());
    }

}
