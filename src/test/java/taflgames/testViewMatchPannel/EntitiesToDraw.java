package taflgames.testViewMatchPannel;

import java.util.Map;

import taflgames.common.code.Position;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

/**
 * This interface allows to create and get the mapping of the positions
 * on the grid to the information about cells and pieces at each position.
 */
public interface EntitiesToDraw {

    /**
     * Creates the mapping of the positions to the information about alive pieces.
     */
    void createPiecesAlive();

    /**
     * Creates the mapping of the positions to the information about the background cells.
     */
    void createBackgroundCells();

    /**
     * Creates the mapping of the positions to the information about the special cells.
     */
    void createSpecialCells();

    /**
     * @return the mapping of the positions to the information about alive pieces
     */
    Map<Position, PieceImageInfo> getPiecesAlive();

    /**
     * @return the mapping of the positions to the information about the background cells
     */
    Map<Position, CellImageInfo> getBackgroundCells();

    /**
     * @return the mapping of the positions to the information about the special cells
     */
    Map<Position, CellImageInfo> getSpecialCells();
}
