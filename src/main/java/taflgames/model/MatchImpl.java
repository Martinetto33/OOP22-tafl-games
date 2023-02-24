package taflgames.model;

import taflgames.common.Player;
import taflgames.common.api.Position;

public final class MatchImpl implements Match {
    
    private final Board board = null;   // TO INITIALIZE
    private Player playerInTurn;

    public MatchImpl() {

    }

    @Override
    public boolean selectSource(final Position start) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectSource'");
    }

    @Override
    public boolean selectDestination(final Position start, final Position destination) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectDestination'");
    }

    @Override
    public void makeMove(final Position start, final Position destination) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeMove'");
    }

}
