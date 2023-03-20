package taflgames.model.board.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.pieces.Piece;

public interface Eaten {

    public void trimHitbox(Set<Position> hitbox);

    public List<Position> getThreatenedPos(Set<Position> hitbox, Map<Player, Map<Position, Piece>> pieces, Piece piece);

    public Map<Position, List<Position>> checkAllies(List<Position> enemies, Map<Player, Map<Position, Piece>> pieces, Player currPlayer);

    public void notifyAllThreatened( Map<Position, List<Position>> enemiesAndAllies, Piece lastMovedPiece, Map<Player, Map<Position, Piece>> pieces);
    
}
