package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Font;
import java.net.URL;
import java.util.Optional;

import javax.swing.JPanel;

import taflgames.view.View;
import taflgames.view.fontManager.FontManager;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

/**
 * Implementation of the home scene.
 */
public class HomeScene extends AbstractScene {

    private static final String HOME = "Home";
    private static final String GAME_TITLE = "TAFL GAMES";
    private static final int TITLE_SIZE = 90;
    private static final String PLAY = "Play";
    private static final String EXIT = "Exit";

    /**
     * Creates the home scene.
     * @param view the view that displays the scene
     */
    public HomeScene(final View view) {

        super(HOME, Optional.of("home-background.jpg"));

        final JPanel scene = super.getScene();
        scene.setLayout(new BoxLayout(scene, BoxLayout.Y_AXIS));

        final FontManager fontManager = new FontManager();

        final JPanel elementsPanel = new JPanel(new BorderLayout());

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel(GAME_TITLE);
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleLabel.setFont(fontManager.getModifiedFont(TITLE_SIZE, Font.PLAIN));
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

        elementsPanel.add(titlePanel, BorderLayout.NORTH);
        elementsPanel.add(buttonsPanel, BorderLayout.CENTER);

        scene.add(elementsPanel);
    }

}
