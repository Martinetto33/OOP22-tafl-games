package taflgames.model.pieces.code;

import taflgames.common.Player;
import taflgames.common.code.Position;
/**
 * extension of AbstractPiece.
 */
public class Swapper extends AbstractPiece {
    /**
     * ccreates the swapper starting from a starting position
     * and a player.
     * @param startingPosition
     * @param p
     */
    public Swapper(final Position startingPosition, final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createSwapperBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
}
