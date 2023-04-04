package taflgames.view.scenecontrollers;

import java.util.Map;
import java.util.stream.Collectors;

import taflgames.common.code.Position;
import taflgames.controller.Controller;
import taflgames.controller.mapper.CellImageMapper;
import taflgames.controller.mapper.CellTypeMapper;
import taflgames.controller.mapper.PieceImageMapper;
import taflgames.controller.mapper.PieceTypeMapper;
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
    private final PieceImageMapper pieceMapper = new PieceTypeMapper();
    private final CellImageMapper cellMapper = new CellTypeMapper();
    
    protected MatchSceneControllerImpl(final View view, final Controller controller) {
        super(view, controller);
        this.matchScene = new MatchScene(this);
        //this.pieceMapper = new PieceTypeMapper();
        //this.cellMapper = new CellTypeMapper();
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
        return this.getController().getCellsDisposition().entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(
                    Map.Entry::getKey, elem -> this.cellMapper.mapToImage(elem.getValue())));
    }

    @Override
    public Map<Position, PieceImageInfo> getPiecesMapping() {
        return this.getController().getPiecesDisposition().entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(
                    Map.Entry::getKey, elem -> this.pieceMapper.mapToImage(elem.getValue())));
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
