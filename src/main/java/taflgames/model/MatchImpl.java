package taflgames.model;

import java.util.List;

import org.apache.commons.collections4.iterators.LoopingIterator;

import taflgames.common.Player;
import taflgames.common.api.Position;

public final class MatchImpl implements Match {
    
    private final Board board = null;   // TO INITIALIZE
    /*
     * Using a collection from the Apache Commons Collection library to implement
     * the players turn queue
     */
    private final LoopingIterator<Player> turnQueue;
    private Player playerInTurn;

    public MatchImpl() {
        this.turnQueue = new LoopingIterator<>(
            List.of(Player.ATTACKER, Player.DEFENDER)
        );
        this.setNextPlayerInTurn();
    }

    @Override
    public Player getNextPlayernTurn() {
        return this.playerInTurn;
    }

    @Override
    public void setNextPlayerInTurn() {
        this.playerInTurn = this.turnQueue.next();
    }

    @Override
    public boolean selectSource(final Position start) {
        return this.board.isStartingPointValid(start, playerInTurn);
    }

    @Override
    public boolean selectDestination(final Position start, final Position destination) {
        return this.board.isDestinationValid(start, destination, playerInTurn);
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

}
