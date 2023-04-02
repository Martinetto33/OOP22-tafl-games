package taflgames.model.cell.api;

import taflgames.model.board.api.Board;

public interface Slider extends Cell, Resettable, TimedEntity {
    /**
     * Add a Mediator to the Slider, this allows the Slider to comunicate with the Board.
     * @param board the Board of the game.
     */
    void addMediator(Board board);
}
