package taflgames.controller.mapper;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.controller.entitystate.CellState;
import taflgames.view.scenes.CellImageInfo;

/**
 * A class used to map CellStates to their relevant CellImageInfo.
 */
public class CellTypeMapper implements CellImageMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public CellImageInfo mapToImage(final CellState state) {
        final String name = state.getPrimaryName();
        final Player player = state.getPlayer();
        final Vector orientation = state.getOrientation();  // NOPMD
        // The Vector class models a vector and provides features that a List does not support.

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
