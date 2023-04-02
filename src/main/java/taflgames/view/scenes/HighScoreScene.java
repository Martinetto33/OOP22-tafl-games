package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import taflgames.common.code.Pair;
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

    private final HighScoreController controller;

    /**
     * Builds a new HighScoreScene.
     * @param controller the {@link taflgames.view.scenecontrollers.HighScoreController}
     * associated to this scene.
     */
    public HighScoreScene(final HighScoreController controller) {
        super(HighScoreScene.SCENE_NAME, Optional.of("home-background.jpeg"));
        this.controller = controller;

        final JPanel scene = this.getScene();
        scene.setLayout(new FlowLayout());
        final JPanel buttonPanel = new JPanel();
        final JPanel tablePanel = new JPanel();

        this.addGoBackButton(buttonPanel);
        this.constructTableFromLeaderboard(this.requestLeaderboard(), tablePanel);

        this.makePanelTransparent(tablePanel);
        this.makePanelTransparent(buttonPanel);

        scene.add(tablePanel);
        scene.add(buttonPanel);
    }

    private void makePanelTransparent(final JPanel panel) {
        panel.setBackground(Scene.TRANSPARENT);
    }

    private void addGoBackButton(final JPanel panel) {
        final JButton button = new JButton("Go Back");
        button.setFont(Scene.FONT_MANAGER.getButtonFont());
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                controller.goToPreviousScene();
            }

        });
        panel.add(button);
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

        table.getTableHeader().setFont(Scene.FONT_MANAGER.getButtonFont());
        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(Scene.FONT_MANAGER.getModifiedFont(FONT_SIZE, Font.ROMAN_BASELINE));
        table.setRowHeight(ROW_HEIGHT);
        table.setEnabled(false); // the user should not be able to interact with the table.

        panel.add(scrollPane, BorderLayout.CENTER);
    }

    private Map<String, Pair<Integer, Integer>> requestLeaderboard() {
        return this.controller.requestLeaderboard();
    }

    private void emptyLeaderboard(final JPanel panel) {
        final JLabel label = new JLabel("No match results yet.");
        label.setFont(Scene.FONT_MANAGER.getModifiedFont(LABEL_FONT_SIZE, Font.PLAIN));
        label.setForeground(Color.WHITE);
        super.addComponentBackground(panel, LABEL_WIDTH, LABEL_HEIGHT, label);
    }

    @Override
    public void update() {
        // There is no scene update for the HighScore scene currently (other than the scene switching).
    }
}
