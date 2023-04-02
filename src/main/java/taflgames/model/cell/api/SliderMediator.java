package taflgames.model.cell.api;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;

public interface SliderMediator {

    Position requestMove(Position source, Vector orientation);  // NOPMD
    // The Vector class models a vector and provides features that a List does not support.

    void updatePiecePos(Position startPos, Position destPos, Player currentPlayer);
}
