package taflgames.model;

import taflgames.common.api.Position;
import taflgames.common.api.Vector;
import taflgames.common.Player;

public interface Board {
    
    boolean isStartingPointValid(Position start, Player player);

    boolean isDestinationValid(Position start, Position dest, Player player);

    void updatePiecePos(Position oldPos, Position newPos);

    Position getFurthestReachablePos(Vector direction);

    void moveByVector(Vector direction);

}
