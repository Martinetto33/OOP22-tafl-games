package taflgames.controller.entitystate;

import taflgames.common.Player;
import taflgames.common.api.Vector;

public final class CellStateImpl implements CellState {

    private final String primaryName;
    private final Vector orientation; // NOPMD
    // The Vector class models a vector and provides features that a List does not support.
    private final Player player;

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
