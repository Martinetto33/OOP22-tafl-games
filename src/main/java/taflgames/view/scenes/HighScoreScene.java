package taflgames.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import taflgames.common.code.Pair;
import taflgames.view.scenecontrollers.HighScoreController;

public class HighScoreScene extends AbstractScene {
    private static final String SCENE_NAME = "High Score";

    private final HighScoreController controller;

    public HighScoreScene(HighScoreController controller) {
        super(HighScoreScene.SCENE_NAME, Optional.of("home-background.jpeg"));
        this.controller = controller;

        final JPanel scene = this.getScene();
        final JPanel buttonPanel = new JPanel();
        final JScrollPane leaderboardPane = new JScrollPane();
    } 

    private void addGoBackButton(final JPanel panel) {
        final JButton button = new JButton("Go Back");
        button.setFont(Scene.FONT_MANAGER.getButtonFont());
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                controller.goToPreviousScene();
            }
            
        });
        panel.add(button);
    }

    private void constructTableFromLeaderboard(final Map<String, Pair<Integer, Integer>> leaderboard, final JPanel panel) {
        final Object[][] data = new Object[leaderboard.size()][];
        final String[] columnNames = new String[leaderboard.size()];
        //this should maintain the order of the leaderboard
        final Map<String, Pair<Integer, Integer>> copy = new LinkedHashMap<>(leaderboard);
        final Iterator<Entry<String, Pair<Integer, Integer>>> entryIterator = copy.entrySet().iterator();
        for (int i = 0; i < leaderboard.size(); i++) {
            final var entry = entryIterator.next();
            columnNames[i] = entry.getKey();
            data[i] = List.of(entry.getKey(), entry.getValue().getX(), entry.getValue().getY()).toArray();
        }
        final JTable table = new JTable(data, columnNames);
        final JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        panel.add(scrollPane);
    }

    private Map<String, Pair<Integer, Integer>> requestLeaderboard() {
        return this.controller.requestLeaderboard();
    }
}
