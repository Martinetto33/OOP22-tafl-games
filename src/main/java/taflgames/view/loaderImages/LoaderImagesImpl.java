package taflgames.view.loaderImages;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import taflgames.common.Player;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

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

    /* private int panelSize;
    private final int numberOfCells; */
    private int unitToScale;

    public LoaderImagesImpl(final int panelSize, final int numberOfCells) {
        /* this.panelSize = panelSize;
        this.numberOfCells = numberOfCells; */
        unitToScale = panelSize/numberOfCells;
    }
    
    public Map<CellImageInfo,ImageIcon> cellImages;
    public Map<PieceImageInfo,ImageIcon> pieceImages;

    @Override
    public void loadCellsImages() {
        cellImages = new HashMap<>();
        cellImages.put(new CellImageInfo("CELL_BASIC", Player.DEFENDER, 0), getImage(CELL_BASIC));
        cellImages.put(new CellImageInfo("CELL_EXIT", Player.DEFENDER, 0), getImage(CELL_EXIT));
        cellImages.put(new CellImageInfo("CELL_SLIDER", Player.DEFENDER, 0), getImage(CELL_SLIDER));
        cellImages.put(new CellImageInfo("CELL_SLIDER", Player.DEFENDER, 90), rotateImage(getImage(CELL_SLIDER), 90));
        cellImages.put(new CellImageInfo("CELL_SLIDER", Player.DEFENDER, 180), rotateImage(getImage(CELL_SLIDER), 180));
        cellImages.put(new CellImageInfo("CELL_SLIDER", Player.DEFENDER, 270), rotateImage(getImage(CELL_SLIDER), 270));
        cellImages.put(new CellImageInfo("CELL_THRONE", Player.DEFENDER, 0), getImage(CELL_THRONE));
        cellImages.put(new CellImageInfo("CELL_TOMB", Player.ATTACKER, 0), getImage(CELL_TOMB_ATTACKER));
        cellImages.put(new CellImageInfo("CELL_TOMB", Player.DEFENDER, 0), getImage(CELL_TOMB_DEFENDERS));
    }

    @Override
    public void loadPiecesImages() {
        pieceImages = new HashMap<>();
        pieceImages.put(new PieceImageInfo("ARCHER", Player.ATTACKER), getImage(ARCHER_ATTACKER));
        pieceImages.put(new PieceImageInfo("ARCHER", Player.DEFENDER), getImage(ARCHER_DEFENDER));
        pieceImages.put(new PieceImageInfo("BASIC_PIECE", Player.ATTACKER), getImage(BASIC_PIECE_ATTACKER));
        pieceImages.put(new PieceImageInfo("BASIC_PIECE", Player.DEFENDER), getImage(BASIC_PIECE_DEFENDER));
        pieceImages.put(new PieceImageInfo("KING", Player.DEFENDER), getImage(KING));
        pieceImages.put(new PieceImageInfo("QUEEN", Player.ATTACKER), getImage(QUEEN_ATTACKER));
        pieceImages.put(new PieceImageInfo("QUEEN", Player.DEFENDER), getImage(QUEEN_DEFENDER));
        pieceImages.put(new PieceImageInfo("SHIELD", Player.ATTACKER), getImage(SHIELD_ATTACKER));
        pieceImages.put(new PieceImageInfo("SHIELD", Player.DEFENDER), getImage(SHIELD_DEFENDER));
        pieceImages.put(new PieceImageInfo("SWAPPER", Player.ATTACKER), getImage(SWAPPER_ATTACKER));
        pieceImages.put(new PieceImageInfo("SWAPPER", Player.DEFENDER), getImage(SWAPPER_DEFENDER));
    }

    private ImageIcon getImage(final String imageName) {
        final URL imageURL = ClassLoader.getSystemResource("src/main/resources/taflgames/images/" + imageName);
        final ImageIcon elemntToDraw = new ImageIcon(imageURL);
        return new ImageIcon(elemntToDraw.getImage().getScaledInstance(unitToScale, unitToScale, Image.SCALE_SMOOTH));
    }

    @Override
    public Map<CellImageInfo,ImageIcon> getCellImageMap() {
        return cellImages;
    }

    @Override
    public Map<PieceImageInfo,ImageIcon> getPieceImageMap() {
        return pieceImages;
    }

    @Override
    public ImageIcon rotateImage(final ImageIcon originalImg, final int rotation) {
        BufferedImage blankCanvas = new BufferedImage(originalImg.getIconWidth(), originalImg.getIconHeight(), BufferedImage.SCALE_SMOOTH);
        Graphics2D g2 = (Graphics2D) blankCanvas.getGraphics();
        g2.rotate(Math.toRadians(rotation), originalImg.getIconWidth()/2, originalImg.getIconHeight()/2);
        g2.drawImage(originalImg.getImage(), 0, 0, null);
        originalImg.setImage(blankCanvas);
        return new ImageIcon(originalImg.getImage());
    }
}