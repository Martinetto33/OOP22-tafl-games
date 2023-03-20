package taflgames.model.pieces.code;
import taflgames.common.Player;
import taflgames.common.code.Position;
/**
 * extension of AbstractPiece.
 */
public class King extends AbstractPiece {
    /**
     * ccreates the king starting from a starting position
     * and a player.
     * @param startingPosition
     */
    public King(final Position startingPosition) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(Player.DEFENDER);
        this.setMyType(this.getFactory().createKingBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
}
