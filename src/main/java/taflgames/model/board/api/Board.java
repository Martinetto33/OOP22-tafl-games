package taflgames.model.board.api;


import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Resettable;
import taflgames.model.cell.api.TimedEntity;
import taflgames.model.pieces.api.Piece;

import java.util.Set;
import taflgames.common.Player;

public interface Board {

    boolean isStartingPointValid(Position start, Player player);

    boolean isDestinationValid(Position start, Position dest, Player player);

    void updatePiecePos(Position oldPos, Position newPos);

    Position getFurthestReachablePos(Position startPos, Vector direction);

    public void signalOnMove(Position source, Piece movedPiece);

    /**
     * This method must be called by Match after method {@link #updatePiecePos}
     */
    void eat();

    /**
     * This method adds a set of resettable objects like sliders.
     * @param resettableEntities the resettable objects
    */
    void addResettableEntities(final Set<Resettable> resettableEntities);

    /**
     * This method adds a set of timed objects like sliders.
     * @param timedEntities the timed objects
     */
    void addTimedEntities(final Set<TimedEntity> timedEntities);

}
