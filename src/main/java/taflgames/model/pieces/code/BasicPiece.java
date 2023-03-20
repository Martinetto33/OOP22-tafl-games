package taflgames.model.pieces.code;

import taflgames.common.Player;
import taflgames.common.code.Position;

public class BasicPiece extends AbstractPiece{

    public BasicPiece(final Position startingPosition,final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createBasicPieceBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
    
}
