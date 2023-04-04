package taflgames.controller.mapper;

import taflgames.controller.entitystate.PieceState;
import taflgames.view.scenes.PieceImageInfo;

public class PieceTypeMapper implements PieceImageMapper {

    @Override
    public PieceImageInfo mapToImage(PieceState state) {
        return new PieceImageInfo(state.getName(), state.getPlayer());
    }
}
