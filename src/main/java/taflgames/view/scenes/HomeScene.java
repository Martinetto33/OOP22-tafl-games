package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import taflgames.view.fontManager.FontManager;
import taflgames.view.scenecontrollers.HomeController;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Implementation of the home scene.
 */
public class HomeScene extends AbstractScene {

    private static final String HOME = "Home";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final String BG_FILENAME = "home-background.jpeg";
    private static final String GAME_TITLE = "TAFL GAMES";
    private static final int TITLE_SIZE = 100;
    private static final String PLAY = "Play";
    private static final String EXIT = "Exit";
    private static final double BTN_HEIGHT_PROP = 0.10;
    private static final double BTN_WIDTH_PROP = 0.45;
    private static final Insets DEFAULT_INSETS = new Insets(20, 20, 20, 20);

    private final HomeController controller;

    /**
     * Creates the home scene.
     * @param controller the scene controller
     */
    public HomeScene(final HomeController controller) {

        super(HOME, Optional.of(BG_FILENAME));

        this.controller = controller;

        final JPanel scene = super.getScene();
        scene.setLayout(new BorderLayout());
        scene.setBorder(new EmptyBorder(DEFAULT_INSETS));

        final GridBagConstraints gbc = new GridBagConstraints();
        final FontManager fontManager = new FontManager();

        final JPanel titlePanel = new JPanel();
        final JLabel titleLabel = new JLabel(GAME_TITLE);
        titleLabel.setFont(fontManager.getModifiedFont(TITLE_SIZE, Font.PLAIN));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setBackground(TRANSPARENT);
        titlePanel.add(titleLabel);

        final JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(TRANSPARENT);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = DEFAULT_INSETS;
        gbc.ipadx = (int) (this.controller.getViewWidth() * BTN_WIDTH_PROP);
        gbc.ipady = (int) (this.controller.getViewHeight() * BTN_HEIGHT_PROP);
        final JButton playButton = new JButton(PLAY);
        playButton.setFont(AbstractScene.getDefaultFont());
        buttonsPanel.add(playButton, gbc);
        final JButton exitButton = new JButton(EXIT);
        exitButton.setFont(AbstractScene.getDefaultFont());
        buttonsPanel.add(exitButton, gbc);

        playButton.addActionListener((e) -> {
            this.controller.goToNextScene();
        });

        exitButton.addActionListener((e) -> this.controller.close());
        
        scene.add(titlePanel, BorderLayout.NORTH);
        scene.add(buttonsPanel, BorderLayout.CENTER);
    }

}
