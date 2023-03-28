package taflgames.model.cell.api;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;

public interface SliderMediator {

    Position requestMove(Position source, Vector orientation);

    void updatePiecePos(Position startPos, Position destPos, Player currentPlayer);
}
