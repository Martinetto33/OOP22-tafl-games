package taflgames.view.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JPanel;
import taflgames.common.code.Position;
import taflgames.view.scenecontrollers.MatchSceneController;
/**
 * TO DO: complete class
 */
public class MatchScene extends AbstractScene {
    private static final String MATCH = "BATTLE!";
    private static final String GO_BACK = "Go Back";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final int NUMB_CELLS_SIDE = 11;

    private MatchSceneController controller;
    private MatchPanelImpl matchState;

    private Optional<Position> source = Optional.empty();
    private Optional<Position> destination = Optional.empty();

    public MatchScene(final MatchSceneController controller) {
        super(MatchScene.MATCH, Optional.of("home-background.jpeg"));
        this.controller = controller;
        final JPanel scene = super.getScene();
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = new Dimension(controller.getViewHeight(), controller.getViewWidth());
        scene.setSize(screenSize);
        scene.setLayout(new FlowLayout());
        matchState = new MatchPanelImpl(MatchScene.NUMB_CELLS_SIDE, controller.getViewHeight());
        matchState.setMatchController(this.controller);
        scene.add(matchState);
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

        // When the match starts, cells and pieces are drawn for the first time.
        this.update();
    }

    /*It will not be used since this method is already present in MatchPanelImpl*/
    public void selectPosition(final Position pos) {
        if (this.source.isEmpty() && this.controller.isSourceSelectionValid(pos)) {
            // If the source position is empty and the selected one is a valid source,
            // then the selected position is set as source
            this.source = Optional.of(pos);
        } else if (this.source.get().equals(pos)) {
            // If the current source is equal to the selected position,
            // this means that the source is deselected
            this.source = Optional.empty();
        } else {
            // If the source is already set and it is not deselected,
            // then the selected position is the destination
            this.destination = Optional.of(pos);
            // Once the destination is selected, the move is triggered;
            // it will be performed if it is legal.
            this.requestMove();
        }
    }

    /*It will not be used since this method is already present in MatchPanelImpl*/
    private void requestMove() {
        if (this.source.isPresent() && this.destination.isPresent()) {
            if (this.controller.moveIfLegal(this.source.get(), this.destination.get())) {
                // If the move is performed, source is reset
                this.source = Optional.empty();
            }
            // Destination is reset whether the move has been made or not
            this.destination = Optional.empty();
        }
    }

    /**
     * draws all pieces currently alive.
     * @param piecesAlive
     */
    public void drawAllPieces(final Map<Position, PieceImageInfo> piecesAlive) {
        this.matchState.drawAllPieces(piecesAlive);
    }
    /**
     * draws the special cells currently active.
     * @param cells
     */
    public void drawAllSpecialCells(Map<Position, CellImageInfo> cells) {
        this.matchState.drawAllSpecialCells(cells);
    }
    /**
     * draws the board in the background.
     * @param cells
     */
    public void drawBackgroundCells(Map<Position, CellImageInfo> cells) {
        this.matchState.drawBackgroundCells(cells);
    }
    /**
     * Not used, but could turn out useful for more
     * efficency in future versions of the game.
     * @param originalPos
     * @param newPosition
     */
    public void movePiece(Position originalPos, Position newPosition) {
        this.matchState.movePiece(originalPos, newPosition);
    }
    /**
     * unsets the icons of all jLabel-pieces on the board.
     */
    public void removeAllPiecesOnLayer() {
        this.matchState.removeAllIconsOnLayer(matchState.getMapPieces());
    }
    /**
     * unsets the icons of all jLabel-specialCells on the board.
     */
    public void removeAllSpecialCellsOnLayer() {
        this.matchState.removeAllIconsOnLayer(matchState.getMapSpecialCell());
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
        drawBackgroundCells(this.controller.getCellsMapping());
        drawAllSpecialCells(cells);
        drawAllPieces(piecesAlive);
    }

    @Override
    public void update() {
        this.updateBoardInstance(this.controller.getPiecesMapping(), this.controller.getCellsMapping());
    }
}
