package taflgames.view.scenes;

import taflgames.common.Player;

/**
 * This interface allows to get information about the images.
 */
public interface ImageInfo {

    /**
     * @return the name of the entity rappresented on the image
     */
    String getName();

    /**
     * @return the player (or color) of the piece
     */
    Player getPlayer();

}
