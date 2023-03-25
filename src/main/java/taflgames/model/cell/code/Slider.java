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
import taflgames.model.memento.api.CellMemento;

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
    
    /**
     * Returns a memento describing the current state of this Slider.
     * @return the SliderMemento.
     */
    public SliderMementoImpl save() {
        return this.new SliderMementoImpl();
    }

    /**
     * Restores the state of this slider to the one given by the SliderMemento.
     * @param sm the {@link taflgames.model.cell.code.Slider.SliderMementoImpl}
     * from which to restore this slider's status.
     */
    public void restore(SliderMementoImpl sm) {
        this.orientation = sm.getOrientation();
        this.active = sm.isActive();
        this.lastActivityTurn = sm.getLastActivityTurn();
        this.triggered = sm.isTriggered();
        /* Here is a call to the Abstract Cell, that restores itself
         * by calling the "getCellStatus()" method on this memento.
         */
        super.restore(sm);
    }

    /**
     * This class represents the saved state of a Slider.
     */
    public class SliderMementoImpl implements CellMemento {
        private final Vector orientation;
        private final boolean triggered;
        private final int lastActivityTurn;
        private final boolean active;
        private final boolean isFree;

        /**
         * Builds a Slider Memento from en existing Slider object.
         */
        public SliderMementoImpl() {
            this.orientation = Slider.this.orientation;
            this.triggered = Slider.this.triggered;
            this.lastActivityTurn = Slider.this.lastActivityTurn;
            this.active = Slider.this.active;
            this.isFree = Slider.this.isFree();
        }

        @Override
        public void restore() {
            Slider.this.restore(this);
        }

        @Override
        public boolean getCellStatus() {
            return this.isFree;
        }

        public Vector getOrientation() {
            return this.orientation;
        }

        public boolean isTriggered() {
            return this.triggered;
        }

        public int getLastActivityTurn() {
            return this.lastActivityTurn;
        }

        public boolean isActive() {
            return this.active;
        }

    }

}
