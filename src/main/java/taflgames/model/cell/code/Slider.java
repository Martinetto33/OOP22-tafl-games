package taflgames.model.cell.code;

import java.util.*;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.code.Piece;
import taflgames.model.cell.api.Cell;

public class Slider extends AbstractCell{

    private Vector direction;
    //private Mediator mediator;

    public Slider() {
        super();
    }

    @Override
    public boolean canAccept(Piece piece) {
        return false;
    }
    
    public void notify(Position source, Piece movedPiece, List<String> events) {
        if (events.contains("MOVE")) {
            //mediator.requestMove(source, direction);
        }
    }

    public void reset() {

    }

    public void notifyTurnHasEnded(final int turn){
        direction.rotate(90);
    }

    @Override
    public String getType() {
        return "Slider";
    }
    
}
