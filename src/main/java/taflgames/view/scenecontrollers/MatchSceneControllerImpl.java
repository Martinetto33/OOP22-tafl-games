package taflgames.view.scenecontrollers;

import java.util.Map;
import java.util.stream.Collectors;

import taflgames.common.Player;
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

/**
 * This class implements a scene controller for a {@link taflgames.view.scenes.MatchScene}.
 */
public final class MatchSceneControllerImpl extends AbstractBasicSceneController implements MatchSceneController {

    /* The controller has a reference to a Match Scene in order to update it when
     * the game state is updated.
    */
    private final MatchScene matchScene;
    private final PieceImageMapper pieceMapper = new PieceTypeMapper();
    private final CellImageMapper cellMapper = new CellTypeMapper();
    private boolean wasMoveDone;

    /**
     * Creates a new match scene controller.
     * @param view the view of the application
     * @param controller the main controller of the application
     */
    public MatchSceneControllerImpl(final View view, final Controller controller) {
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
            new HomeSceneControllerImpl(this.getView(), this.getController())
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
    public boolean isSourceSelectionValid(final Position pos) {
        return this.getController().isStartingPointValid(pos);
    }

    @Override
    public boolean moveIfLegal(final Position source, final Position destination) {
        if (this.wasMoveDone) {
            return false;
        } else {
            final boolean isMoveLegal = this.getController().moveIfLegal(source, destination);
            if (isMoveLegal) {
                this.wasMoveDone = true;
            }
            return isMoveLegal;
        }
    }

    @Override
    public void updateView() {
        this.matchScene.updateBoardInstance(this.getPiecesMapping(), this.getCellsMapping());
    }

    @Override
    public boolean isMatchOver() {
        return this.getController().isOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        this.getController().undo();
        this.wasMoveDone = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean passTurn() {
        if (!this.wasMoveDone) {
            /* Cannot pass before a move was done. */
            return false;
        }
        this.getController().passTurn();
        this.wasMoveDone = false;
        this.updateView();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayerInTurn() {
        return this.getController().getCurrentPlayer();
    }

}
