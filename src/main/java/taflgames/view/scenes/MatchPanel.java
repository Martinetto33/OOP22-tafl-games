package taflgames.view.scenes;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import taflgames.common.code.Position;
/**
 * the panel in wich the match will take place
 */
public interface MatchPanel {
    /**
     * draws all the pieces that are still alive given by the MatchController
     * the layer dedicated to the pieces.
     * @param piecesAlive
     */
    void drawAllPieces(Map<Position, PieceImageInfo> piecesAlive);
    /**
     * draws all special cells given by the MatchController on the layer
     * dedicated to the special cells.
     * @param cells
     */
    void drawAllSpecialCells(Map<Position, CellImageInfo> cells);
    /**
     * draws all special cells given by the MatchController on the layer
     * dedicated to the special cells.
     * @param cells
     */
    void drawBackgroundCells(Map<Position, CellImageInfo> cells);
    /**
     * removes the images of every JLabel in the chosen mapLabel.
     * @param mapLabel
     */
    void removeAllIconsOnLayer(Map<Position,JLabel> mapLabel);
    /**
     * moves the piece from the original position to the new position
     * if the new position isn't occupied by other pieces.
     * @param originalPos
     * @param newPosition
     */
    void movePiece(Position originalPos, Position newPosition);

    Map<JButton, Position> getMapBottoni();

    Map<Position, JLabel> getMapPedine();

    Map<Position, JLabel> getMapSpecialCell();

    Map<Position, JLabel> getMapBoard();

    int getMySize();
    
}
