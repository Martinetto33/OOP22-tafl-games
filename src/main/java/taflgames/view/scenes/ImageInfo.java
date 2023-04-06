package taflgames.view.scenes;

import taflgames.common.Player;

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
