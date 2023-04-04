package taflgames.model.cell.code;

import java.util.List;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.controller.entitystate.CellState;
import taflgames.controller.entitystate.CellStateImpl;
import taflgames.model.pieces.api.Piece;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.SliderMediator;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.Slider;

/**
 * Thid class models a Slider {@link taflgames.model.cell.api.Slider}.
 */
public final class SliderImpl extends AbstractCell implements Slider {

    // Un versore che indica la direzione in cui questo slider punta
    private Vector orientation = new VectorImpl(0, 1);  // NOPMD
    // The Vector class models a vector and provides features that a List does not support.

    private boolean triggered; //dice se è già stata attivata in questo turno
    private SliderMediator mediator;
    private final Position sliderPos;
    private int lastActivityTurn;
    private boolean active;
    private static final int TURNS_FOR_REACTIVATION = 2;
    private static final int ANGLE_ROTATION = 90;

    /**
     * Create a new SliderImpl in the Position that is given.
     * @param sliderPos the Position of the map where there will be a Slider.
     */
    public SliderImpl(final Position sliderPos) {
        super();
        this.sliderPos = sliderPos;
        this.active = true;
        this.triggered = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAccept(final Piece piece) {
        return super.isFree();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(
        final Position source,
        final Piece movedPiece,
        final List<String> events,
        final Map<Player, Map<Position, Piece>> pieces, 
        final Map<Position, Cell> cells
    ) {
        if (this.sliderPos.equals(source) && !this.triggered && this.active) {
            this.triggered = true;
            final Position newPosition = this.mediator.requestMove(source, this.orientation);
            /*Trovo la casella più lontana su cui ci si possa spostare seguendo la direzione del vettore orientamento */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.triggered = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyTurnHasEnded(final int turn) {
        if (turn - this.lastActivityTurn == SliderImpl.TURNS_FOR_REACTIVATION) {
            this.orientation = this.orientation.rotate(SliderImpl.ANGLE_ROTATION).get();
            this.active = true;
            this.lastActivityTurn = turn;
        } else {
            this.active = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "Slider";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMediator(final Board board) {
        this.mediator = new SliderMediatorImpl(board);
    }

    @Override
    public Vector getOrientation() {
        return this.orientation;
    }

    @Override
    public final CellState getSubclassCellState() {
        return new CellStateImpl(this.getType(), this.getOrientation(), null);
    }
}
