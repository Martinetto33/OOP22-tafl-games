package taflgames.model.pieces.code;

import taflgames.common.Player;
import taflgames.common.code.Position;

public class King extends AbstractPiece{

    public King(final Position startingPosition) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(Player.DEFENDER);
        this.setMyType(this.factory.createKingBehaviour());
        this.getMyType().generate();
        this.setCurrNumbOfLivesLimited(this.getMyType().getTotalNumbOfLives());
    }
    
}
