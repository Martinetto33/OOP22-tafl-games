package taflgames.view.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import taflgames.common.code.Position;
import taflgames.view.scenecontrollers.MatchSceneController;

/**
 * This class implements the scene of the match.
 */
public final class MatchScene extends AbstractScene {

    private static final String MATCH = "BATTLE!";
    private static final String GO_BACK = "Go Back";
    private static final String UNDO = "Undo move";
    private static final String PASS = "Pass turn";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final int NUMB_CELLS_SIDE = 11;

    private final MatchSceneController controller;
    private final MatchPanelImpl matchState;

    /**
     * Creates the match scene.
     * @param controller the controller of the scene
     */
    public MatchScene(final MatchSceneController controller) {
        super(MatchScene.MATCH);
        this.controller = controller;
        final JPanel scene = super.getScene();
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final Dimension screenSize = new Dimension(controller.getViewHeight(), controller.getViewWidth());
        scene.setSize(screenSize);
        scene.setLayout(new FlowLayout());
        matchState = new MatchPanelImpl(MatchScene.NUMB_CELLS_SIDE, controller.getViewHeight());
        matchState.setMatchController(this.controller);
        scene.add(matchState);
        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);

        /* Code added for the memento */
        final JButton undoButton = new JButton(UNDO);
        final JButton passTurnButton = new JButton(PASS);

        southPanel.add(goBackButton);
        southPanel.add(undoButton);
        southPanel.add(passTurnButton);
        southPanel.setBackground(TRANSPARENT);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        undoButton.addActionListener(e -> {
            this.controller.undo();
        });

        passTurnButton.addActionListener(e -> {
            if (!this.controller.passTurn()) {
                JOptionPane.showMessageDialog(getScene(), "Cannot pass turn because "
                    + this.controller.getPlayerInTurn().toString()
                    + " has not made a move yet.",
                    "No move made by current player",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        scene.add(southPanel, FlowLayout.LEFT);

        // When the match starts, cells and pieces are drawn for the first time.
        this.update();
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
    public void drawAllSpecialCells(final Map<Position, CellImageInfo> cells) {
        this.matchState.drawAllSpecialCells(cells);
    }

    /**
     * draws the board in the background.
     * @param cells
     */
    public void drawBackgroundCells(final Map<Position, CellImageInfo> cells) {
        this.matchState.drawBackgroundCells(cells);
    }

    /**
     * Not used, but could turn out useful for more
     * efficency in future versions of the game.
     * @param originalPos
     * @param newPosition
     */
    public void movePiece(final Position originalPos, final Position newPosition) {
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
        // Check if the match is over; if it is, then the view switches to the game over scene
        if (this.controller.isMatchOver()) {
            this.controller.goToNextScene();
        }
    }
}
