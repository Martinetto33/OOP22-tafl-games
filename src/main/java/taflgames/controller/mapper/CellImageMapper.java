package taflgames.controller.mapper;

import taflgames.controller.entitystate.CellState;
import taflgames.view.scenes.CellImageInfo;

public interface CellImageMapper {
    
    CellImageInfo mapToImage(CellState state);

}
