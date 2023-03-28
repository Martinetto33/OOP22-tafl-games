package taflgames.view.loaderImages;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class LoaderImagesImpl implements LoaderImages{

    public static final String ARCHER_ATTACKER = "ARCHER_ATTACKER.png";
    public static final String ARCHER_DEFENDER = "ARCHER_DEFENDER.png";
    public static final String BASIC_PIECE_ATTACKER = "BASIC_PIECE_ATTACKER.png";
    public static final String BASIC_PIECE_DEFENDER = "BASIC_PIECE_DEFENDER.png";
    public static final String KING = "KING.png";
    public static final String QUEEN_ATTACKER = "QUEEN_ATTACKER.png";
    public static final String QUEEN_DEFENDER = "QUEEN_DEFENDER.png";
    public static final String SHIELD_ATTACKER = "SHIELD_ATTACKER.png";
    public static final String SHIELD_DEFENDER = "SHIELD_DEFENDER.png";
    public static final String SWAPPER_ATTACKER = "SWAPPER_ATTACKER.png";
    public static final String SWAPPER_DEFENDER = "SWAPPER_DEFENDER.png";

    public static final String CELL_BASIC = "CELL_BASIC.png";
    public static final String CELL_EXIT = "CELL_EXIT.png";
    public static final String CELL_SLIDER = "CELL_SLIDER.png";
    public static final String CELL_THRONE = "CELL_THRONE.png";
    public static final String CELL_TOMB_ATTACKER = "CELL_TOMB_ATTACKER.png";
    public static final String CELL_TOMB_DEFENDERS = "CELL_TOMB_DEFENDERS.png";
    public static final String CELL_TOMB_NEUTRAL = "CELL_TOMB_NEUTRAL.png";
    
    public Map<String,ImageIcon> cellImages;
    public Map<String,ImageIcon> pieceImages;

    @Override
    public void loadCellsImages() {
        cellImages = new HashMap<>();
        cellImages.put("", getImage(CELL_BASIC));
        cellImages.put("", getImage(CELL_EXIT));
        cellImages.put("", getImage(CELL_SLIDER));
        cellImages.put("", getImage(CELL_THRONE));
        cellImages.put("", getImage(CELL_TOMB_ATTACKER));
        cellImages.put("", getImage(CELL_TOMB_DEFENDERS));
        cellImages.put("", getImage(CELL_TOMB_NEUTRAL));
    }

    @Override
    public void loadPiecesImages() {
        pieceImages = new HashMap<>();
        pieceImages.put("", getImage(ARCHER_ATTACKER));
        pieceImages.put("", getImage(ARCHER_DEFENDER));
        pieceImages.put("", getImage(BASIC_PIECE_ATTACKER));
        pieceImages.put("", getImage(KING));
        pieceImages.put("", getImage(QUEEN_ATTACKER));
        pieceImages.put("", getImage(SHIELD_ATTACKER));
        pieceImages.put("", getImage(SHIELD_DEFENDER));
        pieceImages.put("", getImage(SWAPPER_ATTACKER));
        pieceImages.put("", getImage(SWAPPER_DEFENDER));
    }

    public ImageIcon getImage(final String imageName) {
        final URL imageURL = ClassLoader.getSystemResource("src/main/resources/taflgames/images/" + imageName);
        final ImageIcon elemntToDraw = new ImageIcon(imageURL);
        return new ImageIcon(elemntToDraw.getImage().getScaledInstance(0, 0, 0));
    }
}
