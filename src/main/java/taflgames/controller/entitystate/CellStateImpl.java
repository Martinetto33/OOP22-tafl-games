package taflgames.controller.entitystate;

import taflgames.common.Player;
import taflgames.common.api.Vector;

public class CellStateImpl implements CellState {
    private final String primaryName;
    private final Vector orientation;
    private final Player player;

    public CellStateImpl(final String primaryName, final Vector orientation, final Player player) {
        this.primaryName = primaryName;
        this.orientation = orientation;
        this.player = player;
    }

    @Override
    public String getPrimaryName() {
        return this.primaryName;
    }

    @Override
    public Vector getOrientation() {
        return this.orientation;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
    
}
