package taflgames.model.cell.code;

import java.util.*;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.model.pieces.api.Piece;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.SliderMediator;
import taflgames.model.cell.api.Resettable;
import taflgames.model.cell.api.TimedEntity;

public class Slider extends AbstractCell implements TimedEntity, Resettable {

    private Vector orientation = new VectorImpl(0, 1); //un versore che indica la direzione in cui questo slider punta
	private boolean triggered = false; //dice se è già stata attivata in questo turno
	private SliderMediator mediator;
	private final Position sliderPos;
	private int lastActivityTurn;
	private boolean active;
	private static final int TURNS_FOR_REACTIVATION = 2;
    private static final int ANGLE_ROTATION = 90;

    public Slider(final Position sliderPos) {
        super();
        this.sliderPos = sliderPos;
    }

    @Override
    public boolean canAccept(Piece piece) {
        if(super.isFree()) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void notify(Position source, Piece movedPiece, List<String> events, Map<Player, Map<Position, Piece>> pieces, 
                        Map<Position, Cell> cells) {
        if (this.sliderPos.equals(source)) {
            /* Non mi importa che tipo di pezzo sia arrivato, lo slider lo fa scivolare */
            if (!this.triggered && this.active) {
                this.triggered = true;
                Position p = this.mediator.requestMove(source, this.orientation); /*Trovo la casella più lontana su cui ci si possa
                spostare seguendo la direzione del vettore orientamento */
                this.mediator.updatePiecePos(this.sliderPos, p);
            }
        }
    }

    public void reset() {
        this.triggered = false;
    }

    public void notifyTurnHasEnded(final int turn){
        if (turn - this.lastActivityTurn == Slider.TURNS_FOR_REACTIVATION) {
            this.orientation.rotate(Slider.ANGLE_ROTATION);
			this.active = true;
			this.lastActivityTurn = turn;
		}
		else {
			this.active = false;
		}
	}

    @Override
    public String getType() {
        return "Slider";
    }

    

}
