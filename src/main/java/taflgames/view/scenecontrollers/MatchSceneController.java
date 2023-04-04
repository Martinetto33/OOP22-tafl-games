package taflgames.view.scenecontrollers;

import java.util.Map;

import taflgames.common.code.Position;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

public interface MatchSceneController extends BasicSceneController {
    
    /**
     * @return the mapping of each position to the type of cell
     * (and the cells components if present) at that position.
     */
    Map<Position, CellImageInfo> getCellsMapping();

    /**
     * @return the mapping of the positions 
     */
    Map<Position, PieceImageInfo> getPiecesMapping();

    /**
     * Updates the view with the information it needs to redraw correctly the
     * current state of the match.
     */
    void updateView();

    /**
     * @return if the selected source is valid or not
     */
    boolean isSourceSelectionValid(Position pos);

    /**
     * Tells the controller to make the move from {@code source} to {@code destination}
     * if the move is legal.
     * @param source the coordinates of the selected source cell
     * @param destination the coordinates of the selected destination cell
     * @return true if the move is legal and then performed, false otherwise
     */
    boolean moveIfLegal(Position source, Position destination);

}
