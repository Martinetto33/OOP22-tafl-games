package taflgames.view.loaderImages;

import java.util.Map;

import javax.swing.ImageIcon;

import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

public interface LoaderImages {

    /**
     * Load the images of the game's cells into a map
     * that can be used during the game to have the images of the cells.
     * Thanks to that the images don't have to be loaded everytime they are needed.
     */
    void loadCellsImages();

    /**
     * Load the images of the game's pieces into a map
     * that can be used during the game to have the images of the cells.
     * Thanks to that the images don't have to be loaded everytime they are needed.
     */
    void loadPiecesImages();

    /**
     * Return the map that contains the cells'images.
     * @return a Map of CellImageInfo and ImageIcon that contains the cells'images.
     */
    Map<CellImageInfo,ImageIcon> getCellImageMap();

    /**
     * Return the map that contains the pieces'images.
     * @return a Map of PieceImageInfo and ImageIcon that contains the pieces'images.
     */
    Map<PieceImageInfo,ImageIcon> getPieceImageMap();
}
