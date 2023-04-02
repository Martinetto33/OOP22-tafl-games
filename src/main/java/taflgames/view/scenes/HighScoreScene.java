package taflgames.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
                
            }
            
        });
    }
    
}
