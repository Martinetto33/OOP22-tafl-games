package taflgames.controller.entitystate;

import taflgames.common.Player;
import taflgames.common.api.Vector;

/**
 * An interface representing the state of a Cell. This will be used
 * for view purposes only.
 */
public interface CellState {

    /**
     * @return the name of the primary type of this Cell
     * (if this contains a Tomb, the primary type is considered to be Tomb)
     */
    String getPrimaryName();

    /**
     * @return the rotation of this cell on the board.
     */
    Vector getOrientation();

    /**
     * @return the Player associated with this cell (useful
     * to determine the color of a Tomb).
     */
    Player getPlayer();
}
