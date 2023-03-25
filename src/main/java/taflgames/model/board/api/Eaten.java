package taflgames.model.board.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.pieces.api.Piece;

public interface Eaten {

    Set<Position> trimHitbox(Piece currentPiece,Map<Player, Map<Position, Piece>> pieces, Map<Position, Cell> cells, final int size);

    List<Piece> getThreatenedPos(Set<Position> hitbox, Map<Player, Map<Position, Piece>> pieces, Piece piece);

    Map<Piece, Set<Piece>> checkAllies(List<Piece> enemies, Map<Player, Map<Position, Piece>> pieces, Piece lastMovedPiece);

    void notifyAllThreatened( Map<Piece, Set<Piece>> enemiesAndAllies, Piece lastMovedPiece, Map<Position, Cell> cells, Map<Player, Map<Position, Piece>> pieces );
    
}
