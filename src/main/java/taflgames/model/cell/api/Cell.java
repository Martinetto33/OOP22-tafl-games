package taflgames.model.cell.api;
import java.util.*;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.pieces.api.Piece;

public interface Cell {
    
    public void notify(Position source, Piece sender, List<String> events, Map<Player, Map<Position, Piece>> pieces, Map<Position, Cell> cells);

    public boolean canAccept(Piece piece);

    public void setFree(boolean isFree);

    public String getType();

    public boolean isFree();

}
