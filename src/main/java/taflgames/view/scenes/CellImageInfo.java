package taflgames.view.scenes;

import taflgames.common.Player;
import taflgames.common.api.Vector;

public class CellImageInfo implements ImageInfo{
    private final String name;
    private final Player player;
    private final Vector rotation;
    //private final QUALCOSA
    public CellImageInfo(String name, Player player, Vector rotation) {
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

    public Vector getRotation() {
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CellImageInfo other = (CellImageInfo) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (player != other.player)
            return false;
        if (rotation == null) {
            if (other.rotation != null)
                return false;
        } else if (!rotation.equals(other.rotation))
            return false;
        return true;
    }

    
    //finire il getter di QUALCOSA, l'hashCode e L'equals
}
