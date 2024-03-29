package taflgames.view.scenes;

import taflgames.common.Player;

/**
 * This class contains and gives information about the image of a piece.
 */
public final class PieceImageInfo implements ImageInfo {

    private final String name;
    private final Player player;

    /**
     * Creates a new object that contains information about the image of a piece.
     * @param name the name of the piece represented by the image
     * @param player the player that owns the piece
     */
    public PieceImageInfo(final String name, final Player player) {
        this.name = name;
        this.player = player;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((player == null) ? 0 : player.hashCode());
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
        final PieceImageInfo other = (PieceImageInfo) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return player == other.player;
    }
}
