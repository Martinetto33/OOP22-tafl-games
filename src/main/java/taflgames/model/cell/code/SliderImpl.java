package taflgames.model.cell.code;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.controller.entitystate.CellState;
import taflgames.controller.entitystate.CellStateImpl;
import taflgames.model.pieces.api.Piece;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.SliderMediator;
import taflgames.model.memento.api.CellComponentMemento;
import taflgames.model.memento.api.CellMemento;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.Slider;

/**
 * Thid class models a Slider {@link taflgames.model.cell.api.Slider}.
 */
public final class SliderImpl extends AbstractCell implements Slider {

    // A versor that indicates the direction of the sliding
    private Vector orientation = new VectorImpl(0, 1);  // NOPMD
    // The Vector class models a vector and provides features that a List does not support.

    private boolean triggered; // indicates if the slider has already been activated in this turn
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
    @SuppressFBWarnings(
        value = "UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR",
        justification = "SliderImpl.mediator is initialized in BoardImpl constructor,"
                + "since the mediator of the slider must have a reference to the Board."
    )
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
            Objects.requireNonNull(this.mediator);
            final Position newPosition = this.mediator.requestMove(source, this.orientation);
            // Find the furthest reachable cell in the direction defined by the orrientation of the slider
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getOrientation() {    // NOPMD
        // The Vector class models a vector and provides features that a List does not support.
        return this.orientation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState getSubclassCellState() {
        return new CellStateImpl(this.getType(), this.getOrientation(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellMemento save() {
        return this.new SliderMementoImpl(super.getComponents().stream()
                    .map(component -> component.saveComponentState())
                    .collect(Collectors.toUnmodifiableSet()),
                super.getJustAddedComponents().stream()
                    .map(component -> component.saveComponentState())
                    .collect(Collectors.toUnmodifiableSet()),
                super.isFree());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restore(final CellMemento cm) {
        super.restore(cm);
    }

    /**
     * A class modelling a CellMemento for a Slider. Its methods override the
     * ones of Abstract Cell, since Sliders have additional fields
     * that need to be restored and saved. 
     */
    public class SliderMementoImpl implements CellMemento {

        private final boolean innerTriggered; //dice se è già stata attivata in questo turno
        private final int innerLastActivityTurn;
        private final boolean innerActive;
        private final boolean innerCellStatus;
        private final Set<CellComponentMemento> innerComponents;
        private final Set<CellComponentMemento> innerJustAddedComponents;

        /**
         * Builds a new SliderMementoImpl.
         * @param components the Set of all the {@link taflgames.model.cell.api.CellComponent} attached
         * to this Cell.
         * @param justAddedComponents the Set of all the {@link taflgames.model.cell.api.CellComponent}
         * that were attached this turn.
         * @param cellStatus the status of this Cell (true if this Cell is free, false if it is occupied).
         */
        @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = """
                A method to get a copy of an object of type CellComponentMemento is not provided.
                CellComponentMemento are guaranteed not to be changed by any code.
                """
        )
        public SliderMementoImpl(final Set<CellComponentMemento> components,
                                 final Set<CellComponentMemento> justAddedComponents, final boolean cellStatus) {
            this.innerTriggered = SliderImpl.this.triggered;
            this.innerLastActivityTurn = SliderImpl.this.lastActivityTurn;
            this.innerActive = SliderImpl.this.active;
            this.innerComponents = components.stream().collect(Collectors.toUnmodifiableSet());
            this.innerJustAddedComponents = justAddedComponents.stream().collect(Collectors.toUnmodifiableSet());
            this.innerCellStatus = cellStatus;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void restore() {
            SliderImpl.this.restore(this);
            SliderImpl.this.active = this.innerActive;
            SliderImpl.this.lastActivityTurn = this.innerLastActivityTurn;
            SliderImpl.this.triggered = this.innerTriggered;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean getCellStatus() {
            return this.innerCellStatus;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<CellComponentMemento> getComponentMementos() {
            if (!this.innerJustAddedComponents.isEmpty()) {
                this.innerComponents.removeAll(this.innerJustAddedComponents);
            }
            return this.innerComponents.stream().toList();
        }
    }
}
