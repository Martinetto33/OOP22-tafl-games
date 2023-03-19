package taflgames.model.pieces.code;


import taflgames.common.Player;
import taflgames.common.code.Position;

public class Swapper extends AbstractPiece {

    public Swapper(final Position startingPosition,final Player p) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createSwapperBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
    
}
