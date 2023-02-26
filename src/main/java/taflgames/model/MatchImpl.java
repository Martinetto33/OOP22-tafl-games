package taflgames.model;

import java.util.List;

import org.apache.commons.collections4.iterators.LoopingIterator;

import taflgames.common.Player;
import taflgames.common.code.Position;

/**
 * This class implements a match.
 */
public final class MatchImpl implements Match {

    private final Board board = null;   // TO INITIALIZE
    /*
     * Using a collection from the Apache Commons Collection library to implement
     * the players turn queue
     */
    private final LoopingIterator<Player> turnQueue;
    private Player activePlayer;

    /**
     * Creates a new match.
     */
    public MatchImpl() {
        this.turnQueue = new LoopingIterator<>(
            List.of(Player.ATTACKER, Player.DEFENDER)
        );
        this.setNextActivePlayer();
    }

    @Override
    public Player getActivePlayer() {
        return this.activePlayer;
    }

    @Override
    public void setNextActivePlayer() {
        this.activePlayer = this.turnQueue.next();
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
        this.board.makeMove(start, destination);
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
    }

}
