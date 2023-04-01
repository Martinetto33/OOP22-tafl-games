package taflgames.testViewMatchPannel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import taflgames.view.scenes.MatchPanelImpl;

public class TestViewMatchPannel extends JFrame{
    private static final long serialVersionUID = -6218820567019985015L;
    public TestViewMatchPannel(int sizeFrame) {
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        Double scrensizeHight = screenSize.getHeight();
        MatchPanelImpl a = new MatchPanelImpl(11, scrensizeHight.intValue());
        this.add(a);
        EntitiesToDraw toDraw = new EntitiesToDrawImpl();
        toDraw.createPiecesAlive();
        toDraw.createBackgroundCells();
        a.drawAllPieces(toDraw.getPiecesAlive());
        a.drawBackgroundCells(toDraw.getBackgroundCells());
        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton();
        goBackButton.setText("INDIETRO");
        southPanel.add(goBackButton);
        this.add(southPanel, FlowLayout.LEFT);
        this.setVisible(true);
    }
    public static void main(String[] args) throws java.io.IOException {
        new TestViewMatchPannel(11); 
    }
}
