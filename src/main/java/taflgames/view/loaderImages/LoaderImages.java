package taflgames.view.loaderImages;

import java.util.Map;

import javax.swing.ImageIcon;

import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

public interface LoaderImages {
    
    void loadCellsImages();
    
    void loadPiecesImages();

    Map<CellImageInfo,ImageIcon> getCellImageMap();

    Map<PieceImageInfo,ImageIcon> getPieceImageMap();
    
}
