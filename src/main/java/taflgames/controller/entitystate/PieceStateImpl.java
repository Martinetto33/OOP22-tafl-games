package taflgames.controller.entitystate;

import taflgames.common.Player;

public class PieceStateImpl implements PieceState {

    private final String name;
    private final Player player;

    public PieceStateImpl(final String name, final Player player) {
        this.name = name;
        this.player = player;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
    
}
