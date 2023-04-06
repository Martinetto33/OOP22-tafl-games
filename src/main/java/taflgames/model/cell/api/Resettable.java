package taflgames.model.cell.api;
/**
 * This interface will model entities that have a default state
 * that change during the game and must be reset at some point. 
 * In our version of the game this interface is extended by Slider 
 * {@link taflgames.model.cell.api.Slider}
 * and implemented by SliderImpl {@link taflgames.model.cell.code.SliderImpl}
 */
public interface Resettable {

    /**
     * Set the state of a field of a 
     * resettable entity to its default state.
     */
    void reset();

}
