package taflgames.model.board.code;

import java.util.List;
import java.util.Set;
import taflgames.common.code.Position;
import taflgames.common.api.Vector;


public interface Piece {
    
    Player getTeam();
    Set<Vector> getMovementVectors();
    boolean isAlive();
    void setAlive(boolean isAlive);
    boolean canSwap();
    Set<Position> getHitbox();
    boolean wasKilled(Set<Position> menacingPieces, Position lastMovedPiecePos);
    Set<Vector> getEffectTriggerRange();
    List<String> getSignalOnMove();
}

