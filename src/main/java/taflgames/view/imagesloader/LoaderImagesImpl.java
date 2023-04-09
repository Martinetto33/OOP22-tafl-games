package taflgames.view.imagesloader;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import taflgames.common.Player;
import taflgames.common.code.VectorImpl;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

/**
 * This class models a LoaderImages {@link taflgames.view.imagesloader.LoaderImages}.
 */
public class LoaderImagesImpl implements LoaderImages {

    private static final String ARCHER_ATTACKER = "ARCHER_ATTACKER.png";
    private static final String ARCHER_DEFENDER = "ARCHER_DEFENDER.png";
    private static final String BASIC_PIECE_ATTACKER = "BASIC_PIECE_ATTACKER.png";
    private static final String BASIC_PIECE_DEFENDER = "BASIC_PIECE_DEFENDER.png";
    private static final String KING = "KING.png";
    private static final String QUEEN_ATTACKER = "QUEEN_ATTACKER.png";
    private static final String QUEEN_DEFENDER = "QUEEN_DEFENDER.png";
    private static final String SHIELD_ATTACKER = "SHIELD_ATTACKER.png";
    private static final String SHIELD_DEFENDER = "SHIELD_DEFENDER.png";
    private static final String SWAPPER_ATTACKER = "SWAPPER_ATTACKER.png";
    private static final String SWAPPER_DEFENDER = "SWAPPER_DEFENDER.png";

    private static final String CELL_BASIC = "CELL_BASIC.png";
    private static final String CELL_EXIT = "CELL_EXIT.png";
    private static final String CELL_SLIDER = "CELL_SLIDER.png";
    private static final String CELL_THRONE = "CELL_THRONE.png";
    private static final String CELL_TOMB_NEUTRAL = "CELL_TOMB_NEUTRAL.png";
    private static final String CELL_TOMB_ATTACKERS = "CELL_TOMB_ATTACKERS.png";
    private static final String CELL_TOMB_DEFENDERS = "CELL_TOMB_DEFENDERS.png";

    private static final String CELL_SLIDER_NAME = "CELL_SLIDER";

    private static final String SEP = "/";
    private static final String ROOT = "taflgames" + SEP + "images" + SEP;

    private static final int ANGLE_90 = 90;
    private static final int ANGLE_180 = 180;
    private static final int ANGLE_270 = 270;

    private final int unitToScale;
    private final Map<CellImageInfo, ImageIcon> cellImages = new HashMap<>();
    private final Map<PieceImageInfo, ImageIcon> pieceImages = new HashMap<>();

    /**
     * Create a new LoaderImagesImpl to load the images used in the game.
     * Calculate the size each image should have to fit the panel properly.
     * @param panelSize the size of the panel of the view.
     * @param numberOfCells the number of cell of the games' board.
     */
    public LoaderImagesImpl(final int panelSize, final int numberOfCells) {
        unitToScale = panelSize / numberOfCells;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCellsImages() {
        this.cellImages.put(
            new CellImageInfo("CELL_BASIC", null, new VectorImpl(0, 0)), getImage(CELL_BASIC)
        );
        this.cellImages.put(
            new CellImageInfo("CELL_EXIT", null, new VectorImpl(0, 0)), getImage(CELL_EXIT)
        );
        this.cellImages.put(
            new CellImageInfo(CELL_SLIDER_NAME, null, new VectorImpl(0, -1)), getImage(CELL_SLIDER)
        );
        this.cellImages.put(
            new CellImageInfo(CELL_SLIDER_NAME, null, new VectorImpl(-1, 0)), rotateImage(getImage(CELL_SLIDER), ANGLE_90)
        );
        this.cellImages.put(
            new CellImageInfo(CELL_SLIDER_NAME, null, new VectorImpl(0, 1)), rotateImage(getImage(CELL_SLIDER), ANGLE_180)
        );
        this.cellImages.put(
            new CellImageInfo(CELL_SLIDER_NAME, null, new VectorImpl(1, 0)), rotateImage(getImage(CELL_SLIDER), ANGLE_270)
        );
        this.cellImages.put(
            new CellImageInfo("CELL_THRONE", null, new VectorImpl(0, 0)), getImage(CELL_THRONE)
        );
        this.cellImages.put(
            new CellImageInfo("CELL_TOMB", null, new VectorImpl(0, 0)), getImage(CELL_TOMB_NEUTRAL)
        );
        this.cellImages.put(
            new CellImageInfo("CELL_TOMB", Player.ATTACKER, new VectorImpl(0, 0)), getImage(CELL_TOMB_ATTACKERS)
        );
        this.cellImages.put(
            new CellImageInfo("CELL_TOMB", Player.DEFENDER, new VectorImpl(0, 0)), getImage(CELL_TOMB_DEFENDERS)
        );
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Return the ImageIcon of the image given.
     * Search the image in the path specified by {@link #ROOT}. 
     * @param imageName a String representing the name of the file image.
     * @return an ImageIcon of the given image.
     */
    private ImageIcon getImage(final String imageName) {
        final URL imageURL = ClassLoader.getSystemResource(ROOT + imageName);
        final ImageIcon elemntToDraw = new ImageIcon(imageURL);
        return new ImageIcon(elemntToDraw.getImage().getScaledInstance(unitToScale, unitToScale, Image.SCALE_SMOOTH));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<CellImageInfo, ImageIcon> getCellImageMap() {
        return new HashMap<>(cellImages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<PieceImageInfo, ImageIcon> getPieceImageMap() {
        return new HashMap<>(pieceImages);
    }

    /**
     * Rotate an ImageIcon of a given angle.
     * @param originalImg the Imageicon that should be rotated.
     * @param rotation the angle of the rotation in degrees.
     * @return am ImageIcon of the rotated image.
     */
    private ImageIcon rotateImage(final ImageIcon originalImg, final int rotation) {
        final BufferedImage blankCanvas = new BufferedImage(
                originalImg.getIconWidth(), originalImg.getIconHeight(), BufferedImage.SCALE_SMOOTH
            );
        final Graphics2D g2 = (Graphics2D) blankCanvas.getGraphics();
        g2.rotate(Math.toRadians(rotation), (double) originalImg.getIconWidth() / 2, (double) originalImg.getIconHeight() / 2);
        g2.drawImage(originalImg.getImage(), 0, 0, null);
        originalImg.setImage(blankCanvas);
        return new ImageIcon(originalImg.getImage());
    }
}
