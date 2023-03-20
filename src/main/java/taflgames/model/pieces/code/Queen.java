package taflgames.model.pieces.code;

import taflgames.common.Player;
import taflgames.common.code.Position;

public class Queen extends AbstractPiece{

    public Queen(final Position startingPosition,final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createQueenBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
    
}
