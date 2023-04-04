package taflgames.controller.mapper;

import taflgames.controller.entitystate.PieceState;
import taflgames.view.scenes.PieceImageInfo;

/**
 * An object that maps Cells and Pieces types to their corrisponding images.
 */
public interface PieceImageMapper {
    
    PieceImageInfo mapToImage(PieceState state);

}
