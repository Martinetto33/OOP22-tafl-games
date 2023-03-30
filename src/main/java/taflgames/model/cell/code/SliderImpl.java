package taflgames.model.cell.code;

import java.util.*;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.model.pieces.api.Piece;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.SliderMediator;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.Slider;

public class SliderImpl extends AbstractCell implements Slider {

    private Vector orientation = new VectorImpl(0, 1); //un versore che indica la direzione in cui questo slider punta
	private boolean triggered = false; //dice se è già stata attivata in questo turno
	private SliderMediator mediator;
	private final Position sliderPos;
	private int lastActivityTurn;
	private boolean active;
	private static final int TURNS_FOR_REACTIVATION = 2;
    private static final int ANGLE_ROTATION = 90;

    public SliderImpl(final Position sliderPos) {
        super();
        this.sliderPos = sliderPos;
        this.active = true;
        this.triggered = false;
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
                Position newPosition = this.mediator.requestMove(source, this.orientation); /*Trovo la casella più lontana su cui ci si possa
                spostare seguendo la direzione del vettore orientamento */
                this.mediator.updatePiecePos(this.sliderPos, newPosition, movedPiece.getPlayer());
                
                if (movedPiece.getCurrentPosition().equals(this.sliderPos)) {
                    /* If the piece is still here after requesting a move to the Mediator,
                     * it means it is stuck by obstacles in its way and therefore any
                     * CellComponents attached to this cell should be notified.
                    */
                    super.updateComponents(source, movedPiece, events, pieces, cells);
                }
            }
        }
    }

    public void reset() {
        this.triggered = false;
    }

    public void notifyTurnHasEnded(final int turn){
        if (turn - this.lastActivityTurn == SliderImpl.TURNS_FOR_REACTIVATION) {
            this.orientation = this.orientation.rotate(SliderImpl.ANGLE_ROTATION).get();
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

    public void addMediator(final Board board) {
        this.mediator = new SliderMediatorImpl(board);
    }

}
