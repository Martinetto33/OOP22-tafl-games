package taflgames.view.scenecontrollers;

import java.util.List;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;

public interface MatchSceneController extends BasicSceneController {
    
    /**
     * @return the mapping of each position to the type of cell
     * (and the cells components if present) at that position.
     */
    Map<Position, List<String>> getCellsMapping();

    /**
     * @return the mapping of the positions 
     */
    Map<Player, Map<Position, String>> getPiecesMapping();

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
