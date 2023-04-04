package taflgames.model.cell.api;

import taflgames.common.api.Vector;
import taflgames.model.board.api.Board;

/**
 * This interface will model a special cell called slider. 
 * When a pice ends up on it the slider moves the piece 
 * to the furthest reacheble Position along a certain direction.
 */
public interface Slider extends Cell, Resettable, TimedEntity {
    /**
     * Add a Mediator to the Slider, this allows the Slider to comunicate with the Board.
     * @param board the Board of the game.
     */
    void addMediator(Board board);

    Vector getOrientation();
}
