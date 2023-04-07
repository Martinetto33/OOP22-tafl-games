package taflgames.controller.entitystate;

import taflgames.common.Player;
import taflgames.common.api.Vector;

/**
 * This class provides some information about the state of the cell,
 * useful for view purposes.
 */
public final class CellStateImpl implements CellState {

    private final String primaryName;
    private final Vector orientation; // NOPMD
    // The Vector class models a vector and provides features that a List does not support.
    private final Player player;

    /**
     * Creates a new object that contains information about the state of the cell.
     * @param primaryName the name of the primary type of this Cell
     *      (if this contains a Tomb, the primary type is considered to be Tomb)
     * @param orientation the rotation of this cell on the board
     * @param player the Player associated with this cell (useful to determine the color of a Tomb)
     */
    public CellStateImpl(final String primaryName, final Vector orientation, final Player player) { // NOPMD
        // The Vector class models a vector and provides features that a List does not support.
        this.primaryName = primaryName;
        this.orientation = orientation;
        this.player = player;
    }

    @Override
    public String getPrimaryName() {
        return this.primaryName;
    }

    @Override
    public Vector getOrientation() { // NOPMD
        // The Vector class models a vector and provides features that a List does not support.
        return this.orientation;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

}
