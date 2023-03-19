package taflgames.model.pieces.code;

import taflgames.common.Player;
import taflgames.common.code.Position;

public class Archer extends AbstractPiece{

    public Archer(final Position startingPosition,final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createArcherBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
    
}
