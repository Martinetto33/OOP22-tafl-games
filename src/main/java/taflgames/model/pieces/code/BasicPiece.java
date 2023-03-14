package taflgames.model.pieces.code;

import java.util.Optional;

import taflgames.common.Player;
import taflgames.common.code.Position;

public class BasicPiece extends AbstractPiece{

    public BasicPiece(Position startingPosition, Player p, Optional<String> name) {
        this.setCurrentPosition(startingPosition);
        this.setMyPlayer(p);
        this.setMyType(this.factory.createBasicPieceBehaviour());
        this.getMyType().generate();
    }
    
}
