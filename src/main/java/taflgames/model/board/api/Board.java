package taflgames.model.board.api;


import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.Player;

public interface Board {

    public boolean isStartingPointValid(Position start, Player player);

    public boolean isDestinationValid(Position start, Position dest, Player player);

    public void updatePiecePos(Position oldPos, Position newPos);

    public Position getFurthestReachablePos(Position startPos, Vector direction);

    public void moveByVector(Vector direction);

    
}
