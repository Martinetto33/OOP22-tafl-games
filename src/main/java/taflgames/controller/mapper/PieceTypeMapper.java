package taflgames.controller.mapper;

import taflgames.controller.entitystate.PieceState;
import taflgames.view.scenes.PieceImageInfo;

public final class PieceTypeMapper implements PieceImageMapper {

    @Override
    public PieceImageInfo mapToImage(final PieceState state) {
        return new PieceImageInfo(state.getName(), state.getPlayer());
    }
}
