package taflgames.controller.mapper;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.controller.entitystate.CellState;
import taflgames.view.scenes.CellImageInfo;

public final class CellTypeMapper implements CellImageMapper {

    @Override
    public CellImageInfo mapToImage(final CellState state) {
        final String name = state.getPrimaryName();
        final Player player = state.getPlayer();
        final Vector orientation = state.getOrientation();
        switch (name) {
            case "ClassicCell":
                return new CellImageInfo("CELL_BASIC", player, orientation);
            case "Exit":
                return new CellImageInfo("CELL_EXIT", player, orientation);
            case "Throne":
                return new CellImageInfo("CELL_THRONE", player, orientation);
            case "Slider":
                return new CellImageInfo("CELL_SLIDER", player, orientation);
            case "Tomb":
                return new CellImageInfo("CELL_TOMB", player, orientation);
            default:
                return null;
        }
    }

}
