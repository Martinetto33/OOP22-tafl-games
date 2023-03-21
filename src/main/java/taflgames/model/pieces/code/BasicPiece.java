package taflgames.model.pieces.code;
import taflgames.common.Player;
import taflgames.common.code.Position;
/**
 * extension of AbstractPiece.
 */
public class BasicPiece extends AbstractPiece {
    /**
     * creates the basic piece starting from a starting position
     * and a player.
     * @param startingPosition
     * @param p
     */
    public BasicPiece(final Position startingPosition, final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.getFactory().createBasicPieceBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
}
