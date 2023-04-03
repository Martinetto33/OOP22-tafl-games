package taflgames.controller.entitystate;

import taflgames.common.Player;

/**
 * An interface representing the state of a Piece. This will be used
 * for view purposes only.
 */
public interface PieceState {

    /**
     * @return the name of the Piece.
     */
    String getName();

    /**
     * @return the Player owning this Piece.
     */
    Player getPlayer();
}
