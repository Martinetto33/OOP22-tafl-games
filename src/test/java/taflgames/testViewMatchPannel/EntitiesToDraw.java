package taflgames.testViewMatchPannel;

import java.util.Map;

import taflgames.common.code.Position;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

public interface EntitiesToDraw {

    void createPiecesAlive();

    void createBackgroundCells();

    Map<Position,PieceImageInfo> getPiecesAlive();

    Map<Position, CellImageInfo> getBackgroundCells();
    
}