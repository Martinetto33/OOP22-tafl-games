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

}
