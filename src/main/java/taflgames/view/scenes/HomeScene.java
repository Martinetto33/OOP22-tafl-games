package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;

import taflgames.view.View;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Implementation of the home scene.
 */
public class HomeScene extends AbstractScene {

    private static final String HOME = "Home";
    private static final String GAME_TITLE = "TAFL GAMES";
    private static final String PLAY = "Play";
    private static final String EXIT = "Exit";

    /**
     * Creates the home scene.
     * @param view the view that displays the scene
     */
    public HomeScene(final View view) {

        super(HOME);

        final JPanel scenePanel = super.getScene();
        scenePanel.setLayout(new BoxLayout(scenePanel, BoxLayout.Y_AXIS));

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel(GAME_TITLE);
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 90));
        titlePanel.add(titleLabel);

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        final JButton playButton = new JButton(PLAY);
        playButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsPanel.add(playButton);
        final JButton exitButton = new JButton(EXIT);
        exitButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsPanel.add(exitButton);

        playButton.addActionListener((e) -> {
            view.setScene(new GameChoiceScene(view));
        });

        exitButton.addActionListener((e) -> view.close());

        scenePanel.add(titlePanel, BorderLayout.NORTH);
        scenePanel.add(buttonsPanel, BorderLayout.CENTER);
    }

}
