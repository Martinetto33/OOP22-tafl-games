package taflgames.model.pieces.code;

import java.util.Optional;

import taflgames.common.Player;
import taflgames.common.code.Position;

public class BasicPiece extends AbstractPiece{

    public BasicPiece(Position startingPosition, Player p, Optional<String> name) {
        this.setCurrentPosition(startingPosition);
        this.myTeam = new ImplTeam(p, name);
        this.myType = this.factory.createBasicPieceBehaviour();
        this.myType.generate();
    }
    
}
