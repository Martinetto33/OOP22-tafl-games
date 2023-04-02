package taflgames.view.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JPanel;
import taflgames.common.code.Position;
import taflgames.common.Player;
import taflgames.view.scenecontrollers.MatchSceneController;
/**
 * TO DO: complete class
 */
public class MatchScene extends AbstractScene {
    private static final String MATCH = "BATTLE!";
    private static final String GO_BACK = "Go Back";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final int NUMB_CELLS_SIDE = 11;
    /**
     * TO DO: controller = new ImplMatchSceneController;
     */
    private MatchSceneController controller;
    private MatchPanelImpl a;

    public MatchScene(final MatchSceneController controller) {
        super(MatchScene.MATCH, Optional.of("home-background.jpeg"));
        this.controller = controller;
        final JPanel scene = super.getScene();
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = new Dimension(controller.getViewHeight(), controller.getViewWidth());
        scene.setSize(screenSize);
        scene.setLayout(new FlowLayout());
        a = new MatchPanelImpl(MatchScene.NUMB_CELLS_SIDE, controller.getViewHeight());
        scene.add(a);
        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(TRANSPARENT);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        scene.add(southPanel, FlowLayout.LEFT);
        /*FORSE DA USARE ANCHE....
         * drawAllPieces
         * drawAllSpecialCells
         * 
         * ... per coerenza. DIPENDONO ENTRAMBI DA CONTROLLER.
        */
        /*da usare drawBackgroundcell insieme alle info del controller */
        scene.setVisible(true);

        // When the match starts, cells and pieces are drawn for the first time.
        this.update();
    }

    @Override
    public void update() {
        /*
         * The board shown in the match scene must reflect the current state of the model.
         * All cells and pieces must be drawn in the correct position and using the correct
         * sprite according to their type.
         */
        final Map<Position, List<String>> cellsMapping = this.controller.getCellsMapping();
        final Map<Player, Map<Position, String>> piecesMapping = this.controller.getPiecesMapping();
        /*
         * These two collections will be used to redraw cells and pieces in the MatchPanel.
         * The Strings in these two maps represent the type of cells and pieces at each position.
         * I suppose that the redrawing will follow these steps.
         * - The Strings will be used somehow to create CellImageInfo and PieceImageInfo objects.
         * - The methods drawBackgroundCells(), drawAllSpecialCells and drawAllPieces() of the MatchPanel will be called,
         *   passing the maps of positions and CellImageInfo/PieceImageInfo.
         */
    }

    /**
     * draws all pieces currently alive.
     * @param piecesAlive
     */
    public void drawAllPieces(final Map<Position, PieceImageInfo> piecesAlive) {
        this.a.drawAllPieces(piecesAlive);
    }
    /**
     * draws the special cells currently active.
     * @param cells
     */
    public void drawAllSpecialCells(Map<Position, CellImageInfo> cells) {
        this.a.drawAllSpecialCells(cells);
    }
    /**
     * draws the board in the background.
     * @param cells
     */
    public void drawBackgroundCells(Map<Position, CellImageInfo> cells) {
        this.a.drawBackgroundCells(cells);
    }
    /**
     * Not used, but could turn out useful for more
     * efficency in future versions of the game.
     * @param originalPos
     * @param newPosition
     */
    public void movePiece(Position originalPos, Position newPosition) {
        this.a.movePiece(originalPos, newPosition);
    }
    /**
     * unsets the icons of all jLabel-pieces on the board.
     */
    public void removeAllPiecesOnLayer() {
        this.a.removeAllIconsOnLayer(a.getMapPieces());
    }
    /**
     * unsets the icons of all jLabel-specialCells on the board.
     */
    public void removeAllSpecialCellsOnLayer() {
        this.a.removeAllIconsOnLayer(a.getMapSpecialCell());
    }
    /**
     * gives the position of the selected piece's moveset the MatchPanel
     * has to colour.
     */
    public void givePositionToColour() {
        /*utilizzato per dare le posizioni (date dal controller) che il
        MatchPanel deve colorare.*/
    }
    /**
     * updates the current state of the board by drawing only the pieces and
     * special cells that are currently active given as arguments in this method
     * by the controller.
     * @param piecesAlive
     * @param cells
     */
    public void updateBoardInstance(final Map<Position, PieceImageInfo> piecesAlive, final Map<Position, CellImageInfo> cells) {
        removeAllPiecesOnLayer();
        removeAllSpecialCellsOnLayer();
        drawAllSpecialCells(cells);
        drawAllPieces(piecesAlive);
    }
}
