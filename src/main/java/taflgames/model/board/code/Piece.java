package taflgames.model.board.code;

import java.util.Set;
import javax.swing.text.Position;
import org.w3c.dom.events.Event;
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
    Event getSignalOnMove();
}

