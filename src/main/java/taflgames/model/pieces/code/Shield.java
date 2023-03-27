package taflgames.model.pieces.code;

import taflgames.common.Player;
import taflgames.common.code.Position;
/**
 * extension of AbstractPiece.
 */
public class Shield extends AbstractPiece {
    /**
     * ccreates the shield starting from a starting position
     * and a player.
     * @param startingPosition
     * @param p
     */
    public Shield(final Position startingPosition, final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.getFactory().createShieldBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
}
