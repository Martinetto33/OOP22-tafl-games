package taflgames.model.cell.api;

import taflgames.common.api.Vector;
import taflgames.model.board.api.Board;

/**
 * An interface modelling a Slider, a particular type of Cell that pushes
 * Pieces that land onto it if it is active. The Slider periodically
 * deactivates itself and when it is reactivated, it changes its orientation
 * by rotating by a 90 degrees angle.
 */
public interface Slider extends Cell, Resettable, TimedEntity {

    /**
     * Adds a Mediator to the Slider, this allows the Slider to comunicate with the Board.
     * @param board the Board of the game.
     */
    void addMediator(Board board);

    /**
     * Returns the direction that this Slider currently points towards.
     * @return a {@link taflgames.common.api.Vector} describing the current
     * orientation of this Slider.
     */
    Vector getOrientation();

}
