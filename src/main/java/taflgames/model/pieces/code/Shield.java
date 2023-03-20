package taflgames.model.pieces.code;

import taflgames.common.Player;
import taflgames.common.code.Position;

public class Shield extends AbstractPiece{
    
    public Shield(final Position startingPosition,final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createShieldBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    } 
}
