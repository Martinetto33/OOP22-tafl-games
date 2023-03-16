package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Optional;

import javax.swing.JPanel;

import taflgames.view.fontManager.FontManager;
import taflgames.view.scenecontrollers.HomeController;

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
    private static final int TITLE_SIZE = 90;
    private static final String PLAY = "Play";
    private static final String EXIT = "Exit";

    private final HomeController controller;

    /**
     * Creates the home scene.
     * @param controller the scene controller
     */
    public HomeScene(final HomeController controller) {

        super(HOME, Optional.of("home-background.jpeg"));

        this.controller = controller;

        final JPanel scene = super.getScene();

        final FontManager fontManager = new FontManager();

        final JPanel elementsPanel = new JPanel(new BorderLayout());
        elementsPanel.setBackground(new Color(0, 0, 0, 0));

        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel(GAME_TITLE);
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleLabel.setFont(fontManager.getModifiedFont(TITLE_SIZE, Font.PLAIN));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(0, 0, 0, 0));
        titlePanel.add(titleLabel);

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        final JButton playButton = new JButton(PLAY);
        playButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsPanel.add(playButton);
        final JButton exitButton = new JButton(EXIT);
        exitButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsPanel.setBackground(new Color(0, 0, 0, 0));
        buttonsPanel.add(exitButton);

        playButton.addActionListener((e) -> {
            this.controller.goToNextScene();
        });

        exitButton.addActionListener((e) -> this.controller.close());

        elementsPanel.add(titlePanel, BorderLayout.NORTH);
        elementsPanel.add(buttonsPanel, BorderLayout.CENTER);

        scene.add(elementsPanel);
    }

}
