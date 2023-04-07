package taflgames.view.scenes;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import taflgames.common.code.Position;
import taflgames.view.fontmanager.FontManager;
import taflgames.view.scenecontrollers.MatchSceneController;

/**
 * This class implements the scene of the match.
 */
public final class MatchScene extends AbstractScene {

    private static final String MATCH = "BATTLE!";
    private static final String GO_BACK = "Go Back";
    private static final String UNDO = "Undo move";
    private static final String PASS = "Pass turn";
    private static final int NUMB_CELLS_SIDE = 11;

    private final FontManager fontManager = AbstractScene.getFontManager();

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
        final Dimension screenSize = new Dimension(controller.getViewHeight(), controller.getViewWidth());
        scene.setSize(screenSize);

        final JPanel elementsPanel = new JPanel();
        elementsPanel.setBackground(AbstractScene.getTransparency());
        elementsPanel.setLayout(new BoxLayout(elementsPanel, BoxLayout.Y_AXIS));
        matchState = new MatchPanelImpl(MatchScene.NUMB_CELLS_SIDE, controller.getViewHeight());
        matchState.setBackground(AbstractScene.getTransparency());
        matchState.setMatchController(this.controller);
        elementsPanel.add(matchState);

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(AbstractScene.getTransparency());
        final JButton goBackButton = new JButton(GO_BACK);
        goBackButton.setFont(fontManager.getButtonFont());
        final JButton undoButton = new JButton(UNDO);
        undoButton.setFont(fontManager.getButtonFont());
        final JButton passTurnButton = new JButton(PASS);
        passTurnButton.setFont(fontManager.getButtonFont());

        buttonsPanel.add(passTurnButton);
        buttonsPanel.add(undoButton);
        buttonsPanel.add(goBackButton);

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

        elementsPanel.add(buttonsPanel);
        scene.add(elementsPanel);

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
