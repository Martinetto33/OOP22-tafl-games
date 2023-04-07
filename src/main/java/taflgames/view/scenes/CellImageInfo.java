package taflgames.view.scenes;

import taflgames.common.Player;
import taflgames.common.api.Vector;

/**
 * This class contains and gives information about the image of a cell.
 */
public final class CellImageInfo implements ImageInfo {

    private final String name;
    private final Player player;
    private final Vector rotation; // NOPMD
    // The Vector class models a vector and provides features that a List does not support.

    /**
     * Creates a new object that contains information about the image of a cell.
     * @param name the name of the cell represented by the image
     * @param player the player related to the image
     *      (for example, it is the attacker if the cell is the tomb of an attacker's piece)
     * @param rotation a vector representing the rotation of the cell image
     */
    public CellImageInfo(final String name, final Player player, final Vector rotation) { // NOPMD
        // The Vector class models a vector and provides features that a List does not support.
        this.name = name;
        this.player = player;
        this.rotation = rotation;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @return a vector representing the rotation of the cell image
     */
    public Vector getRotation() { // NOPMD
        // The Vector class models a vector and provides features that a List does not support.
        return rotation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        result = prime * result + ((rotation == null) ? 0 : rotation.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CellImageInfo other = (CellImageInfo) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (player != other.player) {
            return false;
        }
        if (rotation == null) {
            if (other.rotation != null) {
                return false;
            }
        } else if (!rotation.equals(other.rotation)) {
            return false;
        }
        return true;
    }

}
