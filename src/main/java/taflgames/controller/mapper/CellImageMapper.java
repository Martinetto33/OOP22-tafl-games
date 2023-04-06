package taflgames.controller.mapper;

import taflgames.controller.entitystate.CellState;
import taflgames.view.scenes.CellImageInfo;

/**
 * An object that maps Pieces types to their corrisponding images.
 */
public interface CellImageMapper {

    /**
     * Maps a {@link taflgames.controller.entitystate.CellState} to its
     * corresponding {@link taflgames.view.scenes.CellImageInfo}.
     * @param state the state of the piece that has to be drawn.
     * @return a PieceImageInfo that corresponds to this Piece's state.
     */
    CellImageInfo mapToImage(CellState state);

}
