package taflgames.view.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JLabel;
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
        a = new MatchPanelImpl(11, controller.getViewHeight());
        scene.add(a);
        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(TRANSPARENT);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        scene.add(southPanel, FlowLayout.LEFT);
        /* EntitiesToDraw toDraw = new EntitiesToDrawImpl();
        toDraw.createPiecesAlive();
        toDraw.createBackgroundCells(); 
        a.drawAllPieces(toDraw.getPiecesAlive());
        a.drawBackgroundCells(toDraw.getBackgroundCells()); */
        scene.setVisible(true);
        //final MatchPanelImpl match = new MatchPanelImpl(MatchScene.NUMB_CELLS_SIDE, );
        //scene.add(match);
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
    
    public void removeAllIconsOnLayer(Map<Position,JLabel> mapLabel) {
        a.removeAllIconsOnLayer(mapLabel);
    }
    
    public void movePiece(Position originalPos, Position newPosition) {
        a.movePiece(originalPos, newPosition);
    }

    public Map<JButton, Position> getMapBottoni() {
        return a.getMapBottoni();

    }

    public Map<Position, JLabel> getMapPedine() {
        return a.getMapPedine();
    }

    public Map<Position, JLabel> getMapSpecialCell() {
        return a.getMapSpecialCell();
    }

    public Map<Position, JLabel> getMapBoard() {
        return a.getMapBoard();
    }

}
