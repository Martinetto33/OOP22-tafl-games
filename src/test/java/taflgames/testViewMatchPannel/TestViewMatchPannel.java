package taflgames.testViewMatchPannel;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import taflgames.view.scenes.MatchPanelImpl;

public class TestViewMatchPannel extends JFrame{
    private static final long serialVersionUID = -6218820567019985015L;
    public TestViewMatchPannel(int sizeFrame) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        MatchPanelImpl a = new MatchPanelImpl(11, screenSize.getHeight());
        this.add(a);
        EntitiesToDraw toDraw = new EntitiesToDrawImpl();
        toDraw.createPiecesAlive();
        toDraw.createBackgroundCells();
        a.drawAllPieces(toDraw.getPiecesAlive());
        a.drawBackgroundCells(toDraw.getBackgroundCells());
        this.setVisible(true);
    }
    public static void main(String[] args) throws java.io.IOException {
        new TestViewMatchPannel(11); 
    }
}
