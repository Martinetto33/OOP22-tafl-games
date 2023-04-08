package taflgames.testmatchpanel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import taflgames.view.scenes.MatchPanelImpl;

/**
 * This class allows to test if the board and its elements are drawn correctly
 * according to the mappings given by {@link EntitiesToDraw}.
 */
public class TestViewMatchPannel extends JFrame {

    public static final long serialVersionUID = 2L;

    private static final int BOARD_SIZE = 11;

    /**
     * Creates a frame that shows how the board and its elements are drawn
     * according to the given mappings.
     */
    public TestViewMatchPannel() {

        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        final Double scrensizeHight = screenSize.getHeight();
        final MatchPanelImpl a = new MatchPanelImpl(BOARD_SIZE, scrensizeHight.intValue());
        this.add(a);
        final EntitiesToDraw toDraw = new EntitiesToDrawImpl();
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

    /**
     * Creates the frame to test the drawing of the board and its elements.
     * @param args unused
     */
    public static void main(final String[] args) {
        new TestViewMatchPannel(); 
    }

}
