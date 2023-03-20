package taflgames.model.pieces.code;
import taflgames.common.Player;
import taflgames.common.code.Position;
/**
 * extension of AbstractPiece.
 */
public class Archer extends AbstractPiece {
    /**
     * ccreates the archer starting from a starting position
     * and a player.
     * @param startingPosition
     * @param p
     */
    public Archer(final Position startingPosition, final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createArcherBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
}
