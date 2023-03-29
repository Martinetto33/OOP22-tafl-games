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

    public static final String ARCHER_ATTACKER = "taflgames/images/ARCHER_ATTACKER.png";
    public static final String ARCHER_DEFENDER = "taflgames/images/ARCHER_DEFENDER.png";
    public static final String BASIC_PIECE_ATTACKER = "taflgames/images/BASIC_PIECE_ATTACKER.png";
    public static final String BASIC_PIECE_DEFENDER = "taflgames/images/BASIC_PIECE_DEFENDER.png";
    public static final String KING = "taflgames/images/KING.png";
    public static final String QUEEN_ATTACKER = "taflgames/images/QUEEN_ATTACKER.png";
    public static final String QUEEN_DEFENDER = "taflgames/images/QUEEN_DEFENDER.png";
    public static final String SHIELD_ATTACKER = "taflgames/images/SHIELD_ATTACKER.png";
    public static final String SHIELD_DEFENDER = "taflgames/images/SHIELD_DEFENDER.png";
    public static final String SWAPPER_ATTACKER = "taflgames/images/SWAPPER_ATTACKER.png";
    public static final String SWAPPER_DEFENDER = "taflgames/images/SWAPPER_DEFENDER.png";

    public static final String CELL_BASIC = "taflgames/images/CELL_BASIC.png";
    public static final String CELL_EXIT = "taflgames/images/CELL_EXIT.png";
    public static final String CELL_SLIDER = "taflgames/images/CELL_SLIDER.png";
    public static final String CELL_THRONE = "taflgames/images/CELL_THRONE.png";
    public static final String CELL_TOMB_ATTACKERS = "taflgames/images/CELL_TOMB_ATTACKERS.png";
    public static final String CELL_TOMB_DEFENDERS = "taflgames/images/CELL_TOMB_DEFENDERS.png";

    /* private int panelSize;
    private final int numberOfCells; */
    private int unitToScale;

    public LoaderImagesImpl(final int panelSize, final int numberOfCells) {
        /* this.panelSize = panelSize;
        this.numberOfCells = numberOfCells; */
        unitToScale = panelSize/numberOfCells;
    }
    
    public Map<CellImageInfo,ImageIcon> cellImages = new HashMap<>();
    public Map<PieceImageInfo,ImageIcon> pieceImages = new HashMap<>();

    @Override
    public void loadCellsImages() {
        this.cellImages.put(new CellImageInfo("CELL_BASIC", Player.DEFENDER, 0), getImage(CELL_BASIC));
        this.cellImages.put(new CellImageInfo("CELL_EXIT", Player.DEFENDER, 0), getImage(CELL_EXIT));
        this.cellImages.put(new CellImageInfo("CELL_SLIDER", Player.DEFENDER, 0), getImage(CELL_SLIDER));
        this.cellImages.put(new CellImageInfo("CELL_SLIDER", Player.DEFENDER, 90), rotateImage(getImage(CELL_SLIDER), 90));
        this.cellImages.put(new CellImageInfo("CELL_SLIDER", Player.DEFENDER, 180), rotateImage(getImage(CELL_SLIDER), 180));
        this.cellImages.put(new CellImageInfo("CELL_SLIDER", Player.DEFENDER, 270), rotateImage(getImage(CELL_SLIDER), 270));
        this.cellImages.put(new CellImageInfo("CELL_THRONE", Player.DEFENDER, 0), getImage(CELL_THRONE));
        this.cellImages.put(new CellImageInfo("CELL_TOMB", Player.ATTACKER, 0), getImage(CELL_TOMB_ATTACKERS));
        this.cellImages.put(new CellImageInfo("CELL_TOMB", Player.DEFENDER, 0), getImage(CELL_TOMB_DEFENDERS));
    }

    @Override
    public void loadPiecesImages() {
        this.pieceImages.put(new PieceImageInfo("ARCHER", Player.ATTACKER), getImage(ARCHER_ATTACKER));
        this.pieceImages.put(new PieceImageInfo("ARCHER", Player.DEFENDER), getImage(ARCHER_DEFENDER));
        this.pieceImages.put(new PieceImageInfo("BASIC_PIECE", Player.ATTACKER), getImage(BASIC_PIECE_ATTACKER));
        this.pieceImages.put(new PieceImageInfo("BASIC_PIECE", Player.DEFENDER), getImage(BASIC_PIECE_DEFENDER));
        this.pieceImages.put(new PieceImageInfo("KING", Player.DEFENDER), getImage(KING));
        this.pieceImages.put(new PieceImageInfo("QUEEN", Player.ATTACKER), getImage(QUEEN_ATTACKER));
        this.pieceImages.put(new PieceImageInfo("QUEEN", Player.DEFENDER), getImage(QUEEN_DEFENDER));
        this.pieceImages.put(new PieceImageInfo("SHIELD", Player.ATTACKER), getImage(SHIELD_ATTACKER));
        this.pieceImages.put(new PieceImageInfo("SHIELD", Player.DEFENDER), getImage(SHIELD_DEFENDER));
        this.pieceImages.put(new PieceImageInfo("SWAPPER", Player.ATTACKER), getImage(SWAPPER_ATTACKER));
        this.pieceImages.put(new PieceImageInfo("SWAPPER", Player.DEFENDER), getImage(SWAPPER_DEFENDER));
    }

    private ImageIcon getImage(final String imagePath) {
        final URL imageURL = ClassLoader.getSystemResource(imagePath);
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
