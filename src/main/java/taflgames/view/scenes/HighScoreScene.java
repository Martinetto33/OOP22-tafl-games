package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import taflgames.common.code.Pair;
import taflgames.view.fontmanager.FontManager;
import taflgames.view.scenecontrollers.HighScoreController;

/**
 * A View scene displaying the results of all past matches, if any.
 */
public class HighScoreScene extends AbstractScene {
    private static final String SCENE_NAME = "High Score";
    private static final int LABEL_WIDTH = 120;
    private static final int LABEL_HEIGHT = 60;
    private static final int ROW_HEIGHT = 40;
    private static final int FONT_SIZE = 15;
    private static final int LABEL_FONT_SIZE = 50;

    private final FontManager fontManager = AbstractScene.getFontManager();

    private final HighScoreController controller;

    /**
     * Builds a new HighScoreScene.
     * @param controller the {@link taflgames.view.scenecontrollers.HighScoreController}
     * associated to this scene.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The scene controller cannot be created inside the class that implements the scene:"
                + "at the moment of the creation of the scene controller, it must get a reference to the View and the Controller"
                + "of the application and the class implementing the scene does not have access to them."
    )
    public HighScoreScene(final HighScoreController controller) {
        super(HighScoreScene.SCENE_NAME);
        this.controller = controller;

        final JPanel scene = this.getScene();
        scene.setLayout(new BorderLayout());
        final JPanel buttonPanel = new JPanel();
        final JPanel tablePanel = new JPanel();

        this.addGoBackButton(buttonPanel);
        this.addClearLeaderboardButton(buttonPanel, tablePanel);
        this.constructTableFromLeaderboard(this.requestLeaderboard(), tablePanel);

        this.makePanelTransparent(tablePanel);
        this.makePanelTransparent(buttonPanel);

        scene.add(tablePanel, BorderLayout.NORTH);
        scene.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void makePanelTransparent(final JPanel panel) {
        panel.setBackground(AbstractScene.getTransparency());
    }

    private void addGoBackButton(final JPanel panel) {
        final JButton button = new JButton("Go Back");
        button.setFont(fontManager.getButtonFont());
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                controller.goToPreviousScene();
            }

        });
        panel.add(button);
    }

    private void addClearLeaderboardButton(final JPanel buttonPanel, final JPanel tablePanel) {
        final JButton button = new JButton("Clear Leaderboard");
        button.setFont(fontManager.getButtonFont());
        button.addActionListener(e -> {
            final int answer = JOptionPane.showConfirmDialog(getScene(),
            "Clearing the leaderboard will eliminate all registered results. Are you sure you want to continue?",
            "Warning", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                this.controller.clearLeaderboard();
                this.getScene().remove(tablePanel);
                final JPanel newTablePanel = new JPanel();
                this.makePanelTransparent(newTablePanel);
                this.emptyLeaderboard(newTablePanel);
                this.getScene().add(newTablePanel, BorderLayout.NORTH);
                this.getScene().revalidate();
                this.getScene().repaint();
            }
        });
        buttonPanel.add(button);
    }

    private void constructTableFromLeaderboard(final Map<String, Pair<Integer, Integer>> leaderboard, final JPanel panel) {
        if (leaderboard.isEmpty()) {
            this.emptyLeaderboard(panel);
            return;
        }
        final Object[][] data = new Object[leaderboard.size()][];
        final String[] columnNames = {"Name", "Victories", "Defeats"};
        //this should maintain the order of the leaderboard
        final Map<String, Pair<Integer, Integer>> copy = new LinkedHashMap<>(leaderboard);
        final Iterator<Entry<String, Pair<Integer, Integer>>> entryIterator = copy.entrySet().iterator();
        for (int i = 0; i < leaderboard.size(); i++) {
            final var entry = entryIterator.next();
            data[i] = List.of(entry.getKey(), entry.getValue().getX(), entry.getValue().getY()).toArray();
        }

        final JTable table = new JTable(data, columnNames);
        final JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        table.getTableHeader().setFont(fontManager.getButtonFont());
        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(fontManager.getModifiedFont(FONT_SIZE, Font.ROMAN_BASELINE));
        table.setRowHeight(ROW_HEIGHT);
        table.setEnabled(false); // the user should not be able to interact with the table.

        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private Map<String, Pair<Integer, Integer>> requestLeaderboard() {
        return this.controller.requestLeaderboard();
    }

    private void emptyLeaderboard(final JPanel panel) {
        final JLabel label = new JLabel("No match results yet.");
        label.setFont(fontManager.getModifiedFont(LABEL_FONT_SIZE, Font.PLAIN));
        label.setForeground(Color.WHITE);
        super.addComponentBackground(panel, LABEL_WIDTH, LABEL_HEIGHT, label);
    }

}
