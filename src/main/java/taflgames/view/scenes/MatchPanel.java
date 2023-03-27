package taflgames.view.scenes;

import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import taflgames.common.code.Position;

public interface MatchPanel {
    /**
     * draws all the pieces that are still alive given by the MatchController
     * the layer dedicated to the pieces.
     * @param piecesAlive
     */
    public void drawAllPieces(final Map<Position, PieceImageInfo> piecesAlive);
    /**
     * draws all special cells given by the MatchController on the layer
     * dedicated to the special cells.
     * @param cells
     */
    public void drawAllSpecialCells(final Map<CellImageInfo, ImageIcon> cells);
    /**
     * draws all special cells given by the MatchController on the layer
     * dedicated to the special cells.
     * @param cells
     */
    public void drawBackgroundCells(final Map<CellImageInfo,ImageIcon> cells);
    /**
     * removes the images of every JLabel in the chosen mapLabel.
     * @param mapLabel
     */
    public void removeAllIconsOnLayer(Map<Position,JLabel> mapLabel);
    /**
     * moves the piece from the original position to the new position
     * if the new position isn't occupied by other pieces
     * @param originalPos
     * @param newPosition
     */
    public void movePiece(Position originalPos, Position newPosition);
}
