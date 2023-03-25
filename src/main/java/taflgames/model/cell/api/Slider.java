package taflgames.model.cell.api;

import taflgames.model.board.api.Board;

public interface Slider extends Resettable, TimedEntity{
    
    public void addMediator(final Board board);
}
