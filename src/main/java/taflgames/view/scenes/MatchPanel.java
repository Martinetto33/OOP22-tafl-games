package taflgames.view.scenes;

import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JLabel;
import taflgames.common.code.Position;
import taflgames.view.scenecontrollers.MatchSceneController;
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
    /**
     * 
     * @return the map of jbuttons with their position
     */
    Map<JButton, Position> getMapButtons();
    /**
     * 
     * @return the map of jlabels that rappresents the pieces with their position
     */
    Map<Position, JLabel> getMapPieces();
    /**
     * 
     * @return the map of jlabels that rappresents the pieces with their position
     */
    Map<Position, JLabel> getMapSpecialCell();
    /**
     * 
     * @return the map of jlabels that rappresents the pieces with their position
     */
    Map<Position, JLabel> getMapBoard();
    /**
     * 
     * @return size of MatchPanel
     */
    int getMySize();

    /**
     * 
     * @return first cell selected 
     */
    Optional<Position> getStartingPosition();
    /**
     * 
     * @return second and last cell selected
     */
    Optional<Position> getDestination();

    /**
     * Set the controller of the match scene in MatchPanel.
     * @param controller
     */
    void setMatchController(final MatchSceneController controller);
}
