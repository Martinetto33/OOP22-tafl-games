package taflgames.model;

import java.util.List;

import org.apache.commons.collections4.iterators.LoopingIterator;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.memento.api.BoardMemento;
// import taflgames.model.memento.api.Caretaker;
import taflgames.model.memento.api.MatchMemento;
// import taflgames.model.memento.code.CaretakerImpl;

/**
 * This class implements a match.
 */
public final class MatchImpl implements Match {

    private final Board board;
    private final LoopingIterator<Player> turnQueue;
    private Player activePlayer;
    private int turnNumber;

    // A reference to the Caretaker class, that handles the "undo" operation
    // private final Caretaker caretaker;

    /**
     * Creates a new match.
     * @param board the board used for the match
     */
    public MatchImpl(final Board board) {
        this.board = board;
        this.turnQueue = new LoopingIterator<>(
            List.of(Player.ATTACKER, Player.DEFENDER)
        );
        this.setNextActivePlayer();
        this.turnNumber = 0;
        // this.caretaker = new CaretakerImpl(this);
    }

    @Override
    public Player getActivePlayer() {
        return this.activePlayer;
    }

    @Override
    public int getTurnNumber() {
        return this.turnNumber;
    }

    @Override
    public void setNextActivePlayer() {
        this.activePlayer = this.turnQueue.next();
        this.turnNumber++;
    }

    @Override
    public boolean selectSource(final Position start) {
        return this.board.isStartingPointValid(start, activePlayer);
    }

    @Override
    public boolean selectDestination(final Position start, final Position destination) {
        return this.board.isDestinationValid(start, destination, activePlayer);
    }

    @Override
    public void makeMove(final Position start, final Position destination) {
        this.board.updatePiecePos(start, destination);
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
    }

    /**
     * This inner class implements a {@link MatchMemento}, which is responsible
     * for saving the current state of the match and provide it on request.
     */
    public final class MatchMementoImpl implements MatchMemento {

        private final int turnNumber;
        private final Player activePlayer;
        private final BoardMemento boardMemento;

        /**
         * Creates a new snapshot of the current state of the match.
         * @param boardMemento the snapshot of the current state of the {@link Board}
         */
        public MatchMementoImpl(final BoardMemento boardMemento) {
            this.turnNumber = MatchImpl.this.turnNumber;
            this.activePlayer = MatchImpl.this.activePlayer;
            this.boardMemento = boardMemento;
        }

        @Override
        public int getTurnNumber() {
            return this.turnNumber;
        }

        @Override
        public Player getActivePlayer() {
            return this.activePlayer;
        }

        @Override
        public BoardMemento getBoardMemento() {
            return this.boardMemento;
        }

    }

    @Override
    public MatchMemento save() {
        return new MatchMementoImpl(this.board.save());
    }

    @Override
    public void restore(final MatchMemento matchMemento) {
        this.turnNumber = matchMemento.getTurnNumber();
        this.activePlayer = matchMemento.getActivePlayer();
        matchMemento.getBoardMemento().restore();
    }

}
