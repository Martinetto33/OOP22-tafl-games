package taflgames.model.board.api;


import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.code.Player;

public interface Board {

    public boolean isStartingPointValid(Position start, Player player);

    public boolean isDestinationValid(Position start, Position dest, Player player);

    public void updatePiecePos(Position oldPos, Position newPos);

    public Position getFurthestReachablePos(Vector direction);

    public void moveByVector(Vector direction);

    
}
