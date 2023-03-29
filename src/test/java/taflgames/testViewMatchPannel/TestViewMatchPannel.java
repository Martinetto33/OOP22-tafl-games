package taflgames.testViewMatchPannel;

import javax.swing.JFrame;

import taflgames.view.scenes.MatchPanelImpl;

public class TestViewMatchPannel extends JFrame{
    private static final long serialVersionUID = -6218820567019985015L;
    public TestViewMatchPannel(int sizeFrame) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(60*sizeFrame, 60*sizeFrame);
        MatchPanelImpl a = new MatchPanelImpl(11, 60*sizeFrame);
        this.add(a);
        //a.drawAllPieces();
        this.setVisible(true);
    }

    public static void main(String[] args) throws java.io.IOException {
        new TestViewMatchPannel(11); 
    }
}
