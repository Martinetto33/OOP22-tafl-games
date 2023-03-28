package taflgames.model.cell.api;

import taflgames.model.board.api.Board;

public interface Slider extends Cell, Resettable, TimedEntity{
    
    public void addMediator(final Board board);
}
