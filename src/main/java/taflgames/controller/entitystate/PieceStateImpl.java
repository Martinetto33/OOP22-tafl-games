package taflgames.controller.entitystate;

import taflgames.common.Player;

/**
 * A representation of the state of a {@link taflgames.model.pieces.api.Piece}.
 * This class provides only getters, and it is used for view purposes.
 */
public final class PieceStateImpl implements PieceState {

    private final String name;
    private final Player player;

    /**
     * Builds a new PieceStateImpl.
     * @param name the type of the Piece.
     * @param player the Player whose team this Piece belongs to.
     */
    public PieceStateImpl(final String name, final Player player) {
        this.name = name;
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

}
