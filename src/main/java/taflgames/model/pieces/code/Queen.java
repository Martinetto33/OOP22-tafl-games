package taflgames.model.pieces.code;

import taflgames.common.Player;
import taflgames.common.code.Position;
/**
 * extension of AbstractPiece.
 */
public class Queen extends AbstractPiece {
     /**
     * ccreates the queen starting from a starting position
     * and a player.
     * @param startingPosition
     * @param p
     */
    public Queen(final Position startingPosition, final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createQueenBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
}
