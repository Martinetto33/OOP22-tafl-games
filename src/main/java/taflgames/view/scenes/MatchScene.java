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


public class MatchScene extends AbstractScene {
    private static final String MATCH = "BATTLE!";
    private static final String GO_BACK = "Go Back";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final int NUMB_CELLS_SIDE = 11;
    //private MatchController = new ImplMatchController();
    //TO DO: MODIFY BASICSCENECONTROLLER AS ABOVE
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
        /*da usare drawBackgroundcell insieme alle info del controller */
        scene.setVisible(true);
    }

    public void drawAllPieces(final Map<Position, PieceImageInfo> piecesAlive) {
        a.drawAllPieces(piecesAlive);
    }

    public void drawAllSpecialCells(Map<Position, CellImageInfo> cells) {
        a.drawAllSpecialCells(cells);
    }

    public void drawBackgroundCells(Map<Position, CellImageInfo> cells) {
        a.drawBackgroundCells(cells);
    }

    /**
     * Not used but useful for efficency for future versions of the game.
     * @param originalPos
     * @param newPosition
     */
    public void movePiece(Position originalPos, Position newPosition) {
        a.movePiece(originalPos, newPosition);
    }

    public void removeAllPiecesOnLayer() {
        a.removeAllIconsOnLayer(a.getMapPedine());
    }

    public void removeAllSpecialCellsOnLayer() {
        a.removeAllIconsOnLayer(a.getMapSpecialCell());
    }

    public void giveInfo() {
        //utilizzato per dare le posizioni da colorare dal panel
    }

    public void updateMatchInstance(final Map<Position, PieceImageInfo> piecesAlive, final Map<Position, CellImageInfo> cells) {
        removeAllPiecesOnLayer();
        removeAllSpecialCellsOnLayer();
        drawAllSpecialCells(cells);
        drawAllPieces(piecesAlive);
    }
}
