package taflgames.view.scenes;

import taflgames.common.Player;

public class CellImageInfo implements ImageInfo{
    private final String name;
    private final Player player;
    //private final QUALCOSA
    public CellImageInfo(String name, Player player) {
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
    //finire il getter di QUALCOSA, l'hashCode e L'equals
}
