package taflgames.controller.mapper;

import taflgames.controller.entitystate.PieceState;
import taflgames.view.scenes.PieceImageInfo;

/**
 * A class used to map PieceStates to their particular PieceImageInfo.
 */
public class PieceTypeMapper implements PieceImageMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public PieceImageInfo mapToImage(final PieceState state) {
        return new PieceImageInfo(state.getName(), state.getPlayer());
    }

}
