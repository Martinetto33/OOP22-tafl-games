package taflgames.testViewMatchPannel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import taflgames.view.scenes.MatchPanelImpl;

public class TestViewMatchPannel extends JFrame {

    private static final int BOARD_SIZE = 11;

    public TestViewMatchPannel(final int sizeFrame) {

        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        Double scrensizeHight = screenSize.getHeight();
        MatchPanelImpl a = new MatchPanelImpl(BOARD_SIZE, scrensizeHight.intValue());
        this.add(a);
        EntitiesToDraw toDraw = new EntitiesToDrawImpl();
        toDraw.createPiecesAlive();
        toDraw.createBackgroundCells();
        toDraw.createSpecialCells();
        a.drawAllPieces(toDraw.getPiecesAlive());
        a.drawBackgroundCells(toDraw.getBackgroundCells());
        a.drawAllSpecialCells(toDraw.getSpecialCells());
        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton();
        goBackButton.setText("INDIETRO");
        southPanel.add(goBackButton);
        this.add(southPanel, FlowLayout.LEFT);
        this.setVisible(true);

    }

    public static void main(final String[] args) throws java.io.IOException {
        new TestViewMatchPannel(BOARD_SIZE); 
    }

}
